package com.example.mobileprogrammingpracticals

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Practical2_1 : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    private lateinit var toolbarText: TextView
    private lateinit var goBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical2_1)
        toolbar = findViewById(R.id.tb_main)
        toolbarText = findViewById(R.id.tb_title_text)
        toolbarText.text = getString(R.string.practical2_1_toolbar_title)
        goBack = findViewById(R.id.bt_go_back_to_first_activity)
        goBack.setOnClickListener {
            finish()
        }
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }

    }
}