package com.kelompok3.oceana.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.oceana.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoggedIn: Boolean = false,
    val isRegistered: Boolean = false,
    val username: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

class AuthViewModel : ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    fun register(email: String, username: String, password: String, confirmPassword: String) {
        if (email.isBlank() || username.isBlank() || password.isBlank()) {
            _authState.value = _authState.value.copy(errorMessage = "Semua field harus diisi")
            return
        }
        if (password != confirmPassword) {
            _authState.value = _authState.value.copy(errorMessage = "Password tidak cocok")
            return
        }

        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, errorMessage = null)
            try {
                supabase.auth.signUpWith(Email) {
                    this.email = email
                    this.password = password
                    data = kotlinx.serialization.json.buildJsonObject {
                        put("username", kotlinx.serialization.json.JsonPrimitive(username))
                    }
                }
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isRegistered = true,
                    errorMessage = null
                )
            } catch (e: Exception) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Registrasi gagal"
                )
            }
        }
    }

    fun login(emailOrUsername: String, password: String) {
        if (emailOrUsername.isBlank() || password.isBlank()) {
            _authState.value = _authState.value.copy(errorMessage = "Semua field harus diisi")
            return
        }

        viewModelScope.launch {
            _authState.value = _authState.value.copy(isLoading = true, errorMessage = null)
            try {
                supabase.auth.signInWith(Email) {
                    this.email = emailOrUsername
                    this.password = password
                }
                val user = supabase.auth.currentUserOrNull()
                val username = user?.userMetadata
                    ?.get("username")
                    ?.toString()
                    ?.trim('"')
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    username = username
                )
            } catch (e: Exception) {
                _authState.value = _authState.value.copy(
                    isLoading = false,
                    errorMessage = e.message ?: "Login gagal"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                supabase.auth.signOut()
            } catch (_: Exception) {}
            _authState.value = AuthState()
        }
    }

    fun clearError() {
        _authState.value = _authState.value.copy(errorMessage = null)
    }
}