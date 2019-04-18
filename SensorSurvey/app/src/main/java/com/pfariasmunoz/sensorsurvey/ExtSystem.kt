package com.pfariasmunoz.sensorsurvey

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

val Context.sensorManager: SensorManager
    get() = this.getSystemService(Context.SENSOR_SERVICE) as SensorManager

val SensorManager.sensorList: List<Sensor>
    get() = this.getSensorList(Sensor.TYPE_ALL)