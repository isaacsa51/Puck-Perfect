package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.ui.geometry.Offset
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun QuestionarieWrapper(
    modifier: Modifier = Modifier,
    optionLabels: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit,
    title: String,
    onPrimaryButton: () -> Unit,
    onSecondaryButton: () -> Unit,
    primaryButtonText: String,
    secondaryButtonText: String,
    content: @Composable BoxScope.() -> Unit
) {
    BoxWithConstraints(modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        val topHeight = maxHeight * 0.65f
        val bottomHeight = maxHeight - topHeight
        Column(modifier = Modifier.fillMaxSize()) {
            Surface(
                shape = RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp),
                color = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .height(topHeight)
                    .fillMaxWidth()
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    content()
                }
            }
            Surface(
                shape = RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp),
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier
                    .height(bottomHeight)
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        OutlinedButton(
                            onClick = onSecondaryButton,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(secondaryButtonText)
                        }
                        Spacer(Modifier.width(16.dp))
                        Button(
                            onClick = onPrimaryButton,
                            modifier = Modifier.weight(2f),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(primaryButtonText)
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuestionariePreview() {
    MaterialTheme {
        var idx by remember { mutableStateOf(1) }
        QuestionarieWrapper(
            optionLabels = listOf("Sad", "Happy", "Angry"),
            selectedIndex = idx,
            onOptionSelected = { idx = it },
            title = "How do you feel today?",
            onPrimaryButton = {},
            onSecondaryButton = {},
            primaryButtonText = "Next",
            secondaryButtonText = "Skip",
        ) {

        }
    }
}
