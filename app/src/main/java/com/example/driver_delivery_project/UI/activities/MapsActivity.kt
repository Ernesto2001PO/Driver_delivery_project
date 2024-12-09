package com.example.driver_delivery_project.UI.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.example.driver_delivery_project.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.driver_delivery_project.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.Marker

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationCallback: LocationCallback
    private var currentMarker: Marker? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        requestMapPermissions()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap?.uiSettings?.isZoomControlsEnabled = true
        mMap?.uiSettings?.isCompassEnabled = true



        val latitude = intent.getStringExtra("latitude")
        val longitude = intent.getStringExtra("longitude")



        Log.d("MapsActivity", "latitude: $latitude, longitude: $longitude")


        if (latitude == null || longitude == null) {
            Log.d("MapsActivity", "No se recibió la ubicación de la orden")
            return
        }

        val orderLocation = LatLng(latitude.toDouble(), longitude.toDouble())
        mMap.addMarker(MarkerOptions().position(orderLocation).title("Order location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(orderLocation))
        zoomToLocation(orderLocation)

        if (latitude != null && longitude != null) {
            val orderLocation = LatLng(latitude.toDouble(), longitude.toDouble())
            mMap.addMarker(MarkerOptions().position(orderLocation).title("Order Location"))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(orderLocation))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(orderLocation, 15f))

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    for (location in locationResult.locations) {
                        if (currentMarker != null) {
                            currentMarker?.remove()
                        }
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        currentMarker = mMap.addMarker(
                            MarkerOptions().position(currentLatLng).title("Mi ubicación actual")
                        )
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 20f))
                    }
                }
            }

            enableLocationOnMap()
        } else {
            Log.d("MapsActivity", "OrderResponse is null")
        }

    }

    private fun zoomToLocation(orderLocation: LatLng) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(orderLocation, 15f))

    }

    private fun requestMapPermissions() {
        if (
            checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
            && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val permissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
        requestPermissions(permissions, 0)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enableLocationOnMap()
        } else {
            Toast.makeText(
                this,
                "Necesita habilitar la ubicación en la configuración de la app",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", packageName, null)
            })
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    private fun enableLocationOnMap() {
        mMap?.isMyLocationEnabled = true
    }
}