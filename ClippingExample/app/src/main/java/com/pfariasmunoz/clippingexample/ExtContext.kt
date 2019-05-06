package com.pfariasmunoz.clippingexample

import android.content.Context
import android.view.View
import androidx.annotation.DimenRes

fun View.getDimen(@DimenRes res: Int): Int {
    return this.resources.getDimensionPixelOffset(res)
}