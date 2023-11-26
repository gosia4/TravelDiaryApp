package com.example.jour

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jour.MVVM.Place
import com.example.jour.MVVM.PlaceViewModel
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.FileOutputStream

class MainActivity : AppCompatActivity(), NoteClickEditInterface, NoteClickDeleteInterface {
    lateinit var jourRV: RecyclerView
    lateinit var addButton: FloatingActionButton
    lateinit var mapButton: FloatingActionButton
    private lateinit var viewModel: PlaceViewModel
    lateinit var mapView: MapView
//    lateinit var mapsIntegration: MapsIntegration
    private val LOCATION_PERMISSION_REQUEST_CODE = 123


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jourRV = findViewById(R.id.jourRecyclerView)
        addButton = findViewById(R.id.jourAddButton)
        mapButton = findViewById(R.id.floatingActionButton)

//        fun requestLocationPermission() {
//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.ACCESS_FINE_LOCATION
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    LOCATION_PERMISSION_REQUEST_CODE
//                )
//            } else {
//                // Uprawnienia już nadane
//                mapsIntegration.enableMyLocation()
//            }
//        }

//        fun onRequestPermissionsResult(
//            requestCode: Int,
//            permissions: Array<out String>,
//            grantResults: IntArray
//        ) {
//            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//            if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
//                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Uprawnienia zostały nadane
//                    mapsIntegration.enableMyLocation()
//                } else {
//                    // Uprawnienia nie zostały nadane, obsłuż to odpowiednio
//                    Toast.makeText(
//                        this,
//                        "Location permission denied. Some features may not work.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }

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





//        mapView = findViewById(R.id.mapView)
//        mapButton = findViewById(R.id.floatingActionButton)
////        mapButton.setOnClickListener {
////            // Nic nie rób, ponieważ MapsIntegration obsługuje długie kliknięcia
////        }
//        mapButton.setOnClickListener {
//            val intent = Intent(this, MapsActivity2::class.java)
//            startActivity(intent)
//        }
////        mapsIntegration = MapsIntegration(this, mapView)
////        mapsIntegration = MapsIntegration(this, mapView)
//        jourRV.layoutManager = LinearLayoutManager(this)
//        //RVAdapter
//        val jourRVAdapter = PlaceRVAdapter(this, this, this)
//        jourRV.adapter = jourRVAdapter
//        viewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        )[PlaceViewModel::class.java]
//
//        viewModel.allEntries.observe(this, Observer { list ->
//            list?.let {
//                jourRVAdapter.updateList(it)
//            }
//        })

        // Find the Button using its correct ID
//        val openMapsButton = findViewById<Button>(R.id.floatingActionButton)
//
        // Find the Button using its correct ID
//        mapButton.setOnClickListener {
//            // Open Google Maps to select a location
//            mapsIntegration.openGoogleMaps()
//        }

//        addButton.setOnClickListener {
//            // Open AddEditNoteActivity without a selected location
//            openAddEditNoteActivity(null)
//        }
//        mapButton.setOnClickListener {
//            requestLocationPermission()
//        }

        // Set listener for long clicks on the map to add POI
//        mapsIntegration.setOnMapLongClickListener { latLng ->
//            showAddPOIDialog(latLng)
//        }
//
//        // Set listener for marker clicks to show detailed view
//        mapsIntegration.setOnMarkerClickListener { marker ->
//            showDetailedViewDialog(marker)
//            true
//        }


//    }
//    private fun showAddPOIDialog(latLng: LatLng) {
//        val alertDialog = AlertDialog.Builder(this)
//        alertDialog.setTitle("Add POI")
//
//        val inflater = LayoutInflater.from(this)
//        val dialogLayout = inflater.inflate(R.layout.dialog_add_poi, null)
//
//        val titleEditText = dialogLayout.findViewById<EditText>(R.id.editTextTitle)
//        val descriptionEditText = dialogLayout.findViewById<EditText>(R.id.editTextDescription)
//        val eventDateEditText = dialogLayout.findViewById<EditText>(R.id.editTextEventDate)
//
//        alertDialog.setView(dialogLayout)
//
//        alertDialog.setPositiveButton("OK") { _, _ ->
//            val title = titleEditText.text.toString()
//            val description = descriptionEditText.text.toString()
//            val eventDate = eventDateEditText.text.toString()
//
//            // Add the POI to your data structure or perform other actions
//            mapsIntegration.addPointOfInterest(latLng, title, description, eventDate)
//        }
//
//        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
//            dialog.dismiss()
//        }
//
//        alertDialog.show()
//    }

    private fun showDetailedViewDialog(marker: Marker) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(marker.title)
        alertDialog.setMessage(marker.snippet)

        alertDialog.setPositiveButton("Edit") { _, _ ->
            // Redirect to AddEditNoteActivity with marker details
            openAddEditNoteActivity(marker.position)
        }

        alertDialog.setNegativeButton("Close") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.show()
    }


    private fun openAddEditNoteActivity(selectedLocation: LatLng?) {
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

    private fun openGoogleMaps() {
        // Launch Google Maps using intent
        val gmmIntentUri = Uri.parse("geo:0,0?q=")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // Handle Google Maps not installed
        }
    }
//    private fun setOnMapLongClickListener() {
//        mapsIntegration.setOnMapLongClickListener { latLng ->
//            // Pokaż dialog do dodawania POI
//            showAddPOIDialog(latLng)
//        }
//    }

    private fun showAddPOIDialog(latLng: LatLng) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Add POI")

        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL

        val titleEditText = EditText(this)
        titleEditText.hint = "Title"
        linearLayout.addView(titleEditText)

        val descriptionEditText = EditText(this)
        descriptionEditText.hint = "Description"
        linearLayout.addView(descriptionEditText)

        val eventDateEditText = EditText(this)
        eventDateEditText.hint = "Event Date (YYYY-MM-DD)"
        linearLayout.addView(eventDateEditText)

        alertDialog.setView(linearLayout)

        alertDialog.setPositiveButton("Save") { _, _ ->
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val eventDate = eventDateEditText.text.toString()

            // Dodaj POI do mapy
//            mapsIntegration.addPointOfInterest(latLng, title, description, eventDate)
        }

        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.show()
    }



// Function to get the user's current location

//    private fun getUserLocation(): LatLng {
//        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val location = mapsIntegration.getLastKnownLocation(locationManager)
//
//        return LatLng(location!!.latitude, location.longitude)
//
//    }

}