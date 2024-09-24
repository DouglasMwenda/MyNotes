package com.example.mynotes.repo

import androidx.lifecycle.LiveData
import com.example.mynotes.database.NotesDao
import com.example.mynotes.models.Note

class NoteRepo(private val noteDao: NotesDao) {
    val allNotes : LiveData<List<Note>> = noteDao.getAllNotes()



    suspend fun insert (note: Note){
        noteDao.Insert(note)
    }

    suspend fun delete (note: Note){
        noteDao.Delete(note)
    }

    suspend fun update (note: Note){
        noteDao.update(note.id,note.title,note.note)
    }
}