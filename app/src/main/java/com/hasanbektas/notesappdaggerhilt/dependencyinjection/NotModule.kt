package com.hasanbektas.notesappdaggerhilt.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.hasanbektas.notesappdaggerhilt.repository.NoteRepository
import com.hasanbektas.notesappdaggerhilt.roomdb.NotesDao
import com.hasanbektas.notesappdaggerhilt.roomdb.NotesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NotModule  {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, NotesDatabase::class.java,"notesDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao (database : NotesDatabase) = database.notesDao()

    @Singleton
    @Provides
    fun injectNormalrepo(dao : NotesDao) = NoteRepository(dao)

}