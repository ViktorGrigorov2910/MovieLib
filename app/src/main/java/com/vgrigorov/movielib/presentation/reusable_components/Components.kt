package com.vgrigorov.movielib.presentation.reusable_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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