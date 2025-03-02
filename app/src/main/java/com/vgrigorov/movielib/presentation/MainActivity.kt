package com.vgrigorov.movielib.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.vgrigorov.movielib.domain.models.Movie
import com.vgrigorov.movielib.presentation.home.HomeScreen
import com.vgrigorov.movielib.presentation.movie_details.MovieDetailsScreen
import com.vgrigorov.movielib.presentation.theme.MovieLibTheme
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
                    color = Color.Black
                ) {
                    MovieLibApp()
                }
            }
        }
    }
}

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
                    navController = navController
                )
            }

            // Movie Details Screen
            composable(Screen.MovieDetails.route) { backStackEntry ->
                // Retrieve the movie ID from the route
                val movie =
                    navController.previousBackStackEntry?.savedStateHandle?.get<Movie>(Screen.MovieDetails.MOVIE_KEY)

                MovieDetailsScreen (movie = movie, onBackClicked =  { navController.popBackStack() })
            }

            composable(Screen.Search.route) { SearchScreen() }
            composable(Screen.Favorites.route) { FavoritesScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(Screen.Home, Screen.Search, Screen.Favorites)

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black
    ) {
        items.forEach { screen ->
            NavigationBarItem(
                label = { Text(screen.title ?: "") },
                selected = currentRoute == screen.route, // Will update later
                onClick = {
                    navController.navigate(screen.route)
                },
                icon = { Icon(Icons.Default.Home, contentDescription = screen.title) }
            )
        }
    }
}

sealed class Screen(val route: String, val title: String? = null) {
    object Home : Screen("home", "Home")
    object Search : Screen("search", "Search")
    object Favorites : Screen("favorites", "Favorites")
    object MovieDetails : Screen("movie_details") {
        const val MOVIE_KEY = "movie"
    }
}

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
