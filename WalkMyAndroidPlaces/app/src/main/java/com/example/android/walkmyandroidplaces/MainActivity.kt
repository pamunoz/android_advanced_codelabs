/*
 * Copyright 2017 Google Inc.
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
package com.example.android.walkmyandroidplaces

import android.Manifest
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient





class MainActivity : AppCompatActivity(), FetchAddressTask.OnTaskCompleted {

    // Location classes
    private var mTrackingLocation: Boolean = false
    private var mFusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationCallback: LocationCallback? = null

    // Animation
    private var mRotateAnim: AnimatorSet? = null
    private val places_api_key = getString(R.string.places_api_key)

    // You use this object to get information about the device's current location.


    /**
     * Sets up the location request.
     *
     * @return The LocationRequest object containing the desired parameters.
     */
    private val locationRequest: LocationRequest
        get() {
            val locationRequest = LocationRequest()
            locationRequest.interval = 10000
            locationRequest.fastestInterval = 5000
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            return locationRequest
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Places.
        Places.initialize(applicationContext, places_api_key)

        // Create a new Places client instance.
        val placesClient = Places.createClient(this)


        // Initialize the FusedLocationClient.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(
                this)

        // Set up the animation.
        mRotateAnim = AnimatorInflater.loadAnimator(this, R.animator.rotate) as AnimatorSet
        mRotateAnim!!.setTarget(imageview_android)

        // Restore the state if the activity is recreated.
        if (savedInstanceState !=
                null /* Looper */) {
            mTrackingLocation = savedInstanceState.getBoolean(
                    TRACKING_LOCATION_KEY)
        }

        // Set the listener for the location button.
        button_location.setOnClickListener {
            if (!mTrackingLocation) {
                startTrackingLocation()
            } else {
                stopTrackingLocation()
            }
        }


        // Initialize the location callbacks.
        mLocationCallback = object : LocationCallback() {
            /**
             * This is the callback that is triggered when the
             * FusedLocationClient updates your location.
             * @param locationResult The result containing the device location.
             */
            override fun onLocationResult(locationResult: LocationResult?) {
                // If tracking is turned on, reverse geocode into an address
                if (mTrackingLocation) {
                    FetchAddressTask(this@MainActivity, this@MainActivity)
                            .execute(locationResult!!.lastLocation)
                }
            }
        }
    }

    /**
     * Starts tracking the device. Checks for
     * permissions, and requests them if they aren't present. If they are,
     * requests periodic location updates, sets a loading text and starts the
     * animation.
     */
    private fun startTrackingLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_LOCATION_PERMISSION)
        } else {
            mTrackingLocation = true
            mFusedLocationClient!!.requestLocationUpdates(locationRequest,
                    mLocationCallback!!, null)

            // Set a loading text while you wait for the address to be
            // returned
            textview_location.text = getString(R.string.address_text,
                    getString(R.string.loading),
                    System.currentTimeMillis())
            button_location.setText(R.string.stop_tracking_location)
            mRotateAnim!!.start()
        }
    }


    /**
     * Stops tracking the device. Removes the location
     * updates, stops the animation, and resets the UI.
     */
    private fun stopTrackingLocation() {
        if (mTrackingLocation) {
            mTrackingLocation = false
            button_location.setText(R.string.start_tracking_location)
            textview_location.setText(R.string.textview_hint)
            mRotateAnim!!.end()
        }
    }


    /**
     * Saves the last location on configuration change
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(TRACKING_LOCATION_KEY, mTrackingLocation)
        super.onSaveInstanceState(outState)
    }

    /**
     * Callback that is invoked when the user responds to the permissions
     * dialog.
     *
     * @param requestCode  Request code representing the permission request
     * issued by the app.
     * @param permissions  An array that contains the permissions that were
     * requested.
     * @param grantResults An array with the results of the request for each
     * permission requested.
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION ->

                // If the permission is granted, get the location, otherwise,
                // show a Toast
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTrackingLocation()
                } else {
                    Toast.makeText(this,
                            R.string.location_permission_denied,
                            Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onTaskCompleted(result: String) {
        if (mTrackingLocation) {
            // Update the UI
            textview_location.text = getString(R.string.address_text,
                    result, System.currentTimeMillis())
        }
    }

    override fun onPause() {
        if (mTrackingLocation) {
            stopTrackingLocation()
            mTrackingLocation = true
        }
        super.onPause()
    }

    override fun onResume() {
        if (mTrackingLocation) {
            startTrackingLocation()
        }
        super.onResume()
    }

    companion object {

        // Constants
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val TRACKING_LOCATION_KEY = "tracking_location"
    }

}
