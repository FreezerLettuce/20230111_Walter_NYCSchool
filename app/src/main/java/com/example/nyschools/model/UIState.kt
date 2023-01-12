package com.example.nyschools.model

sealed class UIState {
    class Loading: UIState()
    data class Error(val msg: String): UIState()
    data class Success<T>(val response: T): UIState()
}
