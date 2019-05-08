package com.pfariasmunoz.surfaceviewexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fl = FlashlightCone(200, 200)
        Log.d("FLASH", "BEFORE X:${fl.x} AND Y: ${fl.y}")
        fl.update(3, 4)
        Log.d("FLASH", "AFTER X:${fl.x} AND Y: ${fl.y}")
    }
}
