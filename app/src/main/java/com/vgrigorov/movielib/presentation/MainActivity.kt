package com.vgrigorov.movielib.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
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
                    color = MaterialTheme.colorScheme.background
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
            composable(Screen.Home.route) { HomeScreen() }
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

@Composable
fun HomeScreen() {
    Text(text = "Home Screen - Movie Categories will be here")
}

@Composable
fun SearchScreen() {
    Text(text = "Search Screen - Movie Search Results")
}

@Composable
fun FavoritesScreen() {
    Text(text = "Favorites Screen - Saved Movies")
}
