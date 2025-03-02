package com.vgrigorov.movielib.presentation.movie_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.skydoves.cloudy.Cloudy
import com.skydoves.landscapist.glide.GlideImage
import com.vgrigorov.movielib.domain.models.Movie


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
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {
                    val successState = uiState as UiState.Success
                    MovieDetailsContent(
                        movie = successState.movie,
                        onBackClicked = onBackClicked
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



//TODO: Might be good to add the movies' genres as ENUM and add them above the overview
// Since it is ENUM we just do fori on the list and show them dynamically
@Composable
fun MovieDetailsContent(
    movie: Movie,
    onBackClicked: () -> Unit
) {
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
                imageModel = { movie.posterPath },
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(86.dp),
                        color = MaterialTheme.colorScheme.secondary,
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

            // Back Button
            IconButton(
                onClick = onBackClicked,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Back",
                    tint = Color.Black // Black icon
                )
            }
        }
    }
}

@Composable
fun GlideImageFailedState(
    modifier: Modifier = Modifier,
    size: Dp = 150.dp, // Default size
    shape: Shape = CircleShape, // Default shape
    iconSize: Dp = 48.dp, // Size of the fallback icon
    backgroundColor: Color = Color.LightGray, // Background color for the fallback
    iconTint: Color = Color.White, // Tint color for the fallback icon
    textColor: Color = Color.White, // Text color for the fallback message
    text: String = "Image not available" // Fallback message
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(shape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Failed to load image",
                tint = iconTint,
                modifier = Modifier.size(iconSize)
            )
            Text(
                text = text,
                color = textColor,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
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