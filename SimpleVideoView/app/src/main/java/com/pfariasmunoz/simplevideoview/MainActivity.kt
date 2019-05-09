package com.pfariasmunoz.simplevideoview

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mCurrentPosition = 0

    companion object {
        const val VIDEO_SAMPLE = "tacoma_narrows"
        const val PLAYBACK_TIME = "play_time    "
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let { mCurrentPosition = savedInstanceState.getInt(PLAYBACK_TIME) }

        val controller = MediaController(this).apply {
            setMediaPlayer(vv_video)
        }
        vv_video.setMediaController(controller)
    }

    /**
     * This method converts the name of the sample media file (a string) into a full URI object
     * representing the path to the sample in your app's resources. Note that although the
     * actual filename in the app's resource directory is tacoma_narrows.mp4,
     * the string name and resulting resource name do not include the extension.
     */
    private fun getMedia(mediaName: String): Uri = Uri.parse("android.resource://$packageName/raw/$mediaName")

    private fun initializePlayer() {
        val videoUri = getMedia(VIDEO_SAMPLE)
        vv_video.setVideoURI(videoUri)
        if (mCurrentPosition > 0) {
            vv_video.seekTo(mCurrentPosition)
        } else {
            // Skipping to 1 shows the first frame of the video
            vv_video.seekTo(1)
        }
        vv_video.start()

        vv_video.setOnCompletionListener {
            Toast.makeText(this@MainActivity, "Playback completed", Toast.LENGTH_SHORT).show()
            // reset to the beginning
            vv_video.seekTo(1)
        }
    }

    private fun releasePlayer() = vv_video.stopPlayback()

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt(PLAYBACK_TIME, vv_video.currentPosition)
    }

    override fun onStart() {
        super.onStart()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            vv_video.pause()
        }
    }
}
