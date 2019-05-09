package com.pfariasmunoz.propertyanimation

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

class PulseAnimationView(context: Context?, attrs: AttributeSet? = null) : View(context, attrs) {

    var radius: Float = 0.0f
        set(value) {
            field = value
            // adjust the color
            mPaint.color = Color.GREEN + radius.toInt() / COLOR_ADJUSTER
            invalidate()
        }
    private val mPaint: Paint = Paint()
    private var mXpos: Float = 0f
    private var mYpos: Float = 0f
    private val mPulseAnimatorSet: AnimatorSet = AnimatorSet()

    companion object {
        const val COLOR_ADJUSTER = 5
        const val ANIMATION_DURATION = 4000
        const val ANIMATION_DELAY = 1000L
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.actionMasked == MotionEvent.ACTION_DOWN) {
            mXpos = event.x
            mYpos = event.y
        }
        return super.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val growAnimator = ObjectAnimator.ofFloat(this,"radius", 0f, width.toFloat()).apply {
            duration = ANIMATION_DURATION.toLong()
            interpolator = LinearInterpolator()
        }
        val shrinkAnimator = ObjectAnimator.ofFloat(this,"radius", width.toFloat(), 0f).apply {
            duration = ANIMATION_DURATION.toLong()
            interpolator = LinearOutSlowInInterpolator()
            startDelay = ANIMATION_DELAY
        }
        val repeatAnimator = ObjectAnimator.ofFloat(this,"radius", 0f, width.toFloat()).apply {
            duration = ANIMATION_DURATION.toLong()
            startDelay = ANIMATION_DELAY
            repeatCount = 1
            repeatMode = ValueAnimator.REVERSE
        }
    }

}