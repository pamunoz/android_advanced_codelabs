package com.pfariasmunoz.propertyanimation

import android.content.Context
import android.util.AttributeSet
import android.view.View

class PulseAnimationView(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    var radius: Float = 0.0f
        set(value) {
            field = value
            invalidate()
        }

}