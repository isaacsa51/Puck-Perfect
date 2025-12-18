package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.displayMediumCondensed
import com.serranoie.app.puckperfect.core.ui.theme.displayMediumExpressive
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.abs
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GramsSlider(
    currentGrams: Float,
    minGrams: Float,
    maxGrams: Float,
    showDecimals: Boolean,
    stepSize: Float,
    onValueChanged: (Float) -> Unit
) {
    val values = remember(minGrams, maxGrams, stepSize, showDecimals) {
        if (showDecimals) {
            // Use a larger multiplier to avoid floating-point precision issues
            val multiplier = 10
            val minInt = (minGrams * multiplier).toInt()
            val maxInt = (maxGrams * multiplier).toInt()
            val stepInt = (stepSize * multiplier).toInt().coerceAtLeast(1)
            (minInt..maxInt step stepInt).map { it.toFloat() / multiplier }
        } else {
            val minInt = minGrams.toInt()
            val maxInt = maxGrams.toInt()
            (minInt..maxInt).map { it.toFloat() }
        }
    }
    val initialIndex = remember(currentGrams, showDecimals) {
        var idx = values.indexOfFirst { it == currentGrams }
        if (idx < 0) {
            idx = values.indices.minByOrNull { abs(values[it] - currentGrams) } ?: 0
        }
        idx
    }
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = initialIndex)
    val snapBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    var isTogglingDecimals by remember { mutableStateOf(false) }
    val currentCenteredIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }
    val itemHeight = 120.dp

    LaunchedEffect(currentCenteredIndex) {
        if (!isTogglingDecimals && currentCenteredIndex in values.indices) {
            val newValue = values[currentCenteredIndex]
            if (abs(newValue - currentGrams) > 0.001f) {
                onValueChanged(newValue)
            }
        }
    }
    LaunchedEffect(showDecimals) {
        isTogglingDecimals = true
        var newIndex = values.indexOfFirst { it == currentGrams }
        if (newIndex < 0) {
            newIndex = values.indices.minByOrNull { abs(values[it] - currentGrams) } ?: 0
        }
        listState.scrollToItem(newIndex)
        delay(100)
        isTogglingDecimals = false
    }
    LaunchedEffect(currentGrams) {
        if (!isTogglingDecimals) {
            var newIndex = values.indexOfFirst { it == currentGrams }
            if (newIndex < 0) {
                newIndex = values.indices.minByOrNull { abs(values[it] - currentGrams) } ?: 0
            }
            if (newIndex != currentCenteredIndex) {
                listState.animateScrollToItem(newIndex)
            }
        }
    }

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        val verticalPadding = (maxHeight - itemHeight) / 2
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LazyColumn(
                state = listState,
                flingBehavior = snapBehavior,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = verticalPadding),
                modifier = Modifier.fillMaxSize()
            ) {
                items(values, key = { it }) { value ->
                    val index = values.indexOf(value)
                    val offset = index - currentCenteredIndex
                    val isCentered = offset == 0

                    val animatedFontSize by animateFloatAsState(
                        targetValue = if (isCentered) 96f else 36f, label = "fontSizeAnimation"
                    )
                    val animatedFontWeight by animateIntAsState(
                        targetValue = if (isCentered) FontWeight.ExtraBold.weight else FontWeight.W300.weight,
                        label = "fontWeightAnimation"
                    )
                    val animatedColor by animateColorAsState(
                        targetValue = if (isCentered) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                        label = "colorAnimation"
                    )
                    val animatedAlpha by animateFloatAsState(
                        targetValue = 1f - (offset.absoluteValue * 0.3f).coerceIn(0f, 0.8f),
                        label = "alphaAnimation"
                    )
                    val animatedScale by animateFloatAsState(
                        targetValue = 1f - (offset.absoluteValue * 0.15f).coerceIn(0f, 0.5f),
                        label = "scaleAnimation"
                    )
                    val animatedLetterSpacing by animateFloatAsState(
                        targetValue = if (isCentered) 0.0f else (-0.02f),
                        label = "letterSpacingAnimation"
                    )
                    Box(
                        modifier = Modifier.height(itemHeight).fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.graphicsLayer {
                                this.alpha = animatedAlpha
                                scaleX = animatedScale
                                scaleY = animatedScale
                            }) {
                            Text(
                                text = formatGrams(value, showDecimals),
                                color = animatedColor,
                                fontSize = animatedFontSize.sp,
                                fontWeight = FontWeight(animatedFontWeight),
                                style = if (isCentered) MaterialTheme.typography.displayMediumExpressive() else MaterialTheme.typography.displayMediumCondensed(),
                                letterSpacing = animatedLetterSpacing.sp,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Clip,
                                modifier = Modifier,
                                lineHeight = animatedFontSize.sp
                            )
                            if (isCentered) {

                            } else {
                                Spacer(modifier = Modifier.height(24.dp))
                            }
                        }
                    }
                }
            }
            // Overlay: Stepped indicator/weight machine lines
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 44.dp)
//                    .align(Alignment.Center),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Canvas(
//                    modifier = Modifier
//                        .weight(1f)
//                        .height(3.dp)
//                ) {
//                    drawLine(
//                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.37f),
//                        start = Offset(0f, size.height / 2),
//                        end = Offset(size.width, size.height / 2),
//                        strokeWidth = 2.dp.toPx()
//                    )
//                }
//                Spacer(modifier = Modifier.width(180.dp))
//                Canvas(
//                    modifier = Modifier
//                        .weight(1f)
//                        .height(3.dp)
//                ) {
//                    drawLine(
//                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.37f),
//                        start = Offset(0f, size.height / 2),
//                        end = Offset(size.width, size.height / 2),
//                        strokeWidth = 2.dp.toPx()
//                    )
//                }
//            }
        }
    }
}

private fun formatGrams(grams: Float, showDecimals: Boolean = true): String {
    return if (!showDecimals || grams % 1.0f == 0.0f) {
        grams.toInt().toString()
    } else {
        // Manual formatting for multiplatform compatibility
        val intPart = grams.toInt()
        val decimalPart = ((grams - intPart) * 10).toInt()
        "$intPart.$decimalPart"
    }
}

@Preview(showBackground = true)
@Composable
fun GramsSliderPreview() {
    PuckPerfectTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            var grams by remember { mutableStateOf(18.0f) }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                GramsSlider(
                    currentGrams = grams,
                    minGrams = 10f,
                    maxGrams = 25f,
                    showDecimals = true,
                    stepSize = 0.1f,
                    onValueChanged = { grams = it })
            }
        }
    }
}