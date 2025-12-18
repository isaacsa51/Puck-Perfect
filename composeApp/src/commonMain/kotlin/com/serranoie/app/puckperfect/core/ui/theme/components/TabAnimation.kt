package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.abs

@Composable
fun TabAnimation(
    modifier: Modifier = Modifier,
    index: Int,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    onSelectedColor: Color = MaterialTheme.colorScheme.onPrimary,
    unselectedColor: Color = MaterialTheme.colorScheme.surface,
    onUnselectedColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.9f),
    selectedIndex: Int,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current
    val isSelected = index == selectedIndex
    val scale = remember { Animatable(1f) }
    val offsetX = remember { Animatable(0f) }

    val animationSpec = tween<Float>(durationMillis = 250, easing = FastOutSlowInEasing)

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) selectedColor else unselectedColor,
        animationSpec = tween(durationMillis = 200),
        label = "Tab Background Color"
    )
    val contentColor by animateColorAsState(
        targetValue = if (isSelected) onSelectedColor else onUnselectedColor,
        animationSpec = tween(durationMillis = 200),
        label = "Tab Content Color"
    )

    // Handles the "pop" animation for the selected tab.
    LaunchedEffect(isSelected) {
        if (isSelected) {
            launch {
                scale.animateTo(1.15f, animationSpec = animationSpec)
                scale.animateTo(1f, animationSpec = animationSpec)
            }
        } else {
            scale.snapTo(1f)
        }
    }

    // Handles the offset for neighbor tabs when selection changes.
    LaunchedEffect(selectedIndex) {
        if (!isSelected) {
            val distance = index - selectedIndex
            if (abs(distance) == 1) { // Only affect direct neighbors
                val direction = if (distance > 0) 1 else -1
                // Move neighbors slightly
                val offsetValue = 12f * direction
                launch {
                    offsetX.animateTo(offsetValue, animationSpec = animationSpec)
                    offsetX.animateTo(0f, animationSpec = animationSpec)
                }
            } else {
                // Instantly reset offset for non-neighbor tabs
                offsetX.snapTo(0f)
            }
        } else {
            // Ensure the selected tab itself has no offset
            offsetX.snapTo(0f)
        }
    }

    Surface(
        modifier = modifier.padding(
            horizontal = 8.dp, vertical = 12.dp
        ).graphicsLayer {
            scaleX = scale.value
            translationX = offsetX.value
        }.clip(RoundedCornerShape(5.dp)).background(
            color = backgroundColor, shape = RoundedCornerShape(8.dp)
        ).clickable {
            hapticFeedback.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
        }, color = backgroundColor, shape = RoundedCornerShape(8.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TabPreview() {
    PuckPerfectTheme {
        Row() {
            TabAnimation(
                modifier = Modifier,
                index = 0,
                selectedIndex = 0,
                onClick = {},
                content = { Text("Tab Title 2") })

            TabAnimation(
                modifier = Modifier,
                index = 0,
                selectedIndex = 1,
                onClick = {},
                content = { Text("Selected") })
        }

    }
}