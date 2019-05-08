package com.pfariasmunoz.surfaceviewexample

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {

    private var mGameView: GamveView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Lock the screen orientation into landscape. Games often lock the screen orientation.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        // Create an instance of GameView.
        mGameView = GamveView(this).apply {
            // Set mGameView to completely fill the screen.
            systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        // Set mGameView as the content view for MainActivity.
        setContentView(mGameView)

    }
}
