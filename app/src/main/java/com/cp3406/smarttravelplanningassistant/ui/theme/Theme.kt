package com.cp3406.smarttravelplanningassistant.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = LightBluePrimary,
    onPrimary = Color.White,
    primaryContainer = LightBluePrimaryContainer,
    onPrimaryContainer = Color.Black,
    secondary = LightBlueSecondary,
    onSecondary = Color.White,
    background = LightBlueBackground,
    onBackground = Color.Black,
    surface = LightBlueBackground,
    onSurface = Color.Black
)

@Composable
fun TravelAssistantTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
