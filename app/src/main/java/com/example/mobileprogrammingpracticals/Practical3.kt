package com.example.mobileprogrammingpracticals

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar

class Practical3 : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    private lateinit var toolbarText: TextView
    private lateinit var inputText: EditText
    private lateinit var nextActivity: Button
    private lateinit var saveText: Button
    private lateinit var resetPreference: Button
    private lateinit var chooseTheme: Spinner
    private lateinit var selectedTheme : String

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
        resetPreference = findViewById(R.id.bt_reset_preferences)
        selectedTheme = getString(R.string.default_label)
        nextActivity.setOnClickListener {
            val intent = Intent(this, Practical3_1::class.java)
            startActivity(intent)
        }
        resetPreference.setOnClickListener {
            resetPreferences()
        }
        saveText.setOnClickListener {
            saveText()
        }
        setUpSpinner()
        checkIfTextExists()

        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun saveText() {
        val sharedPrefs = getSharedPreferences(getString(R.string.myprefs_label), MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(getString(R.string.inputtext_editor_label), inputText.text.toString())
        editor.apply()
        showToast(inputText.text.toString() + getString(R.string.text_saved_text))
    }

    private fun checkIfTextExists() {
        val sharedPrefs = getSharedPreferences(getString(R.string.myprefs_label), MODE_PRIVATE)
        val savedText = sharedPrefs.getString(getString(R.string.inputtext_editor_label), "")
        if (savedText != null && savedText != "") {
            inputText.hint = getString(R.string.text_saved_text) + ": $savedText"
        } else {
            inputText.hint = getString(R.string.input_text_with_colon)
        }
    }

    private fun setUpSpinner() {
        val option = arrayOf(getString(R.string.default_label),
            getString(R.string.dark_theme_label), getString(R.string.light_theme_label))
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, option)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        chooseTheme.adapter = adapter
        chooseTheme.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedTheme = option[position]
                when (position) {
                    1 -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }

                    2 -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }

                    else -> {


                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun resetPreferences() {
        val sharedPrefs = getSharedPreferences(getString(R.string.myprefs_label), MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.remove(getString(R.string.inputtext_editor_label))
        editor.apply()
        showToast(getString(R.string.preferences_reset_textt))
        inputText.hint = getString(R.string.input_text_with_colon)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
