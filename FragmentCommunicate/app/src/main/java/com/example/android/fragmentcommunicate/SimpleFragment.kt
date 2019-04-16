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
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_simple.view.*

/**
 * A simple [Fragment] subclass that shows a question
 * with radio buttons for providing feedback. If the user
 * clicks "Yes" the text header changes to "Article: Like".
 * If the user clicks "No" the text header changes to "Thanks".
 */
class SimpleFragment : Fragment() {

    var mRadioButtonChoice = NONE

    /**
     * Creates the view for the fragment.
     *
     * @param inflater           LayoutInflater to inflate any views in the fragment
     * @param container          ViewGroup of parent view to attach fragment
     * @param savedInstanceState Bundle for previous state
     * @return rootView
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment.
        val rootView = inflater.inflate(R.layout.fragment_simple,
                container, false)
        //val radioGroup = rootView.findViewById<RadioGroup>(R.id.radio_group)

        // Set the radioGroup onCheckedChanged listener.
        rootView.radio_group.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = rootView.radio_group.findViewById<View>(checkedId)

            when (rootView.radio_group.indexOfChild(radioButton)) {
                YES // User chose "Yes".
                -> rootView.fragment_header.text = getString(R.string.yes_message)
                NO // User chose "No".
                -> rootView.fragment_header.text = getString(R.string.no_message)
                else // No choice made.
                -> {
                }
            }// Do nothing.
        }

        // Return the View for the fragment's UI.
        return rootView
    }

    companion object {

        // The radio button choice has 3 states: 0 = yes, 1 = no,
        // 2 = default (no choice). Using only 0 and 1.
        private const val YES = 0
        private const val NO = 1
        private const val NONE = 2

        fun newInstance(): SimpleFragment {
            return SimpleFragment()
        }
    }
}// Required empty public constructor
