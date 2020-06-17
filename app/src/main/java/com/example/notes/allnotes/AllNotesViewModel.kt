package com.example.notes.allnotes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.database.NoteDao
import kotlinx.coroutines.*

class AllNotesViewModel(val database: NoteDao,
                        application: Application
) : AndroidViewModel(application){
    private var viewModelJob = Job()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //retrieve all notes for recycler view
    val allNotes = database.getAllNotes()

    private val _navigateToEditor= MutableLiveData<Boolean>()
    val navigateToEditor : LiveData<Boolean>
        get() = _navigateToEditor

    fun navigateToEditor(){
        _navigateToEditor.value = true
    }

    fun doneNavigating(){
        _navigateToEditor.value = null
    }


    //to delete everything
    private suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }
    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}