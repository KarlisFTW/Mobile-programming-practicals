package com.example.mobileprogrammingpracticals

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class Practical2_1 : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var toolbarText: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var apiAdapter: ApiAdapter
    private val postsList = mutableListOf<JSONObject>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_practical2_1)
        Log.e(getString(R.string.practical2_1), getString(R.string.oncreate_called))
        toolbar = findViewById(R.id.tb_main)
        toolbarText = findViewById(R.id.tb_title_text)
        recyclerView = findViewById(R.id.rv_api_data)

        toolbarText.text = getString(R.string.practical2_1_toolbar_title)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""
        toolbar.setNavigationIcon(R.drawable.toolbar_navigation_back)
        toolbar.setNavigationOnClickListener { finish() }

        hideSystemUI()
        recyclerView.layoutManager = LinearLayoutManager(this)
        apiAdapter = ApiAdapter(postsList)
        recyclerView.adapter = apiAdapter

        fetchDataFromAPI()
    }

    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun fetchDataFromAPI() {
        // Replace this URL with your chosen API endpoint
        val url = getString(R.string.https_jsonplaceholder_typicode_com_posts)

        // Create a new Volley request queue
        val queue = Volley.newRequestQueue(this)

        // Create a JsonArrayRequest to fetch the data (instead of JsonObjectRequest)
        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->
                // Successfully received the response, process and display it
                postsList.clear() // Clear previous data

                // Extract posts from the response (directly as a JSONArray)
                for (i in 0 until response.length()) {
                    val post: JSONObject = response.getJSONObject(i)
                    postsList.add(post)
                }

                // Notify the adapter that data has changed
                apiAdapter.notifyDataSetChanged()
            },
            { error ->
                // Handle the error
                error.printStackTrace()
            })

        // Add the request to the queue
        queue.add(jsonArrayRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.readmenu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_go_back_to_maps -> {
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
