package com.example.mobileprogrammingpracticals

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import net.objecthunter.exp4j.ExpressionBuilder

class Practical4 : AppCompatActivity() {
    private lateinit var resultTextView: TextView
    lateinit var toolbar: Toolbar
    private lateinit var toolbarText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical4)
        toolbar = findViewById(R.id.tb_main)
        toolbarText = findViewById(R.id.tb_title_text)
        resultTextView = findViewById(R.id.tv_show_result_process)
        toolbarText.text = getString(R.string.practical4_toolbar_title)
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }

    }

    fun onDigitClick(view: android.view.View) {
        val digit = (view as android.widget.Button).text.toString()
        val currentText = resultTextView.text.toString()
        val newText = if (currentText == getString(R.string.zero)) digit else currentText + digit
        resultTextView.text = newText

    }

    fun onOperatorClick(view: android.view.View) {
        val operator = (view as android.widget.Button).text.toString()
        if (resultTextView.text.isNotEmpty()) {
            resultTextView.append(operator)
        }
    }

    fun onEnterClick(view: android.view.View) {
        val expression = resultTextView.text.toString()
        try {
            val result = ExpressionBuilder(expression).build().evaluate()
            resultTextView.text = result.toString()
        } catch (e: Exception) {
            resultTextView.text = getString(R.string.error_label)
        }
    }

    fun onLastDeleteClick(view: android.view.View) {
        val currentText = resultTextView.text.toString()
        if (currentText.isNotEmpty()) {
            val newText = currentText.substring(0, currentText.length - 1)
            resultTextView.text = newText.ifEmpty { getString(R.string.zero) }
        }
    }

    fun onFullDeleteClick(view: android.view.View) {
        resultTextView.text = ""
    }


    // Memory Functions
    fun onMemorySave(view: android.view.View) {
        try {
            saveValueToPreferences(resultTextView.text.toString())
            toast(getString(R.string.text_saved_text))
        } catch (e: Exception) {
            toast(getString(R.string.no_text_found_text))
        }

    }

    fun onMemoryRead(view: android.view.View) {
        try {
            resultTextView.append(getValueFromPreferences())
            if (resultTextView.text.isNotEmpty()) {
                toast(getString(R.string.text_read_text))
            }

        } catch (e: Exception) {
            toast(getString(R.string.no_text_found_text))
        }
    }

    fun onMemoryClear(view: android.view.View) {
        try {
            deleteValueFromPreferences()
            toast(getString(R.string.text_deleted_text))
        } catch (e: Exception) {
            toast(getString(R.string.no_text_found_text))
        }
    }

    private fun getValueFromPreferences(): String? {
        val sharedPrefs = getSharedPreferences(getString(R.string.myprefs_label), MODE_PRIVATE)
        val savedText = sharedPrefs.getString(getString(R.string.savedvalue_editor_label), "")
        if (savedText != null) {
            return savedText.toString()
        } else {
            toast(getString(R.string.no_text_found_text))
            return null
        }
    }

    private fun saveValueToPreferences(value: String) {
        val sharedPrefs = getSharedPreferences(getString(R.string.myprefs_label), MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString(getString(R.string.savedvalue_editor_label), value)
        editor.apply()

    }

    private fun deleteValueFromPreferences() {
        val sharedPrefs = getSharedPreferences(getString(R.string.myprefs_label), MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        if (editor != null) {
            editor.remove(getString(R.string.savedvalue_editor_label))
            editor.apply()
        } else {
            toast(getString(R.string.no_text_found_text))
        }

    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}