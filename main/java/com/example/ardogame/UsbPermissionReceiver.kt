package com.example.ardogame

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbManager
import android.widget.Toast

class UsbPermissionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (ACTION_USB_PERMISSION == intent.action) {
            synchronized(this) {
                val granted = intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)
                if (granted) {
                    Toast.makeText(context, "USB permission granted. You can now read from the device.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "USB permission denied.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val ACTION_USB_PERMISSION = "com.example.ardogame.USB_PERMISSION"
    }
}
