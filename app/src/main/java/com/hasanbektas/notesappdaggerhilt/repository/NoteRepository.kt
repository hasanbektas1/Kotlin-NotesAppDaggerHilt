package com.hasanbektas.notesappdaggerhilt.repository

import androidx.lifecycle.LiveData
import com.hasanbektas.notesappdaggerhilt.model.Notes
import com.hasanbektas.notesappdaggerhilt.roomdb.NotesDao
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao : NotesDao
) {
    suspend fun insertNote(note: Notes) {
        noteDao.insertNot(note)
    }

    suspend fun deleteNote(note: Notes) {
        noteDao.deleteNot(note)
    }

    fun getNotes(): LiveData<List<Notes>> {
        return noteDao.observeNot()
    }

    fun searchNote(query: String?) = noteDao.searchNote(query)

}