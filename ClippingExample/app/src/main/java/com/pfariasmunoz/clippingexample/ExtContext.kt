package com.pfariasmunoz.clippingexample

import android.content.Context
import androidx.annotation.DimenRes

fun Context.getDimen(@DimenRes res: Int): Int {
    return this.resources.getDimensionPixelOffset(res)
}