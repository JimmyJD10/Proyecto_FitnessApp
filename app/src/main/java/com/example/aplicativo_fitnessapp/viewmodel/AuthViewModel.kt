package com.example.aplicativo_fitnessapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    // Inicia sesión con credenciales
    fun signInWithCredential(credential: AuthCredential, callback: (Boolean) -> Unit) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    _isLoggedIn.value = task.isSuccessful
                    callback(task.isSuccessful)
                }
            }
    }

    // Registro con email y contraseña
    fun registerWithEmail(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, "Registro exitoso.")
                } else {
                    callback(false, task.exception?.message ?: "Error en el registro.")
                }
            }
    }

    // Inicio de sesión con email y contraseña
    fun signInWithEmail(email: String, password: String, callback: (Boolean, String) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _isLoggedIn.value = true // Actualizar el estado
                    callback(true, "Inicio de sesión exitoso.")
                } else {
                    callback(false, task.exception?.message ?: "Error en el inicio de sesión.")
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
        _isLoggedIn.value = false // Actualizar el estado
    }
}
