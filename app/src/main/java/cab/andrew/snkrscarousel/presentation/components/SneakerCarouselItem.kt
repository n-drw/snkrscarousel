package cab.andrew.snkrscarousel.presentation.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.ui.tooling.preview.Preview
import cab.andrew.snkrscarousel.rememberRandomSampleImageUrl
import com.google.accompanist.coil.rememberCoilPainter
import typography
import cab.andrew.snkrscarousel.R
import cab.andrew.snkrscarousel.domain.models.Sneaker
import cab.andrew.snkrscarousel.presentation.ui.sneaker_list.SneakerListViewModel
import com.google.accompanist.imageloading.ImageLoadState
import com.google.accompanist.imageloading.MaterialLoadingImage

@Composable
fun SneakerCarouselItem(modifier: Modifier = Modifier, sneaker: Sneaker) {
    val dummyImg = painterResource(id = R.drawable.sneaker)
    val sneakerImg =  sneaker.image.original
    val sneakerBrand = sneaker.brand
    val sneakerRelease = sneaker.releaseYear
    val sneakerStory = sneaker.story
    val sneakerColor = sneaker.colorway
    val sneakerPrice = sneaker.retailPrice

    val painter = rememberCoilPainter(
        fadeIn = true,
        request = sneakerImg ?: null
    )

    Column {
        BoxWithConstraints(modifier) {

            Image(
                painter = painter,
                contentDescription = null,
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.5f)
            )
            when (painter.loadState) {
                is ImageLoadState.Loading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                is ImageLoadState.Error -> {
                    Image(
                        painter = dummyImg,
                        contentDescription = null,
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.5f)
                    )
                }
            }
        }
        Column(
            modifier = Modifier.padding(0.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = sneaker.name,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 8.dp),
                style = typography.caption,
                textAlign = TextAlign.Left
            )
            Text(
                text = "$" + sneaker.retailPrice.toString(),
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 15.dp, start = 8.dp),
                style = typography.subtitle1,
                textAlign = TextAlign.Left
            )
            Text(
                text = sneaker.colorway,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 8.dp, top = 15.dp),
                style = typography.subtitle2,
                textAlign = TextAlign.Left
            )
        }

    }
}

@Preview
@Composable
fun SneakerCarouselItemPreview(sneaker: Sneaker) {
    SneakerCarouselItem(sneaker = sneaker)
}