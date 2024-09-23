package com.example.mobileprogrammingpracticals

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar

class Practical3 : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var toolbarText: TextView
    lateinit var inputText: EditText
    lateinit var nextActivity: Button
    lateinit var saveText: Button
    lateinit var chooseTheme: Spinner
    var selectedTheme = "Default"
    //val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical3)
        toolbar = findViewById(R.id.tb_main)
        toolbarText=findViewById(R.id.tb_title_text)
        toolbarText.text = getString(R.string.practical3_toolbar_title)
        inputText = findViewById(R.id.et_input_text)
        nextActivity = findViewById(R.id.bt_go_to_next_activity)
        saveText = findViewById(R.id.bt_save_text)
        chooseTheme = findViewById(R.id.sp_themes)
        nextActivity.setOnClickListener {
            val intent = Intent(this, Practical3_1::class.java)
            startActivity(intent)
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
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("inputText", inputText.text.toString())
        editor.apply()
    }

    private fun checkIfTextExists() {
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedText = sharedPrefs.getString("inputText", "")
        if (savedText != null) {
            inputText.hint = savedText
        }
    }

    private fun setUpSpinner() {
        val option = arrayOf("Default", "Dark Theme", "Light Theme")
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

}
