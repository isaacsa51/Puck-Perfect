package com.serranoie.app.puckperfect.feature.shots

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.ShotFlavor
import com.serranoie.app.puckperfect.core.ui.theme.components.ShotItem
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidAnimatedVisibility
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.abs
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShotsScreen(
    onSwipeToExtraction: (grams: Float, timeSeconds: Int) -> Unit = { _, _ -> },
) {
    // Mock data
    data class Shot(
        val id: Int,
        val bean: String,
        val flavor: String,
        val grinder: String,
        val weight: String,
        val time: String,
    )

    var shots by remember {
        mutableStateOf(
            listOf(
                Shot(1, "Ethiopia Sidamo", "Sweet", "Setting: 14", "32", "28s"),
                Shot(2, "Brazil Cerrado", "Acid", "Setting: 10", "34", "30s"),
                Shot(3, "Colombia Supremo", "Bitter", "Setting: 12", "30", "32s"),
                Shot(4, "Kenya AA", "Sweet", "Setting: 13", "31", "29s"),
                Shot(5, "Sumatra Mandheling", "Acid", "Setting: 9", "33", "27s"),
                Shot(6, "Costa Rica Tarrazu", "Bitter", "Setting: 11", "29", "33s"),
                Shot(7, "Guatemala Huehuetenango", "Sweet", "Setting: 15", "35", "30s"),
                Shot(8, "Tanzania Peaberry", "Acid", "Setting: 13", "36", "28s"),
                Shot(9, "Yemen Mocha", "Bitter", "Setting: 10", "30", "34s"),
                Shot(10, "Honduras Marcala", "Sweet", "Setting: 8", "32", "31s"),
                Shot(11, "Peru Chanchamayo", "Bitter", "Setting: 14", "31", "32s"),
                Shot(12, "El Salvador Pacamara", "Sweet", "Setting: 12", "3", "29s"),
                Shot(13, "Nicaragua Maragogype", "Acid", "Setting: 16", "34", "27s"),
                Shot(14, "Bolivia Caranavi", "Bitter", "Setting: 9", "36", "35s"),
                Shot(15, "India Monsooned Malabar", "Sweet", "Setting: 10", "30", "28s"),
                Shot(16, "Rwanda Bourbon", "Acid", "Setting: 13", "32", "31s"),
                Shot(17, "Panama Gesha", "Bitter", "Setting: 11", "34", "32s"),
                Shot(18, "Java Estate", "Sweet", "Setting: 15", "35", "33s"),
                Shot(19, "Mexico Altura", "Bitter", "Setting: 14", "33", "34s"),
                Shot(20, "Vietnam Robusta", "Acid", "Setting: 12", "36", "29s"),
            ),
        )
    }
    val listState = rememberLazyListState()
    val density = LocalDensity.current
    val swipeThresholdPx = with(density) { 96.dp.toPx() }
    val maxSwipePx = with(density) { 160.dp.toPx() }
    var expandedBadShotId by remember { mutableIntStateOf(-1) }

    Scaffold { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize().padding(bottom = padding.calculateBottomPadding()),
            contentPadding = PaddingValues(top = 0.dp),
        ) {
            items(shots, key = { it.id }) { shot ->
                val flavorEnum =
                    when (shot.flavor.uppercase()) {
                        "BITTER" -> ShotFlavor.BITTER
                        "ACID" -> ShotFlavor.ACID
                        else -> ShotFlavor.SWEET
                    }
                val settingVal =
                    shot.grinder
                        .substringAfter(":", "")
                        .trim()
                        .takeIf { it.isNotEmpty() }
                        ?: shot.grinder

                FluidAnimatedVisibility(
                    visible = true,
                ) {
                    val offsetX = remember(shot.id) { Animatable(0f) }
                    val timeSeconds = shot.time.filter { it.isDigit() }.toIntOrNull() ?: 30
                    val grams = shot.weight.toFloatOrNull() ?: 18f
                    val isBadShot = flavorEnum != ShotFlavor.SWEET
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        ShotItem(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                                        .pointerInput(shot.id) {
                                            kotlinx.coroutines.coroutineScope {
                                                detectHorizontalDragGestures(
                                                    onHorizontalDrag = { change, dragAmount ->
                                                        change.consume()
                                                        val next = (offsetX.value + dragAmount).coerceIn(-maxSwipePx, maxSwipePx)
                                                        launch { offsetX.snapTo(next) }
                                                    },
                                                    onDragEnd = {
                                                        val shouldNavigate = abs(offsetX.value) >= swipeThresholdPx
                                                        if (shouldNavigate) {
                                                            onSwipeToExtraction(grams, timeSeconds)
                                                        }
                                                        launch { offsetX.animateTo(0f, animationSpec = tween(220)) }
                                                    },
                                                    onDragCancel = {
                                                        launch { offsetX.animateTo(0f, animationSpec = tween(durationMillis = 220)) }
                                                    },
                                                )
                                            }
                                        },
                                beanName = shot.bean,
                                dateTime = "Today",
                                grinder = "Grinder",
                                grinderSetting = settingVal,
                                grams = shot.weight,
                                time = shot.time.trimEnd('s', 'S'),
                                flavor = flavorEnum,
                                underExtracted = isBadShot,
                                isFavorite = false,
                                badShotExpanded = isBadShot && expandedBadShotId == shot.id,
                                onBadShotToggle =
                                    if (isBadShot) {
                                        {
                                            expandedBadShotId = if (expandedBadShotId == shot.id) -1 else shot.id
                                        }
                                    } else {
                                        null
                                    },
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ShotsScreenPreview() {
    PuckPerfectTheme {
        ShotsScreen()
    }
}
