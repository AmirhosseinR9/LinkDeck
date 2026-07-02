package com.smartlinksaver.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun ShimmerBox(modifier: Modifier = Modifier) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val x by transition.animateFloat(
        initialValue  = -400f,
        targetValue   = 1200f,
        animationSpec = infiniteRepeatable(
            animation  = tween(1100, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_x"
    )
    Box(
        modifier = modifier.background(
            Brush.linearGradient(
                colors = listOf(Color(0xFF1C1E2D), Color(0xFF2A2D3E), Color(0xFF1C1E2D)),
                start  = Offset(x, x),
                end    = Offset(x + 400f, x + 400f)
            )
        )
    )
}
