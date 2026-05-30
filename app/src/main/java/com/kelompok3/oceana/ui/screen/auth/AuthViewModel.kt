package com.kelompok3.oceana.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kelompok3.oceana.supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

data class AuthState(
    val isLoggedIn: Boolean = false,
    val isRegistered: Boolean = false,
    val username: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)

@Serializable
data class Profile(
    val email: String
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
                    errorMessage = parseSupabaseError(e.message)
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
                val emailToUse = if (emailOrUsername.contains("@")) {
                    emailOrUsername
                } else {
                    val result = supabase.postgrest
                        .from("profiles")
                        .select {
                            filter {
                                eq("username", emailOrUsername)
                            }
                        }
                        .decodeSingleOrNull<Profile>()

                    if (result == null) {
                        _authState.value = _authState.value.copy(
                            isLoading = false,
                            errorMessage = "Username tidak ditemukan"
                        )
                        return@launch
                    }
                    result.email
                }

                supabase.auth.signInWith(Email) {
                    this.email = emailToUse
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
                    errorMessage = parseSupabaseError(e.message)
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

    private fun parseSupabaseError(message: String?): String {
        if (message == null) return "Terjadi kesalahan"
        val code = message.substringBefore(" ").substringBefore("(").trim()
        return when (code) {
            "user_already_exists"        -> "Email atau username sudah terdaftar"
            "weak_password"              -> "Password minimal 6 karakter"
            "invalid_credentials"        -> "Email atau password salah"
            "email_not_confirmed"        -> "Email belum dikonfirmasi"
            "over_email_send_rate_limit" -> "Terlalu banyak percobaan, coba lagi nanti"
            else                         -> "Terjadi kesalahan, coba lagi"
        }
    }
}