package com.example.mobileprogrammingpracticals

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Practical3_1: AppCompatActivity() {
    lateinit var backButton: Button
    lateinit var toolbar: Toolbar
    lateinit var toolbarText: TextView
    lateinit var preferenceText: TextView
    lateinit var showPreferenceValue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical3_1)

        preferenceText = findViewById(R.id.tv_preference_text)
        showPreferenceValue = findViewById(R.id.bt_read_preferences)
        toolbar = findViewById(R.id.tb_main)
        toolbarText=findViewById(R.id.tb_title_text)
        toolbarText.text = getString(R.string.practical3_1_toolbar_title)
        backButton = findViewById(R.id.bt_go_back)
        backButton.setOnClickListener {
            finish()
        }
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        showPreferenceValue.setOnClickListener {
            checkPreferences()
        }
    }

    private fun checkPreferences(){
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedText = sharedPrefs.getString("inputText", "")
        if(savedText != null){
            preferenceText.text = savedText.toString()
        }else{
            Toast.makeText(this, "No text found", Toast.LENGTH_SHORT).show()
        }


    }
}