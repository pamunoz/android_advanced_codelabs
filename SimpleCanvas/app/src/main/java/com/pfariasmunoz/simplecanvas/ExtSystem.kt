package com.pfariasmunoz.simplecanvas

import android.content.Context
import androidx.annotation.ColorRes
import androidx.core.content.res.ResourcesCompat

fun Context.getResColor(@ColorRes res: Int): Int = ResourcesCompat.getColor(this.resources, res, null)