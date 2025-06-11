package com.example.movieapp



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SearchMoviesScreen(viewModel: MovieViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val movie = viewModel.movie
    val message = viewModel.message
    val isLoading = viewModel.isLoading
    val scrollState = rememberScrollState()

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
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Search for Movies",
            style = MaterialTheme.typography.headlineSmall,
            color = White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Movie Title",color= White) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black
            )



        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { viewModel.searchMoviesByTitle(searchQuery) },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color(0xFF000080))
            ) {
                Text("Retrieve Movie")
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { viewModel.saveMovieToDatabase() },
                modifier = Modifier.weight(1f),
                enabled = movie != null,
                colors = ButtonDefaults.buttonColors(Color(0xFF000080))
            ) {
                Text("Save to Database")
            }
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        message?.let {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        movie?.let {
            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text("Title: ${it.title}",color= White)
                Text("Year: ${it.year}",color= White)
                Text("Rated: ${it.rated}",color= White)
                Text("Released: ${it.released}",color= White)
                Text("Runtime: ${it.runtime}",color= White)
                Text("Genre: ${it.genre}",color= White)
                Text("Director: ${it.director}",color= White)
                Text("Writer: ${it.writer}",color= White)
                Text("Actors: ${it.actors}",color= White)
                Text("Plot: \"${it.plot}\"",color= White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color(0xFF000080))
        ) {
            Text("Back to Home")
        }
    }
}