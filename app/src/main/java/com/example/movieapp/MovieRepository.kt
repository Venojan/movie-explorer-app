package com.example.movieapp



import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class MovieRepository(context: Context) {
    private val movieDao = MovieDatabase.getDatabase(context).movieDao()

    // API key for OMDB API - replace with your own API key
    private val apiKey = "57163e64"

    // Add predefined movies to the database
    suspend fun addPreDefinedMovies() {
        val movies = listOf(
            Movie(
                title = "The Shawshank Redemption",
                year = "1994",
                rated = "R",
                released = "14 Oct 1994",
                runtime = "142 min",
                genre = "Drama",
                director = "Frank Darabont",
                writer = "Stephen King, Frank Darabont",
                actors = "Tim Robbins, Morgan Freeman, Bob Gunton",
                plot = "Two imprisoned men bond over a number of years, finding solace and eventual redemption through acts of common decency."
            ),
            Movie(
                title = "Batman: The Dark Knight Returns, Part 1",
                year = "2012",
                rated = "PG-13",
                released = "25 Sep 2012",
                runtime = "76 min",
                genre = "Animation, Action, Crime, Drama, Thriller",
                director = "Jay Oliva",
                writer = "Bob Kane (character created by: Batman), Frank Miller (comic book), Klaus Janson (comic book), Bob Goodman",
                actors = "Peter Weller, Ariel Winter, David Selby, Wade Williams",
                plot = "Batman has not been seen for ten years. A new breed of criminal ravages Gotham City, forcing 55-year-old Bruce Wayne back into the cape and cowl. But, does he still have what it takes to fight crime in a new era?"
            ),
            Movie(
                title = "The Lord of the Rings: The Return of the King",
                year = "2003",
                rated = "PG-13",
                released = "17 Dec 2003",
                runtime = "201 min",
                genre = "Action, Adventure, Drama",
                director = "Peter Jackson",
                writer = "J.R.R. Tolkien, Fran Walsh, Philippa Boyens",
                actors = "Elijah Wood, Viggo Mortensen, Ian McKellen",
                plot = "Gandalf and Aragorn lead the World of Men against Sauron's army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring."
            ),
            Movie(
                title = "Inception",
                year = "2010",
                rated = "PG-13",
                released = "16 Jul 2010",
                runtime = "148 min",
                genre = "Action, Adventure, Sci-Fi",
                director = "Christopher Nolan",
                writer = "Christopher Nolan",
                actors = "Leonardo DiCaprio, Joseph Gordon-Levitt, Elliot Page",
                plot = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.",
            ),
            Movie(
                title = "The Matrix",
                year = "1999",
                rated = "R",
                released = "31 Mar 1999",
                runtime = "136 min",
                genre = "Action, Sci-Fi",
                director = "Lana Wachowski, Lilly Wachowski",
                writer = "Lilly Wachowski, Lana Wachowski",
                actors = "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
                plot = "When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence."
            )
        )

        movieDao.insertMovies(movies)
    }

    // Search for movies by actor name
    suspend fun searchMoviesByActor(actorName: String): List<Movie> {
        return movieDao.searchMoviesByActor(actorName)
    }

    // Get movie from OMDB API by title
    suspend fun getMovieByTitle(title: String): Movie? {
        return withContext(Dispatchers.IO) {
            try {
                val response = URL("https://www.omdbapi.com/?t=$title&apikey=$apiKey").readText()
                val jsonObject = JSONObject(response)

                if (jsonObject.optString("Response") == "True") {
                    Movie(
                        title = jsonObject.optString("Title"),
                        year = jsonObject.optString("Year"),
                        rated = jsonObject.optString("Rated"),
                        released = jsonObject.optString("Released"),
                        runtime = jsonObject.optString("Runtime"),
                        genre = jsonObject.optString("Genre"),
                        director = jsonObject.optString("Director"),
                        writer = jsonObject.optString("Writer"),
                        actors = jsonObject.optString("Actors"),
                        plot = jsonObject.optString("Plot"),

                    )
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    // Search for multiple movies by title
    suspend fun searchMoviesByTitle(query: String): List<Movie> {
        return withContext(Dispatchers.IO) {
            try {
                val response = URL("https://www.omdbapi.com/?s=$query&apikey=$apiKey").readText()
                val jsonObject = JSONObject(response)

                val moviesList = mutableListOf<Movie>()

                if (jsonObject.optString("Response") == "True") {
                    val results = jsonObject.getJSONArray("Search")

                    for (i in 0 until results.length()) {
                        val movie = results.getJSONObject(i)
                        moviesList.add(
                            Movie(
                                title = movie.optString("Title"),
                                year = movie.optString("Year"),
                                rated = "",
                                released = "",
                                runtime = "",
                                genre = "",
                                director = "",
                                writer = "",
                                actors = "",
                                plot = ""
                            )
                        )
                    }
                }

                moviesList
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    // Save a movie to the database
    suspend fun saveMovie(movie: Movie) {
        movieDao.insertMovie(movie)
    }
}