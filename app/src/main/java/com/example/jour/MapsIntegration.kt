    // MapsIntegration.kt
    package com.example.jour
    
    import android.app.AlertDialog
    import android.content.Context
    import android.content.pm.PackageManager
    import android.location.Location
    import android.location.LocationManager
    import android.widget.EditText
    import android.widget.LinearLayout
    import androidx.core.app.ActivityCompat
    import com.google.android.gms.common.ConnectionResult
    import com.google.android.gms.common.GoogleApiAvailability
    import com.google.android.gms.maps.CameraUpdateFactory
    import com.google.android.gms.maps.GoogleMap
    import com.google.android.gms.maps.MapView
    import com.google.android.gms.maps.OnMapReadyCallback
    import com.google.android.gms.maps.model.LatLng
    import com.google.android.gms.maps.model.Marker
    import com.google.android.gms.maps.model.MarkerOptions
    
    class MapsIntegration(private val context: Context, private val mapView: MapView) :
        OnMapReadyCallback {
    
        private var googleMap: GoogleMap? = null
        private val poiMarkers = mutableListOf<Marker>()
    
        init {
            initializeMap()
        }
    
        private fun initializeMap() {
            if (isGooglePlayServicesAvailable()) {
                mapView.onCreate(null)
                mapView.getMapAsync(this)
            } else {
                // Handle Google Play Services not available
            }
        }
    
        private fun isGooglePlayServicesAvailable(): Boolean {
            val apiAvailability = GoogleApiAvailability.getInstance()
            val resultCode = apiAvailability.isGooglePlayServicesAvailable(context)
            return resultCode == ConnectionResult.SUCCESS
        }
    
        override fun onMapReady(map: GoogleMap) {
            googleMap = map
            if (ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                googleMap?.isMyLocationEnabled = true
                centerMapOnUserLocation()
                // Add more map configurations as needed
            }
        }
    
        private fun centerMapOnUserLocation() {
            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val location = getLastKnownLocation(locationManager)
            if (location != null) {
                val userLatLng = LatLng(location.latitude, location.longitude)
                googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
            }
        }
    
        fun getLastKnownLocation(locationManager: LocationManager): Location? {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Handle the case where location permission is not granted
                // You might want to request permission here or handle it in a different way
                return null
            }
    
            val providers = locationManager.getProviders(true)
            var bestLocation: Location? = null
    
            for (provider in providers) {
                try {
                    val l = locationManager.getLastKnownLocation(provider) ?: continue
                    if (bestLocation == null || l.accuracy < bestLocation.accuracy) {
                        bestLocation = l
                    }
                } catch (e: SecurityException) {
                    e.printStackTrace()
                }
            }
    
            return bestLocation
        }
    
    
        fun addPointOfInterest(latLng: LatLng) {
            val alertDialog = AlertDialog.Builder(context)
            alertDialog.setTitle("Add POI")
    
            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL
    
            val titleEditText = EditText(context)
            titleEditText.hint = "Title"
            linearLayout.addView(titleEditText)
    
            val descriptionEditText = EditText(context)
            descriptionEditText.hint = "Description"
            linearLayout.addView(descriptionEditText)
    
            val eventDateEditText = EditText(context)
            eventDateEditText.hint = "Event Date (YYYY-MM-DD)"
            linearLayout.addView(eventDateEditText)
    
            alertDialog.setView(linearLayout)
    
            alertDialog.setPositiveButton("OK") { _, _ ->
                val title = titleEditText.text.toString()
                val description = descriptionEditText.text.toString()
                val eventDate = eventDateEditText.text.toString()
    
                // TODO: Add the POI to your data structure or perform other actions
                // For now, just add a marker on the map
                val marker = googleMap?.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(title)
                        .snippet(description)
                )
    
                marker?.let {
                    poiMarkers.add(it)
                }
            }
    
            alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
    
            alertDialog.show()
        }
    
    
        // New method to display all added POIs as markers on the map
        fun displayAllPointsOfInterest() {
            poiMarkers.forEach { marker ->
                marker.isVisible = true // Ensure the marker is visible
                googleMap?.addMarker(
                    MarkerOptions()
                        .position(marker.position)
                        .title(marker.title)
                        .snippet(marker.snippet)
                )
            }
        }
    
    
        // New method to handle marker click events (show InfoWindow)
        fun setOnMarkerClickListener(onMarkerClickListener: GoogleMap.OnMarkerClickListener) {
            googleMap?.setOnMarkerClickListener(onMarkerClickListener)
        }
    
        // New method to handle InfoWindow click events (detailed view)
        fun setOnInfoWindowClickListener(onInfoWindowClickListener: GoogleMap.OnInfoWindowClickListener) {
            googleMap?.setOnInfoWindowClickListener(onInfoWindowClickListener)
        }
    
    
    
    
        // Add methods for adding markers and handling other map functionalities as needed
    }
