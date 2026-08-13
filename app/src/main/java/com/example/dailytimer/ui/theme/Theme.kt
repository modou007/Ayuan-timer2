package com.example.dailytimer.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = StartBtn,
    secondary = PauseBtn,
    tertiary = StopBtn,
    background = BgPrimary,
    surface = CardBg,
    onPrimary = TextWhite,
    onSecondary = TextWhite,
    onBackground = TextDark,
    onSurface = TextDark,
    outline = Divider,
    surfaceVariant = Accent
)

private val DarkColors = darkColorScheme(
    primary = StartBtn,
    secondary = PauseBtn,
    tertiary = StopBtn,
    background = BgPrimary,
    surface = CardBg,
    onPrimary = TextWhite,
    onSecondary = TextWhite,
    onBackground = TextDark,
    onSurface = TextDark,
    outline = Divider,
    surfaceVariant = Accent
)

@Composable
fun DailyTimerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
