package com.example.mynotes.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mynotes.models.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(note: Note)

    @Delete
    suspend fun Delete(note: Note)

    @Query("Select * from notes_table order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("UPDATE notes_table Set title = :title, note = :note WHERE id = :id")
    suspend fun update(id: Int?, title: String?, note: String?)

}