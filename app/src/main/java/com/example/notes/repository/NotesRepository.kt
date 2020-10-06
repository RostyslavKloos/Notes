package com.example.notes.repository

import androidx.lifecycle.LiveData
import com.example.notes.data.NotesDao
import com.example.notes.model.Notes

class NotesRepository(private val notesDao: NotesDao) {

    val getAllNotes: LiveData<List<Notes>> = notesDao.getAllNotes()

    suspend fun addNotes(notes: Notes) {
        notesDao.addNotes(notes)
    }

    suspend fun updateNotes(notes: Notes) {
        notesDao.addNotes(notes)
    }

    suspend fun deleteNotes(notes: Notes) {
        notesDao.deleteNotes(notes)
    }

    suspend fun deleteAllNotes() {
        notesDao.deleteAllNotes()
    }
}