package com.example.ardogame

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.Build
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import kotlinx.coroutines.*
import java.io.IOException

// Restored to a simple class with a public constructor
class USBmanager(private val context: Context) {

    private val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
    private var port: UsbSerialPort? = null
    private var connection: UsbDeviceConnection? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private var readJob: Job? = null

    private companion object {
        const val ACTION_USB_PERMISSION = "com.example.ardogame.USB_PERMISSION"
        private const val READ_WAIT_MILLIS = 250 // Int, not Long
    }

    fun connect(callback: (Boolean, String) -> Unit) {
        val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
        if (availableDrivers.isEmpty()) {
            callback(false, "No USB devices found")
            return
        }

        val driver = availableDrivers.find { it.device.vendorId == 0x2341 } ?: availableDrivers.first()

        if (!usbManager.hasPermission(driver.device)) {
            val permissionReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    context.unregisterReceiver(this)
                    if (intent.action == ACTION_USB_PERMISSION) {
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            connectToDevice(driver.device, callback)
                        } else {
                            callback(false, "Permission denied")
                        }
                    }
                }
            }

            // ** THE CRITICAL FIX APPLIED TO THE CORRECT, SIMPLER STRUCTURE **
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(permissionReceiver, IntentFilter(ACTION_USB_PERMISSION), Context.RECEIVER_NOT_EXPORTED)
            } else {
                context.registerReceiver(permissionReceiver, IntentFilter(ACTION_USB_PERMISSION))
            }

            val permissionIntent = PendingIntent.getBroadcast(context, 0, Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_IMMUTABLE)
            usbManager.requestPermission(driver.device, permissionIntent)
            return
        }
        connectToDevice(driver.device, callback)
    }

    private fun connectToDevice(device: UsbDevice, callback: (Boolean, String) -> Unit) {
        if (port?.isOpen == true) {
            callback(true, "Already connected")
            return
        }
        try {
            val driver = UsbSerialProber.getDefaultProber().probeDevice(device)!!
            connection = usbManager.openDevice(device)
            port = driver.ports[0]
            port?.open(connection)
            port?.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
            callback(true, "Connected successfully")
        } catch (e: Exception) {
            disconnect()
            callback(false, "Error opening port: ${e.message}")
        }
    }

    fun startReading(onDataReceived: (String) -> Unit) {
        if (port?.isOpen != true || readJob?.isActive == true) return

        readJob = scope.launch {
            val lineBuffer = StringBuilder()
            while (isActive) {
                try {
                    val buffer = ByteArray(256)
                    val numBytesRead = port?.read(buffer, READ_WAIT_MILLIS) ?: 0
                    if (numBytesRead > 0) {
                        lineBuffer.append(String(buffer, 0, numBytesRead))
                        var newlineIndex = lineBuffer.indexOf('\n')
                        while (newlineIndex != -1) {
                            val line = lineBuffer.substring(0, newlineIndex).trim()
                            lineBuffer.delete(0, newlineIndex + 1)
                            if(line.isNotEmpty()) {
                                withContext(Dispatchers.Main) { onDataReceived(line) }
                            }
                            newlineIndex = lineBuffer.indexOf('\n')
                        }
                    }
                } catch (e: IOException) {
                    disconnect()
                    break
                }
                delay(10)
            }
        }
    }

    fun disconnect() {
        readJob?.cancel()
        readJob = null
        try { port?.close() } catch (ignored: IOException) {}
        port = null
        connection = null
    }
}
