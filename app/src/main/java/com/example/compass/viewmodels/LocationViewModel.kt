package com.example.compass.viewmodels

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.*
import android.os.Looper
import androidx.annotation.RequiresPermission

class MyLocation(
    val lat: Double,
    val long: Double,
    val speed: Float,
    val accuracy: Float,
    val altitude: Double,
    val bearing: Float,
    val time: Long
)

interface ILocationViewModel {
    var locationFromGps: MyLocation?
    fun startTracking()
    fun stopTracking()
}

class LocationViewModel(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
): ViewModel(), ILocationViewModel {

    override var locationFromGps: MyLocation? by mutableStateOf(null)

    private val locationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationFromGps = locationResult.lastLocation?.let { l ->
                    MyLocation(
                        l.latitude,
                        l.longitude,
                        l.speed,
                        l.accuracy,
                        l.altitude,
                        l.bearing,
                        l.time
                    )
                }
            }
        }

    @RequiresPermission(anyOf = [ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION])
    override fun startTracking() {

        val locationRequest = LocationRequest
            .Builder(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    override fun stopTracking() {
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }
}