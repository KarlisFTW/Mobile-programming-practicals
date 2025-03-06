package com.example.mobileprogrammingpracticals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ApiAdapter(
    private val posts: List<JSONObject>, // List of posts (JSON objects)
) : RecyclerView.Adapter<ApiAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.api_item, parent, false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val postTitle: TextView = itemView.findViewById(R.id.tv_record_name)
        private val postDate: TextView = itemView.findViewById(R.id.tv_record_date)

        fun bind(post: JSONObject) {
            // Get the title and body
            val title = post.getString("title")
            // Format the date (assuming you may have a date field, else use some default)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val formattedDate =
                dateFormat.format(Date()) // Modify if you have an actual date field in the API response

            // Set data to the views
            postTitle.text = title
            postDate.text =
                formattedDate // You can replace this with a real date field from the API response
        }
    }
}
