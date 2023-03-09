package com.hasanbektas.notesappdaggerhilt.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hasanbektas.notesappdaggerhilt.model.Notes


@Database(entities = [Notes::class],version = 1)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun notesDao():NotesDao
}