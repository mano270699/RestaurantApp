package com.ahmed.newpro

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.io.IOException
import java.util.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private val LOCATION_REQUEST_CODE = 101
    private var locationManager: LocationManager? = null
    private var locationListener: LocationListener? = null
    private lateinit var edittext: EditText

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "userInfo"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor;
    var USERAddress_KEY = "UserAddress"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()




    }
    private fun requestPermission(
        permissionType: String,
        requestCode: Int
    ) {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(permissionType), requestCode
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            LOCATION_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] !=
                    PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(
                        this,
                        "Unable to show location - permission required",
                        Toast.LENGTH_LONG
                    ).show()
                } else {

                    val mapFragment = supportFragmentManager
                        .findFragmentById(R.id.map) as SupportMapFragment
                    mapFragment.getMapAsync(this)
                }
            }
        }


    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (mMap != null) {
            val permission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            if (permission == PackageManager.PERMISSION_GRANTED) {
                mMap?.isMyLocationEnabled = true


            } else {
                requestPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    LOCATION_REQUEST_CODE
                )
            }
        }

        mMap.setOnMapLongClickListener(this)
        mMap.uiSettings.isZoomControlsEnabled =true

        mMap.uiSettings.isCompassEnabled =true

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
            override fun onLocationChanged(location: Location) {
                Log.i("location", location.toString())



                val sydney = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15F));


            }
        }







    }

    override fun onMapLongClick(p0: LatLng?) {
        mMap.clear()
        mMap.addMarker(
            MarkerOptions().position(p0!!).title(p0.toString()).icon(
                BitmapDescriptorFactory.defaultMarker(
                    BitmapDescriptorFactory.HUE_ORANGE
                )
            )
        ).showInfoWindow()

        val geocoder = Geocoder(applicationContext, Locale.getDefault())
        try {
            val addresses =
                geocoder.getFromLocation(p0.latitude, p0.longitude, 1)
            if (addresses!!.size > 0 && addresses != null) {

                var fulladdress=addresses.get(0).getAddressLine(0);
                Log.i("Address_line", fulladdress)

                editor.putString(USERAddress_KEY, fulladdress)
                editor.apply()

                val intent = Intent(this, edit_text::class.java)
                startActivity(intent)
                finish();




            } else {
                Log.i("Address", "address not found !")
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("onCatch", "error !")
        }
    }
}