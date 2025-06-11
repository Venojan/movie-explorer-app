package com.example.movieapp



import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepository(application)

    var movie by mutableStateOf<Movie?>(null)
        private set

    var actorSearchResults by mutableStateOf<List<Movie>>(emptyList())
        private set

    var titleSearchResults by mutableStateOf<List<Movie>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var message by mutableStateOf<String?>(null)
        private set

    // Add predefined movies to database
    fun addPreDefinedMovies() {
        viewModelScope.launch {
            try {
                isLoading = true
                repository.addPreDefinedMovies()
                message = "Movies added to database successfully"
            } catch (e: Exception) {
                message = "Error adding movies: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // Search for a movie by title
    fun searchMoviesByTitle(title: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                message = null
                val result = repository.getMovieByTitle(title)
                movie = result
                if (result == null) {
                    message = "Movie not found"
                }
            } catch (e: Exception) {
                message = "Error searching for movie: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // Save current movie to database
    fun saveMovieToDatabase() {
        viewModelScope.launch {
            try {
                isLoading = true
                message = null
                movie?.let {
                    repository.saveMovie(it)
                    message = "Movie saved to database successfully"
                } ?: run {
                    message = "No movie to save"
                }
            } catch (e: Exception) {
                message = "Error saving movie: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // Search for movies by actor name
    fun searchMoviesByActor(actorName: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                message = null
                val results = repository.searchMoviesByActor(actorName)
                actorSearchResults = results
                if (results.isEmpty()) {
                    message = "No movies found with actor: $actorName"
                }
            } catch (e: Exception) {
                message = "Error searching for actor: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // Search for multiple movies by title
    fun searchMultipleMoviesByTitle(query: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                message = null
                val results = repository.searchMoviesByTitle(query)
                titleSearchResults = results
                if (results.isEmpty()) {
                    message = "No movies found with title: $query"
                }
            } catch (e: Exception) {
                message = "Error searching for movies: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // Reset message
    fun resetMessage() {
        message = null
    }
}