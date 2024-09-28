package com.example.mobileprogrammingpracticals

import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import net.objecthunter.exp4j.ExpressionBuilder

class Practical4 : AppCompatActivity() {
    private var memory: Double? = null
    private var currentOperator: String? = null
    private var currentValue: Double = 0.0
    private lateinit var resultTextView: TextView
    lateinit var toolbar: Toolbar
    lateinit var toolbarText: TextView
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
        val newText = if (currentText == "0") digit else currentText + digit
        resultTextView.text = newText

    }

    fun onOperatorClick(view: android.view.View) {
        val operator = (view as android.widget.Button).hint.toString()
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
            resultTextView.text = "Error"
        }
    }

    fun onLastDeleteClick(view: android.view.View) {
        val currentText = resultTextView.text.toString()
        if (currentText.isNotEmpty()) {
            val newText = currentText.substring(0, currentText.length - 1)
            resultTextView.text = newText.ifEmpty { "0" }
        }
    }

    fun onFullDeleteClick(view: android.view.View) {
        resultTextView.text = ""
    }


    // Memory Functions
    fun onMemorySave(view: android.view.View) {
        try {
            saveValueToPreferences(resultTextView.text.toString())
            toast("Text saved")
        } catch (e: Exception) {
            toast("No text found")
        }

    }

    fun onMemoryRead(view: android.view.View) {
        try {
            resultTextView.append(getValueFromPreferences())
            if(resultTextView.text.isNotEmpty()){
                toast("Text read")
            }

        } catch (e: Exception) {
            toast("No text found")
        }
    }

    fun onMemoryClear(view: android.view.View) {
        try {
            deleteValueFromPreferences()
            toast("Text deleted")
        } catch (e: Exception) {
            toast("No text found")
        }
    }

    private fun getValueFromPreferences(): String? {
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val savedText = sharedPrefs.getString("savedValue", "")
        if (savedText != null) {
            return savedText.toString()
        } else {
            toast("No text found")
            return null
        }
    }

    private fun saveValueToPreferences(value: String) {
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("savedValue", value)
        editor.apply()

    }

    private fun deleteValueFromPreferences() {
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        if (editor != null) {
            editor.remove("savedValue")
            editor.apply()
        } else {
            toast("No text found")
        }

    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}