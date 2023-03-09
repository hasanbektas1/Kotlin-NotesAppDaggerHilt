package com.hasanbektas.notesappdaggerhilt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasanbektas.notesappdaggerhilt.model.Notes
import com.hasanbektas.notesappdaggerhilt.repository.NoteRepository
import com.hasanbektas.notesappdaggerhilt.roomdb.NotesDatabase
import com.hasanbektas.notesappdaggerhilt.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
    private val repository: NoteRepository,
    private val database : NotesDatabase
): ViewModel() {

    val selectedNotLiveData = MutableLiveData<Notes>()

    private var insertNotMsg = MutableLiveData<Resource<Notes>>()
    val insertNotMessage : LiveData<Resource<Notes>>
        get() = insertNotMsg


    fun resetInsertNotMsg() {
        insertNotMsg = MutableLiveData<Resource<Notes>>()
    }

    fun insertNot(not:Notes) = viewModelScope.launch {
        repository.insertNote(not)
    }

    fun makeNot(title: String, note: String, date: String){

        if (title.isEmpty() || note.isEmpty() || date.isEmpty()){

            insertNotMsg.postValue(Resource.error("Başlık ve Notu Giriniz",null))
            return
        }

        val addNote = Notes(title,note,date)
        insertNot(addNote)
        insertNotMsg.postValue(Resource.success(addNote))
    }

    fun getDataSelectedNote(notId: Int) = viewModelScope.launch {

        val dao = database.notesDao()
        val selectedNote = dao.getNot(notId)
        selectedNotLiveData.value = selectedNote

    }
}
