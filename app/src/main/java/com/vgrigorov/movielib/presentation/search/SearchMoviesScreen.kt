package com.vgrigorov.movielib.presentation.search

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import com.vgrigorov.movielib.presentation.Screen
import com.vgrigorov.movielib.presentation.home.MovieComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchMoviesViewModel = hiltViewModel()
) {
    val searchResult = viewModel.search.value.collectAsLazyPagingItems()
    var query by rememberSaveable { mutableStateOf("") }
    var expanded by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    //TODO: Fix UI, Background color, spacings etc.
    Column {
        SearchBar(
            modifier = Modifier
                .semantics { traversalIndex = 0f },
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                if (query.isNotEmpty()) {
                    viewModel.searchParam.value = query
                    viewModel.searchMovies(query)
                    expanded = false
                } else {
                    Toast.makeText(context, "Please enter text", Toast.LENGTH_LONG).show()
                }
            },
            placeholder = { Text("Enter text...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            active = expanded,
            onActiveChange = {}
        ) {}

        LazyColumn {
            items(searchResult.itemCount) { index ->
                val movie = searchResult[index]
                if (movie != null) {
                    //TODO: Fix UI for this, maybe do it in a row? so
                    // [ picture title ] - clicked goes to detials as it is now
                    MovieComponent(movie = movie, onClick = {
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            Screen.MovieDetails.MOVIE_KEY,
                            movie
                        )
                        navController.navigate(Screen.MovieDetails.route)
                    })
                }
            }

            // Show loading indicator when loading more items
            if (searchResult.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}