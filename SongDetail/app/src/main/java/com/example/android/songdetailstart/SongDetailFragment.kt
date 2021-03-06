package com.example.android.songdetailstart


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.songdetailstart.content.SongUtils
import kotlinx.android.synthetic.main.song_detail.view.*

/**
 * A simple [Fragment] subclass.
 *
 */
class SongDetailFragment : Fragment() {

    // SongItem includes the song title and detail.
    var mSong: SongUtils.Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.containsKey(SongUtils.SONG_ID_KEY)!!) {
            // Load the content specified by the fragment arguments.
            mSong = arguments?.getInt(SongUtils.SONG_ID_KEY)?.let { SongUtils.SONG_ITEMS[it] }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.song_detail, container, false)

        // Show the detail information in a TextView.
        mSong?.let { rootView.song_detail.text = mSong!!.details }
        return rootView
    }

    companion object {
        /**
         * This method receives the selectedSong (the integer position of the song title in the list),
         * and creates the arguments Bundle with SONG_ID_KEY and selectedSong. It then uses
         * setArguments(arguments) to set the arguments for the Fragment, and returns the Fragment.
         */
        fun newInstance(selectedSong: Int) = SongDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(SongUtils.SONG_ID_KEY, selectedSong)
            }
        }
    }


}
