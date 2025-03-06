package com.example.mobileprogrammingpracticals

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private lateinit var practical1: Button
    private lateinit var practical2: Button
    lateinit var toolbar: Toolbar
    private lateinit var toolbarText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideSystemUI()
        practical1 = findViewById(R.id.bt_practical_1)
        practical2 = findViewById(R.id.bt_practical_2)
        toolbar = findViewById(R.id.tb_main)
        toolbarText = findViewById(R.id.tb_title_text)
        toolbarText.text = getString(R.string.main_toolbar_title)

        practical1.setOnClickListener {
            val intent = Intent(this, Practical1::class.java)
            startActivity(intent)
        }
        practical2.setOnClickListener {
            val intent = Intent(this, Practical2::class.java)
            startActivity(intent)
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
}