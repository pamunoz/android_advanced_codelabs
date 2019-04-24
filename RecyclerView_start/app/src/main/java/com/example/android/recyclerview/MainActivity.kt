/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.recyclerview

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import java.util.LinkedList

/**
 * Implements a basic RecyclerView that displays a list of generated words.
 * - Clicking an item marks it as clicked.
 * - Clicking the fab button adds a new word to the list.
 */
class MainActivity : AppCompatActivity() {

    //private var mRecyclerView: RecyclerView? = null
    private var mAdapter: WordListAdapter? = null
    private val mWordList = LinkedList<String>()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Put initial data into the word list.
        for (i in 0..199) {
            mWordList.addLast("Word $i")
        }
        // Create an adapter and supply the data to be displayed.
        mAdapter = WordListAdapter(this, mWordList)
        // Connect the adapter with the recycler view.
        recyclerview.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }


        // Add a floating action click handler for creating new entries.
        //val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val wordListSize = mWordList.size
            // Add a new word to the wordList.
            mWordList.addLast("+ Word $wordListSize")
            // Notify the adapter, that the data has changed.
            recyclerview.adapter?.notifyItemInserted(wordListSize)
            // Scroll to the bottom.
            recyclerview.smoothScrollToPosition(wordListSize)
        }
    }
}