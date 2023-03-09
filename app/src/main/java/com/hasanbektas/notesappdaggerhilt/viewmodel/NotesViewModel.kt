package com.hasanbektas.notesappdaggerhilt.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasanbektas.notesappdaggerhilt.model.Notes
import com.hasanbektas.notesappdaggerhilt.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository : NoteRepository

): ViewModel(){

    var noteList = repository.getNotes()

    fun deleteNot(not : Notes) = viewModelScope.launch {
        repository.deleteNote(not)
    }

    fun searchNote(query: String?) = repository.searchNote(query)


}