package com.example.aplicativo_fitnessapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.aplicativo_fitnessapp.ui.screens.LoginScreen
import com.example.aplicativo_fitnessapp.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            val isLoggedIn by authViewModel.isLoggedIn.collectAsState(initial = false)

            if (isLoggedIn) {
                HomeScreen(onLogout = { authViewModel.signOut() })
            } else {
                WelcomeScreen(
                    onGetStarted = {
                        setContent {
                            LoginScreen(
                                authViewModel = authViewModel,
                                onGoogleSignIn = { signInWithGoogle() },
                                onNavigateToRegister = {
                                    // Navegar a la pantalla de registro
                                    var showRegisterScreen = true
                                },
                                onLoginSuccess = {
                                    setContent {
                                        HomeScreen(onLogout = { authViewModel.signOut() })
                                    }
                                }
                            )
                        }
                    },
                    onLogin = {
                        setContent {
                            LoginScreen(
                                authViewModel = authViewModel,
                                onGoogleSignIn = { signInWithGoogle() },
                                onNavigateToRegister = {
                                    // Navegar a la pantalla de registro
                                    var showRegisterScreen = true
                                },
                                onLoginSuccess = {
                                    setContent {
                                        HomeScreen(onLogout = { authViewModel.signOut() })
                                    }
                                }
                            )
                        }
                    }
                )
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        authViewModel.signInWithCredential(credential) { success ->
            if (success) {
                // Usuario autenticado exitosamente
            } else {
                // Manejar error de autenticación
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Manejar el error de autenticación
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
