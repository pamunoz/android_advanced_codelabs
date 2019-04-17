package com.example.android.fragmentcommunicate

import android.content.Context
import android.widget.Toast

fun Context.toast(message: Any, duration: Int = Toast.LENGTH_SHORT) {
    when(message) {
        is Int -> Toast.makeText(this, this.getString(message), duration).show()
        is String -> Toast.makeText(this, message, duration).show()
        else -> {}
    }
}