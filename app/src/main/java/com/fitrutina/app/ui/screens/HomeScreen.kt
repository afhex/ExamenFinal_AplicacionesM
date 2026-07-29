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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fitrutina.app.data.remote.dto.ExerciseCategoryDto
import com.fitrutina.app.ui.viewmodel.ExerciseViewModel

/**
 * Retorna un emoji descriptivo basado en el nombre o ID de la categoría de la API.
 */
fun getCategoryEmoji(categoryName: String): String {
    return when (categoryName.lowercase()) {
        "arms", "brazos" -> "💪"
        "legs", "piernas" -> "🦵"
        "chest", "pecho" -> "🫁"
        "back", "espalda" -> "🔙"
        "shoulders", "hombros" -> "🏋️"
        "abs", "abdomen", "core" -> "🧱"
        "calves", "gemelos" -> "🏃"
        else -> "🏋️‍♂️"
    }
}

/**
 * Pantalla principal que muestra las categorías musculares consumidas desde la API REST.
 */
@Composable
fun HomeScreen(
    viewModel: ExerciseViewModel,
    onCategoryClick: (categoryId: Int, categoryName: String) -> Unit = { _, _ -> }
) {
    val categories by viewModel.categories.collectAsStateWithLifecycle()

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

        // Lista de categorías obtenidas de la API
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories) { category ->
                CategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category.id, category.name) }
                )
            }
        }
    }
}

/**
 * Card individual para una categoría muscular obtenida de Retrofit.
 */
@Composable
private fun CategoryCard(
    category: ExerciseCategoryDto,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = getCategoryEmoji(category.name),
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
                    text = "Categoría oficial API wger.de",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
