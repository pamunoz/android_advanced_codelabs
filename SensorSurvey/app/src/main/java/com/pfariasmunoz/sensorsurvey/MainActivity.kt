package com.pfariasmunoz.sensorsurvey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sensorText = StringBuilder()
        for (currentSensor in sensorManager.sensorList) {
            sensorText.append(currentSensor.name).append(System.getProperty("line.separator"))
        }
        tv_sensor_list.text = sensorText.toString()
    }
}
