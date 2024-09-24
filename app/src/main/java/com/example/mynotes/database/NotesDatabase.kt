package com.example.mynotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mynotes.models.Note
import com.example.mynotes.utils.DATABASE_NAME

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NotesDao

    companion object {

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance
                instance
            }
        }

    }
}
