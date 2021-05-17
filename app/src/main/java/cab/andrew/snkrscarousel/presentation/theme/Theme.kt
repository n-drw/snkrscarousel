package cab.andrew.snkrscarousel.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import typography

private val LightColorPalette = lightColors(
    primary = Red200,
    primaryVariant = Red200,
    secondary = Red200,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun SnkrsCarouselTheme(
    content: @Composable() () -> Unit
) {

    MaterialTheme(
        colors = LightColorPalette,
        typography = typography,
        shapes = Shapes,
        content = content
    )
}