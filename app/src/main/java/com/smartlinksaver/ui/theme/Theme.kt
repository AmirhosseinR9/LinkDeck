package com.smartlinksaver.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val LinkDeckColorScheme = darkColorScheme(
    primary            = Primary,
    onPrimary          = White,
    primaryContainer   = PrimaryDim,
    onPrimaryContainer = OnBackground,
    background         = Background,
    onBackground       = OnBackground,
    surface            = Surface,
    onSurface          = OnSurface,
    surfaceVariant     = SurfaceVariant,
    onSurfaceVariant   = OnSurfaceVariant,
    error              = ErrorColor,
    onError            = White,
    outline            = Outline
)

@Composable
fun LinkDeckTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LinkDeckColorScheme,
        typography  = LinkDeckTypography,
        content     = content
    )
}
