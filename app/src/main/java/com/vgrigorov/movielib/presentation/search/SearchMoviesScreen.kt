package com.vgrigorov.movielib.presentation.search

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import com.skydoves.landscapist.glide.GlideImage
import com.vgrigorov.movielib.Constants.Companion.BASE_POSTER_IMAGE_URL
import com.vgrigorov.movielib.R
import com.vgrigorov.movielib.domain.models.Movie
import com.vgrigorov.movielib.presentation.Screen
import com.vgrigorov.movielib.presentation.reusable_components.GlideImageFailedState

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
    val keyboardController = LocalSoftwareKeyboardController.current

    // Set the background to black
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column {
            // Search Bar
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .semantics { traversalIndex = 0f },
                query = query,
                onQueryChange = { query = it },
                onSearch = {
                    if (query.isNotEmpty()) {
                        keyboardController?.hide() // Hides the keyboard
                        viewModel.searchParam.value = query
                        viewModel.searchMovies(query)
                        expanded = false
                    } else {
                        Toast.makeText(context, "Please enter text", Toast.LENGTH_LONG).show()
                    }
                },
                placeholder = {
                    Text(
                        stringResource(id = R.string.search_placeholder_msg),
                        color = Color.Gray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Black
                    )
                },
                active = false,
                onActiveChange = { expanded = it },
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White, // White background for the search bar
                    inputFieldColors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black, // Black text when focused
                        unfocusedTextColor = Color.Black, // Black text when unfocused
                        cursorColor = Color.Black, // Black cursor
                        focusedIndicatorColor = Color.Transparent, // No underline when focused
                        unfocusedIndicatorColor = Color.Transparent // No underline when unfocused
                    )
                )
            ) {}

            if (searchResult.itemCount == 0) {
                SearchEmptyState()
            } else {
                // Search Results
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    items(searchResult.itemCount) { index ->
                        val movie = searchResult[index]
                        if (movie != null) {
                            // Display search results in a row format
                            SearchResultRow(
                                movie = movie,
                                onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        Screen.MovieDetails.MOVIE_KEY,
                                        movie
                                    )
                                    navController.navigate(Screen.MovieDetails.route)
                                }
                            )
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
                                CircularProgressIndicator(color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultRow(
    movie: Movie,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(color = Color.White),
                onClick = onClick
            )
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Movie Poster
        GlideImage(
            imageModel = { BASE_POSTER_IMAGE_URL + movie.posterPath },
            loading = {
                CircularProgressIndicator(
                    modifier = Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            },
            failure = {
                GlideImageFailedState(
                    size = 64.dp,
                    iconSize = 12.dp,
                    shape = RoundedCornerShape(8.dp),
                )
            },
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Movie Title
        Text(
            text = movie.title,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun SearchEmptyState() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search Icon",
            tint = Color.White,
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.search_empty_state_msg),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
