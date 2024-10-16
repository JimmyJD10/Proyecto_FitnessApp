package com.example.aplicativo_fitnessapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    var user: FirebaseUser? = null
        private set

    // Inicia sesión con credenciales
    fun signInWithCredential(credential: AuthCredential, callback: (Boolean) -> Unit) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    _isLoggedIn.value = task.isSuccessful
                    if (task.isSuccessful) {
                        user = auth.currentUser
                    }
                    callback(task.isSuccessful)
                }
            }
    }

    // Registro con email y contraseña
    fun registerWithEmail(email: String, password: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                user = auth.currentUser
                _isLoggedIn.value = true
                callback(true, "Registro exitoso.")
            } catch (e: Exception) {
                callback(false, e.message ?: "Error en el registro.")
            }
        }
    }

    // Inicio de sesión con email y contraseña
    fun signInWithEmail(email: String, password: String, callback: (Boolean, String) -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                user = auth.currentUser
                _isLoggedIn.value = true
                callback(true, "Inicio de sesión exitoso.")
            } catch (e: Exception) {
                callback(false, e.message ?: "Error en el inicio de sesión.")
            }
        }
    }

    // Recuperar contraseña
    fun sendPasswordResetEmail(email: String, callback: (Boolean, String) -> Unit) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Correo de recuperación enviado.")
                } else {
                    callback(false, task.exception?.message ?: "Error al enviar el correo.")
                }
            }
    }

    // Cerrar sesión
    fun signOut() {
        auth.signOut()
        _isLoggedIn.value = false
        user = null
    }
}
