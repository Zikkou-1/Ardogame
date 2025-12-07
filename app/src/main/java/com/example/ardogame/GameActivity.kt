package com.example.ardogame

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GameActivity : AppCompatActivity() {

    private lateinit var usbManager: USBmanager
    private lateinit var gameView: GameView
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_view)

        gameView = findViewById(R.id.gameView)
        usbManager = USBmanager(this)

        // --- Start Background Music ---
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.soundtrack)
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
        } catch (e: Exception) {
            Toast.makeText(this, "Error playing music: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        // ------------------------------

        usbManager.connect { isConnected, message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (isConnected) {
                usbManager.startReading { data ->
                    val x = data.trim().toFloatOrNull()
                    if (x != null) {
                        gameView.updateXPosition(x)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Pause music when the activity is not in the foreground
        mediaPlayer?.pause()
    }

    override fun onResume() {
        super.onResume()
        // Resume music if it was paused
        mediaPlayer?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        usbManager.disconnect()

        // --- Release MediaPlayer ---
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        // ---------------------------
    }
}
