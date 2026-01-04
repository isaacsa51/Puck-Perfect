package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.titleLargeExpressive
import org.jetbrains.compose.ui.tooling.preview.Preview

data class ProcessType(val icon: ImageVector, val name: String, val description: String)

@Composable
fun BeanProcessMethodCard(
    processType: ProcessType, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    val backgroundColor =
        if (selected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surface.copy(
            alpha = 0.13f
        )
    val borderColor =
        if (selected) MaterialTheme.colorScheme.primary else Color.White.copy(alpha = 0.25f)
    val elevation = if (selected) 8.dp else 0.dp

    Box(
        modifier = modifier.shadow(elevation, RoundedCornerShape(16.dp), clip = false)
            .clip(RoundedCornerShape(16.dp)).background(backgroundColor)
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick() }, contentAlignment = Alignment.TopEnd
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 18.dp).fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(62.dp).background(
                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
                    shape = CircleShape
                ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = processType.icon,
                    contentDescription = processType.name,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = processType.name,
                style = MaterialTheme.typography.titleLargeExpressive(),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = processType.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun BeanProcessMethodCardList(
    processOptions: List<ProcessType>,
    selectedProcess: String,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier,
        userScrollEnabled = true,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp
    ) {
        items(processOptions) { process ->
            BeanProcessMethodCard(
                processType = process,
                selected = process.name == selectedProcess,
                onClick = { onSelect(process.name) })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBeanProcessMethodCard() {
    PuckPerfectTheme {
        BeanProcessMethodCard(
            processType = ProcessType(
                icon = Icons.Filled.WaterDrop,
                name = "Washed",
                description = "Clean & crisp acidity"
            ), selected = true, onClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBeanProcessMethodCardList() {
    val options = listOf(
        ProcessType(Icons.Filled.WaterDrop, "Washed", "Clean & crisp acidity"),
        ProcessType(Icons.Filled.WaterDrop, "Natural", "Sweet berry notes"),
        ProcessType(Icons.Filled.WaterDrop, "Fermented", "Funky & complex"),
        ProcessType(Icons.Filled.WaterDrop, "Honey", "Balanced & silky mouthfeel")
    )
    PuckPerfectTheme {
        Column(Modifier.background(MaterialTheme.colorScheme.tertiaryContainer)) {
            BeanProcessMethodCardList(
                processOptions = options, selectedProcess = "Natural", onSelect = {})
        }
    }
}
