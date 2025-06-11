package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
            }
        }
    }


@Composable
fun MovieApp() {
    val navController = rememberNavController()
    val movieViewModel: MovieViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("addMovies") {
            AddMoviesScreen(movieViewModel, navController)
        }
        composable("searchMovies") {
            SearchMoviesScreen(movieViewModel, navController)
        }
        composable("searchActors") {
            SearchActorsScreen(movieViewModel, navController)
        }
        composable("searchByTitle") {
            SearchByTitleScreen(movieViewModel, navController)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {

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
            text = "Movie App",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { navController.navigate("addMovies") },
            modifier = Modifier
                .size(200.dp, 50.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF000080))

        ) {
            Text("Add Movies to DB")
        }

        Button(
            onClick = { navController.navigate("searchMovies") },
            modifier = Modifier
                .size(200.dp, 50.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF000080))
        ) {
            Text("Search for Movies")
        }

        Button(
            onClick = { navController.navigate("searchActors") },
            modifier = Modifier
                .size(200.dp, 50.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF000080))
        ) {
            Text("Search for Actors")
        }

        Button(
            onClick = { navController.navigate("searchByTitle") },
            modifier = Modifier
                .size(200.dp, 50.dp)
                .padding(bottom = 16.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF000080))
        ) {
            Text("Search Movies by Title")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
   MovieApp()
}