package com.pfariasmunoz.customfancontroller

import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

class DialView : View {

    private val SELECTION_COUNT = 4 // Total number of selections.
    private val mWidth: Float = 0.toFloat()                   // Custom view width.
    private val mHeight: Float = 0.toFloat()                  // Custom view height.
    private val mTextPaint: Paint? = null               // For text in the view.
    private val mDialPaint: Paint? = null               // For dial circle in the view.
    private val mRadius: Float = 0.toFloat()                  // Radius of the circle.
    private val mActiveSelection: Int = 0           // The active selection.
    // String buffer for dial labels and float for ComputeXY result.
    private val mTempLabel = StringBuffer(8)
    private val mTempResult = FloatArray(2)


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )
}