package com.example.ardogame

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var usbManager: USBmanager
    private lateinit var adapter: InAdapter
    private val lines = mutableListOf<InItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvIN = findViewById<RecyclerView>(R.id.rvIN)
        val btREAD = findViewById<Button>(R.id.btREAD)
        val btGAME = findViewById<Button>(R.id.btGAME) // Assuming you have a button with this id

        usbManager = USBmanager(this)

        lines.add(InItem("THE START OF THE INPUT LOG : "))
        adapter = InAdapter(lines)
        rvIN.adapter = adapter
        rvIN.layoutManager = LinearLayoutManager(this)

        btREAD.setOnClickListener {
            try {
            usbManager.connect { isConnected, message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                if (isConnected) {
                    usbManager.startReading { data ->
                            lines.add(InItem(data))
                            adapter.notifyItemInserted(lines.size - 1)
                            rvIN.scrollToPosition(lines.size - 1)
                        }
                    }
                }
            }catch(e: Exception){
                    // Display the crash message in a long toast
                    Toast.makeText(this, "ERROR: ${e.javaClass.simpleName}: ${e.message}", Toast.LENGTH_LONG).show()
                    lines.add(InItem("ERROR: ${e.javaClass.simpleName}: ${e.message}"))
                    adapter.notifyItemInserted(lines.size - 1)
                    rvIN.scrollToPosition(lines.size - 1)

            }
            }

        
        btGAME.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        usbManager.disconnect()
    }
}