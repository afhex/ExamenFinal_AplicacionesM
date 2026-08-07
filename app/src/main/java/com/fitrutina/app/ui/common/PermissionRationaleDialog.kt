package com.fitrutina.app.ui.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Abre la pantalla de ajustes de la aplicación en el sistema Android.
 */
fun openAppSettings(context: Context) {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    ).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}

/**
 * Diálogo explicativo que se muestra cuando el usuario deniega el permiso de cámara.
 */
@Composable
fun PermissionRationaleDialog(
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "📷 Permiso de Cámara Requerido")
        },
        text = {
            Text(
                text = "FitRutina necesita acceso a la cámara para tomar fotos de tu progreso físico y guardarlas localmente. Puedes otorgar el permiso desde los ajustes de la aplicación."
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss()
                    openAppSettings(context)
                }
            ) {
                Text("Abrir Ajustes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
