package com.example.notes.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.database.NoteDao

class EditorActivityViewModelFactory(
    private val dataSource: NoteDao,
    private val selectedNoteId : Long
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditorActivityViewModel::class.java)) {
            return EditorActivityViewModel(
                dataSource,
                selectedNoteId
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
