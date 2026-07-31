package com.fitrutina.app.ui.common

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.compose.runtime.Composable

/**
 * Función utilitaria para verificar si el permiso de CÁMARA ha sido concedido en runtime.
 */
fun isCameraPermissionGranted(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}

/**
 * Composable que encapsula la solicitud del permiso de cámara en runtime
 * utilizando ActivityResultContracts.RequestPermission.
 */
@Composable
fun rememberCameraPermissionLauncher(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.RequestPermission()
) { isGranted: Boolean ->
    if (isGranted) {
        onPermissionGranted()
    } else {
        onPermissionDenied()
    }
}
