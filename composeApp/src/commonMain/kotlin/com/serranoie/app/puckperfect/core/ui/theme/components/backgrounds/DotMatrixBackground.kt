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
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

@Composable
fun DotMatrixBackground(
    modifier: Modifier = Modifier,
    dotColor: Color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f),
    background: Color = MaterialTheme.colorScheme.surfaceVariant,
    rotationAngle: Float = 0f
) {
    Box(modifier.background(background)) {
        Canvas(Modifier.fillMaxWidth().fillMaxHeight()) {
            val density = this@Canvas
            val dotRadius = with(density) { 2.3.dp.toPx() }
            val horizontalSpacing = with(density) { 16.dp.toPx() }
            val verticalSpacing = with(density) { 14.dp.toPx() }

            // Rotate the entire dot pattern around the center
            rotate(degrees = rotationAngle, pivot = center) {
                val cols =
                    (size.width / horizontalSpacing).toInt() + 4  // Extra dots for rotation coverage
                val rows = (size.height / verticalSpacing).toInt() + 4

                // Offset to center the grid
                val offsetX = -(cols * horizontalSpacing) / 2 + size.width / 2
                val offsetY = -(rows * verticalSpacing) / 2 + size.height / 2

                for (row in 0 until rows) {
                    for (col in 0 until cols) {
                        val x = offsetX + col * horizontalSpacing
                        val y = offsetY + row * verticalSpacing
                        drawCircle(
                            color = dotColor,
                            radius = dotRadius,
                            center = Offset(x, y),
                            style = Fill
                        )
                    }
                }
            }
        }
    }
}