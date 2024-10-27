@file:Suppress("ktlint:twitter-compose:modifier-composable-check")

package com.shashluchok.medianotes.presentation.modifiers.scrollbars

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shashluchok.medianotes.presentation.modifiers.scrollbars.ScrollbarDimensions.Companion.toDimensions

private const val alphaAnimationLabel = "Alpha"

@Composable
internal fun Modifier.scrollbar(
    state: LazyListState,
    config: ScrollbarConfig = ScrollbarConfig.defaults(),
    alwaysVisible: Boolean = false
): Modifier {
    val drawer = remember(config) {
        LazyListScrollbarDrawer(state)
    }
    return scrollbar(state.isScrollInProgress, drawer, config, alwaysVisible)
}

@Composable
internal fun Modifier.horizontalScrollbar(
    state: ScrollState,
    config: ScrollbarConfig = ScrollbarConfig.defaults(),
    alwaysVisible: Boolean = false
): Modifier {
    val drawer = remember(Orientation.Horizontal, config) {
        DefaultScrollbarDrawer(state, Orientation.Horizontal)
    }
    return scrollbar(state.isScrollInProgress, drawer, config, alwaysVisible)
}

@Composable
internal fun Modifier.verticalScrollbar(
    state: ScrollState,
    config: ScrollbarConfig = ScrollbarConfig.defaults(),
    alwaysVisible: Boolean = false
): Modifier {
    val drawer = remember(Orientation.Vertical, config) {
        DefaultScrollbarDrawer(state, Orientation.Vertical)
    }
    return scrollbar(state.isScrollInProgress, drawer, config, alwaysVisible)
}

@Composable
internal fun Modifier.horizontalScrollbar(
    state: LazyGridState,
    spans: Int,
    config: ScrollbarConfig = ScrollbarConfig.defaults(),
    alwaysVisible: Boolean = false
): Modifier {
    val drawer = remember(Orientation.Horizontal, spans, config) {
        LazyGridScrollbarDrawer(state, Orientation.Horizontal, spans)
    }
    return scrollbar(state.isScrollInProgress, drawer, config, alwaysVisible)
}

@Composable
internal fun Modifier.verticalScrollbar(
    state: LazyGridState,
    spans: Int,
    config: ScrollbarConfig = ScrollbarConfig.defaults(),
    alwaysVisible: Boolean = false
): Modifier {
    val drawer = remember(Orientation.Vertical, spans, config) {
        LazyGridScrollbarDrawer(state, Orientation.Vertical, spans)
    }
    return scrollbar(state.isScrollInProgress, drawer, config, alwaysVisible)
}

@Composable
private fun Modifier.scrollbar(
    isScrollInProgress: Boolean,
    drawer: ScrollbarDrawer,
    config: ScrollbarConfig = ScrollbarConfig.defaults(),
    alwaysVisible: Boolean = true
): Modifier {
    if (alwaysVisible) {
        return drawWithContent {
            drawContent()
            val dimensions = config.toDimensions(this)
            with(drawer) {
                drawScrollbar(
                    color = config.color,
                    alpha = 1f,
                    dimensions = dimensions
                )
            }
        }
    }

    val duration = when (isScrollInProgress) {
        true -> config.fadeInDurationMillis
        false -> config.fadeOutDurationMillis
    }
    val alpha by animateFloatAsState(
        targetValue = if (isScrollInProgress) 1f else 0f,
        animationSpec = tween(
            durationMillis = duration,
            delayMillis = if (isScrollInProgress) 0 else config.scrollbarFadeOutDelay
        ),
        label = alphaAnimationLabel
    )

    return drawWithContent {
        drawContent()
        if (alpha != 0f) {
            val dimensions = config.toDimensions(this)
            with(drawer) {
                drawScrollbar(
                    color = config.color,
                    alpha = alpha,
                    dimensions = dimensions
                )
            }
        }
    }
}

internal data class ScrollbarConfig internal constructor(
    val color: Color,
    val thickness: Dp,
    val padding: PaddingValues,
    val fadeInDurationMillis: Int,
    val fadeOutDurationMillis: Int,
    val scrollbarFadeOutDelay: Int
) {
    companion object {
        @Composable
        fun defaults(
            scrollbarColor: Color = MaterialTheme.colorScheme.outlineVariant,
            scrollbarThickness: Dp = 4.dp,
            padding: PaddingValues = PaddingValues(2.dp),
            fadeInDurationMillis: Int = 150,
            fadeOutDurationMillis: Int = 500,
            scrollbarFadeOutDelay: Int = 200
        ): ScrollbarConfig = ScrollbarConfig(
            scrollbarColor,
            scrollbarThickness,
            padding,
            fadeInDurationMillis,
            fadeOutDurationMillis,
            scrollbarFadeOutDelay
        )
    }
}
