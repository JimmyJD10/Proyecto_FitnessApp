package com.example.aplicativo_fitnessapp.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*

@Composable
fun WelcomeScreen(
    onSignInClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    // Colores personalizados
    val backgroundColor = Color(0xFFF0F0F0) // Fondo claro
    val textColor = Color.Black // Texto oscuro

    // Estructura principal
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor) // Fondo claro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Título de la app
        Text(
            text = "FITNESS",
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = textColor,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        // Espacio entre el título y la animación
        Spacer(modifier = Modifier.height(10.dp))

        // Animación Lottie centrada
        val composition by rememberLottieComposition(LottieCompositionSpec.Asset("Animation_-_1729129889323.json"))
        val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(400.dp) // Ajustar tamaño si es necesario
                .padding(bottom = 16.dp)
        )

        // Espacio entre la animación y los botones
        Spacer(modifier = Modifier.height(10.dp))

        // Botones de Iniciar Sesión y Registrarse
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { onSignInClick() },
                modifier = Modifier
                    .width(200.dp) // Ancho personalizado
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD50404) // Color rojo
                ),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text("Iniciar Sesión", color = Color.White)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onRegisterClick() },
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD50404) // Color rojo
                ),
                shape = RoundedCornerShape(50.dp)
            ) {
                Text("Registrarse", color = Color.White)
            }
        }
    }
}

