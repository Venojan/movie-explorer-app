package com.example.movieapp



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AddMoviesScreen(viewModel: MovieViewModel, navController: NavController) {
    val message = viewModel.message
    val isLoading = viewModel.isLoading

    LaunchedEffect(Unit) {
        // Reset message when screen is loaded
        viewModel.resetMessage()
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color(0xFF000033))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Add Predefined Movies to Database",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            textAlign = TextAlign.Center

        )

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.addPreDefinedMovies() },
                modifier = Modifier.size(250.dp, 40.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF000080))
            ) {
                Text("Add Movies to Database")
            }
        }

        message?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.size(250.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF000080))
        ) {
            Text("Back to Home")
        }
    }
}