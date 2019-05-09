package com.pfariasmunoz.simplevideoview

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

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
    private fun getMedia(mediaName: String): Uri = Uri.parse("android:resource://$packageName/raw/$mediaName")
}
