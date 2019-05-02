package com.pfariasmunoz.customfancontroller

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DialView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    /** The SELECTION_COUNT defines the total number of selections for this custom view.
     * The code is designed so that you can change this value to create a control with more or fewer selections. */
    private val SELECTION_COUNT = 4 // Total number of selections.
    private var mWidth: Float = 0.toFloat()                   // Custom view width.
    private var mHeight: Float = 0.toFloat()                  // Custom view height.
    private var mTextPaint: Paint            // For text in the view.
    private var mDialPaint: Paint           // For dial circle in the view.
    private var mRadius: Float = 0.toFloat()                  // Radius of the circle.
    private var mActiveSelection: Int   // The active selection.
    // String buffer for dial labels and float for ComputeXY result.
    /** The mTempLabel and mTempResult member variables provide temporary storage for the result of
     * calculations, and are used to reduce the memory allocations while drawing. */
    private val mTempLabel = StringBuffer(8)
    private val mTempResult = FloatArray(2)
    // Set default fan on and fan off colors
    var mFanOnColor = 0
    var mFanOffColor = 0

    init {
        // set the default fan colors
        mFanOnColor = Color.CYAN
        mFanOffColor = Color.GRAY
        mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.FILL_AND_STROKE
            textAlign = Paint.Align.CENTER
            textSize = 40f
        }
        mDialPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = mFanOffColor
        }
        // Initialize current selection
        mActiveSelection = 0
        // Set up onClick listener for this view.
        setOnClickListener {
            // Rotate the selection to the next valid choice
            mActiveSelection = (mActiveSelection + 1) % SELECTION_COUNT
            // Set dial background color if selection is >= 1
            mDialPaint.color = if (mActiveSelection >= 1) Color.GREEN else Color.GRAY
            // redraw the view
            invalidate()
        }
        // Get the custom attributes (fanOnColor and fanOffColor) if available.

    }

    /**
     * The onSizeChanged() method is called when the layout is inflated and when the view has changed.
     * Its parameters are the current width and height of the view, and the "old" (previous) width and height.
     */
    override fun onSizeChanged(currentWith: Int, currentHeight: Int, oldWidth: Int, oldHeight: Int) {
        // Calculate the radius from with and height
        mWidth = currentWith.toFloat()
        mHeight = currentHeight.toFloat()
        mRadius = (Math.min(mWidth, mHeight) / 2f * 0.6f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        // Draw the dial.
        canvas?.drawCircle(mWidth / 2, mHeight / 2, mRadius, mDialPaint)

        // Draw the text labels.
        val labelRadius = mRadius + 20f
        val label = mTempLabel
        for (i in 0 until SELECTION_COUNT) {
            val (x, y) = computeXYForPosition(i, labelRadius)
            label.apply {
                setLength(0)
                append(i)
            }
            canvas?.drawText(label, 0, label.length, x, y, mTextPaint)
        }

        // Draw the indicator mark
        val markerRadius = mRadius - 35
        val (x, y) = computeXYForPosition(mActiveSelection, markerRadius)
        canvas?.drawCircle(x, y, 20f, mTextPaint)
    }


    /**
     * compute the X and Y coordinates for the text label and indicator (0, 1, 2, or 3) of the chosen selection,
     * given the position number and radius:
     *
     *  The pos parameter is a position index (starting at 0). The radius parameter is for the outer circle.

    You will use the computeXYForPosition() method in the onDraw() method. It returns a
    two-element array for the position, in which element 0 is the X coordinate, and element 1 is the Y coordinate.
     */
    private fun computeXYForPosition(pos: Int, radius: Float): FloatArray {
        val result = mTempResult
        val startAngle: Double = Math.PI * (9 / 8.toDouble()) // Angles are in radians
        val angle: Double = startAngle + (pos * (Math.PI / 4))
        result[0] = (radius * Math.cos(angle)).toFloat() + (mWidth / 2)
        result[1] = (radius * Math.sin(angle)).toFloat() + (mHeight / 2)
        return result
    }
}