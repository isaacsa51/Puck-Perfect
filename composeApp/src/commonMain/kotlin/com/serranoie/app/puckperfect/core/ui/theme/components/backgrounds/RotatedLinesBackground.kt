package com.serranoie.app.puckperfect.core.ui.theme.components.backgrounds

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RotatedLinesBackground(
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
    background: Color = MaterialTheme.colorScheme.surfaceVariant,
    angle: Float = -45f,
    lineWidth: Dp = 4.dp,
    lineSpacing: Dp = 16.dp
) {
    Box(modifier.background(background)) {
        Canvas(Modifier.fillMaxWidth().fillMaxHeight()) {
            val density = this@Canvas
            val spacing = with(density) { lineSpacing.toPx() }
            val strokeWidth = with(density) { lineWidth.toPx() }

            // Convert angle to radians
            val angleRad = angle * PI.toFloat() / 180f

            // Calculate direction vectors for the line
            val dirX = cos(angleRad)
            val dirY = sin(angleRad)

            // Calculate perpendicular direction for spacing between lines
            val perpX = -sin(angleRad)
            val perpY = cos(angleRad)

            // Calculate how many lines we need to cover the entire canvas
            val diagonal = sqrt(size.width * size.width + size.height * size.height)
            val numLines = (diagonal / spacing).toInt() + 2

            // Calculate starting point (center of canvas offset back)
            val centerX = size.width / 2
            val centerY = size.height / 2

            for (i in -numLines / 2..numLines / 2) {
                // Calculate the center point of this line
                val lineCenterX = centerX + perpX * i * spacing
                val lineCenterY = centerY + perpY * i * spacing

                // Calculate start and end points along the line direction
                val startX = lineCenterX - dirX * diagonal
                val startY = lineCenterY - dirY * diagonal
                val endX = lineCenterX + dirX * diagonal
                val endY = lineCenterY + dirY * diagonal

                drawLine(
                    color = lineColor,
                    start = Offset(startX, startY),
                    end = Offset(endX, endY),
                    strokeWidth = strokeWidth
                )
            }
        }
    }
}

@Preview
@Composable
private fun RotatedLinesBackgroundPreview() {
    PuckPerfectTheme {
        RotatedLinesBackground(
            angle = 45f, lineWidth = 5.dp, lineSpacing = 16.dp
        )
    }
}