package com.example.notes.editor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.notes.R
import com.example.notes.database.NoteDatabase
import com.example.notes.databinding.ActivityEditorBinding

class EditorActivity : AppCompatActivity() {
    val SELECTED_NOTE_ID_EXTRA = "selectedNoteId"
    private lateinit var  viewModel : EditorActivityViewModel
    private lateinit var binding: ActivityEditorBinding
    private lateinit var toolbar: Toolbar
    private var selectedNoteId : Long = -1L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_editor)
        selectedNoteId = intent.getLongExtra(SELECTED_NOTE_ID_EXTRA,-1)
        toolbar = binding.editorToolbar
        val dataSource = NoteDatabase.getInstance(application).noteDao
        val factory = EditorActivityViewModelFactory(dataSource, selectedNoteId)
        viewModel = ViewModelProvider(this,factory).get(EditorActivityViewModel::class.java)


        //up functionality
        binding.editorToolbar.setNavigationOnClickListener {
            this.onBackPressed()
        }

        //set text only when user select something
        if (selectedNoteId != -1L){
            viewModel.selectedNote.observe(this, Observer {
                binding.titleEdittext.apply {
                    setText(it.title)
                    //no setSelection because bodyeditetxt will be won focus
                }
                binding.bodyEdittext.apply {
                    setText(it.body)
                    requestFocus()
                    setSelection(it.body.length)
                }
            })
        }

        setupToolbar()
    }

    fun setupToolbar(){
        toolbar.inflateMenu(R.menu.editor_menu)
        toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.done -> {
                    if (selectedNoteId != -1L){
                        viewModel.onUpdate(
                            binding.titleEdittext.text.toString().trim(),
                            binding.bodyEdittext.text.toString().trim()
                        )
                        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
                        //this.onBackPressed()
                    }else{
                        viewModel.onInsert(
                            binding.titleEdittext.text.toString().trim(),
                            binding.bodyEdittext.text.toString().trim()
                        )
                        Toast.makeText(this,"Inserted",Toast.LENGTH_SHORT).show()
                        this.onBackPressed()
                    }
                }
            }
            true
        }
    }
}