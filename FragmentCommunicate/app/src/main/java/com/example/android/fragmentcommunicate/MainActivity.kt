/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.fragmentcommunicate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SimpleFragment.OnFragmentInteractionListener {
    override fun onRadioButtonChoice(choice: Int) {
        // Keep the radio button choice to pass it back to the fragment.
        mRadioButtonChoice = choice
        toast("Choice is $choice")
    }

    private var isFragmentDisplayed = false
    var mRadioButtonChoice = 2; // The default (no choice).

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // If returning from a configuration change, get the
        // fragment state and set the button text.
        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(STATE_FRAGMENT)
            if (isFragmentDisplayed) {
                // If the fragment is displayed, change button to "close".
                open_button!!.setText(R.string.close)
            }
        }
        // Set the click listener for the button.
        open_button!!.setOnClickListener {
            if (!isFragmentDisplayed) {
                displayFragment()
            } else {
                closeFragment()
            }
        }
    }

    /**
     * This method is called when the user clicks the button
     * to open the fragment.
     */
    private fun displayFragment() {
        // Instantiate the fragment.
        val simpleFragment = SimpleFragment.newInstance()
        // Get the FragmentManager and start a transaction.
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager
                .beginTransaction()

        // Add the SimpleFragment.
        fragmentTransaction.add(R.id.fragment_container,
                simpleFragment).addToBackStack(null).commit()

        // Update the Button text.
        open_button!!.setText(R.string.close)
        // Set boolean flag to indicate fragment is open.
        isFragmentDisplayed = true
    }

    /**
     * This method is called when the user clicks the button to
     * close the fragment.
     */
    private fun closeFragment() {
        // Get the FragmentManager.
        val fragmentManager = supportFragmentManager
        // Check to see if the fragment is already showing.
        val simpleFragment = fragmentManager
                .findFragmentById(R.id.fragment_container) as SimpleFragment?
        if (simpleFragment != null) {
            // Create and commit the transaction to remove the fragment.
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.remove(simpleFragment).commit()
        }
        // Update the Button text.
        open_button!!.setText(R.string.open)
        // Set boolean flag to indicate fragment is closed.
        isFragmentDisplayed = false
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        // Save the state of the fragment (true=open, false=closed).
        savedInstanceState.putBoolean(STATE_FRAGMENT, isFragmentDisplayed)
        super.onSaveInstanceState(savedInstanceState)
    }

    companion object {

        // Saved instance state key.
        internal const val STATE_FRAGMENT = "state_of_fragment"
    }
}
