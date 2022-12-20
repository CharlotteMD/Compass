package com.example.compass

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.google.accompanist.permissions.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compass.ui.theme.CompassTheme
import android.Manifest.permission.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.repeatOnLifecycle
import com.example.compass.views.WhenPermissionDenied
import com.example.compass.views.WhenPermissionGranted

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompassTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Permissions()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Permissions() {

    val necessaryPermissions: MultiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION
        )
    )

    val permissionsLifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(
        key1 = permissionsLifecycleOwner,
        effect = {
            val permissionsObserver = LifecycleEventObserver {_, event ->
                if(event == Lifecycle.Event.ON_RESUME) {
                    necessaryPermissions.launchMultiplePermissionRequest()
                }
            }
            permissionsLifecycleOwner.lifecycle.addObserver(permissionsObserver)

            onDispose {
                permissionsLifecycleOwner.lifecycle.removeObserver(permissionsObserver)
            }
        }
    )

    if (necessaryPermissions.allPermissionsGranted) {
        WhenPermissionGranted()
    } else {
        WhenPermissionDenied()
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CompassTheme {
        Permissions()
    }
}