package com.example.mobileprogrammingpracticals

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Practical2: AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var nextActivity: Button
    lateinit var openDialog: Button

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_practical2)
        toolbar = findViewById(R.id.tb_main)
        toolbar.title = "1-st Activity"
        nextActivity = findViewById(R.id.bt_go_to_2nd_activity)
        openDialog = findViewById(R.id.bt_open_dialog)
        nextActivity.setOnClickListener {
            val intent = Intent(this, Practical2_1::class.java)
            startActivity(intent)
        }
        openDialog.setOnClickListener {
            showAlertDialog()
        }

    }
    private fun showAlertDialog() {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.custom_dialog, null)

        // Create the AlertDialog Builder
        val builder = AlertDialog.Builder(this)

        // Set the custom view for the AlertDialog
        builder.setView(dialogView)
        val dialog = builder.create()
        // Find views in the dialog layout
        val checkbox1 = dialogView.findViewById<CheckBox>(R.id.cb_member_1)

        checkbox1.setOnClickListener {
            showToast("${checkbox1.text} {${checkbox1.isChecked}}")
        }
        val checkbox2 = dialogView.findViewById<CheckBox>(R.id.cb_member_2)
        checkbox2.setOnClickListener {
            showToast("${checkbox2.text} {${checkbox2.isChecked}}")
        }
        val checkbox3 = dialogView.findViewById<CheckBox>(R.id.cb_member_3)
        checkbox3.setOnClickListener {
            showToast("${checkbox3.text} {${checkbox3.isChecked}}")
        }
        val closeButton = dialogView.findViewById<Button>(R.id.bt_close)
        val okButton = dialogView.findViewById<Button>(R.id.bt_ok)

        // Set up the button listeners
        okButton.setOnClickListener {
            showToast("You clicked OK!")
        }

        closeButton.setOnClickListener {
            dialog.dismiss()
            showToast("You clicked Close!")
        }
        dialog.show()
    }

    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

    }
}
