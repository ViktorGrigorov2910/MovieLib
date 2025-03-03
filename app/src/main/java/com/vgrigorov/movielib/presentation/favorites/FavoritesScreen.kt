package com.vgrigorov.movielib.presentation.favorites

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.landscapist.glide.GlideImage
import com.vgrigorov.movielib.Constants.Companion.BASE_POSTER_IMAGE_URL
import com.vgrigorov.movielib.domain.models.Movie

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "FavoriteBorder",
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )

        // Hint Text
        Text(
            text = "Swipe left to remove a movie from favorites",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // List of Favorite Movies
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            items(favoriteMovies.size) { id ->
                FavMovieRow(
                    movie = favoriteMovies[id],
                    onRemove = { viewModel.removeMovieFromFavorites(favoriteMovies[id].id) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavMovieRow(
    movie: Movie,
    onRemove: () -> Unit
) {
    val swipeState = rememberDismissState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                DismissValue.DismissedToStart -> false // Disable swipe from start to end
                DismissValue.DismissedToEnd -> {
                    onRemove() // Trigger removal on swipe from end to start
                    true
                }

                DismissValue.Default -> false
            }
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.5f } // slightly more than half way
    )

    SwipeToDismiss(
        state = swipeState,
        directions = setOf(DismissDirection.StartToEnd), // Only allow swipe from end to start
        background = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Red),
                contentAlignment = Alignment.CenterStart
            ) {

                Icon(
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .align(Alignment.CenterStart),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Movie Poster
                    GlideImage(
                        imageModel = { BASE_POSTER_IMAGE_URL + movie.posterPath },
                        loading = {
                            CircularProgressIndicator(
                                modifier = Modifier.size(64.dp),
                                color = Color.Black,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        },
                        failure = {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.LightGray),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = "Failed to load image",
                                    tint = Color.White,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
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
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    )
}