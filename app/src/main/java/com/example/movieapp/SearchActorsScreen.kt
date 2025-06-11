package com.example.movieapp



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun SearchActorsScreen(viewModel: MovieViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults = viewModel.actorSearchResults
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
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Search for Actors",
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Actor Name",color= Color.White) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.searchMoviesByActor(searchQuery) },
            modifier = Modifier.size(150.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF000080))
        ) {
            Text("Search")
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

        Spacer(modifier = Modifier.height(16.dp))

        if (searchResults.isNotEmpty()) {
            Text(
                text = "Movies with ${searchQuery}:",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(searchResults) { movie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text("Year: ${movie.year}")
                            Text("Director: ${movie.director}")
                            Text("Actors: ${movie.actors}")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { navController.navigateUp() },
            modifier = Modifier.size(150.dp, 40.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF000080))
        ) {
            Text("Back to Home")
        }
    }
}