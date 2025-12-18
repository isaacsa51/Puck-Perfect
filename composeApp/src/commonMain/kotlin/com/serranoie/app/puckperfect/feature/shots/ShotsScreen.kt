package com.serranoie.app.puckperfect.feature.shots

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.ShotFlavor
import com.serranoie.app.puckperfect.core.ui.theme.components.ShotItem
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShotsScreen() {
    // Mock data
    data class Shot(
        val id: Int,
        val bean: String,
        val flavor: String,
        val grinder: String,
        val weight: String,
        val time: String
    )

    var shots by remember {
        mutableStateOf(
            listOf(
                Shot(1, "Ethiopia Sidamo", "Sweet", "Setting: 14", "32", "28s"),
                Shot(2, "Brazil Cerrado", "Acid", "Setting: 10", "34", "30s"),
                Shot(3, "Colombia Supremo", "Bitter", "Setting: 12", "30", "32s"),
                Shot(4, "Kenya AA", "Sweet", "Setting: 13", "31", "29s"),
                Shot(5, "Sumatra Mandheling", "Acid", "Setting: 9", "33", "27s"),
                Shot(6, "Costa Rica Tarrazu", "Bitter", "Setting: 11", "29", "33s"),
                Shot(7, "Guatemala Huehuetenango", "Sweet", "Setting: 15", "35", "30s"),
                Shot(8, "Tanzania Peaberry", "Acid", "Setting: 13", "36", "28s"),
                Shot(9, "Yemen Mocha", "Bitter", "Setting: 10", "30", "34s"),
                Shot(10, "Honduras Marcala", "Sweet", "Setting: 8", "32", "31s"),
                Shot(11, "Peru Chanchamayo", "Bitter", "Setting: 14", "31", "32s"),
                Shot(12, "El Salvador Pacamara", "Sweet", "Setting: 12", "3", "29s"),
                Shot(13, "Nicaragua Maragogype", "Acid", "Setting: 16", "34", "27s"),
                Shot(14, "Bolivia Caranavi", "Bitter", "Setting: 9", "36", "35s"),
                Shot(15, "India Monsooned Malabar", "Sweet", "Setting: 10", "30", "28s"),
                Shot(16, "Rwanda Bourbon", "Acid", "Setting: 13", "32", "31s"),
                Shot(17, "Panama Gesha", "Bitter", "Setting: 11", "34", "32s"),
                Shot(18, "Java Estate", "Sweet", "Setting: 15", "35", "33s"),
                Shot(19, "Mexico Altura", "Bitter", "Setting: 14", "33", "34s"),
                Shot(20, "Vietnam Robusta", "Acid", "Setting: 12", "36", "29s"),
            )
        )
    }
    val listState = rememberLazyListState()
    val expandedFab by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* Add shot action */ },
                expanded = expandedFab,
                icon = { Icon(Icons.Filled.Add, contentDescription = "Add Shot") },
                text = { Text(text = "New Espresso") },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = padding.calculateBottomPadding()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(top = 0.dp)
        ) {
            items(shots) { shot ->
                val flavorEnum = when (shot.flavor.uppercase()) {
                    "BITTER" -> ShotFlavor.BITTER
                    "ACID" -> ShotFlavor.ACID
                    else -> ShotFlavor.SWEET
                }
                val settingVal =
                    shot.grinder.substringAfter(":", "").trim().takeIf { it.isNotEmpty() }
                        ?: shot.grinder
                ShotItem(
                    beanName = shot.bean,
                    dateTime = "Today", // Replace with real date/time if you have it
                    grinder = "Grinder", // You can split this further if you have a grinder model name
                    grinderSetting = settingVal,
                    yield = shot.weight,
                    time = shot.time.trimEnd('s', 'S'),
                    flavor = flavorEnum,
                    underExtracted = flavorEnum != ShotFlavor.SWEET
                )
            }
        }
    }
}

@Preview
@Composable
private fun ShotsScreenPreview() {
    PuckPerfectTheme {
        ShotsScreen()
    }
}