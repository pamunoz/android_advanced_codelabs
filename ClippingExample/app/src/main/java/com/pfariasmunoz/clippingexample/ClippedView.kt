package com.pfariasmunoz.clippingexample

import android.content.Context
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
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

}