package com.example.notes.allnotes

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.notes.R
import com.example.notes.adapter.NoteClickListener
import com.example.notes.adapter.NotesAdapter
import com.example.notes.database.Note
import com.example.notes.database.NoteDatabase
import com.example.notes.databinding.AllNotesFragmentBinding
import com.example.notes.editor.EditorActivity

class AllNotesFragment : Fragment(),NoteClickListener {
    private val SELECTED_NOTE_ID_EXTRA = "selectedNoteId"
    private lateinit var viewModel: AllNotesViewModel
    private lateinit var viewModelFactory: AllNotesViewModelFactory
    private lateinit var binding: AllNotesFragmentBinding
    private lateinit var toolbar : Toolbar
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.all_notes_fragment, container, false)
        toolbar = binding.allNotesToolbar
        val application = requireNotNull(this.activity).application
        val dataSource = NoteDatabase.getInstance(application).noteDao
        setHasOptionsMenu(true)
        viewModelFactory =
            AllNotesViewModelFactory(
                application = application,
                dataSource = dataSource
            )
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(AllNotesViewModel::class.java)
        binding.allNotesViewModel = viewModel
        viewModel.navigateToAddNote.observe(viewLifecycleOwner, Observer {
            it?.let {
//                findNavController().navigate(R.id.action_allNotesFragment_to_addNoteFragment)
//                viewModel.doneNavigating()
                startActivity(Intent(activity,EditorActivity::class.java))
            }
        })

        val gridLayoutManager = GridLayoutManager(context,2)
        val adapter = NotesAdapter(this)
        binding.recyclerView.adapter =adapter
        binding.recyclerView.layoutManager = gridLayoutManager
        viewModel.allNotes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
                Log.i("Faisal",it.toString())
            }
        })
        toolbarSetup()
        return binding.root
    }

    fun toolbarSetup(){
        toolbar.inflateMenu(R.menu.all_notes_menu)

        toolbar.setOnMenuItemClickListener{item ->
            when(item.itemId){
                R.id.delete_all_notes -> {viewModel.onClear()
                    Toast.makeText(context,"List Cleared",Toast.LENGTH_SHORT).show() }
            }
            true
        }

    }

    override fun onClick(note: Note) {
        startActivity(Intent(activity,EditorActivity::class.java).putExtra(SELECTED_NOTE_ID_EXTRA,note.noteId))
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.all_notes_menu,menu)
//        super.onCreateOptionsMenu(menu, inflater)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.delete_all_notes -> {
//                Toast.makeText(context,"All notes deleted!",Toast.LENGTH_SHORT).show()
//                viewModel.onClear()
//            }
//
//        }
//        return super.onOptionsItemSelected(item)
//    }

}