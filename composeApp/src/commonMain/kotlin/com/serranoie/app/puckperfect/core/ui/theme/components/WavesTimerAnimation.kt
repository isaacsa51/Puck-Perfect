package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WavesTimerAnimation(
    remainingSeconds: Int,
    progress: Float,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f, fill = true)) {
            var timerDurationInMillis by rememberSaveable { mutableStateOf(60000) }
            var timerState by remember { mutableStateOf(TimerState.Stopped) }

            val timerProgress by timerProgressAsState(
                timerState = timerState, timerDurationInMillis = timerDurationInMillis
            )

            val remainingSeconds =
                (timerDurationInMillis / 1000) - ((timerDurationInMillis / 1000) * timerProgress).toInt()

            LaunchedEffect(timerProgress == 0f) {
                if (timerProgress == 0f) {
                    timerState = TimerState.Stopped
                }
            }

            WavesTimerAnimation(
                remainingSeconds = remainingSeconds, progress = 1 - timerProgress
            )
        }
    }
}

private enum class TimerState {
    Started, Stopped, Paused
}

@Composable
private fun timerProgressAsState(
    timerState: TimerState, timerDurationInMillis: Int
): State<Float> {
    val animatable = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(timerState) {
        val animateToStartOrStopState =
            timerState == TimerState.Stopped || (timerState == TimerState.Started && animatable.value == 0f)

        if (animateToStartOrStopState) {
            animatable.animateTo(
                targetValue = if (timerState == TimerState.Started) 1f else 0f,
                animationSpec = spring(stiffness = 100f)
            )
        }

        if (timerState == TimerState.Started) {
            animatable.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = timerDurationInMillis, easing = LinearEasing
                ),
            )
        }
    }

    return remember(animatable) {
        derivedStateOf { animatable.value }
    }
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

@Preview(showBackground = true)
@Composable
fun WavesTimerAnimationPreview() {
    PuckPerfectTheme {
        WavesTimerAnimation(remainingSeconds = 25, progress = 0.5f)
    }
}