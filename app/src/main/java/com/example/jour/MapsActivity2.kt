package com.example.jour
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.jour.MVVM.Place
import com.example.jour.MVVM.PlaceViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.example.jour.databinding.ActivityMaps2Binding
import com.google.android.gms.maps.OnMapReadyCallback

class MapsActivity2 : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMaps2Binding
    private lateinit var viewModel: PlaceViewModel

    // Dodaj te zmienne
    private var selectedImage: Bitmap? = null
    private var selectedLocation: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMaps2Binding.inflate(layoutInflater)
        setContentView(binding.root)

//        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//        viewModel = ViewModelProvider(
//            this,
//            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//        )[PlaceViewModel::class.java]
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[PlaceViewModel::class.java]
    }

//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        mMap.setOnMapLongClickListener(this)
//        mMap.setOnMarkerClickListener(this)
//
//        viewModel.allEntries.observe(this, Observer { list ->
//            list?.let {
//                for (place in it) {
//                    val poiLocation = LatLng(place.latitude, place.longitude)
//                    mMap.addMarker(
//                        MarkerOptions().position(poiLocation).title(place.jourTitle)
//                            .snippet(place.jourDescription)
//                    )
//                }
//            }
//        })
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)
        mMap.setOnMarkerClickListener(this)

        viewModel.allEntries.observe(this, Observer { list ->
            list?.let {
                for (place in it) {
                    val poiLocation = LatLng(place.latitude, place.longitude)
                    mMap.addMarker(
                        MarkerOptions().position(poiLocation).title(place.jourTitle)
                            .snippet(place.jourDescription)
                    )
                }
            }
        })
    }

    override fun onMapLongClick(latLng: LatLng) {
        selectedLocation = latLng
        showAddPOIDialog()
    }

    private fun showAddPOIDialog() {
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

        // Dodaj tutaj obsługę wybierania obrazu
        // Na przykład, możesz użyć startActivityForResult, aby użytkownik mógł wybrać obraz z galerii
        // Zapisz wybrany obraz do zmiennej selectedImage

        alertDialog.setView(linearLayout)

        alertDialog.setPositiveButton("Save") { _, _ ->
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val eventDate = eventDateEditText.text.toString()

            // Dodaj POI do mapy
            if (selectedLocation != null) {
                val newPlace = Place(title, description, eventDate, 0, selectedImage, selectedLocation!!.latitude, selectedLocation!!.longitude)
                viewModel.addNote(newPlace)
            }
        }

        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.show()
    }

//    override fun onMapLongClick(latLng: LatLng) {
//        // Show dialog to add POI
//        showAddPOIDialog(latLng)
//    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Show detailed view dialog
        showDetailedViewDialog(marker)
        return true
    }

    private fun showAddPOIDialog(latLng: LatLng) {
        // Implement your logic to show a dialog for adding POI
        // You can use an AlertDialog or navigate to a new activity for adding POI
        // Pass latLng to the activity if needed
    }

    private fun showDetailedViewDialog(marker: Marker) {
        // Implement your logic to show a detailed view dialog
        // You can use an AlertDialog or navigate to a new activity for detailed view
        // Pass marker details (title, snippet, etc.) to the activity if needed
    }
}

//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.example.jour.MVVM.PlaceViewModel
//import androidx.lifecycle.Observer
//
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import com.example.jour.databinding.ActivityMaps2Binding
//import com.google.android.gms.maps.model.Marker
//
//class MapsActivity2 : AppCompatActivity(), OnMapReadyCallback {
//
//    private lateinit var mMap: GoogleMap
//    private lateinit var binding: ActivityMaps2Binding
//    private lateinit var viewModel: PlaceViewModel
//    private lateinit var activity: MainActivity
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = ActivityMaps2Binding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//    }
//
//    /**
//     * Manipulates the map once available.
//     * This callback is triggered when the map is ready to be used.
//     * This is where we can add markers or lines, add listeners or move the camera. In this case,
//     * we just add a marker near Sydney, Australia.
//     * If Google Play services is not installed on the device, the user will be prompted to install
//     * it inside the SupportMapFragment. This method will only be triggered once the user has
//     * installed Google Play services and returned to the app.
//     */
////    override fun onMapReady(googleMap: GoogleMap) {
////        mMap = googleMap
////
////        // Add a marker in Sydney and move the camera
////        val sydney = LatLng(-34.0, 151.0)
////        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
////    }
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Set listener for long clicks on the map to add POI
//        mMap.setOnMapLongClickListener { latLng ->
//            showAddPOIDialog(latLng)
//        }
//
//        // Set listener for marker clicks to show detailed view
//        mMap.setOnMarkerClickListener { marker ->
//            showDetailedViewDialog(marker)
//            true
//        }
//
//        // Fetch and display all POIs on the map
//        viewModel.allEntries.observe(this, Observer { list ->
//            list?.let {
//                for (place in it) {
//                    val poiLocation = LatLng(place.latitude, place.longitude)
//                    mMap.addMarker(
//                        MarkerOptions().position(poiLocation).title(place.jourTitle)
//                            .snippet(place.jourDescription)
//                    )
//                }
//            }
//        })
//    }
//
//    private fun showAddPOIDialog(latLng: LatLng) {
//        val intent = Intent(this, AddEditNoteActivity::class.java)
//        intent.putExtra("selectedLocation", latLng)
//        startActivity(intent)
//    }
//
//    private fun showDetailedViewDialog(marker: Marker) {
//        val selectedLocation = LatLng(marker.position.latitude, marker.position.longitude)
//        activity.openAddEditNoteActivity(selectedLocation)
//    }
//}