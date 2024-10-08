package com.example.mynotes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mynotes.models.Note
import com.example.mynotes.repo.NoteRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repo: NoteRepo
    val allnotes: LiveData<List<Note>>

    init {
        val dao = NotesDatabase.getDatabase(application).getNoteDao()
        repo = NoteRepo(dao)
        allnotes = repo.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {

        repo.delete(note)
    }

    fun insertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
            repo.insert(note)
        }
    fun updateNote(note: Note)= viewModelScope.launch(Dispatchers.IO) {
        repo.update(note)
    }
}
