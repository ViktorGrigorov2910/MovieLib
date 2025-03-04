package com.vgrigorov.movielib.data.resources

data class VideoListResource(
    val results: List<VideoResource>
)

data class VideoResource(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String
)