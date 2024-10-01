package com.example.mobileprogrammingpracticals

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Practical3_1 : AppCompatActivity() {
    private lateinit var backButton: Button
    lateinit var toolbar: Toolbar
    private lateinit var toolbarText: TextView
    private lateinit var preferenceText: TextView
    private lateinit var showPreferenceValue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical3_1)

        preferenceText = findViewById(R.id.tv_preference_text)
        showPreferenceValue = findViewById(R.id.bt_read_preferences)
        toolbar = findViewById(R.id.tb_main)
        toolbarText = findViewById(R.id.tb_title_text)
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

    private fun checkPreferences() {
        val sharedPrefs = getSharedPreferences(getString(R.string.myprefs_label), MODE_PRIVATE)
        val savedText = sharedPrefs.getString(getString(R.string.inputtext_editor_label), "")
        if (savedText != null && savedText != "") {
            preferenceText.text = savedText.toString()
            showToast(getString(R.string.saved_text_retrieved_successfully_text))
        } else {
            showToast(getString(R.string.no_text_found_text))
        }


    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}