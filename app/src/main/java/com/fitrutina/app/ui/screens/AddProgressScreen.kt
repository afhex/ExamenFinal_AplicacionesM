package com.fitrutina.app.ui.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fitrutina.app.data.local.entity.ProgressPhoto
import com.fitrutina.app.ui.common.isCameraPermissionGranted
import com.fitrutina.app.ui.common.rememberCameraCaptureLauncher
import com.fitrutina.app.ui.common.rememberCameraPermissionLauncher
import com.fitrutina.app.ui.viewmodel.ExerciseViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Formatea una fecha timestamp a String legible.
 */
fun formatTimestamp(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

/**
 * Pantalla para registrar y visualizar la galería de fotos de progreso físico guardadas en Room.
 */
@Composable
fun AddProgressScreen(viewModel: ExerciseViewModel) {
    val context = LocalContext.current
    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var noteText by remember { mutableStateOf("") }
    var showPermissionRationale by remember { mutableStateOf(false) }
    var cameraErrorMessage by remember { mutableStateOf<String?>(null) }

    val savedPhotos by viewModel.progressPhotos.collectAsStateWithLifecycle(initialValue = emptyList())

    val cameraLauncher = rememberCameraCaptureLauncher { bitmap ->
        if (bitmap != null) {
            capturedBitmap = bitmap
            cameraErrorMessage = null
        } else {
            cameraErrorMessage = "Captura cancelada o no disponible."
        }
    }

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
                            .height(240.dp)
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
                            .height(180.dp),
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

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de guardado
        Button(
            onClick = {
                if (capturedBitmap != null) {
                    viewModel.saveProgressPhoto(
                        photoUri = "captured_progress_${System.currentTimeMillis()}",
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

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        // Galería de fotos guardadas en Room
        Text(
            text = "🖼️ Historial de Progreso (${savedPhotos.size})",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(12.dp))

        if (savedPhotos.isEmpty()) {
            Text(
                text = "No has guardado fotos de progreso aún.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                savedPhotos.forEach { photo ->
                    ProgressPhotoItemCard(
                        photo = photo,
                        onDelete = { viewModel.deleteProgressPhoto(photo) }
                    )
                }
            }
        }
    }
}

/**
 * Tarjeta individual para mostrar un registro de progreso guardado en Room.
 */
@Composable
private fun ProgressPhotoItemCard(
    photo: ProgressPhoto,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "📅 ${formatTimestamp(photo.date)}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                if (!photo.note.isNull_or_blank()) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = photo.note!!,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar progreso",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    if (showPermissionRationale) {
        com.fitrutina.app.ui.common.PermissionRationaleDialog(
            onDismiss = { showPermissionRationale = false }
        )
    }
}


private fun String?.isNull_or_blank(): Boolean = this == null || this.trim().isEmpty()
