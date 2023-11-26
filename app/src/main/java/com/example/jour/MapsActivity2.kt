package com.example.jour

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.jour.MVVM.Place
import com.example.jour.MVVM.PlaceViewModel
import com.example.jour.databinding.ActivityMaps2Binding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class MapsActivity2 : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMaps2Binding
    private lateinit var viewModel: PlaceViewModel

    private var selectedImage: Bitmap? = null
    private var selectedLocation: LatLng? = null
    private var selectedStartDate: Calendar? = null
    private var selectedEndDate: Calendar? = null
    private var isStartDate = true
    private var dateEditText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMaps2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[PlaceViewModel::class.java]
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)
        mMap.setOnMarkerClickListener(this)

        viewModel.allEntries.observe(this) { list ->
            list?.let {
                for (place in it) {
                    val poiLocation = LatLng(place.latitude, place.longitude)
                    mMap.addMarker(
                        MarkerOptions().position(poiLocation).title(place.jourTitle)
                            .snippet(place.jourDescription)
                    )
                }
            }
        }
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

        dateEditText = EditText(this)
        dateEditText?.hint = "Event Date (DD-MM-YYYY)"
        linearLayout.addView(dateEditText)

        // Dodaj obsługę kliknięcia na przycisk wybierania daty
        val pickDateButton = Button(this)
        pickDateButton.text = "Pick Date"
        linearLayout.addView(pickDateButton)

        pickDateButton.setOnClickListener {
            if (isStartDate) {
                // Wybierz datę początkową
                onClickPickStartDate()
            } else {
                // Wybierz datę końcową
                onClickPickEndDate()
            }
        }

        alertDialog.setView(linearLayout)

        alertDialog.setPositiveButton("Save") { _, _ ->
            val title = titleEditText.text.toString()
            val description = descriptionEditText.text.toString()
            val eventDate = dateEditText?.text.toString()

            if (selectedLocation != null) {
                val newPlace = Place(
                    title,
                    description,
                    eventDate,
                    0,
                    selectedImage,
                    selectedLocation!!.latitude,
                    selectedLocation!!.longitude
                )
                viewModel.addNote(newPlace)
            }
        }

        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        alertDialog.show()
    }

    private fun onClickPickStartDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
                val formattedDate =
                    String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, month + 1, year)
                dateEditText?.setText(formattedDate)
                selectedStartDate = Calendar.getInstance()
                selectedStartDate?.set(year, month, dayOfMonth)
                // Automatycznie wybierz datę końcową
                onClickPickEndDate()
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun onClickPickEndDate() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, year, month, dayOfMonth ->
                val formattedDate =
                    String.format(Locale.getDefault(), "%02d-%02d-%04d", dayOfMonth, month + 1, year)
                dateEditText?.setText("${dateEditText?.text} - $formattedDate")
                selectedEndDate = Calendar.getInstance()
                selectedEndDate?.set(year, month, dayOfMonth)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }


    private fun parseDate(dateString: String): Calendar {
        val dateParts = dateString.split("-")
        val year = dateParts[2].toInt()
        val month = dateParts[1].toInt() - 1
        val day = dateParts[0].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        return calendar
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // Handle marker click
        return true
    }
}
