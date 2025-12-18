package com.serranoie.app.puckperfect.core.ui.theme.components.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.serranoie.app.puckperfect.core.ui.theme.components.backgrounds.DotMatrixBackground
import com.serranoie.app.puckperfect.core.ui.theme.components.backgrounds.RotatedLinesBackground
import kotlin.random.Random

enum class BackgroundType {
    DOT_MATRIX,
    ROTATED_LINES
}

/**
 * Returns a random background type based on a seed.
 * Using a seed ensures the same input always produces the same background.
 */
fun getRandomBackgroundType(seed: Int): BackgroundType {
    val random = Random(seed)
    return if (random.nextBoolean()) BackgroundType.DOT_MATRIX else BackgroundType.ROTATED_LINES
}

/**
 * Renders a random background based on the provided seed.
 * The seed ensures consistency - the same seed will always render the same background.
 *
 * @param seed A unique identifier (e.g., item ID) to determine which background to show
 * @param modifier Modifier to be applied to the background
 */
@Composable
fun RandomBackground(
    seed: Int,
    modifier: Modifier = Modifier
) {
    val random = Random(seed)
    when (getRandomBackgroundType(seed)) {
        BackgroundType.DOT_MATRIX -> {
            DotMatrixBackground(
                modifier = modifier,
            )
        }

        BackgroundType.ROTATED_LINES -> {
            RotatedLinesBackground(
                modifier = modifier,
                angle = listOf(-45f, 45f, 0f, 90f).random(random),
                lineColor = MaterialTheme.colorScheme.tertiaryFixed.copy(alpha = 0.35f)
            )
        }
    }
}
