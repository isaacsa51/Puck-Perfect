package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.navigation.PPNavScreen
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FabMenu(
    screen: PPNavScreen,
    onMenuAction: (action: () -> Unit) -> Unit,
    onDismiss: () -> Unit,
    onNavigateToExtraction: () -> Unit = {},
    onNavigateToBeanCreation: () -> Unit = {},
) {
    // Overlay/fade in the menu
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable(onClick = onDismiss, indication = null, interactionSource = null),
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = true,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Row(
                modifier = Modifier.padding(bottom = 160.dp),
                horizontalArrangement = Arrangement.spacedBy(28.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                when (screen) {
                    PPNavScreen.BEANS -> {
                        FabActionButton(
                            label = "Save Beans",
                            icon = Icons.Filled.Save,
                            onClick = { onMenuAction { onNavigateToBeanCreation() } },
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                        FabActionButton(
                            label = "Prepare Espresso",
                            icon = Icons.Filled.Coffee,
                            onClick = { onMenuAction { onNavigateToExtraction() } },
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                    PPNavScreen.SHOTS -> {
                        FabActionButton(
                            label = "New Espresso\nLatest Beans",
                            icon = Icons.Filled.Coffee,
                            onClick = { onMenuAction { onNavigateToExtraction() } },
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                    PPNavScreen.GRINDERS -> {
                        FabActionButton(
                            label = "New Grinder",
                            icon = Icons.Filled.Grain,
                            onClick = { onMenuAction { /* Add grinder creation later */ } },
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                    PPNavScreen.MACHINES -> {
                        FabActionButton(
                            label = "Add Machine",
                            icon = Icons.Filled.PersonAdd,
                            onClick = { onMenuAction { /* Add machine creation later */ } },
                            color = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}

@Composable
private fun FabActionButton(label: String, icon: ImageVector, onClick: () -> Unit, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(64.dp)
                .background(color, shape = CircleShape)
        ) {
            Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.size(32.dp))
        }
        Spacer(modifier = Modifier.size(4.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = Color.White,
            modifier = Modifier
                .background(Color.Black.copy(alpha = 0.21f), shape = CircleShape)
                .padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

@Preview
@Composable
fun FabMenuPreview() {
    PuckPerfectTheme {
        FabMenu(
            screen = PPNavScreen.BEANS,
            onMenuAction = { it() },
            onDismiss = {},
        )
    }
}
