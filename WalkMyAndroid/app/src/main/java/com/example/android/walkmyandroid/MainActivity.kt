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
package com.example.android.walkmyandroid

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mLastLocation: Location? = null
    //private var mFusedLocationClient: FusedLocationProviderClient? = null

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 2
    }

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_get_location.setOnClickListener {
            if (!accessFineLocation(REQUEST_LOCATION_PERMISSION)) {
                LocationServices.getFusedLocationProviderClient(this)?.lastLocation?.addOnSuccessListener { location ->
                    if (location != null) {
                        mLastLocation = location
                        textview_location.text = getString(R.string.location_text, mLastLocation?.latitude, mLastLocation?.longitude, mLastLocation?.time)
                    } else {
                        textview_location.text = getString(R.string.no_location)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSION -> {
                // If the permission is granted, get the location,
                // otherwise, show a Toast
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    accessFineLocation(REQUEST_LOCATION_PERMISSION)
                } else {
                    toast(R.string.location_permission_denied)
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
