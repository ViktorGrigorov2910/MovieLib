package com.vgrigorov.movielib.presentation.movie_details

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.cloudy.Cloudy
import com.skydoves.landscapist.glide.GlideImage
import com.vgrigorov.movielib.Constants.Companion.BASE_POSTER_IMAGE_URL
import com.vgrigorov.movielib.R
import com.vgrigorov.movielib.domain.models.Movie
import com.vgrigorov.movielib.presentation.reusable_components.GlideImageFailedState

@Composable
fun MovieDetailsScreen(
    movie: Movie?,
    onBackClicked: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel()
) {
    // Check the movie data and update the UI state
    viewModel.checkMovieData(movie = movie)
    val uiState by viewModel.uiState.collectAsState()

    // Blurred background (common for all states)
    Cloudy(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.6f), // Adjust opacity for better readability
        radius = 10 // Adjust blur radius
    ) {}

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            // Handle UI states
            when (uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .size(350.dp)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.Black)
                    }
                }

                is UiState.Success -> {
                    val successState = uiState as UiState.Success
                    MovieDetailsContent(
                        movie = successState.movie,
                        thrailerUrl = successState.thrailerUrl,
                        isFavourite = successState.isMovieFavorite,
                        onBackClicked = onBackClicked,
                        onSaveClicked = { viewModel.addMovieToFavourites(movie!!) } // we know for sure that there is a movie since we are in the Success State
                    )
                }

                is UiState.Error -> {
                    val errorState = uiState as UiState.Error
                    Box(
                        modifier = Modifier
                            .size(350.dp)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Icon(
                                modifier = Modifier.size(128.dp),
                                imageVector = Icons.Default.Warning,
                                contentDescription = "Back",
                                tint = Color.Black // Black icon
                            )

                            Text(
                                text = errorState.message,
                                color = Color.Black,
                                fontSize = 18.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = onBackClicked) {
                                Text("Return to Home")
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun MovieDetailsContent(
    movie: Movie,
    thrailerUrl: String,
    isFavourite: Boolean,
    onSaveClicked: (movie: Movie) -> Unit,
    onBackClicked: () -> Unit
) {

    val context = LocalContext.current

    AnimatedVisibility(
        visible = true,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Movie Poster (Circular)
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
                        size = 86.dp,
                        shape = CircleShape
                    )
                },
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape) // Make the image circular
                    .background(Color.LightGray) // Background color for the circle
            )

            // Movie Title
            Text(
                text = movie.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            // Release Date and Rating (Fancy Rating)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Release: ${movie.releaseDate}",
                    fontSize = 14.sp,
                    color = Color.Black
                )

                // Fancy Rating (Colored Circle with Rating)
                AnimatedRatingCircle(rating = movie.rating ?: 3.0)

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black // Change to any color
                    ),
                    onClick = {
                        // Create an Intent to open the URL
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(thrailerUrl))
                        context.startActivity(intent)

                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.yt_icon), // Load vector drawable
                        contentDescription = "YT Icon",
                        modifier = Modifier.size(24.dp), // Adjust size
                        tint = Color.White // Change color if needed
                    )
                }

            }

            // Movie Overview (Scrollable Text)
            Text(
                text = movie.overview ?: "N/A",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )

            ButtonRow(
                isFavourite = isFavourite,
                onSaveClicked = {
                    onSaveClicked(movie)
                    // Close Details when saved to favs
                    Toast.makeText(context, "Movie was added to Favourites!", Toast.LENGTH_LONG)
                        .show()
                    onBackClicked()
                },
                onCloseClicked = {
                    onBackClicked()
                }
            )
        }
    }
}

@Composable
fun ButtonRow(
    isFavourite: Boolean,
    onSaveClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        if (!isFavourite) {
            // Save Button
            IconButton(
                onClick = onSaveClicked,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = Color.Black // Black icon
                )
            }

            // Spacer to evenly distribute space
            Spacer(modifier = Modifier.width(16.dp))
        }

        // Close Button
        IconButton(
            onClick = onCloseClicked,
            modifier = Modifier.weight(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Close",
                tint = Color.Black // Black icon
            )
        }
    }
}



@Composable
fun AnimatedRatingCircle(rating: Double) {
    val animatedSize by animateDpAsState(
        targetValue = 40.dp,
        animationSpec = tween(durationMillis = 1200, easing = FastOutSlowInEasing), label = ""
    )

    Box(
        modifier = Modifier
            .size(animatedSize)
            .background(
                color = Color.Black,
                shape = CircleShape
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = rating.toString(),
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}