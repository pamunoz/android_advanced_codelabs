package com.pfariasmunoz.customedittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

class EditTextWithClear : AppCompatEditText {

    private var mClearButtonImage: Drawable?

    init {
        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_opaque_24dp, null)
        // If the text changes, show or hide the clear (X) button.
        addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                showClearButton()
            }

        })
        // If the clear (X) button is tapped, clear the text.
        setOnTouchListener { v, event ->
            compoundDrawablesRelative[2]?.let {
                var clearButtonStart: Float // Used for LTR languages
                var clearButtonEnd: Float // Used for RTL languages
                var isClearButtonClick = false
                // Detect the touch in RTL or LTR layout direction.
                if (layoutDirection == View.LAYOUT_DIRECTION_RTL) {
                    // If RTL, get the end of the button on the left side.
                    clearButtonEnd = mClearButtonImage?.intrinsicWidth?.plus(paddingStart.toFloat()) ?: 0f
                    // If the touch occurred before the end of the button,
                    // set isClearButtonClicked to true.
                    if (event.x < clearButtonEnd) {
                        isClearButtonClick = true
                    }
                } else {
                    // Layout is LTR.
                    // Get the start of the button on the right side.
                    clearButtonStart = (width - paddingEnd.toFloat() - mClearButtonImage?.intrinsicWidth!!)
                    // If the touch occurred after the start of the button,
                    // set isClearButtonClicked to true.
                    if (event.x > clearButtonStart) {
                        isClearButtonClick = true
                    }
                }

                // Check for actions if the button is tapped.
                if (isClearButtonClick) {
                    // Check for ACTION_DOWN (always occurs before ACTION_UP).
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        // Switch to the black version of clear button.
                        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_black_24dp,null)
                        showClearButton()
                    }
                    // Check for ACTION_UP.
                    if (event.action == MotionEvent.ACTION_UP) {
                        // Switch to the opaque version of clear button.
                        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_opaque_24dp, null)
                        // Clear the text and hide the clear button.
                        text?.clear()
                        hideClearButton()
                        true
                    } else {
                        false
                    }
                } else {
                    false
                }

            }
            false
        }
    }
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    /**
     * Show the clear (X) button.
     *  the setCompoundDrawablesRelativeWithIntrinsicBounds() method sets the drawable mClearButtonImage
     *  to the end of the text. The method accommodates right-to-left (RTL) languages by using the
     *  arguments as "start" and "end" positions rather than "left" and "right" positions.
     */
    private fun showClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,mClearButtonImage,null)
    }

    /**
     * Hide the clear (X) button.
     * Use null for positions that should not show a drawable. In hideClearButton(),
     * the setCompoundDrawablesRelativeWithIntrinsicBounds() method replaces the
     * drawable with null in the end position.
     */
    private fun hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null,null,null,null)
    }


}