package com.example.ardogame

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

// Added a rotation property
data class Obstacle(
    var x: Float,
    var y: Float,
    val w: Float,
    val h: Float,
    val texture: Bitmap?,
    val rotation: Float = 0f
) {
    fun draw(canvas: Canvas) {
        texture?.let {
            canvas.save()
            // Move the canvas to the center of the obstacle for rotation
            canvas.translate(x + w / 2, y + h / 2)
            // Rotate the canvas by the obstacle's angle
            canvas.rotate(rotation)
            // Draw the texture, but offset it by half its width/height because we moved the canvas
            canvas.drawBitmap(it, -w / 2, -h / 2, null)
            canvas.restore()
        }
    }
}