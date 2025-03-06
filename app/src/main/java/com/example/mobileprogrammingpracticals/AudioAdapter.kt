package com.example.mobileprogrammingpracticals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class AudioAdapter(
    private val audioFiles: List<String>,
    private val listener: AudioItemClickListener
) : RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.audio_item, parent, false)
        return AudioViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        val audioFile = audioFiles[position]
        holder.bind(audioFile)

        holder.itemView.setOnClickListener {
            listener.onAudioClicked(audioFile, position)
        }
    }

    override fun getItemCount(): Int = audioFiles.size

    inner class AudioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val audioName: TextView = itemView.findViewById(R.id.tv_record_name)
        private val audioDate: TextView = itemView.findViewById(R.id.tv_record_date)

        fun bind(audioFile: String) {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                val formattedDate = SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss",
                    Locale.getDefault()
                ).format(File(audioFile).lastModified())

                audioName.text = itemView.context.getString(R.string.record_name, bindingAdapterPosition + 1)
                audioDate.text = itemView.context.getString(R.string.record_date, formattedDate)
            }
        }
    }
}
