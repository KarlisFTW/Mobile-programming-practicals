package com.example.mobileprogrammingpracticals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AudioAdapter(
    private val audioFiles: List<String>,
    private var playingPosition: Int, // Keep track of the currently playing item
    private val listener: AudioItemClickListener
) : RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.audio_item, parent, false)
        return AudioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val audioFile = audioFiles[position]
        holder.bind(audioFile)

        holder.itemView.setOnClickListener {
            listener.onAudioClicked(audioFile, position) // Notify the activity of the click event
        }
    }

    override fun getItemCount(): Int {
        return audioFiles.size
    }

    fun updatePlayingPosition(newPosition: Int) {
        val previousPosition = playingPosition
        playingPosition = newPosition

        // Notify changes to only the affected items
        notifyItemChanged(previousPosition)
        notifyItemChanged(newPosition)
    }

    inner class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val audioName: TextView = itemView.findViewById(R.id.tv_record_name)
        private val audioDate: TextView = itemView.findViewById(R.id.tv_record_date)

        fun bind(audioFile: String) {
            val formattedDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(File(audioFile).lastModified())
            audioName.text = itemView.context.getString(R.string.record_name, adapterPosition + 1)
            audioDate.text = itemView.context.getString(R.string.record_date, formattedDate)

            // Change text color based on whether it's the currently playing item

        }
    }
}




