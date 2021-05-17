//package cab.andrew.snkrscarousel.presentation.components.pager
//
//import androidx.compose.runtime.Composable
//
//
//@Composable
//fun <T : Any> SneakerRow(
//    list: List<T>,
//    modifier: Modifier = Modifier,
//    scrollDx: Float = SCROLL_DX,
//    delayBetweenScrollMs: Long = DELAY_BETWEEN_SCROLL_MS,
//    divider: @Composable () -> Unit = { Spacer(modifier = Modifier.width(Dimens.grid_1)) },
//    itemContent: @Composable (item: T) -> Unit,
//) {
//    var itemsListState by remember { mutableStateOf(list) }
//    val lazyListState = rememberLazyListState()
//
//    LazyRow(
//        state = lazyListState,
//        modifier = modifier,
//    ) {
//        items(itemsListState) {
//            itemContent(item = it)
//            divider()
//
//            if (it == itemsListState.last()) {
//                val currentList = itemsListState
//
//                val secondPart = currentList.subList(0, lazyListState.firstVisibleItemIndex)
//                val firstPart = currentList.subList(lazyListState.firstVisibleItemIndex, currentList.size)
//
//                rememberCoroutineScope().launch {
//                    lazyListState.scrollToItem(0, maxOf(0, lazyListState.firstVisibleItemScrollOffset - scrollDx.toInt()))
//                }
//
//                itemsListState = firstPart + secondPart
//            }
//        }
//
//    }
//    LaunchedEffect(Unit) {
//        autoScroll(lazyListState, scrollDx, delayBetweenScrollMs)
//    }
//}
//
//private tailrec suspend fun autoScroll(
//    lazyListState: LazyListState,
//    scrollDx: Float,
//    delayBetweenScrollMs: Long,
//) {
//    lazyListState.scroll(MutatePriority.PreventUserInput) {
//        scrollBy(scrollDx)
//    }
//    delay(delayBetweenScrollMs)
//
//    autoScroll(lazyListState, scrollDx, delayBetweenScrollMs)
//}
//
//private const val DELAY_BETWEEN_SCROLL_MS = 8L
//private const val SCROLL_DX = 1f