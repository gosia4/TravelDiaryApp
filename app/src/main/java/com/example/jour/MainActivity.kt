package com.example.jour

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jour.MVVM.Place
import com.example.jour.MVVM.PlaceViewModel
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), NoteClickEditInterface, NoteClickDeleteInterface {
    lateinit var jourRV: RecyclerView
    lateinit var addButton: FloatingActionButton
    lateinit var mapButton: FloatingActionButton
    private lateinit var viewModel: PlaceViewModel
    lateinit var mapView: MapView


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jourRV = findViewById(R.id.jourRecyclerView)
        addButton = findViewById(R.id.jourAddButton)
        mapButton = findViewById(R.id.floatingActionButton)

        mapButton.setOnClickListener {
            val intent = Intent(this, MapsActivity2::class.java)
            startActivity(intent)
        }

        jourRV.layoutManager = LinearLayoutManager(this)

        val jourRVAdapter = PlaceRVAdapter(this, this, this)
        jourRV.adapter = jourRVAdapter
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[PlaceViewModel::class.java]

        viewModel.allEntries.observe(this, Observer { list ->
            list?.let {
                jourRVAdapter.updateList(it)
            }
        })

        addButton.setOnClickListener {
            openAddEditPlaceActivity(null)
        }
    }

    private fun openAddEditPlaceActivity(selectedLocation: LatLng?) {
        val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
        intent.putExtra("selectedLocation", selectedLocation)
        startActivity(intent)
    }

    override fun onDeleteIconClick(note: Place) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "${note.jourTitle} was deleted", Toast.LENGTH_SHORT).show()

    }

    override fun onNoteClick(note: Place) {
        //Passing Bitmap:
        val filename = "bitmap.png"
        try {
            val stream: FileOutputStream = this.openFileOutput(filename, Context.MODE_PRIVATE)
            val bmp: Bitmap? = note.jourImage
            bmp?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            //Clean Up:
            stream.close()
            bmp?.recycle()

            val intent = Intent(this@MainActivity, AddEditNoteActivity::class.java)
            intent.putExtra("noteType", "Edit")
            intent.putExtra("noteTitle", note.jourTitle)
            intent.putExtra("noteDescription", note.jourDescription)
            intent.putExtra("noteDate", note.jourDate)
            intent.putExtra("noteRating", note.rating)
            intent.putExtra("noteID", note.id)
            intent.putExtra("noteImage", filename)
            startActivity(intent)
            this.finish()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sort_alphabetically -> {
                viewModel.sortAlphabetically().observe(this, Observer { list ->
                    list?.let {
                        (jourRV.adapter as PlaceRVAdapter).updateList(it)
                    }
                })
                return true
            }
            R.id.action_sort_by_date -> {
                viewModel.sortByDate().observe(this, Observer { list ->
                    list?.let {
                        (jourRV.adapter as PlaceRVAdapter).updateList(it)
                    }
                })
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }



}