package cab.andrew.snkrscarousel.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import cab.andrew.snkrscarousel.R
import androidx.compose.ui.unit.dp
import cab.andrew.snkrscarousel.presentation.theme.Red200

@Composable
fun SwooshContainer() {
    val swooshLogo = painterResource(id = R.drawable.swoosh)

    Box(Modifier
        .fillMaxWidth()
        .fillMaxHeight(.6f)
        .background(Red200)) {
        SnkrsMarquee(modifier = Modifier
            .fillMaxWidth()
            )
        Image(
            swooshLogo,
            contentDescription = "swoosh",
            modifier = Modifier
                .padding(top = 100.dp)
                .height(388.dp)
                .width(635.dp),
        )
    }
}