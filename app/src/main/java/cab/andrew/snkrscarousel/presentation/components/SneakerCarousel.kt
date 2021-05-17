package cab.andrew.snkrscarousel.presentation.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import kotlinx.coroutines.launch

@InternalCoroutinesApi
@Composable
fun SneakerCarousel(){
    val pagerState = cab.andrew.snkrscarousel.presentation.components.pager.rememberPagerState(65, 38, 0.0f)

    val sneakerListViewModel: SneakerListViewModel = viewModel()
    val sneakers = sneakerListViewModel.sneakers.value

    if(sneakers.isEmpty()) return

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 380.dp)
    ) { page ->
        var currPg = currentPage
        var sneaker = sneakers[pagerState.currentPage]

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
                .height(900.dp)
                .width(300.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            SneakerCarouselItem(Modifier.fillMaxWidth(), sneaker)
        }
    }
}