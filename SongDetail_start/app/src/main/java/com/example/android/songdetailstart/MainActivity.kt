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

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.android.songdetailstart.content.SongUtils

/**
 * An activity representing a list of song titles (items). When one is
 * touched, an intent starts [SongDetailActivity] representing
 * song details.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */

    /**
     * Sets up a song list as a RecyclerView.
     *
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song_list)

        // Set the toolbar as the app bar.
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        toolbar.title = title

        // Get the song list as a RecyclerView.
        val recyclerView = findViewById<View>(R.id.song_list) as RecyclerView
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(SongUtils.SONG_ITEMS)
    }

    /**
     * The ReyclerView for the song list.
     */
    internal inner class SimpleItemRecyclerViewAdapter(private val mValues: List<SongUtils.Song>) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        /**
         * This method inflates the layout for the song list.
         * @param parent ViewGroup into which the new view will be added.
         * @param viewType The view type of the new View.
         * @return
         */
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.song_list_content, parent, false)
            return ViewHolder(view)
        }

        /**
         * This method implements a listener with setOnClickListener().
         * When the user taps a song title, the code checks if mTwoPane
         * is true, and if so uses a fragment to show the song detail.
         * If mTwoPane is not true, it starts SongDetailActivity
         * using an intent with extra data about which song title was selected.
         *
         * @param holder   ViewHolder
         * @param position Position of the song in the array.
         */
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.mItem = mValues[position]
            holder.mIdView.text = (position + 1).toString()
            holder.mContentView.text = mValues[position].song_title
            holder.mView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context,
                        SongDetailActivity::class.java)
                intent.putExtra(SongUtils.SONG_ID_KEY,
                        holder.adapterPosition)
                context.startActivity(intent)
            }
        }

        /**
         * Get the count of song list items.
         * @return
         */
        override fun getItemCount(): Int {
            return mValues.size
        }

        /**
         * ViewHolder describes an item view and metadata about its place
         * within the RecyclerView.
         */
        internal inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
            val mIdView: TextView
            val mContentView: TextView
            var mItem: SongUtils.Song? = null

            init {
                mIdView = mView.findViewById<View>(R.id.id) as TextView
                mContentView = mView.findViewById<View>(R.id.content) as TextView
            }
        }
    }
}
