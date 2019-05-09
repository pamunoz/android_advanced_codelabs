package com.pfariasmunoz.simplevideoview

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val VIDEO_SAMPLE = "tacoma_narrows"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
        vv_video.start()
    }

    private fun releasePlayer() = vv_video.stopPlayback()

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
