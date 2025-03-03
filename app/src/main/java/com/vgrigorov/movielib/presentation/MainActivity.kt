package com.vgrigorov.movielib.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.vgrigorov.movielib.domain.models.Movie
import com.vgrigorov.movielib.presentation.favorites.FavoritesScreen
import com.vgrigorov.movielib.presentation.home.HomeScreen
import com.vgrigorov.movielib.presentation.movie_details.MovieDetailsScreen
import com.vgrigorov.movielib.presentation.search.SearchScreen
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

                MovieDetailsScreen(movie = movie, onBackClicked = { navController.popBackStack() })
            }

            composable(Screen.Search.route) { SearchScreen(navController) }
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
                // screen.icon ?: -> impossible case since screens that are include in navigation already have icons
                icon = {
                    Icon(
                        screen.icon ?: Icons.Default.Home,
                        contentDescription = screen.title
                    )
                }
            )
        }
    }
}

sealed class Screen(val route: String, val title: String? = null, val icon: ImageVector? = null) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Search : Screen("search", "Search", Icons.Default.Search)
    object Favorites : Screen("favorites", "Favorites", Icons.Default.Favorite)
    object MovieDetails : Screen("movie_details") {
        const val MOVIE_KEY = "movie"
    }
}

