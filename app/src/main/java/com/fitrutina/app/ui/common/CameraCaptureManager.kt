package com.fitrutina.app.ui.common

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

/**
 * Composable que proporciona un launcher para capturar fotos usando la cámara del dispositivo
 * retornando un Bitmap capturado.
 */
@Composable
fun rememberCameraCaptureLauncher(
    onPhotoCaptured: (Bitmap) -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.TakePicturePreview()
) { bitmap: Bitmap? ->
    if (bitmap != null) {
        onPhotoCaptured(bitmap)
    }
}
