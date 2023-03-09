package com.hasanbektas.notesappdaggerhilt.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Notes (

    @ColumnInfo(name="title") var title:String?,
    @ColumnInfo(name="note") var note : String,
    @ColumnInfo(name="date") var date : String,

    ) : java.io.Serializable  {

    @PrimaryKey(autoGenerate = true)
    var notId = 0
}