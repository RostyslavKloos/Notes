package com.example.notes.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.ui.adapter.NotesAdapter
import com.example.notes.ui.viewmodel.NotesViewModel

class SwipeToDelete(private var adapter: NotesAdapter, private val viewModel: NotesViewModel): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.notifyItemMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val pos = viewHolder.adapterPosition
        val currentItem = adapter.getNotes(pos)
        viewModel.deleteNotes(currentItem)
        adapter.deleteItem(pos)
    }
}