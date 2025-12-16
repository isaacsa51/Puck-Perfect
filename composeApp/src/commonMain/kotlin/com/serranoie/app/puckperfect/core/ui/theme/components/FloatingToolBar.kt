package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FloatingToolBar(
    modifier: Modifier = Modifier,
    onReset: () -> Unit,
    onDismiss: () -> Unit,
    onClick: () -> Unit,
){
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(15))
            .background(MaterialTheme.colorScheme.surfaceContainerHigh)
            .padding(vertical = 6.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier.align(Alignment.CenterVertically),
            onClick = onReset
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Reset",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(Alignment.CenterVertically),
            shape = CircleShape,
            onClick = onClick,
            icon = { Icon(Icons.Rounded.Check, contentDescription = "Prepare") },
            text = { Text("Prepare") }
        )
    }
}

@Preview(showBackground = false)
@Composable
private fun FloatingToolBarPreview() {
    PuckPerfectTheme {
        FloatingToolBar(
            modifier = Modifier,
            onReset = {},
            onDismiss = {},
            onClick = {}
        )
    }
}