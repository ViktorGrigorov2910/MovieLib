package com.vgrigorov.movielib.presentation.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.glide.GlideImage
import com.vgrigorov.movielib.domain.models.Movie

//TODO: Fix UI - paddings,images,background coloring, etc

@Composable
fun HomeScreen(
    topRatedMovies: List<Movie>,
    popularMovies: List<Movie>,
    nowPlayingMovies: List<Movie>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Black)
    ) {
        MovieCategorySection("Top Rated", topRatedMovies)
        MovieCategorySection("Popular", popularMovies)
        MovieCategorySection("Now Playing", nowPlayingMovies)
    }
}

@Composable
fun MovieCategorySection(title: String, movies: List<Movie>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 4.dp)
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
    Column(
        modifier = Modifier
            .width(120.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GlideImage(
            imageModel = { movie.posterPath },
            loading =  {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(86.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            },
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onSecondary)
        )
        Text(
            text = movie.title,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}



@Composable
@Preview
fun HomeScreenPreview() {
    Text(text = "Home Screen - Movie Categories will be here")
}