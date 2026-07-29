package com.fitrutina.app.ui.common

/**
 * Sealed interface que representa los 3 estados típicos de la interfaz de usuario:
 * - Loading: Operación asíncrona en proceso (indicador de carga)
 * - Success: Operación exitosa con datos de tipo T
 * - Error: Falla en la red o base de datos con un mensaje explicativo
 */
sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Success<out T>(val data: T) : UiState<T>
    data class Error(val message: String) : UiState<Nothing>
}
