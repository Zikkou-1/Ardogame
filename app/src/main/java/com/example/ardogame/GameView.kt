package com.example.ardogame

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.media.AudioAttributes
import android.media.SoundPool
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class GameView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private val scorePaint = Paint().apply {
        color = Color.WHITE
        textSize = 80f
        isAntiAlias = true
        textAlign = Paint.Align.RIGHT
    }

    private val gameOverPaint = Paint().apply {
        color = Color.WHITE
        textSize = 150f
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        setShadowLayer(10f, 0f, 0f, Color.RED)
    }

    private val restartPaint = Paint(gameOverPaint).apply {
        textSize = 80f
        setShadowLayer(5f, 0f, 0f, Color.RED)
    }

    // --- Game State ---
    private var isGameOver = false
    private var isExploding = false
    private var startTime: Long = 0
    private var xPos = 0f
    private var yPos = 200f // Y position remains constant

    // --- Sound Effects ---
    private val soundPool: SoundPool
    private var explosionSoundId: Int = 0

    // --- Background Animation --- //
    private val backgroundResIds = listOf(R.drawable.bg0, R.drawable.bg1, R.drawable.bg2, R.drawable.bg3, R.drawable.bg4, R.drawable.bg5, R.drawable.bg6, R.drawable.bg7, R.drawable.bg8, R.drawable.bg9, R.drawable.bg10, R.drawable.bg11, R.drawable.bg12, R.drawable.bg13, R.drawable.bg14, R.drawable.bg15, R.drawable.bg16, R.drawable.bg17, R.drawable.bg18, R.drawable.bg19, R.drawable.bg20, R.drawable.bg21, R.drawable.bg22, R.drawable.bg23, R.drawable.bg24, R.drawable.bg25, R.drawable.bg26, R.drawable.bg27, R.drawable.bg28, R.drawable.bg29, R.drawable.bg30, R.drawable.bg31, R.drawable.bg32, R.drawable.bg33, R.drawable.bg34, R.drawable.bg35, R.drawable.bg36, R.drawable.bg37, R.drawable.bg38, R.drawable.bg39, R.drawable.bg40, R.drawable.bg41, R.drawable.bg42, R.drawable.bg43, R.drawable.bg44, R.drawable.bg45, R.drawable.bg46)
    private val backgroundBitmaps: List<Bitmap> by lazy { backgroundResIds.map { BitmapFactory.decodeResource(resources, it) } }
    private val scaledBackgroundBitmaps = mutableListOf<Bitmap>()
    private var backgroundFrame = 0
    private var lastFrameChangeTime = 0L
    private val frameDuration = 100L

    // --- Player Animation --- //
    private val playerResIds = listOf(R.drawable.player1, R.drawable.player1, R.drawable.player1, R.drawable.player1, R.drawable.player1, R.drawable.player2, R.drawable.player3, R.drawable.player3, R.drawable.player2)
    private val playerFrames: List<Bitmap> by lazy { playerResIds.map { BitmapFactory.decodeResource(resources, it).let { bmp -> Bitmap.createScaledBitmap(bmp, 150, 115, false) } } }
    private var playerFrame = 0
    private var lastPlayerFrameChangeTime = 0L
    private val playerFrameDuration = 150L

    // --- Explosion Animation ---
    private val explosionResIds = listOf(R.drawable.explosion1, R.drawable.explosion2, R.drawable.explosion3, R.drawable.explosion4, R.drawable.explosion5, R.drawable.explosion6, R.drawable.explosion7, R.drawable.explosion8, R.drawable.explosion9, R.drawable.explosion10)
    private val explosionFrames: List<Bitmap> by lazy { explosionResIds.map { BitmapFactory.decodeResource(resources, it).let { bmp -> Bitmap.createScaledBitmap(bmp, 300, 300, false) } } }
    private var explosionFrame = 0
    private var lastExplosionFrameChangeTime = 0L
    private val explosionFrameDuration = 80L
    private var explosionX = 0f
    private var explosionY = 0f

    // --- Obstacles --- //
    private var obstacleSpeed: Float = 8f
    private val obstacles = ArrayList<Obstacle>()
    private val obstacleBitmap: Bitmap by lazy { BitmapFactory.decodeResource(resources, R.drawable.obstacle) }
    private val scaledObstacleBitmap: Bitmap by lazy { Bitmap.createScaledBitmap(obstacleBitmap, 38 * 3, 33 * 3, false) }

    init {
        val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()
        soundPool = SoundPool.Builder().setMaxStreams(2).setAudioAttributes(audioAttributes).build()
        explosionSoundId = soundPool.load(context, R.raw.explosion_sfx, 1)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        xPos = w / 2f
        // Pre-scale background images once when the size is known
        scaledBackgroundBitmaps.clear()
        val matrix = Matrix().apply { postScale(1f, -1f) } // Vertical flip matrix
        for (bitmap in backgroundBitmaps) {
            val scaled = Bitmap.createScaledBitmap(bitmap, w, h, true)
            scaledBackgroundBitmaps.add(Bitmap.createBitmap(scaled, 0, 0, scaled.width, scaled.height, matrix, true))
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val currentTime = System.currentTimeMillis()

        // --- Animate and Draw Background --- //
        if (currentTime > lastFrameChangeTime + frameDuration) {
            backgroundFrame = (backgroundFrame + 1) % scaledBackgroundBitmaps.size
            lastFrameChangeTime = currentTime
        }
        if (scaledBackgroundBitmaps.isNotEmpty()) {
            canvas.drawBitmap(scaledBackgroundBitmaps[backgroundFrame], 0f, 0f, null)
        }

        if (!isGameOver) {
            // --- Game is running ---
            if (startTime == 0L) startTime = currentTime

            val elapsedTimeSeconds = (currentTime - startTime) / 1000f
            obstacleSpeed = 8f + (elapsedTimeSeconds * 0.3f)
            val score = elapsedTimeSeconds.toInt()

            if (currentTime > lastPlayerFrameChangeTime + playerFrameDuration) {
                playerFrame = (playerFrame + 1) % playerFrames.size
                lastPlayerFrameChangeTime = currentTime
            }

            canvas.save()
            val scoreX = width /2f
            val scoreY = height - 150f
            canvas.rotate(180f, scoreX, scoreY)
            canvas.drawText("Score: $score", scoreX, scoreY, scorePaint)
            canvas.restore()

            if (width > 0 && (obstacles.isEmpty() || (obstacles.size < 5 && obstacles.last().y < height - 350))) {
                obstacles.add(Obstacle(
                    x = Random.nextInt(0, width - scaledObstacleBitmap.width).toFloat(),
                    y = height.toFloat(),
                    w = scaledObstacleBitmap.width.toFloat(),
                    h = scaledObstacleBitmap.height.toFloat(),
                    texture = scaledObstacleBitmap,
                    rotation = Random.nextInt(0, 360).toFloat()
                ))
            }

            val playerRect = getCurrentPlayerRect()

            val iterator = obstacles.iterator()
            while (iterator.hasNext()) {
                val obstacle = iterator.next()
                obstacle.y -= obstacleSpeed
                obstacle.draw(canvas)

                if (checkCollision(playerRect, obstacle)) {
                    isGameOver = true
                    isExploding = true
                    explosionX = xPos
                    explosionY = yPos
                    if(explosionSoundId != 0) soundPool.play(explosionSoundId, 1f, 1f, 1, 0, 1f)
                }

                if (obstacle.y + obstacle.h < 0) {
                    iterator.remove()
                }
            }

            if (playerFrames.isNotEmpty()) {
                val currentFrame = playerFrames[playerFrame]
                canvas.drawBitmap(currentFrame, xPos - currentFrame.width / 2, yPos - currentFrame.height / 2, null)
            }

        } else if (isExploding) {
            // --- Explosion animation is running ---
            obstacles.forEach { it.draw(canvas) }

            if (currentTime > lastExplosionFrameChangeTime + explosionFrameDuration) {
                explosionFrame++
                lastExplosionFrameChangeTime = currentTime
            }

            if (explosionFrame < explosionFrames.size) {
                val currentExplosionFrame = explosionFrames[explosionFrame]
                canvas.drawBitmap(currentExplosionFrame, explosionX - currentExplosionFrame.width / 2, explosionY - currentExplosionFrame.height / 2, null)
            } else {
                isExploding = false
            }

        } else {
            // --- Game is over screen ---
            canvas.drawColor(Color.argb(150, 0, 0, 0))

            canvas.save()
            canvas.translate(width / 2f, height / 2f)
            canvas.rotate(180f)

            val pulse = sin(currentTime / 200.0) * 10 + 150
            gameOverPaint.textSize = pulse.toFloat()
            canvas.drawText("Game Over", 0f, 100f, gameOverPaint)
            canvas.drawText("Tap to Restart", 0f, -150f, restartPaint)

            canvas.restore()
        }

        invalidate()
    }

    private fun checkCollision(playerRect: RectF, obstacle: Obstacle): Boolean {
        val corners = arrayOf(PointF(obstacle.x, obstacle.y), PointF(obstacle.x + obstacle.w, obstacle.y), PointF(obstacle.x, obstacle.y + obstacle.h), PointF(obstacle.x + obstacle.w, obstacle.y + obstacle.h))
        val centerX = obstacle.x + obstacle.w / 2
        val centerY = obstacle.y + obstacle.h / 2
        val angleRad = Math.toRadians(obstacle.rotation.toDouble())
        val cosAngle = cos(angleRad).toFloat()
        val sinAngle = sin(angleRad).toFloat()

        for (corner in corners) {
            val translatedX = corner.x - centerX
            val translatedY = corner.y - centerY
            val rotatedX = translatedX * cosAngle - translatedY * sinAngle
            val rotatedY = translatedX * sinAngle + translatedY * cosAngle
            val finalX = rotatedX + centerX
            val finalY = rotatedY + centerY
            if (playerRect.contains(finalX, finalY)) return true
        }
        return false
    }

    private fun getCurrentPlayerRect(): RectF {
        if (playerFrames.isEmpty()) return RectF()
        val currentFrame = playerFrames[playerFrame]
        return RectF(xPos - currentFrame.width / 2f, yPos - currentFrame.height / 2f, xPos + currentFrame.width / 2f, yPos + currentFrame.height / 2f)
    }

    fun updateXPosition(x: Float) {
        if (!isGameOver && width > 0 && x >= 2 && x <= 22) {
            xPos = (1 - ((x-2)/22  ))* width
            invalidate()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (isGameOver && !isExploding && event?.action == MotionEvent.ACTION_DOWN) {
            restartGame()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun restartGame() {
        obstacles.clear()
        startTime = 0L
        xPos = width / 2f
        isGameOver = false
        isExploding = false
        explosionFrame = 0
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        soundPool.release()
    }
}
