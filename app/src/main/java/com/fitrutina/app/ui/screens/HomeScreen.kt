package com.fitrutina.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fitrutina.app.ui.viewmodel.ExerciseViewModel

/**
 * Modelo temporal para las categorías musculares.
 * En la Fase 2 se reemplazará con datos de la API.
 */
data class MuscleCategory(
    val id: Int,
    val name: String,
    val emoji: String,
    val exerciseCount: String
)

// Categorías hardcodeadas (se conectarán a la API en Fase 2)
private val muscleCategories = listOf(
    MuscleCategory(8, "Brazos", "💪", "15+ ejercicios"),
    MuscleCategory(9, "Piernas", "🦵", "20+ ejercicios"),
    MuscleCategory(11, "Pecho", "🫁", "12+ ejercicios"),
    MuscleCategory(12, "Espalda", "🔙", "18+ ejercicios"),
    MuscleCategory(13, "Hombros", "🏋️", "10+ ejercicios"),
    MuscleCategory(10, "Abdomen", "🧱", "14+ ejercicios"),
    MuscleCategory(14, "Cardio", "❤️", "8+ ejercicios")
)

/**
 * Pantalla principal que muestra las categorías musculares.
 * Cada categoría se muestra como una card con emoji, nombre y conteo.
 */
@Composable
fun HomeScreen(viewModel: ExerciseViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Text(
            text = "💪 FitRutina",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Elige un grupo muscular para explorar ejercicios",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Lista de categorías
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(muscleCategories) { category ->
                CategoryCard(category = category)
            }
        }
    }
}

/**
 * Card individual para una categoría muscular.
 */
@Composable
private fun CategoryCard(category: MuscleCategory) {
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
            // Emoji como ícono
            Text(
                text = category.emoji,
                fontSize = 36.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = category.exerciseCount,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
