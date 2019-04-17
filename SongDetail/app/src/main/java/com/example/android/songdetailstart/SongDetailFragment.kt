package com.example.android.songdetailstart


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.songdetailstart.content.SongUtils

/**
 * A simple [Fragment] subclass.
 *
 */
class SongDetailFragment : Fragment() {

    // SongItem includes the song title and detail.
    var mSong: SongUtils.Song? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song_detail, container, false)
    }


}
