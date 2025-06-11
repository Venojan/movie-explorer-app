package com.example.movieapp


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM movies WHERE actors LIKE '%' || :actorName || '%'")
    suspend fun searchMoviesByActor(actorName: String): List<Movie>

    @Query("SELECT * FROM movies")
    suspend fun getAllMovies(): List<Movie>
}