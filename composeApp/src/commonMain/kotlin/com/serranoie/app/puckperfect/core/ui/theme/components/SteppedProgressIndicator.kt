package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SteppedProgressIndicator(
    currentStep: Int, totalSteps: Int, modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(totalSteps) { index ->
            Box(
                modifier = Modifier.weight(1f).height(4.dp).background(
                    color = if (index <= currentStep) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }, shape = RoundedCornerShape(2.dp)
                )
            )
        }
    }
}