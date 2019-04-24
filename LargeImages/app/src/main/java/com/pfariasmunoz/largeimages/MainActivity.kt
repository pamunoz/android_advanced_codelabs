package com.pfariasmunoz.largeimages

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {

    private var toggle = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun changeImage(view: View) {
        toggle = when(toggle) {
            0 -> {
                view.setBackgroundResource(R.drawable.dinosaur_medium)
                1
            }
            1 -> {
                try {
                    Thread.sleep(32) // two refreshes
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }

                view.setBackgroundResource(R.drawable.ankylo)
                2
            }
            else -> {
                view.setBackgroundResource(R.drawable.dinosaur_large)
                0
            }
        }
    }
}
