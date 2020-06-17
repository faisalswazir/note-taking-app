package com.example.notes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.database.Note

class NotesAdapter(val clickListener: NoteClickListener) : ListAdapter<Note, NotesAdapter.ViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        val height = parent.measuredHeight / 4
        //view.layoutParams.height = height
        view.minimumHeight = height
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener,item)
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val title_textview : TextView = view.findViewById(R.id.title_textview)
        val body_textview : TextView = view.findViewById(R.id.body_textview)
        fun bind(clickListener: NoteClickListener,item: Note){
//            title_textview.text = item.title
//            body_textview.text = item.body

            if (item.title.length > 20){
                title_textview.text   = item.title.substring(0,20) + "..."
            }else{
                title_textview.text   = item.title
            }

            if (item.body.length > 40){
                body_textview.text   = item.body.substring(0,40) + "..."
            }else{
                body_textview.text   = item.body
            }

            itemView.setOnClickListener{
                clickListener.onClick(item)
            }
        }
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}

interface NoteClickListener{
    fun onClick(note: Note)
}
