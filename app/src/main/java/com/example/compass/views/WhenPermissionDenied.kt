package com.example.compass.views

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun WhenPermissionDenied() {
    Text("You must give permission to get your current location. " +
            "Please enable this in your phone settings.")
}