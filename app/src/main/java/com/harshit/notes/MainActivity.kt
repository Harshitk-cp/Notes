package com.harshit.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    private lateinit var viewModel: NoteViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var etInputText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etInputText = findViewById(R.id.etInputText)
        recyclerView = findViewById(R.id.RVNotes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = NotesRVAdapter(this, this)
        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, { List->
            List?.let{
                adapter.updateList(it)
            }

        })

    }

    override fun onItemClicked(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.text} deleted", Toast.LENGTH_SHORT).show()
    }

    fun submitData(view: View) {
        val noteText = etInputText.text.toString()
        if(noteText.isNotEmpty()){
            viewModel.insertNote(Note(noteText))
            etInputText.text.clear()
            recyclerView.smoothScrollToPosition(10000)
        }else{
            Toast.makeText(this, "Enter to submit!!", Toast.LENGTH_SHORT).show()
        }
    }

}