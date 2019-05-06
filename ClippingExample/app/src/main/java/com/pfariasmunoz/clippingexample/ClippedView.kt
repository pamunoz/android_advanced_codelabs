package com.pfariasmunoz.clippingexample

import android.content.Context
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class ClippedView : View {

    private var mPaint: Paint? = null
    private var mPath: Path? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )

}