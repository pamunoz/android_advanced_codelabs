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

package com.example.android.songdetailstart

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.android.songdetailstart.content.SongUtils
import kotlinx.android.synthetic.main.activity_song_detail.*
import kotlinx.android.synthetic.main.song_detail.*

/**
 * An activity representing a single song detail screen.
 */
class SongDetailActivity : AppCompatActivity() {

    // SongItem includes the song title and detail.
    var mSong: SongUtils.Song? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_detail)
        //val toolbar = findViewById<View>(R.id.detail_toolbar) as Toolbar
        setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // This activity displays the detail. In a real-world scenario,
        // get the data from a content repository.
        mSong = SongUtils.SONG_ITEMS[intent.getIntExtra(SongUtils.SONG_ID_KEY, 0)]
        // Show the detail information in a TextView.
        mSong?.let { song_detail.text = mSong!!.details }
    }

    /**
     * Performs action if the user selects the Up button.
     *
     * @param item Menu item selected (Up button)
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.
            // NavUtils allows users to navigate up one level in the
            // application structure.
            NavUtils.navigateUpTo(this, Intent(this, MainActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
