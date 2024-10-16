package com.example.aplicativo_fitnessapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aplicativo_fitnessapp.viewmodel.AuthViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.aplicativo_fitnessapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    onGoogleSignIn: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showPasswordResetMessage by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Colores personalizados
    val backgroundColor = Color(0xFFB2DFDB)
    val primaryColor = Color(0xFF1E88E5)
    val textColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "FITNESS APP",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Image(
            painter = painterResource(R.drawable.logo_fit_x),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                cursorColor = primaryColor,
                focusedIndicatorColor = primaryColor,
                unfocusedIndicatorColor = Color.Gray,
                focusedLabelColor = primaryColor,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña", color = Color.Black) },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation(),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                cursorColor = primaryColor,
                focusedIndicatorColor = primaryColor,
                unfocusedIndicatorColor = Color.Gray,
                focusedLabelColor = primaryColor,
                unfocusedLabelColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(color = primaryColor)
        } else {
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        isLoading = true
                        authViewModel.signInWithEmail(email, password) { success, message ->
                            isLoading = false
                            if (success) {
                                onLoginSuccess()
                            } else {
                                errorMessage = message
                            }
                        }
                    } else {
                        errorMessage = "Por favor, completa todos los campos."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryColor
                ),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text("Iniciar Sesión", color = textColor)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón de inicio de sesión con Google
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onGoogleSignIn() },
                modifier = Modifier
                    .size(64.dp)
                    .background(Color(0xFFB2DFDB), shape = CircleShape)
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.google_icon),
                    contentDescription = "Iniciar sesión con Google",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = {
                if (email.isNotEmpty()) {
                    authViewModel.sendPasswordResetEmail(email) { success, message ->
                        if (success) {
                            showPasswordResetMessage = true // Cambia el estado a verdadero
                            errorMessage = ""
                        } else {
                            errorMessage = message
                            showPasswordResetMessage = false // Cambia el estado a falso
                        }
                    }
                } else {
                    errorMessage = "Introduce tu correo para restablecer la contraseña."
                }
            }
        ) {
            Text("¿Olvidaste tu contraseña?", color = Color.Black)
        }

        if (showPasswordResetMessage) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Correo de recuperación enviado.", color = Color.Green)
        }


        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { onNavigateToRegister() }) {
            Text("¿No tienes una cuenta? Crea una aquí", color = Color.Black)
        }
    }
}

