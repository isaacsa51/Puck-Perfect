package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlin.math.abs

/**
 * Configuration for swipe actions
 */
data class SwipeActionsConfig(
    val icon: @Composable () -> Unit,
    val backgroundColor: Color,
    val onSwipe: () -> Unit,
)

/**
 * Configuration for start (left swipe) actions - Edit
 */
data class StartActionsConfig(
    val editAction: SwipeActionsConfig,
)

/**
 * Configuration for end (right swipe) actions - Delete
 */
data class EndActionsConfig(
    val deleteAction: SwipeActionsConfig,
)

/**
 * Wrapper composable that adds swipe-to-reveal actions to its content.
 * - Swipe from left reveals edit action
 * - Swipe from right reveals delete action
 *
 * @param modifier Modifier for the wrapper
 * @param startActionsConfig Config for left swipe (edit)
 * @param endActionsConfig Config for right swipe (delete)
 * @param onTried Callback when user attempts a swipe
 * @param showTutorial Whether to show the swipe tutorial animation
 * @param threshold The threshold (0-1) at which the action is triggered (default 0.4 = 40%)
 * @param content The content to wrap
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeActions(
    modifier: Modifier = Modifier,
    startActionsConfig: StartActionsConfig? = null,
    endActionsConfig: EndActionsConfig? = null,
    onTried: () -> Unit = {},
    showTutorial: Boolean = false,
    threshold: Float = 0.4f,
    content: @Composable () -> Unit,
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { dismissValue ->
            when (dismissValue) {
                SwipeToDismissBoxValue.StartToEnd -> {
                    startActionsConfig?.editAction?.onSwipe?.invoke()
                    false
                }
                SwipeToDismissBoxValue.EndToStart -> {
                    endActionsConfig?.deleteAction?.onSwipe?.invoke()
                    false
                }
                SwipeToDismissBoxValue.Settled -> false
            }
        },
        positionalThreshold = { it * threshold },
    )

    var willDismiss by remember { mutableFloatStateOf(0f) }
    var revealSize = remember { Animatable(0f) }
    var iconSize = remember { Animatable(1f) }

    val currentDismissValue = dismissState.currentValue
    val targetDismissValue = dismissState.targetValue

    // Determine if we're revealing actions
    val isRevealingStart = currentDismissValue == SwipeToDismissBoxValue.StartToEnd ||
            targetDismissValue == SwipeToDismissBoxValue.StartToEnd
    val isRevealingEnd = currentDismissValue == SwipeToDismissBoxValue.EndToStart ||
            targetDismissValue == SwipeToDismissBoxValue.EndToStart

    val isRevealing = isRevealingStart || isRevealingEnd

    // Handle tutorial animation
    LaunchedEffect(key1 = showTutorial, block = {
        if (showTutorial) {
            // Show tutorial animation - reveal and bounce
            revealSize.snapTo(0f)
            revealSize.animateTo(1f, animationSpec = tween(400))
        }
    })

    // Handle swipe reveal animation
    LaunchedEffect(key1 = isRevealing, key2 = dismissState.progress, block = {
        val progress = abs(dismissState.progress)
        willDismiss = progress

        if (progress > threshold) {
            if (revealSize.value == 0f) {
                // First time crossing threshold - trigger animation
                revealSize.snapTo(0f)
                revealSize.animateTo(1f, animationSpec = tween(400))

                iconSize.snapTo(0.8f)
                iconSize.animateTo(
                    1.25f,
                    spring(dampingRatio = Spring.DampingRatioHighBouncy)
                )
                iconSize.animateTo(
                    1f,
                    spring(dampingRatio = Spring.DampingRatioLowBouncy)
                )
            }
        }
    })

    val animatedReveal by animateFloatAsState(
        targetValue = if (isRevealing) revealSize.value else 0f,
        animationSpec = tween(200),
        label = "revealAnimation"
    )

    val animatedIconScale by animateFloatAsState(
        targetValue = if (isRevealing) iconSize.value else 1f,
        animationSpec = tween(200),
        label = "iconScale"
    )

    Box(modifier = modifier) {
        // Background with action reveal
        if (startActionsConfig != null || endActionsConfig != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp)
            ) {
                // Start (left) action background - Edit
                if (isRevealingStart && startActionsConfig != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterStart)
                            .clip(CirclePath(animatedReveal, CirclePath.Direction.StartToEnd))
                            .background(startActionsConfig.editAction.backgroundColor)
                            .padding(start = 24.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Box(
                            modifier = Modifier
                                .scale(animatedIconScale)
                                .size(24.dp)
                        ) {
                            startActionsConfig.editAction.icon()
                        }
                    }
                }

                // End (right) action background - Delete
                if (isRevealingEnd && endActionsConfig != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .align(Alignment.CenterEnd)
                            .clip(CirclePath(animatedReveal, CirclePath.Direction.EndToStart))
                            .background(endActionsConfig.deleteAction.backgroundColor)
                            .padding(end = 24.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .scale(animatedIconScale)
                                .size(24.dp)
                        ) {
                            endActionsConfig.deleteAction.icon()
                        }
                    }
                }
            }
        }

        // Swipeable content
        SwipeToDismissBox(
            state = dismissState,
            enableDismissFromStartToEnd = startActionsConfig != null,
            enableDismissFromEndToStart = endActionsConfig != null,
            backgroundContent = {},
            content = { content() }
        )
    }
}

/**
 * Custom shape that creates a circular reveal effect for swipe actions.
 * Animates from edge inward based on the progress.
 */
private class CirclePath(
    private val progress: Float,
    private val direction: Direction
) : androidx.compose.ui.graphics.Shape {
    enum class Direction { StartToEnd, EndToStart }

    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): androidx.compose.ui.graphics.Outline {
        val path = Path()
        val maxRadius = size.height.coerceAtLeast(size.width)

        when (direction) {
            Direction.StartToEnd -> {
                // Circular reveal from left
                val centerX = size.width
                val centerY = size.height / 2
                val radius = maxRadius * progress

                path.addOval(
                    androidx.compose.ui.geometry.Rect(
                        left = centerX - radius,
                        top = centerY - radius,
                        right = centerX + radius,
                        bottom = centerY + radius
                    )
                )
            }
            Direction.EndToStart -> {
                // Circular reveal from right
                val centerX = 0f
                val centerY = size.height / 2
                val radius = maxRadius * progress

                path.addOval(
                    androidx.compose.ui.geometry.Rect(
                        left = centerX - radius,
                        top = centerY - radius,
                        right = centerX + radius,
                        bottom = centerY + radius
                    )
                )
            }
        }

        return androidx.compose.ui.graphics.Outline.Generic(path)
    }
}

/**
 * Default edit action configuration
 */
fun defaultStartActionsConfig(
    onEdit: () -> Unit,
    backgroundColor: Color,
): StartActionsConfig {
    return StartActionsConfig(
        editAction = SwipeActionsConfig(
            icon = {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White
                )
            },
            backgroundColor = backgroundColor,
            onSwipe = onEdit
        )
    )
}

/**
 * Default delete action configuration
 */
fun defaultEndActionsConfig(
    onDelete: () -> Unit,
    backgroundColor: Color,
): EndActionsConfig {
    return EndActionsConfig(
        deleteAction = SwipeActionsConfig(
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            },
            backgroundColor = backgroundColor,
            onSwipe = onDelete
        )
    )
}