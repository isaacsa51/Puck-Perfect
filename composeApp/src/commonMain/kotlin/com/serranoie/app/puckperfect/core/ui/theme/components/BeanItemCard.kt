package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.util.AureaSpacing
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidAnimatedVisibility
import com.serranoie.app.puckperfect.core.ui.theme.components.util.RandomBackground
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidAnimateContentSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSpace
import com.serranoie.app.puckperfect.core.ui.theme.components.util.scaledSp
import com.serranoie.app.puckperfect.core.ui.theme.headlineMediumExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelMediumCondensed
import com.serranoie.app.puckperfect.core.ui.theme.labelSmallCondensed
import com.serranoie.app.puckperfect.core.ui.theme.titleLargeExpressive
import org.jetbrains.compose.ui.tooling.preview.Preview

data class LastExtractionSection(
    val dose: String,
    val yield: String,
    val ratio: String,
    val time: String,
    val grinderModel: String,
    val grinderSetting: String,
    val espressoMachine: String,
)

@Composable
fun BeanItemCard(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    name: String,
    origin: String,
    roast: String = "Medium Roast",
    showExtraction: Boolean,
    onToggleExtraction: () -> Unit,
    lastExtraction: LastExtractionSection,
    beanId: Int = name.hashCode(),
) {
    Card(
        modifier =
            modifier
                .padding(4.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
    ) {
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        ) {
            RandomBackground(
                seed = beanId,
                modifier = Modifier.matchParentSize(),
            )
            Column(
                Modifier.fillMaxWidth().padding(start = AureaSpacing.current.m, top = AureaSpacing.current.s, end = AureaSpacing.current.m),
                verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.s),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    ElevatedAssistChip(
                        label = {
                            Text(
                                roast.uppercase(),
                                style = MaterialTheme.typography.labelMediumCondensed(),
                                fontWeight = FontWeight.Bold,
                            )
                        },
                        onClick = {},
                        shape = RoundedCornerShape(percent = 50),
                        colors =
                            AssistChipDefaults.assistChipColors(
                                labelColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.primary,
                            ),
                    )
                    FluidAnimatedVisibility(
                        visible = selected,
                        modifier = Modifier.fluidAnimateContentSize(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.fluidSize(18.dp),
                        )
                    }
                }
                Text(
                    name.uppercase(),
                    style = MaterialTheme.typography.headlineMediumExpressive(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    origin,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = AureaSpacing.current.s),
                )
            }
        }
        HorizontalDivider(
            Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f),
        )
        TextButton(
            onClick = onToggleExtraction,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.padding(start = AureaSpacing.current.m, end = AureaSpacing.current.s).fluidAnimateContentSize(),
        ) {
            val rotation by animateFloatAsState(
                targetValue = if (showExtraction) 180f else 0f,
                animationSpec = tween(300),
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
            ) {
                Text(
                    "Last Extraction".uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge,
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (showExtraction) "Collapse" else "Expand",
                    modifier = Modifier.fluidSize(16.dp).graphicsLayer { rotationZ = rotation },
                )
            }
        }
        AnimatedVisibility(
            visible = showExtraction,
            enter = fadeIn(animationSpec = tween(200)) + expandVertically(animationSpec = tween(250)),
            exit = fadeOut(animationSpec = tween(150)) + shrinkVertically(animationSpec = tween(200)),
        ) {
            ExtractionStatsRow(
                dose = lastExtraction.dose,
                yield = lastExtraction.yield,
                time = lastExtraction.time,
                grind = lastExtraction.grinderSetting,
                ratio = lastExtraction.ratio,
                espressoMachine = lastExtraction.espressoMachine,
                modifier = Modifier,
            )
        }
    }
}

@Composable
private fun ExtractionStatsRow(
    dose: String,
    yield: String,
    time: String,
    grind: String,
    ratio: String,
    espressoMachine: String,
    modifier: Modifier,
) {
    Column(
        modifier = modifier.padding(top = 4.dp, bottom = 4.dp, start = AureaSpacing.current.s, end = AureaSpacing.current.s),
        verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.s),
    ) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f),
        )

        Row(
            Modifier.fillMaxWidth().height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
            ) {
                Text(
                    text = "DOSE",
                    style = MaterialTheme.typography.labelSmallCondensed(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(2.scaledSp()),
                ) {
                    Text(
                        dose,
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text("g", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            VerticalDivider()
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
            ) {
                Text(
                    "YIELD",
                    style = MaterialTheme.typography.labelSmallCondensed(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(2.scaledSp()),
                ) {
                    Text(
                        yield,
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text("g", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            VerticalDivider()
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
            ) {
                Text(
                    "TIME",
                    style = MaterialTheme.typography.labelSmallCondensed(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(2.scaledSp()),
                ) {
                    Text(
                        time,
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text("s", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(fluidSpace(8.dp)),
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceDim,
                shape = RoundedCornerShape(12.scaledSp()),
                modifier = Modifier.weight(1f).fluidAnimateContentSize(),
            ) {
                Column(
                    Modifier.padding(vertical = AureaSpacing.current.s).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
                ) {
                    Text(
                        "GRIND SETTING",
                        style = MaterialTheme.typography.labelSmallCondensed(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                    )
                    Text(
                        grind,
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.scaledSp()),
                shadowElevation = 0.dp,
                modifier = Modifier.weight(1f).fluidAnimateContentSize(),
            ) {
                Column(
                    Modifier.padding(vertical = AureaSpacing.current.s).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
                ) {
                    Text(
                        "RATIO",
                        style = MaterialTheme.typography.labelSmallCondensed(),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f),
                    )
                    Text(
                        ratio,
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                }
            }
        }
        Divider(
            Modifier.padding(top = 4.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f),
        )
        Row(
            Modifier.fillMaxWidth().padding(top = 4.dp, bottom = AureaSpacing.current.s),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                "Espresso Machine",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium,
            )
            Text(
                espressoMachine,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PuckPerfectTheme {
        BeanItemCard(
            modifier = Modifier,
            selected = true,
            onClick = { },
            name = "Bean Name",
            origin = "Puebla, Mexico",
            roast = "Medium Roast",
            showExtraction = true,
            onToggleExtraction = { },
            lastExtraction =
                LastExtractionSection(
                    dose = "20",
                    yield = "40",
                    ratio = "2.1",
                    time = "30",
                    grinderModel = "Niche Zero",
                    grinderSetting = "20",
                    espressoMachine = "Breville Barista Express",
                ),
        )
    }
}
