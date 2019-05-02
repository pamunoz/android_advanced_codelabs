package com.pfariasmunoz.simplecanvas

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private val mPaint: Paint = Paint()
    private val mPaintText: Paint = Paint(Paint.UNDERLINE_TEXT_FLAG)
    private val mRect: Rect = Rect()
    private val mBounds: Rect = Rect()

    private var mCanvas: Canvas? = null
    private var mBitmap: Bitmap? = null
    private var mOffset = OFFSET
    private var mColorBackground: Int = 0
    private var mColorRectangle: Int = 0
    private var mColorAccent: Int = 0

    companion object {
        /** This offset is the distance of a rectangle you draw from the edge of the canvas. */
        const val OFFSET = 120
        /** You will need this constant later, for generating random colors. */
        const val MULTIPLIER = 100
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mColorBackground = getResColor(R.color.colorBackground)
        mColorRectangle = getResColor(R.color.colorRectangle)
        mColorAccent = getResColor(R.color.colorAccent)

        mPaint.color = mColorBackground
        mPaintText.apply {
            color = this@MainActivity.getResColor(R.color.colorPrimaryDark)
            textSize = 70f
        }
    }

    fun drawSomething(view: View) {
        val currentWidth = view.width
        val currentHeight = view.height
        val halfWidth = currentWidth / 2f
        val halfHeight = currentHeight / 2f
        /*
        mOffset == OFFSET. The app is only in this state the first time the user taps.
        Create the Bitmap, associate it with the View, create the Canvas, fill the
        background, and draw some text. Increase the offset.
         */
        if (mOffset == OFFSET) {
            /*
            create a Bitmap.
            Supply the width and height for the bitmap, which are going to be the same
            as the width and height of the view.
            Pass in a Bitmap.config configuration object. A bitmap configuration
            describes how pixels are stored. How pixels are stored affects the
            quality (color depth) as well as the ability to display transparent/translucent
            colors. The ARGB_8888 format supports Alpha, Red, Green, and Blue channels for
            each pixel. Each color is encoded in 8 bits, for a total of 4 bytes per pixel.

             */
            mBitmap = Bitmap.createBitmap(currentWidth, currentHeight, Bitmap.Config.ARGB_8888)
            img_container.setImageBitmap(mBitmap)
            mCanvas = Canvas(mBitmap).apply {
                drawColor(mColorBackground)
                drawText(getString(R.string.keep_tapping), 100f, 100f, mPaintText)
                mOffset += OFFSET
            }

        } else {
            /*
            mOffset != OFFSET and the offset is smaller than half the screen width and height.
            Draw a rectangle with a computed color and increase the offset.
             */
            if (mOffset < halfWidth && mOffset < halfHeight) {

            } else {
                /*
                mOffset != OFFSET and the offset is equal to or larger than half
                the screen width and height. Draw a circle with the text "Done!".
                 */

            }
        }
        view.invalidate()
    }
}
