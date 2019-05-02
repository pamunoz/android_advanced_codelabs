package com.pfariasmunoz.simplecanvas

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat

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
}
