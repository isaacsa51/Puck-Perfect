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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.serranoie.app.puckperfect.core.ui.theme.displaySmallExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelLargeExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelMediumCondensed
import com.serranoie.app.puckperfect.core.ui.theme.titleLargeExpressive
import com.serranoie.app.puckperfect.core.ui.theme.titleMediumCondensed
import com.serranoie.app.puckperfect.core.ui.theme.titleMediumExpressive
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DottedDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
    thickness: Dp = 1.dp,
    dotSpacing: Dp = 5.dp,
    dotDiameter: Dp = 3.dp
) {
    Canvas(modifier = modifier.fillMaxWidth().height(thickness)) {
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
    val topCircleDiameter = 64.dp

    Surface {
        Box(
            modifier = modifier.border(
                2.dp, outlineColor, shape = RoundedCornerShape(8.dp)
            )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(headerBg)
                        .border(1.dp, outlineColor, RoundedCornerShape(8.dp))
                        .padding(vertical = 20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.size(topCircleDiameter).clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .border(2.dp, outlineColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "☕", fontSize = 30.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            brand,
                            color = MaterialTheme.colorScheme.surface,
                            style = MaterialTheme.typography.displaySmallExpressive()
                                .copy(fontWeight = FontWeight.Black),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                // Roasted beans header/section
                Box(
                    Modifier.fillMaxWidth().height(44.dp).border(1.dp, outlineColor)
                        .padding(vertical = 8.dp), contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        "ROASTED BEANS",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.titleLargeExpressive()
                    )
                }

                // Roast/Country row
                Row(
                    Modifier.fillMaxWidth().border(1.dp, outlineColor).height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(Modifier.weight(1f).padding(8.dp)) {
                        Text(
                            "ROAST",
                            style = MaterialTheme.typography.labelMediumCondensed(),
                            color = MaterialTheme.colorScheme.outline,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            roast,
                            style = MaterialTheme.typography.titleMediumExpressive(),
                        )
                    }
                    // vertical divider
                    Box(
                        Modifier.fillMaxHeight().width(2.dp).background(outlineColor)
                    )
                    Column(Modifier.weight(1f).padding(8.dp)) {
                        Text(
                            "COUNTRY",
                            style = MaterialTheme.typography.labelMediumCondensed(),
                            color = MaterialTheme.colorScheme.outline,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            country,
                            style = MaterialTheme.typography.titleMediumExpressive(),
                        )
                    }
                }
                // Processing/date row
                Row(
                    Modifier.height(IntrinsicSize.Max).border(1.dp, outlineColor).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(Modifier.weight(1f).padding(8.dp)) {
                        Text(
                            "PROCESSING",
                            style = MaterialTheme.typography.labelMediumCondensed(),
                            color = MaterialTheme.colorScheme.outline,
                            letterSpacing = 1.sp,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        Text(
                            processing,
                            style = MaterialTheme.typography.titleMediumExpressive(),
                        )
                    }
                    // vertical divider
                    Box(
                        Modifier.width(2.dp).fillMaxHeight().background(outlineColor)
                    )
                    Column(
                        Modifier.weight(1f).padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val dateParts = date.split("-").take(3).reversed()
                        dateParts.forEachIndexed { i, part ->
                            Text(
                                part,
                                style = MaterialTheme.typography.titleLargeExpressive(),
                            )
                            if (i < dateParts.lastIndex) {
                                Spacer(Modifier.height(6.dp))
                                DottedDivider(
                                    modifier = Modifier.padding(horizontal = 0.dp).height(5.dp),
                                )
                                Spacer(Modifier.height(6.dp))
                            }
                        }
                    }
                }
                // Bottom placeholder section
                Box(
                    Modifier.fillMaxWidth().border(1.dp, outlineColor, RoundedCornerShape(8.dp))
                ) {
                    // You can add lines/pattern or a watermark style as placeholder if desired
                }
            }
        }
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