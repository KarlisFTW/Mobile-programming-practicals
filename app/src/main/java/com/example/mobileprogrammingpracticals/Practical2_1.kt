package com.example.mobileprogrammingpracticals

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class Practical2_1 : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var goBack: Button
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_practical2_1)
        toolbar = findViewById(R.id.tb_main)
        toolbar.title = "2nd Activity"
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