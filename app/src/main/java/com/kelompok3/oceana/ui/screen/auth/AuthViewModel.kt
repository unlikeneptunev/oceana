package com.kelompok3.oceana.ui.screen.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class User(
    val email: String,
    val username: String,
    val password: String
)

data class AuthState(
    val isLoggedIn: Boolean = false,
    val loggedInUser: User? = null,
    val errorMessage: String? = null
)

class AuthViewModel : ViewModel() {

    private val registeredUsers = mutableListOf<User>()

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun register(email: String, username: String, password: String, confirmPassword: String): Boolean {
        if (email.isBlank() || username.isBlank() || password.isBlank()) {
            _authState.value = _authState.value.copy(errorMessage = "Semua field harus diisi")
            return false
        }
        if (password != confirmPassword) {
            _authState.value = _authState.value.copy(errorMessage = "Password tidak cocok")
            return false
        }
        if (registeredUsers.any { it.email == email }) {
            _authState.value = _authState.value.copy(errorMessage = "Email sudah terdaftar")
            return false
        }
        if (registeredUsers.any { it.username == username }) {
            _authState.value = _authState.value.copy(errorMessage = "Username sudah digunakan")
            return false
        }
        registeredUsers.add(User(email, username, password))
        _authState.value = _authState.value.copy(errorMessage = null)
        return true
    }

    fun login(emailOrUsername: String, password: String): Boolean {
        if (emailOrUsername.isBlank() || password.isBlank()) {
            _authState.value = _authState.value.copy(errorMessage = "Semua field harus diisi")
            return false
        }
        val user = registeredUsers.find {
            (it.email == emailOrUsername || it.username == emailOrUsername) && it.password == password
        }
        if (user == null) {
            _authState.value = _authState.value.copy(errorMessage = "Email/username atau password salah")
            return false
        }
        _authState.value = AuthState(isLoggedIn = true, loggedInUser = user)
        return true
    }

    fun logout() {
        _authState.value = AuthState()
    }

    fun clearError() {
        _authState.value = _authState.value.copy(errorMessage = null)
    }
}