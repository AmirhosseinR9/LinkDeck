package com.smartlinksaver.presentation.viewmodel

sealed class LinkUiState {
    data object Idle    : LinkUiState()
    data object Loading : LinkUiState()
    data object Success : LinkUiState()
    data class  Error(val message: String) : LinkUiState()
}
