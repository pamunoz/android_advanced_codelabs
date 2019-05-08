package com.pfariasmunoz.surfaceviewexample

import android.content.Context
import android.view.SurfaceView

/**
 * A custom SurfaceView where game play takes place. Responds to motion events on the screen.
 * Draws the game screen in a separate thread, with the flashlight cone at the current position
 * of the user's finger. Shows the "win" message when winning conditions are met.
 */
class GamveView(context: Context?) : SurfaceView(context), Runnable {
    override fun run() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun pause() {

    }

    fun resume() {

    }

}