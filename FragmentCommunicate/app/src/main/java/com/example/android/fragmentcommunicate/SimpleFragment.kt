package com.example.android.fragmentcommunicate


import android.content.Context
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_simple.*
import kotlinx.android.synthetic.main.fragment_simple.view.*
import java.lang.ClassCastException

/**
 * A simple [Fragment] subclass.
 *
 */
class SimpleFragment : Fragment() {

    var mRadioButtonChoice = NONE
    private var mListener: OnFragmentInteractionListener? = null
    interface OnFragmentInteractionListener{ fun onRadioButtonChoice(choice: Int) }

    companion object {


        private const val CHOICE = "choice"

        
        const val YES = 0
        const val NO = 1
        const val NONE = 2
        private var mRadioButtonChoice = NONE

        fun newInstance(choice: Int): SimpleFragment {
            return SimpleFragment().apply {
                arguments = Bundle().apply {
                    putInt(CHOICE, choice)
                }
            }
        }
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
                YES -> {
                    textView.text = getString(R.string.yes_message)
                    mRadioButtonChoice = YES
                    mListener?.onRadioButtonChoice(YES)
                }
                NO -> {
                    textView.text = getString(R.string.no_message)
                    mRadioButtonChoice = NO
                    mListener?.onRadioButtonChoice(NO)
                }
                else -> {
                    mRadioButtonChoice = NONE
                    mListener?.onRadioButtonChoice(NONE)
                }
            }
        }

        rootView.rtb_song_rating.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(rootView.context, "My Rating: ${rating.toDouble()}", Toast.LENGTH_SHORT).show()
        }

        return rootView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw ClassCastException(context.toString() + resources.getString(R.string.exception_message))
        }
    }
}
