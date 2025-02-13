package com.example.mobileprogrammingpracticals

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import java.io.File

class Practical1_1 : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var toolbarText: TextView
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var recordButton: Button
    private lateinit var rvAudio: RecyclerView
    private lateinit var audioAdapter: AudioAdapter
    private val audioFiles = mutableListOf<String>()

    private var mediaRecorder: MediaRecorder? = null
    private var outputFile: String? = null
    private var isRecording = false
    private var mediaPlayer: MediaPlayer? = null

    private var currentlyPlayingPosition: Int? = null  // Variable to track currently playing audio

    companion object {
        private const val REQUEST_RECORD_AUDIO_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical1_1)
        hideSystemUI()

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        toolbar = findViewById(R.id.tb_main)
        toolbarText = findViewById(R.id.tb_title_text)
        toolbarText.text = getString(R.string.practical1_1_toolbar_title)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_back)
        toolbar.setNavigationOnClickListener { finish() }

        rvAudio = findViewById(R.id.rv_audio)
        rvAudio.layoutManager = LinearLayoutManager(this)

        // Pass the currentlyPlayingPosition to the adapter
        audioAdapter = AudioAdapter(
            audioFiles,
            currentlyPlayingPosition ?: -1,
            object : AudioItemClickListener {
                override fun onAudioClicked(audioFile: String, position: Int) {
                    playAudio(audioFile)
                }
            })

        // Passing currentlyPlayingPosition

        rvAudio.adapter = audioAdapter

        recordButton = findViewById(R.id.record_button)
        recordButton.setOnClickListener {
            if (checkPermissions()) {
                logEvent(getString(R.string.record_audio))
                recordAudio()
            }
        }

        loadAudioFiles()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun checkPermissions(): Boolean {
        val permissions = mutableListOf(Manifest.permission.RECORD_AUDIO)

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        return if (missingPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                missingPermissions.toTypedArray(),
                REQUEST_RECORD_AUDIO_PERMISSION
            )
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                recordAudio()
            }
        }
    }

    private fun recordAudio() {
        if (isRecording) {
            mediaRecorder?.apply {
                stop()
                release()
            }
            mediaRecorder = null
            isRecording = false
            recordButton.text = getString(R.string.start_recording)

            audioFiles.add(outputFile!!)
            audioAdapter.notifyItemInserted(audioFiles.size - 1)

            logEvent(getString(R.string.audio_recorded))
        } else {
            outputFile = "${externalCacheDir?.absolutePath}/audio_${System.currentTimeMillis()}.mp3"

            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
                setOutputFile(outputFile)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)

                try {
                    prepare()
                    start()
                    isRecording = true
                    recordButton.text = getString(R.string.stop_recording)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            logEvent(getString(R.string.recording_started))
        }
    }

    private fun deleteAudio() {
        for (filePath in audioFiles) {
            File(filePath).delete()
        }
        audioFiles.clear()
        audioAdapter.notifyDataSetChanged()

        logEvent(getString(R.string.delete_audio))
    }

    private fun loadAudioFiles() {
        val audioDir = externalCacheDir
        audioDir?.listFiles()?.filter { it.extension == "mp3" }?.forEach {
            audioFiles.add(it.absolutePath)
        }
        audioAdapter.notifyDataSetChanged()
    }

    private fun playAudio(audioFile: String) {
        mediaPlayer?.release() // Release any existing media player instance

        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioFile)
            prepare()
            start()
            setOnCompletionListener {
                Toast.makeText(
                    this@Practical1_1,
                    "Playback finished: ${getRecordName(audioFile)}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        Toast.makeText(this, "Playing: ${getRecordName(audioFile)}", Toast.LENGTH_SHORT).show()
        logEvent(getString(R.string.audio_played))
    }

    // Helper function to extract record name (e.g., "Recording 1")
    private fun getRecordName(audioFile: String): String {
        val index = audioFiles.indexOf(audioFile)
        return if (index != -1) "Recording ${index + 1}" else getString(R.string.unknown_recording)
    }

    private fun logEvent(eventName: String) {
        val bundle = Bundle()
        bundle.putString("event_name", eventName)
        firebaseAnalytics.logEvent(eventName, bundle)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.audiomenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_back_to_image -> {
                finish()
                logEvent(getString(R.string.open_image_activity))
                true
            }

            R.id.menu_delete_audio -> {
                deleteAudio()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        mediaPlayer?.release() // Release media player resources when activity is stopped
    }
}

interface AudioItemClickListener {
    fun onAudioClicked(audioFile: String, position: Int)
}

