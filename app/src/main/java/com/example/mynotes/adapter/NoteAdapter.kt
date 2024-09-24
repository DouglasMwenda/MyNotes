package com.example.mynotes.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.models.Note
import kotlin.random.Random

private var CardView.text: String?
    get() {}
    set(value) {}

class NotesAdapter(context: Context) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private val notelist = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_note, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notelist.size
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {

        val currentNote = notelist[position]
        holder.title.text =currentNote.title
        holder.title.isSelected=true

        holder.note_tv.text=currentNote.note

        holder.date.text=currentNote.date
        holder.date.isSelected= true
        holder.notes_layout.setCardBackgroundColor(holder.itemView.resources.getColor(randomColor(),null))

    }

    fun randomColor(): Int{
        val list = ArrayList<Int>()
        list.add(R.color.Note1)
        list.add(R.color.Note2)
        list.add(R.color.Note3)
        list.add(R.color.Note4)
        list.add(R.color.Note5)
        list.add(R.color.Note6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex= Random(seed).nextInt(list.size)
        return list[randomIndex]


    }

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val notes_layout = itemView.findViewById<CardView>(R.id.card_Layout)
        val title =itemView.findViewById<CardView>(R.id.title)
        val note_tv =itemView.findViewById<CardView>(R.id.note)
        val date =itemView.findViewById<CardView>(R.id.date)
    }
}