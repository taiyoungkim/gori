package com.team_gori.gori.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

private val LocalSemanticColors = staticCompositionLocalOf { lightSemanticColors }

val MaterialTheme.semanticColors: SemanticColors
    @Composable
    @ReadOnlyComposable
    get() = LocalSemanticColors.current

@Composable
fun CustomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val semanticColors = if (darkTheme) darkSemanticColors else lightSemanticColors

    val dummyColorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()

    CompositionLocalProvider(LocalSemanticColors provides semanticColors) {
        MaterialTheme(
            colorScheme = dummyColorScheme,
            typography = AppTypography(),
            shapes = AppShapes,
            content = content
        )
    }
}