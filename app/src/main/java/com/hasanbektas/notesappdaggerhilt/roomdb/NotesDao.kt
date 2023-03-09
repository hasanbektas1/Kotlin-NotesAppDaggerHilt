package com.hasanbektas.notesappdaggerhilt.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hasanbektas.notesappdaggerhilt.model.Notes

@Dao
interface NotesDao {

    @Insert
    suspend fun insertNot(notes: Notes)

    @Delete
    suspend fun deleteNot(notes: Notes)

    @Query("SELECT * FROM notes_table")
    fun observeNot() : LiveData<List<Notes>>

    @Query("SELECT * FROM notes_table WHERE notId = :noteId")
    suspend fun getNot(noteId : Int) : Notes

    @Query("SELECT * FROM notes_table WHERE title  LIKE '%' || :query || '%'")
    fun searchNote(query: String?): LiveData<List<Notes>>


}