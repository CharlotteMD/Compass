package com.example.compass.views

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compass.viewmodels.LocationViewModel

@SuppressLint("MissingPermission")
@Composable
fun WhenPermissionGranted(
    locationViewModel: LocationViewModel
) {

    var trackingSwitch by remember { mutableStateOf(false) }

    LaunchedEffect(trackingSwitch) {
        if(trackingSwitch) {
            locationViewModel.startTracking()
        } else {
            locationViewModel.stopTracking()
        }
    }

    Column {

        Row() {
            Text(text = "Tracking switch: ")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Off")
            Switch(
                checked = trackingSwitch, onCheckedChange = { trackingSwitch = it }
            )
            Text(text = "On")
        }

        DisplayLocationData(locationViewModel = locationViewModel)
    }

}

@Composable
fun DisplayLocationData(
    locationViewModel: LocationViewModel
) {

    val latInput = locationViewModel.locationFromGps?.lat ?: 0.0
    val longInput = locationViewModel.locationFromGps?.long ?: 0.0
    val speedInput = locationViewModel.locationFromGps?.speed ?: 0.0F
    val accuracyInput = locationViewModel.locationFromGps?.accuracy ?: 0.0F
    val altitudeInput = locationViewModel.locationFromGps?.altitude ?: 0.0
    val bearingInput = locationViewModel.locationFromGps?.bearing ?: 0.0F
    val timeInput = locationViewModel.locationFromGps?.time ?: 0.0

    Column() {
        Text(text = "Latitude:  " + latInput)
        Text(text = "Longitude:  " + longInput)
        Text(text = "Altitude:  " + altitudeInput)
        Text(text = "Bearing:  " + bearingInput)
        Text(text = "Accuracy: " + accuracyInput)
        Text(text = "Speed: " + speedInput)
        Text(text = "Time:  " + timeInput)
    }
}
