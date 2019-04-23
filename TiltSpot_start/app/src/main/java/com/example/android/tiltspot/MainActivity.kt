/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.tiltspot

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    // System sensor manager instance.
    private var mSensorManager: SensorManager? = null

    // Accelerometer and magnetometer sensors, as retrieved from the
    // sensor manager.
    private var mSensorAccelerometer: Sensor? = null
    private var mSensorMagnetometer: Sensor? = null

    // TextViews to display current sensor values.
//    private var mTextSensorAzimuth: TextView? = null
//    private var mTextSensorPitch: TextView? = null
//    private var mTextSensorRoll: TextView? = null

    private var mAccelerometerData = FloatArray(3)
    private var mMagnetometerData = FloatArray(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lock the orientation to portrait (for now)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

//        mTextSensorAzimuth = findViewById<View>(R.id.value_azimuth)
//        mTextSensorPitch = findViewById<View>(R.id.value_pitch)
//        mTextSensorRoll = findViewById<View>(R.id.value_roll)

        // Get accelerometer and magnetometer sensors from the sensor manager.
        // The getDefaultSensor() method returns null if the sensor
        // is not available on the device.
        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        mSensorAccelerometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mSensorMagnetometer = mSensorManager!!.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    /**
     * Listeners for the sensors are registered in this callback so that
     * they can be unregistered in onStop().
     */
    override fun onStart() {
        super.onStart()

        // Listeners for the sensors are registered in this callback and
        // can be unregistered in onStop().
        //
        // Check to ensure sensors are available before registering listeners.
        // Both listeners are registered with a "normal" amount of delay
        // (SENSOR_DELAY_NORMAL).
        if (mSensorAccelerometer != null) {
            mSensorManager!!.registerListener(this, mSensorAccelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL)
        }
        if (mSensorMagnetometer != null) {
            mSensorManager!!.registerListener(this, mSensorMagnetometer,
                    SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onStop() {
        super.onStop()

        // Unregister all sensor listeners in this callback so they don't
        // continue to use resources when the app is stopped.
        mSensorManager!!.unregisterListener(this)
    }

    override fun onSensorChanged(sensorEvent: SensorEvent) {
        /*
        You use the clone() method to explicitly make a copy of the data in the values array.
        The SensorEvent object (and the array of values it contains) is reused across calls
        to onSensorChanged(). Cloning those values prevents the data you're currently
        interested in from being changed by more recent data before you're done with it.
         */
        when(sensorEvent.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> { mAccelerometerData = sensorEvent.values.clone() }
            Sensor.TYPE_MAGNETIC_FIELD -> { mMagnetometerData = sensorEvent.values.clone() }
        }
        /*
        use the SensorManager.getRotationMatrix() method to generate a rotation matrix
        from the raw accelerometer and magnetometer data. The matrix is used in the
        next step to get the device orientation, which is what you're really interested in.
         */
        var rotationMatrix = FloatArray(9)
        val rotationOk = SensorManager.getRotationMatrix(rotationMatrix, null, mAccelerometerData, mMagnetometerData)
        val orientationValues = FloatArray(3)
        if (rotationOk) SensorManager.getOrientation(rotationMatrix, orientationValues)
        /*
         The angles returned by the getOrientation() method describe how far the device is oriented
         or tilted with respect to the Earth's coordinate system. There are three components to orientation:

            Azimuth: The direction (north/south/east/west) the device is pointing. 0 is magnetic north.
            Pitch: The top-to-bottom tilt of the device. 0 is flat.
            Roll: The left-to-right tilt of the device. 0 is flat.

         All three angles are measured in radians, and range from -π (-3.141) to π.
         */
        val azimuth = orientationValues[0]
        val pitch = orientationValues[1]
        val roll = orientationValues[2]
        value_azimuth.text = resources.getString(R.string.value_format, azimuth)
        value_pitch.text = resources.getString(R.string.value_format, pitch)
        value_roll.text = resources.getString(R.string.value_format, roll)
    }

    /**
     * Must be implemented to satisfy the SensorEventListener interface;
     * unused in this app.
     */
    override fun onAccuracyChanged(sensor: Sensor, i: Int) {}

    companion object {

        // Very small values for the accelerometer (on all three axes) should
        // be interpreted as 0. This value is the amount of acceptable
        // non-zero drift.
        private val VALUE_DRIFT = 0.05f
    }
}