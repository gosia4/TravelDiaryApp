package com.example.jour

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jour.MVVM.Place
import com.example.jour.MVVM.PlaceViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), NoteClickEditInterface, NoteClickDeleteInterface{
    lateinit var jourRV: RecyclerView
    lateinit var addButton: FloatingActionButton
    lateinit var viewModel: PlaceViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jourRV= findViewById(R.id.jourRecyclerView)
        addButton=findViewById(R.id.jourAddButton)
        jourRV.layoutManager=LinearLayoutManager(this)
        //RVAdapter
        val jourRVAdapter = PlaceRVAdapter(this,this,this)
        jourRV.adapter = jourRVAdapter
        viewModel= ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(PlaceViewModel::class.java)
        viewModel.allEntries.observe(this, Observer { list->
            list?.let {
                jourRVAdapter.updateList(it)
            }

        })

        addButton.setOnClickListener{
            val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    override fun onDeleteIconClick(note: Place) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.jourTitle} was deleted", Toast.LENGTH_SHORT).show()

    }

    override fun onNoteClick(note: Place) {
        //Passing Bitmap:
        val filename ="bitmap.png"
        try{
        val stream: FileOutputStream=this.openFileOutput(filename, Context.MODE_PRIVATE)
        val bmp: Bitmap? = note.jourImage
        bmp?.compress(Bitmap.CompressFormat.PNG,100,stream)
        //Clean Up:
        stream.close()
        bmp?.recycle()

        val intent = Intent(this@MainActivity,AddEditNoteActivity::class.java)
        intent.putExtra("noteType", "Edit")
        intent.putExtra("noteTitle", note.jourTitle)
        intent.putExtra("noteDescription", note.jourDescription)
        intent.putExtra("noteDate", note.jourDate) // Pass the date
        intent.putExtra("noteRating", note.rating)
        intent.putExtra("noteID",note.id)
        intent.putExtra("noteImage",filename)
        startActivity(intent)
        this.finish()

        }catch (e: Exception){
            e.printStackTrace()
        }


    }

}