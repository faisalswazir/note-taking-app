package com.example.notes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.database.Note
import com.example.notes.convertLongToDateString
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
        val cardview : CardView= view.findViewById(R.id.cardView)
        val title_textview : TextView = view.findViewById(R.id.title_textview)
        val body_textview : TextView = view.findViewById(R.id.body_textview)
        val date_created_textview : TextView = view.findViewById(R.id.date_created)
        val date_modified_textview : TextView = view.findViewById(R.id.date_modified)

        fun bind(clickListener: NoteClickListener,item: Note){

            if (item.title.isEmpty()){
                // if title is empty it will not show empty textView
                title_textview.visibility = View.GONE
            }else{
                title_textview.visibility = View.VISIBLE
                title_textview.text   = item.title
            }
            //if the text in body is very short the date textview will move up from the bottom corner and ruin the consistent look
            //so im adding few lines to inscrease the short text to minimum of 4 lines
            if (item.body.lines().size < 4){
                item.body = item.body + "\n\n\n"
            }
            body_textview.text   = item.body
            date_created_textview.text = "Created - " + convertLongToDateString(item.dateCreated)
            date_modified_textview.text = "Modified - " + convertLongToDateString(item.dateModified)
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
