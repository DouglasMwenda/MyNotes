package com.example.mynotes

import NotesDatabase
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.adapter.NotesAdapter
import com.example.mynotes.databinding.ActivityMainBinding
import com.example.mynotes.models.Note
import com.example.mynotes.viewmodel.NotesViewModel


class MainActivity : AppCompatActivity(), NotesAdapter.NotesClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NotesDatabase
    lateinit var viewmodel: NotesViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote: Note

private val updateNote =registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    result->
    if (result.resultCode==Activity.RESULT_OK){
        val note= result.data?.getSerializableExtra("note") as? Note
        if (note !=null){
            viewmodel.updateNote(note)
        }
    }
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewmodel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(NotesViewModel::class.java)

        viewmodel.allnotes.observe(this) { list ->
            list?.let {
                adapter.updatedList(list)
            }
        }

    }

    private fun initUI() {
        binding.recViewId.setHasFixedSize(true)
        binding.recViewId.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = NotesAdapter(this, this)
        binding.recViewId.adapter = adapter
        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val note = result.data?.getSerializableExtra("note") as? Note

                    if (note != null) {
                        viewmodel.insertNote(note)
                    }
                }

            }

        binding.fbAddNote.setOnClickListener {
            val intent = Intent(this, AddNote::class.java)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    adapter.filterList(newText)

                }
                return true
            }
        })

    }

    override fun onItemClicked(note: Note) {
        val intent= Intent(this@MainActivity,AddNote::class.java)
        intent.putExtra("current_Note",note)
        updateNote.launch(intent)
    }

    override fun onLongItemClicked(note: Note, cardView: CardView) {
        selectedNote = note
        popUpDisplay(cardView)
    }

    override fun popUpDisplay(cardView: CardView){
    val popup= PopupMenu(this, cardView)
    popup.setOnMenuItemClickListener { item ->
        onMenuItemClick(item)
    }
    popup.inflate(R.menu.pop_up)
    popup.show()
}
    fun onMenuItemClick(item:MenuItem?):Boolean{
        if (item?.itemId==R.id.delete_note){
            viewmodel.deleteNote(selectedNote)
            return true
        }
        return false
    }

}

