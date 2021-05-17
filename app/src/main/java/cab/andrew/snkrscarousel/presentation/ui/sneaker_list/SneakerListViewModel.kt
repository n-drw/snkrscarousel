package cab.andrew.snkrscarousel.presentation.ui.sneaker_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import cab.andrew.snkrscarousel.BuildConfig
import cab.andrew.snkrscarousel.domain.interactors.GetSneaker
import cab.andrew.snkrscarousel.domain.models.Sneaker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

const val LogTag = "SneakersListViewModel: "


@HiltViewModel
class SneakerListViewModel
@Inject
constructor(
    private val getSneakers: GetSneaker
) : ViewModel() {

    val sneakers: MutableState<List<Sneaker>> = mutableStateOf(ArrayList())
    private val loading = mutableStateOf(false)

    fun getSneakers() {
        getSneakers.execute()
            .onEach { dataState ->
                loading.value = dataState.loading

                dataState.data?.let { list ->
                    sneakers.value = list
                }

                dataState.error?.let { error ->
                    Log.d(LogTag, "getSneakers: $error")
                }
            }.launchIn(viewModelScope)
    }
}