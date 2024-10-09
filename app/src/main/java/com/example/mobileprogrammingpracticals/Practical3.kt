package com.example.mobileprogrammingpracticals

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Practical3 : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarText: TextView
    private lateinit var inputText: EditText
    private lateinit var nextActivity: Button
    private lateinit var saveText: Button
    private lateinit var resetPreferences: Button
    private lateinit var chooseTheme: Spinner
    private var selectedTheme: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical3)

        toolbar = findViewById(R.id.tb_main)
        toolbarText = findViewById(R.id.tb_title_text)
        toolbarText.text = getString(R.string.practical3_toolbar_title)
        inputText = findViewById(R.id.et_input_text)
        nextActivity = findViewById(R.id.bt_go_to_next_activity)
        saveText = findViewById(R.id.bt_save_text)
        chooseTheme = findViewById(R.id.sp_themes)
        resetPreferences = findViewById(R.id.bt_reset_preferences)

        resetPreferences.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                applicationContext.dataStore.edit { preferences ->
                    preferences.clear()
                }
                withContext(Dispatchers.Main) {
                    // Now it's on the main thread, so it's safe to show the Toast
                    showToast("Memory was reset!")
                    loadSavedText()
                }
            }
        }

        nextActivity.setOnClickListener {
            val intent = Intent(this, Practical3_1::class.java)
            startActivity(intent)
        }

        saveText.setOnClickListener {
            saveTextToDataStore()
        }

        setUpSpinner()
        loadSavedText()
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_back)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun saveTextToDataStore() {
        val text = inputText.text.toString()
        CoroutineScope(Dispatchers.IO).launch {
            applicationContext.dataStore.edit { preferences ->
                preferences[stringPreferencesKey("inputText")] = text
            }
            withContext(Dispatchers.Main) {
                showToast("$text ${getString(R.string.text_saved_text)}")
            }
        }
    }

    private fun loadSavedText() {
        CoroutineScope(Dispatchers.IO).launch {
            val preferences = applicationContext.dataStore.data.first()
            val savedText = preferences[stringPreferencesKey("inputText")] ?: ""
            withContext(Dispatchers.Main) {
                inputText.hint = getString(R.string.stored_text_text) + savedText
            }
        }
    }

    private fun setUpSpinner() {
        val options = arrayOf(
            getString(R.string.default_label),
            getString(R.string.dark_theme_label),
            getString(R.string.light_theme_label)
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        chooseTheme.adapter = adapter
        chooseTheme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                selectedTheme = options[position]
                when (position) {
                    1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        loadSavedText()
    }
}
