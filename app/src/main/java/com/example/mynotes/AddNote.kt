package com.example.mynotes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mynotes.databinding.ActivityAddNoteBinding
import com.example.mynotes.models.Note
import java.text.SimpleDateFormat
import java.util.Date
import java.util.logging.SimpleFormatter

class AddNote : AppCompatActivity() {
    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var note: Note
    private lateinit var oldNote: Note
    var isUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdated = true
        } catch (e: Exception) {
            e.printStackTrace()
        }

        binding.imagecheck.setOnClickListener {
            val title = binding.etTitle.toString()
            val noteDesc = binding.etNote.toString()

            if (title.isNotEmpty() || noteDesc.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE,d MMM yyy HH:mm a")
                if (isUpdated) {
                    note = Note(
                        oldNote.id, title, noteDesc, formatter.format(Date())
                    )
                } else {
                    note = Note(
                        null, title, noteDesc, formatter.format(Date())
                    )
                }


                val intent= Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK,intent)

                finish()
            }else{
                Toast.makeText(this@AddNote,"pleases enter so data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }
        binding.backArrow.setOnClickListener{
            onBackPressed()
        }
    }


}