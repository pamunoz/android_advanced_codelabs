package com.example.android.walkmyandroid

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices


fun Context.toast(message: Any, duration: Int = Toast.LENGTH_SHORT) {
    when(message) {
        is Int -> Toast.makeText(this, this.resources.getString(message), duration).show()
        is String -> Toast.makeText(this, message, duration).show()
    }
}



fun Any.logd(message: String) {
    val tag: String = this::class.java.simpleName
    Log.d(tag, message)
}