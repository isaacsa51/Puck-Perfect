package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Sync
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.backgrounds.DotMatrixBackground
import com.serranoie.app.puckperfect.core.ui.theme.components.util.RandomBackground
import com.serranoie.app.puckperfect.core.ui.theme.headlineMediumExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelMediumCondensed
import org.jetbrains.compose.ui.tooling.preview.Preview

data class LastExtractionUi(
    val dose: String,
    val yield: String,
    val ratio: String,
    val time: String,
    val grinderModel: String,
    val grinderSetting: String,
    val espressoMachine: String
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
    lastExtraction: LastExtractionUi,
    beanId: Int = name.hashCode() // Use name hash as seed for consistent backgrounds
) {
    Card(
        modifier = modifier.padding(6.dp).fillMaxWidth().wrapContentHeight()
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
    ) {
        // Top hero banner
        Box(
            modifier = Modifier.fillMaxWidth().wrapContentHeight()
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
        ) {
            RandomBackground(
                seed = beanId, modifier = Modifier.matchParentSize()
            )
            Column(
                Modifier.fillMaxWidth().padding(start = 16.dp, top = 8.dp, end = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ElevatedAssistChip(
                        label = {
                            Text(
                                roast.uppercase(),
                                style = MaterialTheme.typography.labelMediumCondensed(),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        onClick = {},
                        shape = RoundedCornerShape(percent = 50),
                        colors = AssistChipDefaults.assistChipColors(
                            labelColor = MaterialTheme.colorScheme.onPrimary,
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    if (selected) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Selected",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    name.uppercase(),
                    style = MaterialTheme.typography.headlineMediumExpressive(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    origin,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
        HorizontalDivider(
            Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f)
        )
        TextButton(
            onClick = onToggleExtraction,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.padding(start = 16.dp, end = 12.dp)
        ) {
            val rotation by animateFloatAsState(
                targetValue = if (showExtraction) 180f else 0f, animationSpec = tween(300)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Sync,
                    contentDescription = "Extraction",
                    tint = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    "Last Extraction".uppercase(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = if (showExtraction) "Collapse" else "Expand",
                    modifier = Modifier.size(22.dp).graphicsLayer { rotationZ = rotation })
            }
        }
        AnimatedVisibility(showExtraction) {
            ExtractionStatsRow(
                dose = lastExtraction.dose,
                yield = lastExtraction.yield,
                time = lastExtraction.time,
                grind = lastExtraction.grinderSetting,
                ratio = lastExtraction.ratio,
                espressoMachine = lastExtraction.espressoMachine,
                modifier = Modifier
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
    modifier: Modifier
) {
    Column(modifier.padding(top = 10.dp, bottom = 18.dp, start = 14.dp, end = 14.dp)) {
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f)
        )

        Row(
            Modifier.fillMaxWidth().height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "DOSE",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        dose,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.width(2.dp))
                    Text("g", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            VerticalDivider()
            // YIELD
            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "YIELD",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        yield,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.width(2.dp))
                    Text("g", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            VerticalDivider()
            // TIME
            Column(
                modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "TIME",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        time,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(Modifier.width(2.dp))
                    Text("s", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(14.dp)) {
            // GRIND
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 0.dp,
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    Modifier.padding(vertical = 12.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "GRIND SETTING",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                    Text(
                        grind,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            // RATIO
            Surface(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 0.dp,
                modifier = Modifier.weight(1f)
            ) {
                Column(
                    Modifier.padding(vertical = 12.dp).fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "RATIO",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.75f)
                    )
                    Text(
                        ratio,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        Divider(
            Modifier.padding(vertical = 8.dp),
            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.16f)
        )
        Row(
            Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Espresso Machine",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Medium
            )
            Text(
                espressoMachine,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        Modifier.width(1.dp).fillMaxHeight()
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
    )
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
            lastExtraction = LastExtractionUi(
                dose = "20",
                yield = "40",
                ratio = "2.1",
                time = "30",
                grinderModel = "Niche Zero",
                grinderSetting = "20",
                espressoMachine = "Breville Barista Express"
            )
        )
    }
}
