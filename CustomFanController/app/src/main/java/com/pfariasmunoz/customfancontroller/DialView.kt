package com.pfariasmunoz.customfancontroller

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi

class DialView : View {

    /** The SELECTION_COUNT defines the total number of selections for this custom view.
     * The code is designed so that you can change this value to create a control with more or fewer selections. */
    private val SELECTION_COUNT = 4 // Total number of selections.
    private val mWidth: Float = 0.toFloat()                   // Custom view width.
    private val mHeight: Float = 0.toFloat()                  // Custom view height.
    private var mTextPaint: Paint? = null               // For text in the view.
    private var mDialPaint: Paint? = null               // For dial circle in the view.
    private val mRadius: Float = 0.toFloat()                  // Radius of the circle.
    private val mActiveSelection: Int   // The active selection.
    // String buffer for dial labels and float for ComputeXY result.
    /** The mTempLabel and mTempResult member variables provide temporary storage for the result of
     * calculations, and are used to reduce the memory allocations while drawing. */
    private val mTempLabel = StringBuffer(8)
    private val mTempResult = FloatArray(2)

    init {
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.FILL_AND_STROKE
            textAlign = Paint.Align.CENTER
            textSize = 40f
        }
        mDialPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.GRAY
        }
        // Initialize current selection
        mActiveSelection = 0
        // TODO: Set up onClick listener for this view.
    }


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