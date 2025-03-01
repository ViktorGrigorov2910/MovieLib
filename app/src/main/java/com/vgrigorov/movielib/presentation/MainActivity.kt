package com.vgrigorov.movielib.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vgrigorov.movielib.presentation.theme.MovieLibTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.vgrigorov.movielib.domain.models.Movie
import com.vgrigorov.movielib.presentation.home.HomeScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieLibTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color =Color.Black
                ) {
                    MovieLibApp()
                }
            }
        }
    }
}

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Search : Screen("search", "Search")
    object Favorites : Screen("favorites", "Favorites")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieLibApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    topRatedMovies = dummyMovies,
                    popularMovies = dummyMovies,
                    nowPlayingMovies = dummyMovies
                )
            }
            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Favorites.route) { FavoritesScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Screen.Home, Screen.Search, Screen.Favorites)
    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                label = { Text(screen.title) },
                selected = false, // Will update later
                onClick = { navController.navigate(screen.route) },
                icon = { Icon(Icons.Default.Home, contentDescription = screen.title) }
            )
        }
    }
}

val dummyMovies = listOf(
    Movie(
        title = "Inception",
        posterPath = "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/s3TBrRGB1iav7gFOCNx3H31MoES.jpg",
        releaseDate = "2010-07-16",
        rating = 8.8,
        overview = "A thief who enters the dreams of others to steal secrets from their subconscious."
    ),
    Movie(
        title = "The Dark Knight",
        posterPath = "https://image.tmdb.org/t/p/w500/qJ2tW6WMUDux911r6m7haRef0WH.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/hjQp148VjWF4KjzhsD90OCMr11h.jpg",
        releaseDate = "2008-07-18",
        rating = 9.0,
        overview = "Batman faces the Joker, a criminal mastermind who seeks to create chaos in Gotham."
    ),
    Movie(
        title = "Interstellar",
        posterPath = "https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/rAiYTfKGqDCRIIqo664sY9XZIvQ.jpg",
        releaseDate = "2014-11-07",
        rating = 8.6,
        overview = "A group of explorers travel through a wormhole in space to ensure humanity's survival."
    ),
    Movie(
        title = "Avatar",
        posterPath = "https://image.tmdb.org/t/p/w500/kyeqWdyUXW608qlYkRqosgbbJyK.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/oM8s40cTb4Z9U1NzqUe6wXBpyUb.jpg",
        releaseDate = "2009-12-18",
        rating = 7.9,
        overview = "A paraplegic Marine is dispatched to the moon Pandora and becomes part of an indigenous race."
    ),
    Movie(
        title = "Parasite",
        posterPath = "https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
        backdropPath = "https://image.tmdb.org/t/p/w1280/TgH9POOinptF9zRzj4uJROK1oNj.jpg",
        releaseDate = "2019-05-30",
        rating = 8.5,
        overview = "Greed and class discrimination threaten the newly formed symbiotic relationship between a wealthy family and a poor one."
    )
)

@Composable
fun SearchScreen() {
    //TODO: Move to separate package
    Text(text = "Search Screen - Movie Search Results")
}

@Composable
fun FavoritesScreen() {
    //TODO: Move to separate package
    // Local DB implementations is required for this to be picked up
    Text(text = "Favorites Screen - Saved Movies")
}
