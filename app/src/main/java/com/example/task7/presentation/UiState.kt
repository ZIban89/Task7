package com.example.task7.presentation

sealed class UiState<out T> {

    object Loading: UiState<Nothing>()

    class Error(val message: String): UiState<Nothing>()

    class Success<T>(val data: T): UiState<T>()

    object Idle: UiState<Nothing>()
}
