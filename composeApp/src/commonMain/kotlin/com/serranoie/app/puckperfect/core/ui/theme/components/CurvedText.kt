package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.isUnspecified
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.titleLargeCondensed
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Draws text in a curved/circular pattern using Compose Multiplatform APIs.
 * This implementation draws each character individually, rotating them along a circular arc.
 */
@Composable
fun CurvedText(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleLargeCondensed(),
    arcWidth: Float = 300f,
    arcHeight: Float = 200f,
    textPadding: Float = 48f,
    startAngle: Float = 180f,
    sweepAngle: Float = 180f,
    margin: Float = 32f
) {
    val color = if (textStyle.color.isUnspecified) {
        MaterialTheme.colorScheme.onSurface
    } else {
        textStyle.color
    }

    val finalTextStyle = textStyle.copy(color = color)
    val textMeasurer = rememberTextMeasurer()

    val canvasWidth = arcWidth + margin * 2
    val canvasHeight = arcHeight + margin * 2

    Canvas(
        modifier = modifier.size(canvasWidth.dp, canvasHeight.dp)
    ) {
        val centerX = size.width / 2
        val centerY = margin + arcHeight / 2 + textPadding

        // Calculate radius for the arc
        val radius = arcWidth / 2

        // Convert angles to radians
        val startAngleRad = startAngle * PI.toFloat() / 180f
        val sweepAngleRad = sweepAngle * PI.toFloat() / 180f

        // Calculate angle step per character
        val angleStep = sweepAngleRad / (text.length - 1).coerceAtLeast(1)

        // Draw each character along the arc
        text.forEachIndexed { index, char ->
            val charString = char.toString()

            // Measure the character
            val textLayoutResult = textMeasurer.measure(
                text = charString,
                style = finalTextStyle
            )

            // Calculate angle for this character
            val angle = startAngleRad + (angleStep * index)

            // Calculate position on the circle
            val x = centerX + radius * cos(angle)
            val y = centerY + radius * sin(angle)

            // Calculate rotation angle (perpendicular to the circle)
            val rotationAngle = (angle * 180f / PI.toFloat()) + 90f

            // Draw the character with rotation
            rotate(
                degrees = rotationAngle,
                pivot = Offset(x, y)
            ) {
                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = x - textLayoutResult.size.width / 2,
                        y = y - textLayoutResult.size.height / 2
                    )
                )
            }
        }
    }
}

/**
 * Draws text in a full circle pattern by splitting the text into top and bottom halves.
 */
@Composable
fun CurvedTextFullCircle(
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle,
    arcWidth: Float,
    arcHeight: Float,
    textPadding: Float,
    margin: Float = 32f
) {
    val halfTextLength = text.length / 2
    val topText = text.substring(0, halfTextLength)
    val bottomText = text.substring(halfTextLength)
    val canvasWidth = arcWidth + margin * 2
    val canvasHeight = arcHeight + margin * 2

    Box(modifier = modifier.size(canvasWidth.dp, canvasHeight.dp)) {
        // Top half of the circle
        CurvedText(
            text = topText,
            textStyle = textStyle,
            arcWidth = arcWidth,
            arcHeight = arcHeight,
            textPadding = textPadding,
            startAngle = 180f,
            sweepAngle = 180f,
            margin = margin,
        )

        // Bottom half of the circle
        CurvedText(
            text = bottomText,
            textStyle = textStyle,
            arcWidth = arcWidth,
            arcHeight = arcHeight,
            textPadding = textPadding,
            startAngle = 0f,
            sweepAngle = 180f,
            margin = margin,
        )
    }
}

@Preview(name = "CurvedText Preview", showBackground = true)
@Composable
fun CurvedTextPreview() {
    PuckPerfectTheme {
        CurvedText(
            text = " SAVED ON PUCK PERFECT",
            textStyle = MaterialTheme.typography.titleLargeCondensed().copy(
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            ),
            arcWidth = 300f,
            arcHeight = 200f,
            startAngle = 180f,
            sweepAngle = 350f,
            margin = 50f,
        )
    }
}
