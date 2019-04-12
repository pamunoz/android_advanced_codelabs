package com.example.android.fragmentexample2


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_simple.*
import kotlinx.android.synthetic.main.fragment_simple.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
class SimpleFragment : Fragment() {

    companion object {
        const val YES = 0
        const val NO = 1

        val newInstance = SimpleFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_simple, container, false)


        rootView.radio_group.setOnCheckedChangeListener { _, checkedId ->
            val radioButton: View = radio_group.findViewById(checkedId)
            val childIndex = radio_group.indexOfChild(radioButton)
            val textView = rootView.tv_fragment_header
            when(childIndex) {
                YES -> textView.text = getString(R.string.yes_message)
                NO -> textView.text = getString(R.string.no_message)
            }
        }

        rootView.rtb_song_rating.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(rootView.context, "My Rating: ${rating.toDouble()}", Toast.LENGTH_SHORT).show()
        }

        return rootView
    }


}
