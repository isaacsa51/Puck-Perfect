package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.util.CompactSpacing
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidTypography
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidHeight
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidPadding
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidWidth
import com.serranoie.app.puckperfect.core.ui.theme.components.util.scaledSp
import com.serranoie.app.puckperfect.core.ui.theme.displaySmallExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelLargeExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelMediumCondensed
import com.serranoie.app.puckperfect.core.ui.theme.titleLargeExpressive
import com.serranoie.app.puckperfect.core.ui.theme.titleMediumCondensed
import com.serranoie.app.puckperfect.core.ui.theme.titleMediumExpressive
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Dotted divider component for decorative separators.
 * Draws a series of circular dots across the width.
 */
@Composable
fun DottedDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
    thickness: Dp = 1.dp,
    dotSpacing: Dp = 5.dp,
    dotDiameter: Dp = 3.dp
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .fluidHeight(thickness)
    ) {
        val width = size.width
        val dotSpacePx = dotSpacing.toPx()
        val dotRadiusPx = dotDiameter.toPx() / 2f
        var x = 0f
        while (x < width) {
            drawCircle(
                color = color,
                radius = dotRadiusPx,
                center = Offset(x + dotRadiusPx, size.height / 2)
            )
            x += dotDiameter.toPx() + dotSpacePx
        }
    }
}

/**
 * Coffee label component displaying coffee bean information in a vintage label style.
 * Features brand, roast level, country of origin, processing method, and roast date.
 *
 * @param brand The coffee brand name
 * @param roast Roast level (e.g., LIGHT, MEDIUM, DARK)
 * @param country Country of origin
 * @param date Roast date in YYYY-MM-DD format
 * @param processing Processing method (e.g., WASHED, NATURAL, HONEY)
 * @param modifier Optional modifier for the label container
 */
@Composable
fun CoffeeLabel(
    brand: String,
    roast: String,
    country: String,
    date: String,
    processing: String = "WASHED",
    modifier: Modifier = Modifier
) {
    val outlineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
    val headerBg = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
    val borderRadius = 8.scaledSp()

    Surface {
        Box(
            modifier = modifier
                .border(
                    width = 2.dp,
                    color = outlineColor,
                    shape = RoundedCornerShape(borderRadius)
                )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Header section with brand and logo
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(headerBg)
                        .border(1.dp, outlineColor, RoundedCornerShape(borderRadius))
                        .fluidPadding(vertical = 20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(CompactSpacing.medium()),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Coffee icon in circle
                        Box(
                            modifier = Modifier
                                .fluidSize(64.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .border(2.dp, outlineColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "☕",
                                fontSize = FluidTypography.headline
                            )
                        }
                        // Brand name
                        Text(
                            text = brand,
                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.displaySmallExpressive()
                                .copy(fontWeight = FontWeight.Black),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                // "ROASTED BEANS" banner
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fluidHeight(44.dp)
                        .border(1.dp, outlineColor)
                        .fluidPadding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ROASTED BEANS",
                        style = MaterialTheme.typography.titleLargeExpressive()
                    )
                }

                // Roast / Country row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, outlineColor)
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Roast column
                    LabelInfoColumn(
                        label = "ROAST",
                        value = roast,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Vertical divider
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fluidWidth(2.dp)
                            .background(outlineColor)
                    )
                    
                    // Country column
                    LabelInfoColumn(
                        label = "COUNTRY",
                        value = country,
                        modifier = Modifier.weight(1f)
                    )
                }
                // Processing / Date row
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .border(1.dp, outlineColor)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Processing column
                    LabelInfoColumn(
                        label = "PROCESSING",
                        value = processing,
                        modifier = Modifier.weight(1f)
                    )
                    
                    // Vertical divider
                    Box(
                        modifier = Modifier
                            .fluidWidth(2.dp)
                            .fillMaxHeight()
                            .background(outlineColor)
                    )
                    
                    // Date column
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fluidPadding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val dateParts = date.split("-").take(3).reversed()
                        dateParts.forEachIndexed { index, part ->
                            Text(
                                text = part,
                                style = MaterialTheme.typography.titleLargeExpressive()
                            )
                            if (index < dateParts.lastIndex) {
                                Spacer(modifier = Modifier.fluidHeight(6.dp))
                                DottedDivider(
                                    modifier = Modifier
                                        .fluidPadding(horizontal = 0.dp)
                                        .fluidHeight(5.dp)
                                )
                                Spacer(modifier = Modifier.fluidHeight(6.dp))
                            }
                        }
                    }
                }
                // Bottom decorative section
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, outlineColor, RoundedCornerShape(borderRadius))
                ) {
                    // Placeholder for additional decorative elements or watermark
                }
            }
        }
    }
}

/**
 * Reusable component for label info sections (label + value).
 * Used for displaying roast, country, and processing information.
 */
@Composable
private fun LabelInfoColumn(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fluidPadding(8.dp),
        verticalArrangement = Arrangement.spacedBy(CompactSpacing.extraSmall())
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMediumCondensed(),
            color = MaterialTheme.colorScheme.outline,
            letterSpacing = 1.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMediumExpressive()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CoffeeLabelPreview() {
    Column {
        PuckPerfectTheme {
            CoffeeLabel(
                brand = "COFFEE BRAND", roast = "MEDIUM", country = "ETHIOPIA", date = "2023-06-17"
            )
        }
    }
}