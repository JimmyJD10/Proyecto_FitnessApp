package com.example.aplicativo_fitnessapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicativo_fitnessapp.ui.theme.FitnessAppTheme

class ExerciseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FitnessAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExerciseScreen()
                }
            }
        }
    }
}

@Composable
fun ExerciseScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Exercise",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Here you can log your exercises.",
            style = MaterialTheme.typography.bodyMedium
        )
        // Add more UI elements based on the Figma design
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewExerciseScreen() {
    FitnessAppTheme {
        ExerciseScreen()
    }
}
