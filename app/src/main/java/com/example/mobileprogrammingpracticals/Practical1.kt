package com.example.mobileprogrammingpracticals

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.analytics.FirebaseAnalytics
import java.io.File
import java.io.FileOutputStream
import android.Manifest
import android.view.WindowManager

class Practical1 : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private lateinit var viewPager: ViewPager2
    private lateinit var takePictureButton: Button
    private val imageList = mutableListOf<String>()
    private lateinit var toolbarText: TextView
    private val REQUEST_CAMERA_PERMISSION = 101
    private val REQUEST_IMAGE_CAPTURE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical1)
        hideSystemUI()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        viewPager = findViewById(R.id.viewPager)
        toolbar = findViewById(R.id.tb_main)
        toolbarText = findViewById(R.id.tb_title_text)
        takePictureButton = findViewById(R.id.btnTakePicture)
        toolbarText.text = getString(R.string.practical1_toolbar_title)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        // Set up the navigation icon for the toolbar
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Button click listener for taking a picture
        takePictureButton.setOnClickListener {
            requestCameraPermission()
        }
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        } else {
            // Permission already granted, open camera
            openCamera()
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, open camera
                openCamera()
            } else {
                // Permission denied
                Toast.makeText(
                    this,
                    getString(R.string.camera_permission_denied), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.picturemenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_go_to_audio -> {
                startActivity(Intent(this, Practical1_1::class.java))
                logEvent(getString(R.string.open_audio_activity))
                return true
            }

            R.id.menu_show_images -> {
                if (imageList.isEmpty()) {
                    Toast.makeText(this, getString(R.string.no_images_to_show), Toast.LENGTH_SHORT)
                        .show()
                } else {

                    viewPager.visibility = View.VISIBLE
                    viewPager.adapter = ImageAdapter(this, imageList)
                    logEvent(getString(R.string.show_images))
                }


                return true
            }

            R.id.menu_delete_images -> {
                deleteImages()
                logEvent(getString(R.string.delete_images))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val file = File(getExternalFilesDir(null), "image_${System.currentTimeMillis()}.jpg")
            val fos = FileOutputStream(file)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.close()
            imageList.add(file.absolutePath)
            logEvent(getString(R.string.picture_taken))
        }
    }

    @SuppressLint("NotifyDataSetChanged")

    private fun deleteImages() {
        val dir = getExternalFilesDir(null)
        dir?.listFiles()?.forEach { it.delete() }
        imageList.clear()
        viewPager.adapter?.notifyDataSetChanged()
    }

    private fun logEvent(eventName: String) {
        val bundle = Bundle()
        bundle.putString("event_name", eventName)
        firebaseAnalytics.logEvent(eventName, bundle)
    }
}
