package com.example.mobileprogrammingpracticals

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    lateinit var practical1: Button
    lateinit var practical2: Button
    lateinit var practical3: Button
    lateinit var practical4: Button
    lateinit var toolbar: Toolbar
    lateinit var toolbarText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        practical1 = findViewById(R.id.bt_practical_1)
        practical2 = findViewById(R.id.bt_practical_2)
        practical3 = findViewById(R.id.bt_practical_3)
        practical4 = findViewById(R.id.bt_practical_4)
        toolbar=findViewById(R.id.tb_main)
        toolbarText=findViewById(R.id.tb_title_text)
        toolbarText.text = getString(R.string.main_toolbar_title)


        practical1.setOnClickListener {
            val intent = Intent(this, Practical1::class.java)
            startActivity(intent)
        }
        practical2.setOnClickListener {
            val intent = Intent(this, Practical2::class.java)
            startActivity(intent)
        }
        practical3.setOnClickListener {
            val intent = Intent(this, Practical3::class.java)
            startActivity(intent)
        }
        practical4.setOnClickListener {
            val intent = Intent(this, Practical4::class.java)
            startActivity(intent)
        }
    }
}