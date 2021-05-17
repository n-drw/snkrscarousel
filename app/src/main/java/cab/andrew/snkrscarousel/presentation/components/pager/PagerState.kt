package cab.andrew.snkrscarousel.presentation.components.pager


import android.util.Log
import androidx.annotation.FloatRange
import androidx.annotation.IntRange
import androidx.compose.animation.core.*
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.roundToInt

const val TAG = "PagerState: "

@Composable
fun rememberPagerState(
    @IntRange(from = 0) pageCount: Int,
    @IntRange(from = 0) initialPage: Int = 0,
    @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
): PagerState = rememberSaveable(saver = PagerState.Saver) {
    PagerState(
        pageCount = pageCount,
        currentPage = initialPage,
        currentPageOffset = initialPageOffset,
    )
}.apply {
    this.pageCount = pageCount
}

@Stable
class PagerState(
    @IntRange(from = 0) pageCount: Int,
    @IntRange(from = 0) currentPage: Int = 0,
    @FloatRange(from = 0.0, to = 1.0) currentPageOffset: Float = 0f,
) : ScrollableState {
    private var _pageCount by mutableStateOf(pageCount)
    private var _currentPage by mutableStateOf(currentPage)
    private val _currentPageOffset = mutableStateOf(currentPageOffset)
    internal var pageSize by mutableStateOf(0)

    private val absolutePosition: Float
        get() = currentPage + currentPageOffset

    private val scrollableState = ScrollableState { deltaPixels ->
        val size = pageSize.coerceAtLeast(1)
        -scrollByOffset(-deltaPixels / size) * size
    }

    init {
        require(pageCount >= 0) { "pageCount must be >= 0" }
        requireCurrentPage(currentPage, "currentPage")
        requireCurrentPageOffset(currentPageOffset, "currentPageOffset")
    }


    @get:IntRange(from = 0)
    var pageCount: Int
        get() = _pageCount
        set(@IntRange(from = 0) value) {
            require(value >= 0) { "pageCount must be >= 0" }
            _pageCount = value
            currentPage = currentPage.coerceIn(0, lastPageIndex)
        }

    internal val lastPageIndex: Int
        get() = (pageCount - 1).coerceAtLeast(0)

    @get:IntRange(from = 0)
    var currentPage: Int
        get() = _currentPage
        private set(value) {
            _currentPage = value.coerceIn(0, lastPageIndex)
        }

    @get:IntRange(from = 0)
    var minPage: Int
        get() = _currentPage
        private set(value) {
            _currentPage = value.coerceIn(minPage, lastPageIndex)
        }

    /**
     * The current offset from the start of [currentPage], as a fraction of the page width.
     *
     * To update the scroll position, use [scrollToPage] or [animateScrollToPage].
     */
    @get:FloatRange(from = 0.0, to = 1.0)
    var currentPageOffset: Float
        get() = _currentPageOffset.value
        private set(value) {
            _currentPageOffset.value = value.coerceIn(
                minimumValue = 0f,
                maximumValue = if (currentPage == lastPageIndex) 0f else 1f,
            )
        }


    suspend fun animateScrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = 0.0, to = 1.0) pageOffset: Float = 0f,
        initialVelocity: Float = 0f,
    ) {
        requireCurrentPage(page, "page")
        requireCurrentPageOffset(pageOffset, "pageOffset")

        if (page == currentPage) return

        scroll {
            animateToPage(
                page = page.coerceIn(0, lastPageIndex),
                pageOffset = pageOffset.coerceIn(0f, 1f),
                initialVelocity = initialVelocity,
            )
        }
    }

    suspend fun scrollToPage(
        @IntRange(from = 0) page: Int,
        @FloatRange(from = 0.0, to = 1.0) pageOffset: Float = 0f,
    ) {
        requireCurrentPage(page, "page")
        requireCurrentPageOffset(pageOffset, "pageOffset")

        scroll {
            currentPage = page
            currentPageOffset = pageOffset
        }
    }

    private fun snapToNearestPage() {
        if (currentPage == lastPageIndex) {
            currentPage = 0
            currentPageOffset = 0f
        } else if(currentPage == 0) {
            currentPage = lastPageIndex
            currentPageOffset = 0f
        }
        currentPage -= currentPageOffset.roundToInt()
    }

    private suspend fun animateToPage(
        page: Int,
        pageOffset: Float = 0f,
        animationSpec: AnimationSpec<Float> = spring(),
        initialVelocity: Float = 0f,
    ) {
        animate(
            initialValue = absolutePosition,
            targetValue = page + pageOffset,
            initialVelocity = initialVelocity,
            animationSpec = animationSpec
        ) { value, _ ->
            updateFromScrollPosition(value)
        }
        snapToNearestPage()
    }

    private fun determineSpringBackOffset(
        velocity: Float,
        offset: Float = currentPageOffset,
    ): Float = when {
        velocity >= pageSize -> 1f
        velocity <= -pageSize -> 0f
        offset < 0.5f -> 0f
        else -> 1f
    }

    private fun updateFromScrollPosition(position: Float) {
        currentPage = floor(position).toInt()
        currentPageOffset = position - currentPage
    }

    private fun scrollByOffset(deltaOffset: Float): Float {
        val current = absolutePosition
        val target = (current + deltaOffset).coerceIn(0f, lastPageIndex.toFloat())
        updateFromScrollPosition(target)
        return deltaOffset - (target - current)
    }

    suspend fun fling(initialVelocity: Float,
        decayAnimationSpec: DecayAnimationSpec<Float> = exponentialDecay(),
        snapAnimationSpec: AnimationSpec<Float> = spring(),
        scrollBy: (Float) -> Float) : Float
    {
        val targetOffset = decayAnimationSpec.calculateTargetValue(
            initialValue = currentPageOffset * pageSize,
            initialVelocity = initialVelocity
        ) / pageSize

        val targetPosition = currentPage + determineSpringBackOffset(
            velocity = initialVelocity,
            offset = targetOffset
        )

        val targetPage = when {
            targetOffset > 0 -> (currentPage + 1).coerceAtMost(lastPageIndex)
            else -> currentPage
        }

        var currentPager = true

        var lastVelocity: Float = initialVelocity

        animate(
            initialValue = absolutePosition * pageSize,
            targetValue = targetPosition.toFloat() * pageSize,
            initialVelocity = initialVelocity,
            animationSpec = snapAnimationSpec,
        ) { value, velocity ->
            if((initialVelocity < 0 && absolutePosition <= targetPage) ||
                (initialVelocity > 0 && absolutePosition >= targetPage))
            {
                currentPage = targetPage
                currentPageOffset = 0f
            }

            scrollBy(value - (absolutePosition * pageSize))
            lastVelocity = velocity

        }
        snapToNearestPage()
        return lastVelocity
    }


    override val isScrollInProgress: Boolean
        get() = scrollableState.isScrollInProgress

    override fun dispatchRawDelta(delta: Float): Float {
        return scrollableState.dispatchRawDelta(delta)
    }

    override suspend fun scroll(
        scrollPriority: MutatePriority,
        block: suspend ScrollScope.() -> Unit
    ) {
        scrollableState.scroll(scrollPriority, block)
    }

    override fun toString(): String = "PagerState(" +
            "pageCount=$pageCount, " +
            "currentPage=$currentPage, " +
            "currentPageOffset=$currentPageOffset" +
            ")"

    private fun requireCurrentPage(value: Int, name: String) {
        if (pageCount == 0) {
            require(value == 0) { "$name must be 0 when pageCount is 0" }
        } else {
            require(value in 0 until pageCount) {
                "$name must be >= 0 and < pageCount"
            }
        }
    }

    private fun requireCurrentPageOffset(value: Float, name: String) {
        if (pageCount == 0) {
            require(value == 0f) { "$name must be 0f when pageCount is 0" }
        } else {
            require(value in 0f..1f) { "$name must be >= 0 and <= 1" }
        }
    }

    companion object {
        val Saver: Saver<PagerState, *> = listSaver(
            save = { listOf<Any>(it.pageCount, it.currentPage, it.currentPageOffset) },
            restore = {
                PagerState(
                    pageCount = it[0] as Int,
                    currentPage = it[1] as Int,
                    currentPageOffset = it[2] as Float
                )
            }
        )
    }
}



inline val PagerState.pageChanges: Flow<Int>
    get() = snapshotFlow { isScrollInProgress }
        .filterNot { it }
        .map { currentPage }
        .distinctUntilChanged()