package com.pfariasmunoz.surfaceviewexample

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lock the screen orientation into landscape. Games often lock the screen orientation.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

    }
}
