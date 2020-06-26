package com.example.notes.editor

import androidx.lifecycle.ViewModel
import com.example.notes.database.Note
import com.example.notes.database.NoteDao
import kotlinx.coroutines.*

class EditorActivityViewModel(val database: NoteDao,val selectedNoteId: Long) : ViewModel() {
    private var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //get the note which user selected
    var selectedNote = database.getNote(selectedNoteId)

    private suspend fun insert(note : Note) {
        withContext(Dispatchers.IO) {
            database.insert(note)
        }
    }
    fun onInsert(title : String,body : String) {
        uiScope.launch {
            val newNote = Note(title = title,body = body)
            insert(newNote)
        }
    }

    private suspend fun update(newTitle: String,newBody: String) {
        withContext(Dispatchers.IO) {
            val note = database.getNoteToUpdate(selectedNoteId) ?: return@withContext
            note.title = newTitle
            note.body = newBody
            note.dateModified = System.currentTimeMillis()
            database.update(note)
        }
    }
    fun onUpdate(newTitle: String,newBody: String) {
        uiScope.launch {
            update(newTitle,newBody)
        }
    }

    // delete functionality still pemding

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}