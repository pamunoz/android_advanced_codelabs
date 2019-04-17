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

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_simple.view.*

/**
 * A simple [Fragment] subclass that shows a question
 * with radio buttons for providing feedback. If the user
 * clicks "Yes" the text header changes to "Article: Like".
 * If the user clicks "No" the text header changes to "Thanks".
 */
class SimpleFragment : Fragment() {

    var mRadioButtonChoice = NONE
    var mListener: OnFragmentInteractionListener? = null

    interface OnFragmentInteractionListener {
        fun onRadioButtonChoice(choice: Int)
    }

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
        val rootView = inflater.inflate(R.layout.fragment_simple, container, false)
        val choiceMade = this.arguments?.containsKey(CHOICE)
        if (choiceMade != null) {
            // A choice was made, so get the choice.
            mRadioButtonChoice = this.arguments?.getInt(CHOICE)!!
            // Check the radio button choice.
            if (mRadioButtonChoice != NONE) rootView.radio_group.check(rootView.radio_group.getChildAt(mRadioButtonChoice).id)
        }
        // Set the radioGroup onCheckedChanged listener.
        rootView.radio_group.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = rootView.radio_group.findViewById<View>(checkedId)

            when (rootView.radio_group.indexOfChild(radioButton)) {
                YES // User chose "Yes".
                -> {
                    rootView.fragment_header.text = getString(R.string.yes_message)
                    setChoice(YES)
                }
                NO // User chose "No".
                -> {
                    rootView.fragment_header.text = getString(R.string.no_message)
                    setChoice(NO)
                }
                else // No choice made.
                -> setChoice(NONE)
            }// Do nothing.
        }

        // Return the View for the fragment's UI.
        return rootView
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        when(context) {
            is OnFragmentInteractionListener -> mListener = context
            else -> throw ClassCastException("$context ${resources.getString(R.string.exception_message)}")
        }
    }

    private fun setChoice(choice: Int) {
        mRadioButtonChoice = choice
        mListener?.onRadioButtonChoice(choice)
    }

    companion object {

        // The radio button choice has 3 states: 0 = yes, 1 = no,
        // 2 = default (no choice). Using only 0 and 1.
        private const val YES = 0
        private const val NO = 1
        private const val NONE = 2
        private const val CHOICE = "choice"

        fun newInstance(choice: Int) = SimpleFragment().apply {
            arguments = Bundle().apply { putInt(CHOICE, choice) }
        }
    }
}// Required empty public constructor
