package com.example.notes.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var noteId : Long = 0L,
    @ColumnInfo(name = "title")
    var title : String = "no_title",
    @ColumnInfo(name = "body")
    var body : String = "no_body"
)