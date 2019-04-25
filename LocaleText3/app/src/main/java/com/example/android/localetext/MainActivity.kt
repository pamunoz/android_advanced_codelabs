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

package com.example.android.localetext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.text.DateFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * This app demonstrates how to localize an app with text, an image,
 * a floating action button, an options menu, and the app bar.
 */
class MainActivity : AppCompatActivity() {

    // Default quantity is 1.
    private var mInputQuantity = 1

    // DONE: Get the number format for this locale.
    private val mNumberFormat = NumberFormat.getInstance()
    private val TAG = MainActivity::class.java.simpleName

    // Fixed price in U.S. dollars and cents: ten cents.
    private var mPrice = 0.10

    // Exchange rates for France (FR) and Israel (IW).
    internal var mFrExchangeRate = 0.93 // 0.93 euros = $1.
    internal var mIwExchangeRate = 3.61 // 3.61 new shekels = $1.

    // DONE: Get locale's currency.
    private var mCurrencyFormat = NumberFormat.getCurrencyInstance()

    /**
     * Creates the view with a toolbar for the options menu
     * and a floating action button, and initialize the
     * app data.
     *
     * @param savedInstanceState Bundle with activity's previously saved state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        fab.setOnClickListener { showHelp() }

        // Get the current date.
        val myDate = Date()
        // Add 5 days in milliseconds to create the expiration date.
        val expirationDate = myDate.time + TimeUnit.DAYS.toMillis(5)
        // Set the expiration date as the date to display.
        myDate.time = expirationDate

        // DONE: Format the date for the locale.
        val formattedDate = DateFormat.getDateInstance().format(myDate)
        // Display the formatted date
        date.text = formattedDate

        // DONE: Apply the exchange rate and calculate the price.
        setupThePriceAndCurrencyFormat()

        // TODO: Show the price string.

        // Get the EditText view for the entered quantity.
        //val enteredQuantity = findViewById<View>(R.id.) as EditText
        // Add an OnEditorActionListener to the EditText view.
        quantity.setOnEditorActionListener { v, actionId, keyEvent ->
            // String myFormattedTotal;
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Close the keyboard.
                val imm = v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)
                // Check if view v is empty.
                if (v.toString() == "") {
                    // Don't format, leave alone.
                } else {

                    // DONE: Parse string in view v to a number.
                    try {
                        // Use the number format for the locale
                        mInputQuantity = mNumberFormat.parse(v.text.toString()).toInt()
                        v.error = null
                    } catch (e: ParseException) {
                        Log.e(TAG, Log.getStackTraceString(e))
                        v.error = getText(R.string.enter_a_number)
                        false
                    }
                    // TODO: Convert to string using locale's number format.
                    val formattedQuantity = mNumberFormat.format(mInputQuantity)
                    // Show the locale-formatted quantity.
                    v.text = formattedQuantity

                    // TODO: Homework: Calculate the total amount from price and quantity.

                    // TODO: Homework: Use currency format for France (FR) or Israel (IL).

                    // TODO: Homework: Show the total amount string.

                    true
                }
            }
            false
        }
    }

    private fun setupThePriceAndCurrencyFormat() {
        val myFormattedPrice: String
        val deviceLocale = Locale.getDefault().country
        // If country code is France or Israel, calculate price
        // with exchange rate and change to the country's currency format
        if ((deviceLocale == "FR") or (deviceLocale == "IL")) {
            if (deviceLocale == "FR") {
                // Calculate mPrice in euros.
                mPrice *= mFrExchangeRate
            } else {
                // Calculate mPrice in new shekels.
                mPrice *= mIwExchangeRate
            }
            // Use the user-chosen locale's currency format, which
            // is either France or Israel.
            myFormattedPrice = mCurrencyFormat.format(mPrice)
        } else {
            // mPrice is the same (based on U.S. dollar).
            // Use the currency format for the U.S.
            mCurrencyFormat = NumberFormat.getCurrencyInstance(Locale.US)
            myFormattedPrice = mCurrencyFormat.format(mPrice)
        }
    }

    /**
     * Shows the Help screen.
     */
    private fun showHelp() {
        // Create the intent.
        val helpIntent = Intent(this, HelpActivity::class.java)
        // Start the HelpActivity.
        startActivity(helpIntent)
    }

    /**
     * Clears the quantity when resuming the app after language is changed.
     */
    override fun onResume() {
        super.onResume()
        quantity.text.clear()
    }

    /**
     * Creates the options menu and returns true.
     *
     * @param menu       Options menu
     * @return boolean   True after creating options menu.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Handles options menu item clicks.
     *
     * @param item      Menu item
     * @return boolean  True if menu item is selected.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle options menu item clicks here.
        when (item.itemId) {
            R.id.action_help -> {
                val helpIntent = Intent(this, HelpActivity::class.java)
                startActivity(helpIntent)
                return true
            }
            R.id.action_language -> {
                val languageIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(languageIntent)
                return true
            }
        }// Do nothing
        return super.onOptionsItemSelected(item)
    }
}
