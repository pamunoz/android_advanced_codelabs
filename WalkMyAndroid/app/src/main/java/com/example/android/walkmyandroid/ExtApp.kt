package com.example.android.walkmyandroid

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat


fun Context.toast(message: Any, duration: Int = Toast.LENGTH_SHORT) {
    when(message) {
        is Int -> Toast.makeText(this, this.resources.getString(message), duration).show()
        is String -> Toast.makeText(this, message, duration).show()
    }
}

fun Activity.accessFineLocation(requestCode: Int): Boolean {
    return if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
        true
    } else {
        logd("getLocation: permissions granted")
        false
    }
}

fun Any.logd(message: String) {
    val tag = this::class.java.simpleName
    Log.d(tag, message)
}