package com.ruokkis


import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val AppColors = lightColorScheme(
    primary = Color(0xFF15370D),
    onPrimary = Color(0xFFfefff2),

    primaryContainer = Color(0xFF728E6D),
    onPrimaryContainer = Color(0xFF061902),

    secondaryContainer = Color(0xFF92b28d),
    onSecondaryContainer = Color(0xFF061902),

    secondary = Color(0xFF546851),
    onSecondary = Color(0xFF061902),

    background = Color(0xFFfefff2),
    surface = Color(0xFF728E6D),
    onSurface = Color(0xFF061902)
)

val AppColorsDark = darkColorScheme(
    primary = Color(0xFF333333),
    onPrimary = Color(0xFFd1f9ca),

    primaryContainer = Color(0xFF2d2d2d),
    onPrimaryContainer = Color(0xFFd1f9ca),

    secondary = Color(0xFF6A994E),

    background = Color(0xFF222222),
    surface = Color(0xFF2d2d2d),
    onSurface = Color(0xFFd1f9ca)
)