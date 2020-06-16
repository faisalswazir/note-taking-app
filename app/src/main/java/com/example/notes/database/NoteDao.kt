package com.example.notes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    fun insert(note : Note)

    @Query("SELECT * FROM notes_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>


    @Query("DELETE FROM notes_table")
    fun clear()

    @Update
    fun update(note : Note)

    @Query("Select * FROM NOTES_TABLE Where noteId = :id")
    fun getNote(id : Long) : LiveData<Note>

    @Query("Select * FROM NOTES_TABLE Where noteId = :id")
    fun getNoteToUpdate(id : Long) : Note
}