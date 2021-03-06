package com.pfariasmunoz.sensorsurvey

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity(), SensorEventListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sensorError = resources.getString(R.string.error_no_sensor)
        if (sensorManager.light == null) tv_label_light.text = sensorError
        if (sensorManager.proximity == null) tv_label_proximity.text = sensorError
    }

    override fun onStart() {
        super.onStart()
        sensorManager.light?.let {
            sensorManager.registerListener(this, sensorManager.light, SensorManager.SENSOR_DELAY_NORMAL)
        }
        sensorManager.proximity.let {
            sensorManager.registerListener(this, sensorManager.proximity, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onStop() {
        super.onStop()
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent?) {
        val sensorType = event?.sensor?.type
        val currentValue = event?.values?.get(0)
        when(sensorType) {
            // Event came from the light sensor
            Sensor.TYPE_LIGHT -> tv_label_light.text = resources.getString(R.string.label_light, currentValue)
            Sensor.TYPE_PROXIMITY -> tv_label_proximity.text = resources.getString(R.string.label_proximity, currentValue)
            else -> {}
        }
    }

}
