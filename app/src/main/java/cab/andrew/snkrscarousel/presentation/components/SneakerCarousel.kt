package cab.andrew.snkrscarousel.presentation.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.ui.tooling.preview.Preview
import cab.andrew.snkrscarousel.R
import cab.andrew.snkrscarousel.domain.models.Sneaker
import com.google.accompanist.pager.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlin.math.absoluteValue
import cab.andrew.snkrscarousel.presentation.components.pager.HorizontalPager
import cab.andrew.snkrscarousel.presentation.components.pager.calculateCurrentOffsetForPage
import cab.andrew.snkrscarousel.presentation.components.pager.pageChanges
import cab.andrew.snkrscarousel.presentation.ui.sneaker_list.SneakerListViewModel
import com.google.accompanist.coil.rememberCoilPainter
import com.google.accompanist.imageloading.ImageLoadState
import typography
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@Composable
fun SneakerCarousel(){
    val pagerState = cab.andrew.snkrscarousel.presentation.components.pager.rememberPagerState(10, 0, 0.0f)
    val dummyImg = painterResource(id = R.drawable.sneaker)
    val sneakerListViewModel: SneakerListViewModel = viewModel()
    val sneakers = sneakerListViewModel.sneakers.value
    if(sneakers.isEmpty()) return
    val lastSneaker = sneakers[pagerState.lastPageIndex]
    val painter = rememberCoilPainter(request = sneakers[pagerState.lastPageIndex].image)
    val invsbl = if (pagerState.isScrollInProgress || pagerState.currentPage > 0) { 0f } else { .5f }

    BoxWithConstraints(modifier = Modifier
        .offset((-180).dp, 430.dp)
        .height(400.dp)
        .width(300.dp)
        .alpha(invsbl)
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.5f)
                )
            }
        }
        Column(
            modifier = Modifier
                .background(Color.Transparent.copy(0f)),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = lastSneaker.name,
                color = Color.Black,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Black,
                fontSize = 34.sp,
                modifier = Modifier
                    .padding(top = 160.dp, end = 50.dp),
                textAlign = TextAlign.Left
            )
            Text(
                text = "$" + lastSneaker.retailPrice.toString(),
                color = Color.Black,
                modifier = Modifier
                    .padding(top = 15.dp, start = 8.dp),
                style = typography.subtitle1,
                textAlign = TextAlign.Left
            )
            Text(
                text = lastSneaker.colorway,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 8.dp, top = 15.dp),
                style = typography.subtitle2,
                textAlign = TextAlign.Left
            )
        }
    }

    ConstraintLayout {
        val horizontalPagerRef = createRef()

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .padding(top = 340.dp)
                .constrainAs(horizontalPagerRef) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .fillMaxSize()
        ) { page ->
            var sneaker = sneakers[page]
            Card(
                Modifier
                    .graphicsLayer {
                        val pageOffSet = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffSet.coerceIn(0f, 1f)
                        )
                    }
                    .height(500.dp)
                    .width(250.dp),
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            ) {
                SneakerCarouselItem(Modifier.fillMaxWidth(), sneaker)
            }
        }
    }
}

@Preview
@Composable
@InternalCoroutinesApi
fun PreviewCarousel() {
    SneakerCarousel()
}