package cab.andrew.snkrscarousel.presentation.components.pager

import android.util.Log
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.ScrollAxisRange
import androidx.compose.ui.semantics.horizontalScrollAxisRange
import androidx.compose.ui.semantics.scrollBy
import androidx.compose.ui.semantics.selectableGroup
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import androidx.annotation.IntRange
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.lazy.LazyRow

private const val SnapSpringStiffness = 3050f

@Immutable
private data class PageData(val page: Int) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any = this@PageData
}

private val Measurable.page: Int
    get() = (parentData as? PageData)?.page ?: error("No PageData for measurable $this")



object PagerDefaults {
    @Composable
    fun defaultPagerFlingConfig(
        state: PagerState,
        decayAnimationSpec: DecayAnimationSpec<Float> = rememberSplineBasedDecay(),
        snapAnimationSpec: AnimationSpec<Float> = spring(stiffness = SnapSpringStiffness),
    ): FlingBehavior = remember(state, decayAnimationSpec, snapAnimationSpec) {
        object : FlingBehavior {
            override suspend fun ScrollScope.performFling(
                initialVelocity: Float
            ): Float = state.fling(
                initialVelocity = -initialVelocity,
                decayAnimationSpec = decayAnimationSpec,
                snapAnimationSpec = snapAnimationSpec,
                scrollBy = { deltaPixels -> -scrollBy(-deltaPixels) },
            )


        }
    }
}


@Composable
fun HorizontalPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    itemSpacing: Dp = 0.dp,
    @IntRange(from = 1) offscreenLimit: Int = 1,
    dragEnabled: Boolean = true,
    flingBehavior: FlingBehavior = PagerDefaults.defaultPagerFlingConfig(state),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable PagerScope.(page: Int) -> Unit,
) {
    Pager(
        state = state,
        modifier = modifier,
        isVertical = false,
        reverseLayout = reverseLayout,
        itemSpacing = itemSpacing,
        verticalAlignment = verticalAlignment,
        horizontalAlignment = horizontalAlignment,
        offscreenLimit = offscreenLimit,
        dragEnabled = dragEnabled,
        flingBehavior = flingBehavior,
        content = content
    )
}

@Composable
fun VerticalPager(
    state: PagerState,
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    itemSpacing: Dp = 0.dp,
    @IntRange(from = 1) offscreenLimit: Int = 1,
    dragEnabled: Boolean = true,
    flingBehavior: FlingBehavior = PagerDefaults.defaultPagerFlingConfig(state),
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    content: @Composable PagerScope.(page: Int) -> Unit,
) {
    Pager(
        state = state,
        modifier = modifier,
        isVertical = true,
        reverseLayout = reverseLayout,
        itemSpacing = itemSpacing,
        verticalAlignment = verticalAlignment,
        horizontalAlignment = horizontalAlignment,
        offscreenLimit = offscreenLimit,
        dragEnabled = dragEnabled,
        flingBehavior = flingBehavior,
        content = content
    )
}


@Composable
internal fun Pager(
    state: PagerState,
    modifier: Modifier,
    reverseLayout: Boolean,
    itemSpacing: Dp,
    isVertical: Boolean,
    verticalAlignment: Alignment.Vertical,
    horizontalAlignment: Alignment.Horizontal,
    @IntRange(from = 1) offscreenLimit: Int,
    dragEnabled: Boolean,
    flingBehavior: FlingBehavior,
    content: @Composable PagerScope.(page: Int) -> Unit,
) {
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl
    val reverseDirection = if (isRtl) !reverseLayout else reverseLayout

    val coroutineScope = rememberCoroutineScope()
    val semanticsAxisRange = remember(state, reverseDirection) {
        ScrollAxisRange(
            value = { state.currentPage + state.currentPageOffset },
            maxValue = { state.lastPageIndex.toFloat() },
        )
    }
    val semantics = Modifier.semantics {
        horizontalScrollAxisRange = semanticsAxisRange
        // Hook up scroll actions to our state
        scrollBy { x, y ->
            coroutineScope.launch {
                if (isVertical) {
                    state.scrollBy(if (reverseDirection) y else -y)
                } else {
                    state.scrollBy(if (reverseDirection) x else -x)
                }
            }
            true
        }
        // Treat this as a selectable group
        selectableGroup()
    }

    val scrollable = Modifier.scrollable(
        orientation = if (isVertical) Orientation.Vertical else Orientation.Horizontal,
        flingBehavior = flingBehavior,
        reverseDirection = reverseDirection,
        state = state,
        enabled = dragEnabled,
    )

    Layout(
        modifier = modifier
            .then(semantics)
            .then(scrollable)
            .clipToBounds(),
        content = {
            val firstPage = (state.currentPage - offscreenLimit).coerceAtLeast(Int.MIN_VALUE)
            val lastPage = (state.currentPage + offscreenLimit).coerceAtMost(Int.MAX_VALUE)

            for (page in firstPage..lastPage) {
                key(page) {
                    val itemSemantics = Modifier.semantics {
                        this.selected = page == state.currentPage
                    }
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = itemSemantics.then(PageData(page))
                    ) {
                        val scope = remember(this, state) {
                            PagerScopeImpl(this, state)
                        }
                        scope.content(page)
                    }
                }
            }
        },
    ) { measurables, constraints ->
        layout(constraints.maxWidth, constraints.maxHeight) {
            val currentPage = state.currentPage
            val offset = state.currentPageOffset
            val childConstraints = constraints.copy(minWidth = 0, minHeight = 0)
            val itemSpacingPx = itemSpacing.roundToPx()

            measurables.forEach {
                val placeable = it.measure(childConstraints)
                val page = it.page

                val xCenterOffset = horizontalAlignment.align(
                    size = placeable.width,
                    space = constraints.maxWidth,
                    layoutDirection = LayoutDirection.Ltr,
                )
                val yCenterOffset = verticalAlignment.align(
                    size = placeable.height,
                    space = constraints.maxHeight
                )

                var yItemOffset = 0
                var xItemOffset = 0
                val offsetForPage = page - currentPage - offset

                if (isVertical) {
                    if (currentPage == page) {
                        state.pageSize = placeable.height
                    }
                    yItemOffset = (offsetForPage * (placeable.height + itemSpacingPx)).roundToInt()
                } else {
                    if (currentPage == page) {
                        state.pageSize = placeable.width
                    }
                    xItemOffset = (offsetForPage * (placeable.width + itemSpacingPx)).roundToInt()
                }

                placeable.placeRelative(
                    x = xCenterOffset + xItemOffset,
                    y = yCenterOffset + yItemOffset,
                )
            }
        }
    }
}

/**
 * Scope for [HorizontalPager] content.
 */

@Stable
interface PagerScope : BoxScope {
    val currentPage: Int

    val currentPageOffset: Float
}


private class PagerScopeImpl(
    private val boxScope: BoxScope,
    private val state: PagerState,
) : PagerScope, BoxScope by boxScope {
    override val currentPage: Int
        get() = state.currentPage

    override val currentPageOffset: Float
        get() = state.currentPageOffset
}

fun PagerScope.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage + currentPageOffset) - page
}
