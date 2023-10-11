package com.example.kotlinize

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.util.*

private const val My_LOCATION_PERMISSION_ID = 5005


class MainActivity : AppCompatActivity() {
    private lateinit var fusedClient: FusedLocationProviderClient
    lateinit var geoCoder: Geocoder
    lateinit var latTV: TextView
    lateinit var longTV: TextView
    lateinit var locationTV: TextView
    lateinit var msgFab: ExtendedFloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()
        fusedClient = LocationServices.getFusedLocationProviderClient(this)
        geoCoder = Geocoder(this, Locale.getDefault())

        msgFab.setOnClickListener { composeMmsMessage(locationTV.text.toString()) }


    }

    private fun initUI() {
        setContentView(R.layout.activity_main)
        latTV = findViewById(R.id.lat_tv)
        longTV = findViewById(R.id.long_tv)
        locationTV = findViewById(R.id.location_tv)
        msgFab = findViewById(R.id.msg_fab)
    }


    override fun onResume() {
        super.onResume()
        getLastLocation()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == My_LOCATION_PERMISSION_ID) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) getLastLocation()

        }
    }

    private fun getLastLocation() {
        if (!isLocationPermissionsGranted()) {
            requestPermissions()
        } else {
            if (!isLocationEnabled()) {
                askUserToEnableLocation()
            } else {
                requestNewLocationData()
            }
        }
    }

    private fun isLocationPermissionsGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            My_LOCATION_PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun askUserToEnableLocation() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }


    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val locationRequest = LocationRequest.create()?.apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        fusedClient = LocationServices.getFusedLocationProviderClient(this)

        if (locationRequest != null) {
            fusedClient.requestLocationUpdates(
                locationRequest,
                mLocationCallback,
                Looper.myLooper()
            )
        }

    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {

            assignLocationToTextViews(locationResult.lastLocation)

        }
    }

    private fun assignLocationToTextViews(lastLocation: Location?) {
        val latitude = lastLocation?.latitude
        val longitude = lastLocation?.longitude
        val address = latitude?.let { lat ->
            longitude?.let { long ->
                geoCoder.getFromLocation(
                    lat,
                    long, 1
                )
            }
        }?.get(0)?.getAddressLine(0)

        latTV.text = latitude.toString()
        longTV.text = longitude.toString()
        locationTV.text = address.toString()
    }

    private fun composeMmsMessage(message: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:")  // Only SMS apps respond to this.
            putExtra("sms_body", message)
        }
        if (intent.resolveActivity(packageManager) != null) {
            Log.i("composeMmsMessage", "composeMmsMessage: $message")
            startActivity(intent)
        }
    }
}
