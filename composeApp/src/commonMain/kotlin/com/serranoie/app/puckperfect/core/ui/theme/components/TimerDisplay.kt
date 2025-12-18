package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.headlineMediumCondensed
import com.serranoie.app.puckperfect.core.ui.theme.headlineSmallExpressive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.PI
import kotlin.math.sin

@Composable
fun TimerDisplay(
    remainingSeconds: Int,
    progress: Float, // 0 = not started, 1 = completed
    backgroundColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    showCompletedMessage: Boolean = true,
    isRunning: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        // Wave animation filling from bottom to top
        TimerWaveAnimation(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.onPrimary,
            progress = progress,
            isAnimating = isRunning
        )

        // Timer text centered on top of waves
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp)
        ) {
            Text(
                text = formatTime(remainingSeconds),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmallExpressive(),
                fontSize = 120.sp,
                fontWeight = FontWeight.Black,
                lineHeight = 120.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (showCompletedMessage) {
                Text(
                    text = if (remainingSeconds > 0) "seconds remaining" else "Completed!",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineMediumCondensed(),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun TimerWaveAnimation(
    modifier: Modifier = Modifier,
    color: Color,
    progress: Float,
    isAnimating: Boolean
) {
    BoxWithConstraints(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        val constraintsWidth = maxWidth
        val constraintsHeight = maxHeight
        val density = LocalDensity.current

        val wavesShader by produceState<Shader?>(
            initialValue = null, constraintsHeight, constraintsWidth, color, density
        ) {
            value = withContext(Dispatchers.Default) {
                createTimerWavesShader(
                    width = with(density) { constraintsWidth.roundToPx() },
                    height = with(density) { constraintsHeight.roundToPx() },
                    color = color
                )
            }
        }

        if (progress > 0f && wavesShader != null) {
            TimerWavesOnCanvas(
                shader = wavesShader!!,
                progress = progress,
                isAnimating = isAnimating
            )
        }
    }
}

@Composable
private fun TimerWavesOnCanvas(
    shader: Shader,
    progress: Float,
    isAnimating: Boolean
) {
    val paint = remember(shader) {
        Paint().apply {
            isAntiAlias = true
            this.shader = shader
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(
            durationMillis = 1000,
            easing = LinearEasing
        ),
        label = "ProgressAnimation"
    )

    val infiniteTransition = rememberInfiniteTransition(label = "WaveShift")
    val waveShiftRatio by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing)
        ),
        label = "WaveShift"
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawIntoCanvas { canvas ->
            val height = size.height
            val width = size.width

            canvas.save()

            // Calculate the fill height from bottom based on animated progress
            val fillHeight = height * animatedProgress

            // Position the canvas at the bottom
            // The Y translation positions where the wave pattern starts
            canvas.translate(
                waveShiftRatio * width,  // Horizontal wave shift animation
                height - fillHeight  // Start from bottom, move up as progress increases
            )

            // Draw the rectangle that shows the wave pattern
            // This rectangle grows from bottom (fillHeight increases with progress)
            canvas.drawRect(
                left = -waveShiftRatio * width,
                top = 0f,  // Top of the visible wave area
                right = width + (1f - waveShiftRatio) * width,
                bottom = fillHeight,  // Height of the fill increases with progress
                paint = paint
            )

            canvas.restore()
        }
    }
}

@Stable
private fun createTimerWavesShader(width: Int, height: Int, color: Color): Shader {
    val angularFrequency = 2f * PI / width
    val amplitude = height * 0.03f

    val bitmap = ImageBitmap(width = width, height = height, ImageBitmapConfig.Argb8888)
    val canvas = Canvas(bitmap)

    val wavePaint = Paint().apply {
        strokeWidth = 2f
        isAntiAlias = true
    }

    val waveY = FloatArray(size = width + 1)

    // First wave (lighter, background wave)
    wavePaint.color = color.copy(alpha = 0.5f)
    for (beginX in 0..width) {
        val wx = beginX * angularFrequency
        val beginY = amplitude + amplitude * sin(wx).toFloat()
        canvas.drawLine(
            p1 = Offset(x = beginX.toFloat(), y = beginY),
            p2 = Offset(x = beginX.toFloat(), y = (height + 1).toFloat()),
            paint = wavePaint
        )
        waveY[beginX] = beginY
    }

    // Second wave (darker, shifted for depth effect)
    wavePaint.color = color.copy(alpha = 0.8f)
    val endX = width + 1
    val waveToShift = width / 4
    for (beginX in 0..width) {
        canvas.drawLine(
            p1 = Offset(x = beginX.toFloat(), y = waveY[(beginX + waveToShift).rem(endX)]),
            p2 = Offset(x = beginX.toFloat(), y = (height + 1).toFloat()),
            paint = wavePaint
        )
    }

    return ImageShader(image = bitmap, tileModeX = TileMode.Repeated, tileModeY = TileMode.Clamp)
}

private fun formatTime(seconds: Int): String {
    return if (seconds < 60) {
        seconds.toString()
    } else {
        val mins = seconds / 60
        val secs = seconds % 60

        "$mins:${secs.toString().padStart(2, '0')}"
    }
}

@Preview
@Composable
private fun TimerPreview() {
    PuckPerfectTheme {
        // Show wave half filled, 20 seconds left
        TimerDisplay(remainingSeconds = 20, progress = 0.4f)
    }
}