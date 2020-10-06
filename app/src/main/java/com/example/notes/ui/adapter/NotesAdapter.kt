package com.example.notes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.NotesItemBinding
import com.example.notes.model.Notes

class NotesAdapter(private val listener: NotesItemListener) :
    RecyclerView.Adapter<NotesViewHolder>() {

    interface NotesItemListener {
        fun onClickedNotes(notes: Notes)
    }

    private val notes = ArrayList<Notes>()

    fun setNotes(notes: ArrayList<Notes>) {
        this.notes.clear()
        this.notes.addAll(notes)
        notifyDataSetChanged()
    }

    fun getNotes(pos: Int): Notes {
        return this.notes[pos]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val binding: NotesItemBinding =
            NotesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotesViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val currentNote = notes[position]
        holder.bind(currentNote)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun deleteItem(pos: Int) {
        notes.removeAt(pos)
        notifyItemRemoved(pos)
    }
}

class NotesViewHolder(
    private val itemBinding: NotesItemBinding,
    private val listener: NotesAdapter.NotesItemListener
) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {
    private lateinit var notes: Notes

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(notes: Notes) {
        this.notes = notes
        itemBinding.tvTitle.text = notes.title
        itemBinding.tvDescription.text = notes.description
    }

    override fun onClick(v: View?) {
        listener.onClickedNotes(notes)
    }
}