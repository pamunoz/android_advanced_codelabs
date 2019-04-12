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

package com.example.android.fragmentexample2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var isDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun displayFragment() {
        // Add the SimpleFragment.
        supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, SimpleFragment.newInstance).addToBackStack(null).commit()
        }
        //Update the Button text
        btn_open.text = getString(R.string.close)
        // Set boolean flag to indicate fragment is open.
        isDisplayed = true
    }

    fun closeFragment() {
        // Check to see if the fragment is already showing.
        val simpleFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as SimpleFragment
        simpleFragment.let { supportFragmentManager.beginTransaction().remove(it).commit() }
        // Update the Button text.
        btn_open.text = getString(R.string.open)
        // Set boolean flag to indicate fragment is closed.
        isDisplayed = false
    }
}
