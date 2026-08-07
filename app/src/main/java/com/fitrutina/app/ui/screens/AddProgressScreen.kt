package com.fitrutina.app.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.fitrutina.app.ui.common.isCameraPermissionGranted
import com.fitrutina.app.ui.common.rememberCameraCaptureLauncher
import com.fitrutina.app.ui.common.rememberCameraPermissionLauncher
import com.fitrutina.app.ui.viewmodel.ExerciseViewModel

/**
 * Pantalla para registrar una foto de progreso físico utilizando la cámara e integrando permisos en runtime.
 */
@Composable
fun AddProgressScreen(viewModel: ExerciseViewModel) {
    val context = LocalContext.current
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var noteText by remember { mutableStateOf("") }
    var showPermissionRationale by remember { mutableStateOf(false) }

    // Launcher para la captura de cámara
    val cameraLauncher = rememberCameraCaptureLauncher { bitmap ->
        capturedBitmap = bitmap
    }

    // Launcher para solicitar permiso de cámara en runtime
    val permissionLauncher = rememberCameraPermissionLauncher(
        onPermissionGranted = {
            cameraLauncher.launch(null)
        },
        onPermissionDenied = {
            showPermissionRationale = true
        }
    )

    fun launchCameraFlow() {
        if (isCameraPermissionGranted(context)) {
            cameraLauncher.launch(null)
        } else {
            permissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "📸 Registrar Progreso",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Toma una foto de tu cambio físico para guardar en tu registro local",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(20.dp))

        // Previsualización de Foto o Botón de Captura
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (capturedBitmap != null) {
                    Image(
                        bitmap = capturedBitmap!!.asImageBitmap(),
                        contentDescription = "Foto de progreso",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedButton(onClick = { launchCameraFlow() }) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Tomar otra foto")
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "Cámara",
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Captura tu progreso físico",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                    Button(onClick = { launchCameraFlow() }) {
                        Icon(Icons.Default.CameraAlt, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text("Abrir Cámara")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Campo para notas del progreso
        OutlinedTextField(
            value = noteText,
            onValueChange = { noteText = it },
            label = { Text("Notas / Peso actual / Observaciones") },
            placeholder = { Text("Ej: 75kg - Semana 4 de entrenamiento") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón de guardado
        Button(
            onClick = {
                if (capturedBitmap != null) {
                    viewModel.saveProgressPhoto(
                        photoUri = "progress_${System.currentTimeMillis()}",
                        note = noteText.ifBlank { null }
                    )
                    capturedBitmap = null
                    noteText = ""
                }
            },
            enabled = capturedBitmap != null,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
            Spacer(modifier = Modifier.size(8.dp))
            Text("Guardar Registro de Progreso")
        }
    }
}
