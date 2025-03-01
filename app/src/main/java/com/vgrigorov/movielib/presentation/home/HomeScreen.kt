package com.vgrigorov.movielib.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.vgrigorov.movielib.domain.models.Movie

//TODO: Add onclick Logic that open movie details (yet to be implemented)

@Composable
fun HomeScreen(
    topRatedMovies: List<Movie>,
    popularMovies: List<Movie>,
    nowPlayingMovies: List<Movie>
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 12.dp)
            .verticalScroll(scrollState), // Enable vertical scrollin
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MovieCategorySection("Top Rated", topRatedMovies)
        MovieCategorySection("Popular", popularMovies)
        MovieCategorySection("Now Playing", nowPlayingMovies)
    }
}

@Composable
fun MovieCategorySection(title: String, movies: List<Movie>) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        LazyRow {
            items(movies) { movie ->
                MovieComponent(movie)
            }
        }
    }
}

@Composable
fun MovieComponent(movie: Movie) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .padding(8.dp),
        elevation = CardDefaults.cardElevation() , // Adds a shadow to the card
        shape = RoundedCornerShape(8.dp) // Rounded corners for the card
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Movie Poster Image
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



@Composable
@Preview
fun HomeScreenPreview() {
    Text(text = "Home Screen - Movie Categories will be here")
}