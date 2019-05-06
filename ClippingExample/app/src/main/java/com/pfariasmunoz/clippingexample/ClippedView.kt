package com.pfariasmunoz.clippingexample

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View

class ClippedView(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    private var mPaint: Paint? = null
    private var mPath: Path? = null
    private var mRectF: RectF? = null

    private val mClipRectRight = getDimen(R.dimen.clipRectRight)
    private val mClipRectBottom = getDimen(R.dimen.clipRectBottom)
    private val mClipRectTop = getDimen(R.dimen.clipRectTop)
    private val mClipRectLeft = getDimen(R.dimen.clipRectLeft)
    private val mRectInset = getDimen(R.dimen.rectInset)
    private val mSmallRectOffSet = getDimen(R.dimen.smallRectOffset)
    private val mCircleRadius = getDimen(R.dimen.circleRadius)
    private val mTextOffSet = getDimen(R.dimen.textOffset)
    private val mTextSize = getDimen(R.dimen.textSize)

    // convenience member variables for row and column coordinates so that you only have to calculate them once
    private val mColumnOne = mRectInset
    private val mColumnTwo = mColumnOne + mRectInset + mClipRectRight
    private val mRowOne = mRectInset
    private val mRowTwo = mRowOne + mRectInset + mClipRectBottom
    private val mRowThree = mRowTwo + mRectInset + mClipRectBottom
    private val mRowFour = mRowThree + mRectInset + mClipRectBottom
    private val mTextRow = mRowFour + (1.5 * mClipRectBottom).toInt()

    init {
        isFocusable = true
        mPaint = Paint().apply {
            isAntiAlias = true
            strokeWidth = getDimen(R.dimen.strokeWidth).toFloat()
            textSize = getDimen(R.dimen.textSize).toFloat()
        }
        mPath = Path()
        mRectF = RectF(Rect(mRectInset, mRectInset, mClipRectRight - mRectInset, mClipRectBottom - mRectInset))
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.apply {

            // FIRST RECTANGLE
            drawColor(Color.GRAY)
            // Save the drawing state of the canvas.
            save()
            // Translate the origin of the canvas to the top-left corner of the first rectangle.
            translate(mColumnOne.toFloat(), mRowOne.toFloat())
            // Call the drawClippedRectangle() method to draw the first rectangle.
            drawClippedRectangle(this)
            // Restore the previous state of the canvas.
            restore()

            // SECOND RECTANGLE
            // Draw a rectangle that uses the difference between two
            // clipping rectangles to create a picture frame effect.
            save()
            // Move the origin to the right for the next rectangle.
            translate(mColumnTwo.toFloat(), mRowOne.toFloat())
            // Use the subtraction of two clipping rectangles to create a frame.
            clipRect(2 * mRectInset, 2 * mRectInset, mClipRectRight - 2 * mRectInset, mClipRectBottom - 2 * mRectInset)
            // The method clipRect(float, float, float, float, Region.Op
            // .DIFFERENCE) was deprecated in API level 26. The recommended
            // alternative method is clipOutRect(float, float, float, float),
            // which is currently available in API level 26 and higher.
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                clipRect(
                    4.toFloat() * mRectInset,
                    4.toFloat() * mRectInset,
                    mClipRectRight - 4.toFloat() * mRectInset,
                    mClipRectBottom - 4.toFloat() * mRectInset, Region.Op.DIFFERENCE)
            } else {
                clipOutRect(
                    4.toFloat() * mRectInset,
                    4.toFloat() * mRectInset,
                    mClipRectRight.toFloat() - 4 * mRectInset,
                    mClipRectBottom.toFloat() - 4 * mRectInset)
            }
            drawClippedRectangle(this)
            restore()
        }
    }

    private fun drawClippedRectangle(canvas: Canvas) {
        canvas.apply {
            // Set the boundaries of the clipping rectangle for whole picture.
            clipRect(mClipRectLeft, mClipRectTop, mClipRectRight, mClipRectBottom)
            // Fill the canvas with white.
            // With the clipped rectangle, this only draws inside the clipping rectangle.
            // The rest of the surface remains gray.
            drawColor(Color.WHITE)

            // Change the color to red and
            // draw a line inside the clipping rectangle.
            mPaint?.color = Color.RED
            drawLine(mClipRectLeft.toFloat(), mClipRectTop.toFloat(), mClipRectRight.toFloat(), mClipRectBottom.toFloat(), mPaint!!)

            // Set the color to green and
            // draw a circle inside the clipping rectangle.
            mPaint?.color = Color.GREEN
            drawCircle(mCircleRadius.toFloat(), (mClipRectBottom - mCircleRadius).toFloat(), mCircleRadius.toFloat(), mPaint!!)

            // Set the color to blue and draw text aligned with the right edge
            // of the clipping rectangle.
            mPaint?.color = Color.BLUE
            // Align the RIGHT side of the text with the origin.
            mPaint?.textAlign = Paint.Align.RIGHT
            drawText(context.getString(R.string.clipping), mClipRectRight.toFloat(), mTextOffSet.toFloat(), mPaint!!)
        }
    }

}