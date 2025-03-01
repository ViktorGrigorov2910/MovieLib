package com.vgrigorov.movielib.presentation.movie_details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.skydoves.cloudy.Cloudy
import com.skydoves.landscapist.glide.GlideImage
import com.vgrigorov.movielib.domain.models.Movie


@Composable
fun MovieDetailsScreenWithAnimation(
    movie: Movie,
    isVisible: Boolean,
    navController: NavController
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        MovieDetailsScreen(movie = movie) {
            navController.popBackStack()
        }
    }
}


//TODO: Final touches for UI -> Aligemnts, text, add icons for rating or something?, fix image (not full/cropped out), add default poster if main one fails
@Composable
fun MovieDetailsScreen(movie: Movie, onBackClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Blurred Background Image
        Cloudy(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.6f), // Adjust opacity for better readability
            radius = 10 // Adjust blur radius
        ) {}

        // Movie Details Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            // Back Button
            IconButton(
                onClick =  {onBackClicked()},
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Back",
                    tint = Color.Black // Black icon
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Movie Poster
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
                    modifier = Modifier
                        .size(150.dp)
                        .align(Alignment.CenterHorizontally)
                )

                // Movie Title
                Text(
                    text = movie.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Release Date and Rating
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Release: ${movie.releaseDate}",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    Text(
                        text = "Rating: ${movie.rating}",
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                }

                // Movie Overview
                Text(
                    text = movie.overview ?: "N/A",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}