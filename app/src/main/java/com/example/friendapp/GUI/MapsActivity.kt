package com.example.friendapp.GUI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.friendapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private val TAG = "xyz"
    private val EASV = LatLng(55.488230, 8.446936)
    private val BAKER = LatLng(55.485386, 8.451585)
//    private var location = LatLng(0.0, 0.0)
    //private var location = LatLng("location")
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        //location = intent.extras!!.get("location") as LatLng
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
//        setupZoomLevels()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val localMarker = MarkerOptions().position(EASV).title("Friend Location")
        val bakerMarker = MarkerOptions().position(BAKER).title("Baker")
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(localMarker)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(EASV))
//        setupZoomLevels()
    }

//    private fun setupZoomLevels() {
//        spinnerZoomLevel.adapter =
//            ArrayAdapter.createFromResource(
//                this,
//                R.array.zoomlevels,
//                android.R.layout.simple_spinner_dropdown_item
//            )
//    }
}