package cab.andrew.snkrscarousel.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.HiltViewModelFactory
import androidx.ui.tooling.preview.Preview
import cab.andrew.snkrscarousel.presentation.components.SneakerCarousel
import cab.andrew.snkrscarousel.presentation.components.SwooshContainer
import cab.andrew.snkrscarousel.presentation.theme.SnkrsCarouselTheme
import cab.andrew.snkrscarousel.presentation.ui.sneaker_list.SneakerListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val sneakerListViewModel by viewModels<SneakerListViewModel>()

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sneakerListViewModel.getSneakers()
        setContent {
            SnkrsCarouselTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SwooshContainer()
                    SneakerCarousel()
                }
            }
        }
    }
}