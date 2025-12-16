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
fun CoffeeLabel() {
    val outlineColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
    val headerBg = MaterialTheme.colorScheme.primary.copy(alpha = 0.95f)
    val topCircleDiameter = 64.dp

    Surface {
        Box(
            modifier = Modifier
                .width(220.dp)
                .wrapContentHeight()
                .border(
                    2.dp,
                    outlineColor,
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(headerBg)
                        .border(2.dp, outlineColor, RoundedCornerShape(8.dp))
                        .padding(vertical = 20.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(topCircleDiameter)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.surface)
                                .border(2.dp, outlineColor, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "☕",
                                fontSize = 30.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "COFFEE BRAND",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            letterSpacing = 2.sp
                        )
                    }
                }
                // Roasted beans header/section
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .border(2.dp, outlineColor)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        "ROASTED BEANS",
                        modifier = Modifier.align(Alignment.Center),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        letterSpacing = 3.sp
                    )
                }

                // Roast/Country row
                Row(
                    Modifier
                        .fillMaxWidth()
                        .border(2.dp, outlineColor)
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(Modifier.weight(1f).padding(8.dp)) {
                        Text(
                            "ROAST",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            letterSpacing = 1.sp
                        )
                        Text(
                            "MEDIUM",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    // vertical divider
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .width(2.dp)
                            .background(outlineColor)
                    )
                    Column(Modifier.weight(1f).padding(8.dp)) {
                        Text(
                            "COUNTRY",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            letterSpacing = 1.sp
                        )
                        Text(
                            "ETHIOPIA",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
                // Processing/date row
                Row(
                    Modifier
                        .height(IntrinsicSize.Max)
                        .border(2.dp, outlineColor)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(Modifier.weight(1f).padding(8.dp)) {
                        Text(
                            "PROCESSING",
                            fontSize = 10.sp,
                            color = Color.Gray,
                            letterSpacing = 1.sp
                        )
                        Text(
                            "WASHED",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    // vertical divider
                    Box(
                        Modifier
                            .width(2.dp)
                            .fillMaxHeight()
                            .background(outlineColor)
                    )
                    Column(
                        Modifier.weight(1f).padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val dateNumbers = listOf("17", "06", "23")
                        dateNumbers.forEachIndexed { i, number ->
                            Text(
                                number,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            if (i < dateNumbers.lastIndex) {
                                Spacer(Modifier.height(6.dp))
                                DottedDivider(
                                    modifier = Modifier
                                        .padding(horizontal = 0.dp)
                                        .height(5.dp),
                                )
                                Spacer(Modifier.height(6.dp))
                            }
                        }
                    }
                }
                // Bottom placeholder section
                Box(
                    Modifier
                        .fillMaxWidth()
                        .border(2.dp, outlineColor, RoundedCornerShape(8.dp))
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
        PuckPerfectTheme { CoffeeLabel() }
    }
}