package com.pfariasmunoz.customedittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

class EditTextWithClear : AppCompatEditText {

    private val mClearButtonImage: Drawable?

    init {
        mClearButtonImage = ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_opaque_24dp, null)
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