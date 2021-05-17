package cab.andrew.snkrscarousel.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import cab.andrew.snkrscarousel.R
import kotlinx.coroutines.delay


@Composable
fun SnkrsMarquee(modifier: Modifier = Modifier) {
    val logo = painterResource(id = R.drawable.snkrs)

    BoxWithConstraints(
        modifier = Modifier
            .padding(top = 40.dp, bottom = 8.dp)
    ) {

        val logoMarquee = rememberInfiniteTransition().animateValue(
            initialValue = 0.dp,
            targetValue = maxWidth,
            typeConverter = Dp.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 6000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        val logoMarquee2 = rememberInfiniteTransition().animateValue(
            initialValue = -maxWidth + logoMarquee.value,
            targetValue = maxWidth,
            typeConverter = Dp.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 12000,
                    easing = LinearEasing
                ),
            repeatMode = RepeatMode.Restart
            )
        )

        val logoMarquee3 = rememberInfiniteTransition().animateValue(
            initialValue = -maxWidth * 2,
            targetValue = maxWidth,
            typeConverter = Dp.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    delayMillis = 8000,
                    durationMillis = 12000,
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                logo,
                contentDescription = "logo",
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .padding(start = 6.dp)
                    .absoluteOffset(x = logoMarquee.value)
            )
            Image(
                logo,
                contentDescription = "logo",
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .padding(start = 6.dp)
                    .absoluteOffset(x = logoMarquee.value)
            )
            Image(
                logo,
                contentDescription = "logo",
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .padding(start = 6.dp)
                    .absoluteOffset(x = logoMarquee.value)
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                logo,
                contentDescription = "logo",
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .padding(start = 6.dp)
                    .absoluteOffset(x = logoMarquee2.value)
            )
            Image(
                logo,
                contentDescription = "logo",
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .padding(start = 6.dp)
                    .absoluteOffset(x = logoMarquee2.value)
            )
            Image(
                logo,
                contentDescription = "logo",
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .padding(start = 6.dp)
                    .absoluteOffset(x = logoMarquee2.value)
            )
        }

    }
}

