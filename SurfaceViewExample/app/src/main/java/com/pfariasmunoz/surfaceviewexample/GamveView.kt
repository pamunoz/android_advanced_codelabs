package com.pfariasmunoz.surfaceviewexample

import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.SurfaceView

/**
 * A custom SurfaceView where game play takes place. Responds to motion events on the screen.
 * Draws the game screen in a separate thread, with the flashlight cone at the current position
 * of the user's finger. Shows the "win" message when winning conditions are met.
 */
class GamveView(context: Context?) : SurfaceView(context), Runnable {
    var mPaint: Paint? = null
    var mPath: Path? = null
    var mBitmapX: Int = 0
    var mBitmapY: Int = 0
    var mWinnerRectF: RectF? = null
    var mViewWidth: Int = 0
    var mViewHeight: Int = 0
    var mBitmap: Bitmap? = null
    var mRunning: Boolean = true
    var mGameThread: Thread? = null
    var mFlashlightCone: FlashlightCone? = null

    override fun run() {
        var canvas: Canvas
        while (mRunning) {
            // Check whether there is a valid Surface available for drawing. If not, do nothing.
            if (holder.surface.isValid) {
                val x = mFlashlightCone?.x
                val y = mFlashlightCone?.y
                val radius = mFlashlightCone?.radius
                try {
                    canvas = holder.lockCanvas()
                    canvas.apply {
                        save()
                        drawColor(Color.WHITE)
                        drawBitmap(mBitmap!!, mBitmapX.toFloat(), mBitmapY.toFloat(), mPaint)
                    }
                    // Set the circle as the clipping path using the DIFFERENCE operator, so that's what's inside the circle is clipped (not drawn).
                    mPath?.addCircle(x?.toFloat()!!, y?.toFloat()!!, radius?.toFloat()!!, Path.Direction.CCW)
                    // The method clipPath(path, Region.Op.DIFFERENCE) was
                    // deprecated in API level 26. The recommended alternative
                    // method is clipOutPath(Path), which is currently available
                    // in API level 26 and higher.
                    if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                        canvas.clipPath(mPath!!, Region.Op.DIFFERENCE)
                    } else {
                        canvas.clipOutPath(mPath!!)
                    }
                    canvas.drawColor(Color.BLACK)

                }
            }
        }
    }

    fun pause() {
        mRunning = false
        try {
            // Stop the thread (rejoin the main thread)
            mGameThread?.join()
        } catch (e: InterruptedException) {

        }

    }

    fun resume() {
        mRunning = true
        mGameThread = Thread(this)
        mGameThread?.start()
    }

    init {
        mPaint = Paint().apply {
            color = Color.DKGRAY
        }
        mPath = Path()
    }

    /**
     * calculates a random location on the screen for the Android image that the user has to find.
     * You also need a way to calculate whether the user has found the bitmap.
     */
    private fun setUpBitmap() {
        // Set mBitmapX and mBitmapY to random x and y positions that fall inside the screen.
        mBitmapX = (Math.floor(Math.random() * (mViewWidth - mBitmap?.width!!)).toInt())
        mBitmapY = (Math.floor(Math.random() * (mViewHeight - mBitmap?.height!!)).toInt())
        // Define a rectangular bounding box that contains the Android image.
        mWinnerRectF = RectF(
            mBitmapX.toFloat(),
            mBitmapY.toFloat(),
            (mBitmapX + mBitmap?.width!!).toFloat(),
            (mBitmapY + mBitmap?.height!!).toFloat()
        )
    }

    /**
     *  is called every time the view changes.The view starts out with 0 dimensions.
     *  When the view is first inflated, its size changes and onSizeChangedMethod()
     *  is called. Unlike in onCreate(), the view's correct dimensions are available.
     */
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(width, height, oldWidth, oldHeight)
        mViewWidth = width
        mViewHeight = height
        mFlashlightCone = FlashlightCone(mViewWidth, mViewHeight)
        mPaint?.textSize = mViewHeight.toFloat() / 5
        mBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.android)
        setUpBitmap()
    }

}