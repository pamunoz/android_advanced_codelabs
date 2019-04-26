package com.example.android.walkmyandroid

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.AsyncTask
import android.text.TextUtils
import android.util.Log
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class FetchAddressTask(private val ctx: Context, private val listener: OnTaskCompleted) : AsyncTask<Location, Unit, String>() {

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