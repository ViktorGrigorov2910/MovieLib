package com.vgrigorov.movielib.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.skydoves.landscapist.glide.GlideImage
import com.vgrigorov.movielib.Constants.Companion.BASE_POSTER_IMAGE_URL
import com.vgrigorov.movielib.R
import com.vgrigorov.movielib.domain.models.Movie
import com.vgrigorov.movielib.presentation.Screen
import com.vgrigorov.movielib.presentation.Screen.MovieDetails.MOVIE_KEY
import com.vgrigorov.movielib.presentation.reusable_components.GlideImageFailedState

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavController
) {
    val scrollState = rememberScrollState()
    val topRatedMovies = viewModel.topRatedMovies.value.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMovies.value.collectAsLazyPagingItems()
    val nowPlayingMovies = viewModel.nowPlayingMovies.value.collectAsLazyPagingItems()
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.White)
            }
        }

        is UiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState) // Enable vertical scrollin
                    .background(Color.Black),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MovieCategorySection(
                    stringResource(id = R.string.now_playing_header_txt),
                    nowPlayingMovies,
                    navController
                )
                MovieCategorySection(
                    stringResource(id = R.string.top_rated_header_txt),
                    topRatedMovies,
                    navController
                )
                MovieCategorySection(
                    stringResource(id = R.string.most_popular_header_txt),
                    popularMovies,
                    navController
                )
            }
        }

        is UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = (uiState as UiState.Error).message,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun MovieCategorySection(
    title: String,
    movies: LazyPagingItems<Movie>,
    navController: NavController
) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow {
            items(movies.itemCount) { index ->
                val movie = movies[index]
                if (movie != null) {
                    MovieComponent(movie,
                        onClick = {
                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                MOVIE_KEY,
                                movie
                            )
                            navController.navigate(Screen.MovieDetails.route)
                        })
                }
            }

            // Show loading indicator when loading more items
            if (movies.loadState.append is LoadState.Loading) {
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

@Composable
fun MovieComponent(movie: Movie, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(), // Adds a shadow to the card
        shape = RoundedCornerShape(8.dp) // Rounded corners for the card
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Movie Poster Image
            GlideImage(
                imageModel = { BASE_POSTER_IMAGE_URL + movie.posterPath },
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(86.dp),
                        color = Color.Black,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                },
                failure = {
                    GlideImageFailedState(
                        size = 200.dp,
                        shape = RoundedCornerShape(8.dp),
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f) // Aspect ratio for rectangular image (2:3)
            )

            // Movie Title
            Text(
                text = movie.title,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                maxLines = 1, // Limit title to 1 lines
                overflow = TextOverflow.Ellipsis // Add ellipsis if text overflows
            )
        }
    }
}
