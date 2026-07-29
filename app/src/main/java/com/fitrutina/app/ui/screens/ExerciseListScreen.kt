package com.fitrutina.app.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fitrutina.app.data.remote.dto.ExerciseDto
import com.fitrutina.app.ui.common.ErrorStateScreen
import com.fitrutina.app.ui.common.LoadingStateScreen
import com.fitrutina.app.ui.common.UiState
import com.fitrutina.app.ui.viewmodel.ExerciseViewModel

/**
 * Remueve etiquetas HTML (como <p>, <br>) para mostrar la descripción limpia.
 */
fun cleanHtmlTags(htmlText: String): String {
    return htmlText.replace(Regex("<[^>]*>"), "").trim()
}

/**
 * Pantalla que muestra la lista de ejercicios pertenecientes a una categoría.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseListScreen(
    categoryId: Int,
    categoryName: String,
    viewModel: ExerciseViewModel,
    onExerciseClick: (ExerciseDto) -> Unit,
    onBackClick: () -> Unit
) {
    LaunchedEffect(categoryId) {
        viewModel.fetchExercisesByCategory(categoryId)
    }

    val exercisesState by viewModel.exercisesState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = categoryName) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            when (val state = exercisesState) {
                is UiState.Loading -> {
                    LoadingStateScreen(message = "Cargando ejercicios de $categoryName...")
                }
                is UiState.Error -> {
                    ErrorStateScreen(
                        message = state.message,
                        onRetry = { viewModel.fetchExercisesByCategory(categoryId) }
                    )
                }
                is UiState.Success -> {
                    if (state.data.isEmpty()) {
                        Text(
                            text = "No se encontraron ejercicios en esta categoría.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 24.dp)
                        )
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(vertical = 12.dp)
                        ) {
                            items(state.data) { exercise ->
                                ExerciseItemCard(
                                    exercise = exercise,
                                    onClick = { onExerciseClick(exercise) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Card individual para un ejercicio de la lista.
 */
@Composable
private fun ExerciseItemCard(
    exercise: ExerciseDto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = exercise.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            val cleanDescription = cleanHtmlTags(exercise.description)
            if (cleanDescription.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = cleanDescription,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
        }
    }
}
