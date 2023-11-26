////// MapsIntegration.kt
//package com.example.jour
//
//import android.Manifest
//import android.app.AlertDialog
//import android.content.Context
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.location.Location
//import android.location.LocationManager
//import android.net.Uri
//import android.view.LayoutInflater
//import android.widget.EditText
//import android.widget.LinearLayout
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.GoogleApiAvailability
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.MapView
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
////
////class MapsIntegration(private val context: Context, private val mapView: MapView) :
////    OnMapReadyCallback {
////    private var onMapLongClickListener: ((LatLng) -> Unit)? = null // Listener for long clicks
////
////    private var googleMap: GoogleMap? = null
////    private val poiMarkers = mutableListOf<Marker>()
////
////    init {
////        initializeMap()
////    }
////
////    private var selectedLocation: LatLng? = null
////
////    fun getSelectedLocation(): LatLng? {
////        return selectedLocation
////    }
////
////    private fun initializeMap() {
////        if (isGooglePlayServicesAvailable()) {
////            mapView.onCreate(null)
////            mapView.getMapAsync(this)
////        } else {
////            // Handle Google Play Services not available
////        }
////    }
////
////    private fun isGooglePlayServicesAvailable(): Boolean {
////        val apiAvailability = GoogleApiAvailability.getInstance()
////        val resultCode = apiAvailability.isGooglePlayServicesAvailable(context)
////        return resultCode == ConnectionResult.SUCCESS
////    }
////
//////    override fun onMapReady(map: GoogleMap) {
//////        googleMap = map
//////        if (ActivityCompat.checkSelfPermission(
//////                context,
//////                android.Manifest.permission.ACCESS_FINE_LOCATION
//////            ) == PackageManager.PERMISSION_GRANTED
//////        ) {
//////            googleMap?.isMyLocationEnabled = true
//////            centerMapOnUserLocation()
//////
//////            // Set listener for long clicks on the map to add POI
//////            mapView.getMapAsync { map ->
//////                map.setOnMapLongClickListener { latLng ->
//////                    selectedLocation = latLng
//////                    showAddPOIDialog(selectedLocation!!)
//////                }
//////            }
//////
//////            // Set listener for marker clicks to show detailed view
//////            googleMap?.setOnMarkerClickListener { marker ->
//////                showDetailedViewDialog(marker)
//////                true
//////            }
//////        }
//////    }
////fun setOnMapLongClickListener(listener: (LatLng) -> Unit) {
////    this.onMapLongClickListener = listener
////}
////
////    // ... (other methods)
////
////    override fun onMapReady(map: GoogleMap) {
////        googleMap = map
////
////        // Set listener for long clicks on the map to add POI
////        googleMap?.setOnMapLongClickListener { latLng ->
////            onMapLongClickListener?.invoke(latLng)
////        }
////
////        // Set listener for marker clicks to show detailed view
////        googleMap?.setOnMarkerClickListener { marker ->
////            showDetailedViewDialog(marker)
////            true
////        }
////
////        if (ActivityCompat.checkSelfPermission(
////                context,
////                android.Manifest.permission.ACCESS_FINE_LOCATION
////            ) == PackageManager.PERMISSION_GRANTED
////        ) {
////            googleMap?.isMyLocationEnabled = true
////            centerMapOnUserLocation()
////        }
////    }
////
////
////    private fun centerMapOnUserLocation() {
////        val locationManager =
////            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
////        val location = getLastKnownLocation(locationManager)
////        if (location != null) {
////            val userLatLng = LatLng(location.latitude, location.longitude)
////            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
////        }
////    }
////    fun getLastKnownLocation(locationManager: LocationManager): Location? {
////            if (ActivityCompat.checkSelfPermission(
////                    context,
////                    android.Manifest.permission.ACCESS_FINE_LOCATION
////                ) != PackageManager.PERMISSION_GRANTED &&
////                ActivityCompat.checkSelfPermission(
////                    context,
////                    android.Manifest.permission.ACCESS_COARSE_LOCATION
////                ) != PackageManager.PERMISSION_GRANTED
////            ) {
////                // Handle the case where location permission is not granted
////                // You might want to request permission here or handle it in a different way
////                return null
////            }
////
////            val providers = locationManager.getProviders(true)
////            var bestLocation: Location? = null
////
////            for (provider in providers) {
////                try {
////                    val l = locationManager.getLastKnownLocation(provider) ?: continue
////                    if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
////                        bestLocation = l
////                    }
////                } catch (e: SecurityException) {
////                    e.printStackTrace()
////                }
////            }
////
////            return bestLocation
////        }
////
////    private fun showAddPOIDialog(latLng: LatLng) {
////        val alertDialog = AlertDialog.Builder(context)
////        alertDialog.setTitle("Add POI")
////
////        val inflater = LayoutInflater.from(context)
////        val dialogLayout = inflater.inflate(R.layout.dialog_add_poi, null)
////
////        val titleEditText = dialogLayout.findViewById<EditText>(R.id.editTextTitle)
////        val descriptionEditText = dialogLayout.findViewById<EditText>(R.id.editTextDescription)
////        val eventDateEditText = dialogLayout.findViewById<EditText>(R.id.editTextEventDate)
////
////        alertDialog.setView(dialogLayout)
////
////        alertDialog.setPositiveButton("OK") { _, _ ->
////            val title = titleEditText.text.toString()
////            val description = descriptionEditText.text.toString()
////            val eventDate = eventDateEditText.text.toString()
////
////            // TODO: Add the POI to your data structure or perform other actions
////            // For now, just add a marker on the map
////            val marker = googleMap?.addMarker(
////                MarkerOptions()
////                    .position(latLng)
////                    .title(title)
////                    .snippet(description)
////            )
////
////            marker?.let {
////                poiMarkers.add(it)
////            }
////        }
////
////        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
////            dialog.dismiss()
////        }
////
////        alertDialog.show()
////    }
////
////    private fun showDetailedViewDialog(marker: Marker) {
////        val alertDialog = AlertDialog.Builder(context)
////        alertDialog.setTitle(marker.title)
////        alertDialog.setMessage(marker.snippet)
////
////        alertDialog.setPositiveButton("Edit") { _, _ ->
////            // TODO: Redirect to AddEditNoteActivity with marker details
////            val intent = Intent(context, AddEditNoteActivity::class.java)
////            intent.putExtra("title", marker.title)
////            intent.putExtra("description", marker.snippet)
////            // Add other details as needed
////            context.startActivity(intent)
////        }
////
////        alertDialog.setNegativeButton("Close") { dialog, _ ->
////            dialog.dismiss()
////        }
////
////        alertDialog.show()
////    }
////
////    // New method to display all added POIs as markers on the map
////    fun displayAllPointsOfInterest() {
////        poiMarkers.forEach { marker ->
////            marker.isVisible = true // Ensure the marker is visible
////            googleMap?.addMarker(
////                MarkerOptions()
////                    .position(marker.position)
////                    .title(marker.title)
////                    .snippet(marker.snippet)
////            )
////        }
////    }
////
////    // New method to handle marker click events (show InfoWindow)
////    fun setOnMarkerClickListener(onMarkerClickListener: GoogleMap.OnMarkerClickListener) {
////        googleMap?.setOnMarkerClickListener(onMarkerClickListener)
////    }
////
////    // New method to handle InfoWindow click events (detailed view)
////    fun setOnInfoWindowClickListener(onInfoWindowClickListener: GoogleMap.OnInfoWindowClickListener) {
////        googleMap?.setOnInfoWindowClickListener(onInfoWindowClickListener)
////    }
////
////
////
////
////
////    fun openGoogleMaps() {
////        // Launch Google Maps using intent
////        val gmmIntentUri = Uri.parse("geo:0,0?q=")
////        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
////        mapIntent.setPackage("com.google.android.apps.maps")
////        if (mapIntent.resolveActivity(context.packageManager) != null) {
////            context.startActivity(mapIntent)
////        } else {
////            // Handle Google Maps not installed
////        }
////    }
////    fun addPointOfInterest(latLng: LatLng, title: String, description: String, eventDate: String) {
////        // Add the POI to your data structure or perform other actions
////        // For now, just add a marker on the map
////        val marker = googleMap?.addMarker(
////            MarkerOptions()
////                .position(latLng)
////                .title(title)
////                .snippet(description)
////        )
////
////        marker?.let {
////            poiMarkers.add(it)
////        }
////    }
////}
////
////
//    class MapsIntegration(private val context: Context, private val mapView: MapView) :
//    OnMapReadyCallback {
//
//    private var googleMap: GoogleMap? = null
//    private val poiMarkers = mutableListOf<Marker>()
//
//    init {
//        initializeMap()
//    }
//
//    private var selectedLocation: LatLng? = null
//
//    fun getSelectedLocation(): LatLng? {
//        return selectedLocation
//    }
//
//    private fun initializeMap() {
//        if (isGooglePlayServicesAvailable()) {
//            mapView.onCreate(null)
//            mapView.getMapAsync(this)
//        } else {
//            // Obsłuż brak dostępności Google Play Services
//        }
//    }
//
//    private fun isGooglePlayServicesAvailable(): Boolean {
//        val apiAvailability = GoogleApiAvailability.getInstance()
//        val resultCode = apiAvailability.isGooglePlayServicesAvailable(context)
//        return resultCode == ConnectionResult.SUCCESS
//    }
//
//    override fun onMapReady(map: GoogleMap) {
//        googleMap = map
//        googleMap?.apply {
//            isMyLocationEnabled = true
//            setOnMapLongClickListener { latLng ->
//                showAddPOIDialog(latLng)
//            }
//        }
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            googleMap?.isMyLocationEnabled = true
//            centerMapOnUserLocation()
//
//            // Set up listener for long-press to add POI
//            googleMap?.setOnMapLongClickListener { latLng ->
//                selectedLocation = latLng
//                showAddPOIDialog(latLng)
//            }
//
//            // Display all POIs as markers on the map
//            displayAllPointsOfInterest()
//
//            // Set up marker click listener to show InfoWindow
//            googleMap?.setOnMarkerClickListener { marker ->
//                showDetailedViewDialog(marker)
//                true
//            }
//        }
//    }
//
//    private fun centerMapOnUserLocation() {
//        val locationManager =
//            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        val location = getLastKnownLocation(locationManager)
//        if (location != null) {
//            val userLatLng = LatLng(location.latitude, location.longitude)
//            googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
//        }
//    }
//    fun enableMyLocation() {
////        googleMap?.isMyLocationEnabled =
//        if (ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            googleMap?.isMyLocationEnabled = true
//        } else {
//            // Brak uprawnień, możesz poinformować użytkownika lub poprosić o nie ponownie
//        }
//    }
//
//
//    private fun showAddPOIDialog(latLng: LatLng) {
//        val alertDialog = AlertDialog.Builder(context)
//        alertDialog.setTitle("Add POI")
//
//        val linearLayout = LinearLayout(context)
//        linearLayout.orientation = LinearLayout.VERTICAL
//
//        val titleEditText = EditText(context)
//        titleEditText.hint = "Title"
//        linearLayout.addView(titleEditText)
//
//        val descriptionEditText = EditText(context)
//        descriptionEditText.hint = "Description"
//        linearLayout.addView(descriptionEditText)
//
//        val eventDateEditText = EditText(context)
//        eventDateEditText.hint = "Event Date (YYYY-MM-DD)"
//        linearLayout.addView(eventDateEditText)
//
//        alertDialog.setView(linearLayout)
//
//        alertDialog.setPositiveButton("Save") { _, _ ->
//            val title = titleEditText.text.toString()
//            val description = descriptionEditText.text.toString()
//            val eventDate = eventDateEditText.text.toString()
//
//            // TODO: Add the POI to your data structure or perform other actions
//            // For now, just add a marker on the map
//            val marker = googleMap?.addMarker(
//                MarkerOptions()
//                    .position(latLng)
//                    .title(title)
//                    .snippet(description)
//            )
//
//            marker?.let {
//                poiMarkers.add(it)
//            }
//        }
//
//        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
//            dialog.dismiss()
//        }
//
//        alertDialog.show()
//    }
//
//    private fun showDetailedViewDialog(marker: Marker) {
//        val alertDialog = AlertDialog.Builder(context)
//        alertDialog.setTitle(marker.title)
//        alertDialog.setMessage(marker.snippet)
//
//        alertDialog.setPositiveButton("Edit") { _, _ ->
//            // Call the editPOI function with marker details
//            editPOI(marker)
//        }
//
//        alertDialog.setNegativeButton("Close") { dialog, _ ->
//            dialog.dismiss()
//        }
//
//        alertDialog.show()
//    }
//
//    private fun editPOI(marker: Marker) {
//        val alertDialog = AlertDialog.Builder(context)
//        alertDialog.setTitle("Edit POI")
//
//        val linearLayout = LinearLayout(context)
//        linearLayout.orientation = LinearLayout.VERTICAL
//
//        val titleEditText = EditText(context)
//        titleEditText.hint = "Title"
//        titleEditText.setText(marker.title)
//        linearLayout.addView(titleEditText)
//
//        val descriptionEditText = EditText(context)
//        descriptionEditText.hint = "Description"
//        descriptionEditText.setText(marker.snippet)
//        linearLayout.addView(descriptionEditText)
//
//        alertDialog.setView(linearLayout)
//
//        alertDialog.setPositiveButton("Save") { _, _ ->
//            val title = titleEditText.text.toString()
//            val description = descriptionEditText.text.toString()
//
//            // Update the marker with new details
//            marker.title = title
//            marker.snippet = description
//        }
//
//        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
//            dialog.dismiss()
//        }
//
//        alertDialog.show()
//    }
//
//
//    fun openGoogleMaps() {
//        val gmmIntentUri = Uri.parse("geo:0,0?q=")
//        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//        mapIntent.setPackage("com.google.android.apps.maps")
//        if (mapIntent.resolveActivity(context.packageManager) != null) {
//            context.startActivity(mapIntent)
//        } else {
//            // Handle Google Maps not installed
//        }
//    }
//    fun getLastKnownLocation(locationManager: LocationManager): Location? {
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED &&
//            ActivityCompat.checkSelfPermission(
//                context,
//                android.Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            // Handle the case where location permission is not granted
//            // You might want to request permission here or handle it in a different way
//            return null
//        }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            val providers = locationManager.getProviders(true)
//            var bestLocation: Location? = null
//
//            for (provider in providers) {
//                try {
//                    val l = locationManager.getLastKnownLocation(provider) ?: continue
//                    if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
//                        bestLocation = l
//                    }
//                } catch (e: SecurityException) {
//                    e.printStackTrace()
//                }
//            }
//
//            return bestLocation
//        }
//
//
//    fun setOnMapLongClickListener(onMapLongClickListener: GoogleMap.OnMapLongClickListener) {
//        googleMap?.setOnMapLongClickListener(onMapLongClickListener)
//    }
//
//    fun addPointOfInterest(latLng: LatLng, title: String, description: String, eventDate: String) {
//        val alertDialog = AlertDialog.Builder(context)
//        alertDialog.setTitle("Add POI")
//
//        val linearLayout = LinearLayout(context)
//        linearLayout.orientation = LinearLayout.VERTICAL
//
//        val titleEditText = EditText(context)
//        titleEditText.hint = "Title"
//        titleEditText.setText(title)
//        linearLayout.addView(titleEditText)
//
//        val descriptionEditText = EditText(context)
//        descriptionEditText.hint = "Description"
//        descriptionEditText.setText(description)
//        linearLayout.addView(descriptionEditText)
//
//        val eventDateEditText = EditText(context)
//        eventDateEditText.hint = "Event Date (YYYY-MM-DD)"
//        eventDateEditText.setText(eventDate)
//        linearLayout.addView(eventDateEditText)
//
//        alertDialog.setView(linearLayout)
//
//        alertDialog.setPositiveButton("OK") { _, _ ->
//            val newTitle = titleEditText.text.toString()
//            val newDescription = descriptionEditText.text.toString()
//            val newEventDate = eventDateEditText.text.toString()
//
//            // TODO: Add the POI to your data structure or perform other actions
//            // For now, just add a marker on the map
//            val marker = googleMap?.addMarker(
//                MarkerOptions()
//                    .position(latLng)
//                    .title(newTitle)
//                    .snippet(newDescription)
//            )
//
//            marker?.let {
//                poiMarkers.add(it)
//            }
//        }
//
//        alertDialog.setNegativeButton("Cancel") { dialog, _ ->
//            dialog.dismiss()
//        }
//
//        alertDialog.show()
//    }
//
//        // New method to display all added POIs as markers on the map
//        fun displayAllPointsOfInterest() {
//            poiMarkers.forEach { marker ->
//                marker.isVisible = true // Ensure the marker is visible
//                googleMap?.addMarker(
//                    MarkerOptions()
//                        .position(marker.position)
//                        .title(marker.title)
//                        .snippet(marker.snippet)
//                )
//            }
//        }
//
//
//        // New method to handle marker click events (show InfoWindow)
//        fun setOnMarkerClickListener(onMarkerClickListener: GoogleMap.OnMarkerClickListener) {
//            googleMap?.setOnMarkerClickListener(onMarkerClickListener)
//        }
//
//        // New method to handle InfoWindow click events (detailed view)
//        fun setOnInfoWindowClickListener(onInfoWindowClickListener: GoogleMap.OnInfoWindowClickListener) {
//            googleMap?.setOnInfoWindowClickListener(onInfoWindowClickListener)
//        }
//
//
//
//
//        // Add methods for adding markers and handling other map functionalities as needed
//    }
