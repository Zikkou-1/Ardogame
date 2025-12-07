//package com.example.ardogame
//
//import android.content.Context
//import android.hardware.usb.UsbManager
//import android.util.Log
//import android.os.Bundle
//import android.widget.Button
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.lifecycleScope
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.hoho.android.usbserial.driver.UsbSerialPort
//import com.hoho.android.usbserial.driver.UsbSerialProber
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//import java.io.IOException
//
//class MainActivityMock : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val rvIN = findViewById<RecyclerView>(R.id.rvIN)
//        val btREAD = findViewById<Button>(R.id.btREAD)
//        Log.d("LIST_DEBUG", "onCreate() called")
//
//        val lines = mutableListOf<InItem>(
//            InItem("THE START OF THE INPUT LOG : ")
//        )
//
//        val adapter = InAdapter(lines)
//        rvIN.adapter = adapter
//        rvIN.layoutManager = LinearLayoutManager(this)
//
//        btREAD.setOnClickListener {
//            lifecycleScope.launch(Dispatchers.IO) {
//                //val usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
//                //val availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
//                val usbManager = MockSerialPort()
//                //val deviceList = usbManager.deviceList
//
//                //Log.d("USB_DEBUG", "Total USB devices found: ${deviceList.size}")
//
//                //for ((name, device) in deviceList) {
//                //    Log.d("USB_DEBUG", "Device found: $name")
//                //    Log.d("USB_DEBUG", "VendorID: ${device.vendorId}, ProductID: ${device.productId}")
//                //}
//
//                //if (availableDrivers.isEmpty()) return@launch
//
//                //val driver = availableDrivers.find { it.device.vendorId == 0x2341 } ?: availableDrivers.first()
//                //val connection = usbManager.openDevice(driver.device) ?: return@launch
//                //val port = driver.ports[0]
//
//                try {
//                    val reading : Boolean = true
//                    //port.open(connection)
//                    //port.setParameters(9600, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
//
//                    val buffer = ByteArray(1024)
//
//                    while (reading) {
//                        //val numBytesRead = port.read(buffer, 500)
//                        //if (numBytesRead > 0) {
//                        //val readData = String(buffer, 0, numBytesRead).trim()
//                        val readData = usbManager.read()
//                        withContext(Dispatchers.Main) {
//                            lines.add(InItem(readData))
//                            adapter.notifyItemInserted(lines.size - 1)
//                            adapter.notifyDataSetChanged()
//                            Log.d("LIST_DEBUG", "notifyDataSetChanged() called")
//
//
//                            rvIN.scrollToPosition(lines.size - 1)
//                        }
//                    }
//                }
//                //} //catch (e: IOException) {
//                //  withContext(Dispatchers.Main) {
//                //      Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//                //  }
//                //}
//                finally {
//                    //if (port.isOpen) port.close()
//                }
//            }
//        }
//
//
//    }
//}