package com.pfariasmunoz.propertyanimation

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PulseAnimationView(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    var radius: Float = 0.0f
        set(value) {
            field = value
            // adjust the color
            mPaint.color = Color.GREEN + radius.toInt() / COLOR_ADJUSTER
            invalidate()
        }
    private val mPaint: Paint = Paint()

    companion object {
        const val COLOR_ADJUSTER = 5
    }

}