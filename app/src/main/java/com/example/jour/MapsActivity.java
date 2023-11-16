//package com.example.jour;
//
//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.MapView;
//
//public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
//    private GoogleMap googleMap;
//    private MapView mapView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_maps);
//
//        mapView = findViewById(R.id.mapView);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);
//    }
//
//    @Override
//    public void onMapReady(GoogleMap map) {
//        googleMap = map;
//
//        // Enable location tracking
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            googleMap.setMyLocationEnabled(true);
//        }
//
//        // Add zoom controls
//        googleMap.getUiSettings().setZoomControlsEnabled(true);
//
//        // Add other map functionalities as needed
//        // ...
//    }
//
//    // Handle lifecycle of the MapView
//    @Override
//    public void onResume() {
//        super.onResume();
//        mapView.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        mapView.onPause();
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mapView.onDestroy();
//    }
//
//    @Override
//    public void onLowMemory() {
//        super.onLowMemory();
//        mapView.onLowMemory();
//    }
//}
