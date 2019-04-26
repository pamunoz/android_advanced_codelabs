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
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), OnTaskCompleted {
    override fun onTaskCompleted(result: String) {
        // Update the UI
        textview_location.text = getString(R.string.address_text, result, System.currentTimeMillis())
    }

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

    inner class FetchAddressTask(private val ctx: Context, private val listener: OnTaskCompleted) : AsyncTask<Location, Unit, String>() {

        override fun doInBackground(vararg params: Location?): String {
            val geocoder = Geocoder(ctx, Locale.getDefault())
            val location: Location? = params[0]
            var addresses: List<Address>? = null
            var resultMessage = ""
            try {
                addresses = location?.let { geocoder.getFromLocation(location.latitude, location.longitude, 1) }
                if (addresses != null) {
                    if (addresses.isEmpty()) {
                        if (resultMessage.isEmpty()) {
                            resultMessage = ctx.getString(R.string.no_address_found)
                            Log.e(MainActivity::class.java.simpleName, resultMessage)
                        }
                    } else {
                        // If an Address is found, read it into result message
                        val address: Address = addresses[0]
                        val addressParts: ArrayList<String> = ArrayList()

                        // Fetch the address lines using getAddressLine
                        // join them, and send them to the thread
                        for (a in 0..address.maxAddressLineIndex) {
                            addressParts.add(address.getAddressLine(a))
                        }
                        resultMessage = TextUtils.join("\n", addressParts)
                    }
                }
            } catch (e: IOException) {
                // Catch Network or other IO problems
                resultMessage = ctx.getString(R.string.no_service_available)
                Log.e(MainActivity::class.java.simpleName, resultMessage, e)
            } catch (e: IllegalArgumentException) {
                // Catch invalid latitude or longitude
                resultMessage = ctx.getString(R.string.invalid_lat_long_used)
                Log.e(MainActivity::class.java.simpleName, "$resultMessage Latitude: ${location?.latitude}, Longitude: ${location?.longitude}", e)
            }
            return resultMessage
        }

        override fun onPostExecute(result: String?) {
            result?.let { listener.onTaskCompleted(result) }
            super.onPostExecute(result)
        }

    }
}
