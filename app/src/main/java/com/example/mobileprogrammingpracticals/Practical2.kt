package com.example.mobileprogrammingpracticals

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Practical2 : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    private lateinit var toolbarText: TextView
    private lateinit var nextActivity: Button
    private lateinit var openDialog: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical2)
        toolbar = findViewById(R.id.tb_main)
        toolbarText = findViewById(R.id.tb_title_text)
        toolbarText.text = getString(R.string.practical2_toolbar_title)
        nextActivity = findViewById(R.id.bt_go_to_2nd_activity)
        openDialog = findViewById(R.id.bt_open_dialog)
        nextActivity.setOnClickListener {
            val intent = Intent(this, Practical2_1::class.java)
            startActivity(intent)
        }
        openDialog.setOnClickListener {
            showAlertDialog()
        }
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_back)
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun showAlertDialog() {
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)

        val builder = AlertDialog.Builder(this)

        builder.setView(dialogView)
        val dialog = builder.create()
        val checkbox1 = dialogView.findViewById<CheckBox>(R.id.cb_member_1)
        val checkbox2 = dialogView.findViewById<CheckBox>(R.id.cb_member_2)
        val checkbox3 = dialogView.findViewById<CheckBox>(R.id.cb_member_3)

        checkbox1.setOnClickListener {
            if (checkbox1.isChecked) {
                showToast(getString(R.string.checked_label, checkbox1.text))
            } else {
                showToast(getString(R.string.unchecked_label, checkbox1.text))
            }

        }

        checkbox2.setOnClickListener {
            if (checkbox2.isChecked) {
                showToast(getString(R.string.checked_label, checkbox2.text))
            } else {
                showToast(getString(R.string.unchecked_label, checkbox2.text))
            }
        }

        checkbox3.setOnClickListener {
            if (checkbox3.isChecked) {
                showToast(getString(R.string.checked_label, checkbox3.text))
            } else {
                showToast(getString(R.string.unchecked_label, checkbox3.text))
            }
        }
        val closeButton = dialogView.findViewById<Button>(R.id.bt_close)
        val okButton = dialogView.findViewById<Button>(R.id.bt_ok)

        // Set up the button listeners
        okButton.setOnClickListener {
            showToast(getString(R.string.you_clicked_ok_text))
        }

        closeButton.setOnClickListener {
            showToast(getString(R.string.you_clicked_close_text))
            dialog.dismiss()

        }
        dialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }
}
