package com.serranoie.app.puckperfect.feature.beans

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.BeanItemCard
import com.serranoie.app.puckperfect.core.ui.theme.components.FloatingToolBar
import com.serranoie.app.puckperfect.core.ui.theme.components.LastExtractionSection
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidAnimatedVisibility
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidAnimateContentSize
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeansScreen(
    onNavigateToExtraction: (beanId: Int) -> Unit = {},
    onAddBean: () -> Unit = {},
) {
    data class Extraction(
        val dose: String,
        val yield: String,
        val ratio: String,
        val time: String,
        val grinderModel: String,
        val grinderSetting: String,
        val espressoMachine: String,
        val flavor: String, // SWEET, ACID, BITTER
    )

    data class Bean(
        val id: Int,
        val name: String,
        val country: String,
        val emoji: String,
        val lastExtraction: Extraction,
        val roast: String,
    )

    val beans =
        remember {
            listOf(
                Bean(
                    1,
                    "Geisha de Finca La...",
                    "Panama",
                    "☕️",
                    Extraction(
                        "18.5",
                        "38.2",
                        "1:2.1",
                        "29",
                        "Niche Zero",
                        "15",
                        "La Marzocco Linea Mini",
                        "SWEET",
                    ),
                    "Medium Roast",
                ),
                Bean(
                    2,
                    "Ethiopia Guji",
                    "Ethiopia",
                    "🫘",
                    Extraction(
                        "19.0",
                        "39.5",
                        "1:2.1",
                        "28",
                        "Commandante",
                        "18",
                        "Lelit Bianca",
                        "ACID",
                    ),
                    "Light Roast",
                ),
                Bean(
                    3,
                    "Colombia Supremo",
                    "Colombia",
                    "☕️",
                    Extraction(
                        "18.0",
                        "36.2",
                        "1:2.0",
                        "27",
                        "DF64",
                        "13",
                        "Breville Barista",
                        "BITTER",
                    ),
                    "Medium Roast",
                ),
                Bean(
                    4,
                    "Kenya AA",
                    "Kenya",
                    "🫘",
                    Extraction(
                        "19.2",
                        "40.1",
                        "1:2.1",
                        "30",
                        "Eureka Mignon",
                        "14",
                        "Rocket Appartamento",
                        "ACID",
                    ),
                    "Light Roast",
                ),
                Bean(
                    5,
                    "Brazil Cerrado",
                    "Brazil",
                    "☕️",
                    Extraction(
                        "17.7",
                        "35.0",
                        "1:2.0",
                        "28",
                        "Mazzer Mini",
                        "11",
                        "Gaggia Classic",
                        "SWEET",
                    ),
                    "Medium Roast",
                ),
                Bean(
                    6,
                    "Guatemala Antigua",
                    "Guatemala",
                    "🫘",
                    Extraction(
                        "18.6",
                        "37.2",
                        "1:2.0",
                        "29",
                        "Niche Zero",
                        "13",
                        "Lelit Mara X",
                        "SWEET",
                    ),
                    "Medium Roast",
                ),
                Bean(
                    7,
                    "Sumatra Mandheling",
                    "Indonesia",
                    "☕️",
                    Extraction(
                        "18.2",
                        "36.4",
                        "1:2.0",
                        "27",
                        "DF64",
                        "15",
                        "ECM Synchronika",
                        "BITTER",
                    ),
                    "Dark Roast",
                ),
                Bean(
                    8,
                    "Costa Rica Tarrazu",
                    "Costa Rica",
                    "🫘",
                    Extraction(
                        "19.1",
                        "39.0",
                        "1:2.1",
                        "30",
                        "Eureka Mignon",
                        "14",
                        "Profitec Pro 700",
                        "SWEET",
                    ),
                    "Medium Roast",
                ),
                Bean(
                    9,
                    "Peru Cajamarca",
                    "Peru",
                    "☕️",
                    Extraction(
                        "18.7",
                        "37.5",
                        "1:2.0",
                        "29",
                        "Commandante",
                        "12",
                        "La Spaziale S1",
                        "BITTER",
                    ),
                    "Medium Roast",
                ),
                Bean(
                    10,
                    "Yemen Mocha",
                    "Yemen",
                    "🫘",
                    Extraction(
                        "19.2",
                        "39.2",
                        "1:2.05",
                        "31",
                        "Mazzer Mini",
                        "17",
                        "Linea Mini",
                        "BITTER",
                    ),
                    "Dark Roast",
                ),
                Bean(
                    11,
                    "Rwanda Bourbon",
                    "Rwanda",
                    "☕️",
                    Extraction("18.3", "36.7", "1:2.0", "28", "DF64", "8", "Lelit Bianca", "ACID"),
                    "Light Roast",
                ),
                Bean(
                    12,
                    "Tanzania Peaberry",
                    "Tanzania",
                    "🫘",
                    Extraction(
                        "18.9",
                        "38.0",
                        "1:2.0",
                        "27",
                        "Eureka Mignon",
                        "12",
                        "Breville Dual Boiler",
                        "ACID",
                    ),
                    "Light Roast",
                ),
                Bean(
                    13,
                    "El Salvador Pacamara",
                    "El Salvador",
                    "☕️",
                    Extraction(
                        "17.8",
                        "35.7",
                        "1:2.0",
                        "26",
                        "Commandante",
                        "9",
                        "Quick Mill Andreja",
                        "SWEET",
                    ),
                    "Light Roast",
                ),
                Bean(
                    14,
                    "Mexico Altura",
                    "Mexico",
                    "🫘",
                    Extraction(
                        "19.3",
                        "41.2",
                        "1:2.1",
                        "32",
                        "Niche Zero",
                        "10",
                        "Rocket Giotto Evoluzione",
                        "SWEET",
                    ),
                    "Medium Roast",
                ),
                Bean(
                    15,
                    "Java Estate",
                    "Indonesia",
                    "☕️",
                    Extraction(
                        "18.6",
                        "37.8",
                        "1:2.0",
                        "29",
                        "Mazzer Mini",
                        "13",
                        "Bezzera BZ10",
                        "BITTER",
                    ),
                    "Dark Roast",
                ),
            )
        }

    val lastUsedBeanId = 3

    var selectedId by remember { mutableStateOf<Int?>(null) }
    var expandedBeanId by remember { mutableStateOf<Int?>(lastUsedBeanId) }
    val snackbarHostState = remember { SnackbarHostState() }

    val sortedBeans =
        remember(beans, lastUsedBeanId) {
            buildList {
                beans.find { it.id == lastUsedBeanId }?.let { add(it) }
                beans.filter { it.id != lastUsedBeanId }.forEach { add(it) }
            }
        }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingToolBar(
                onReset = { onAddBean() },
                onDismiss = { /* Dismiss action */ },
                onClick = {
                    selectedId?.let { beanId ->
                        onNavigateToExtraction(beanId)
                    }
                },
                enabled = selectedId != null,
            )
        },
        bottomBar = {},
        floatingActionButtonPosition = FabPosition.Center,
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(bottom = padding.calculateBottomPadding()),
            contentPadding = PaddingValues(top = 0.dp),
        ) {
            items(sortedBeans, key = { it.id }) { bean ->
                val selected =
                    selectedId == bean.id || (selectedId == null && bean.id == lastUsedBeanId)
                val expanded = expandedBeanId == bean.id

                FluidAnimatedVisibility(
                    visible = true,
                    modifier = Modifier.fluidAnimateContentSize(),
                ) {
                    BeanItemCard(
                        selected = selected,
                        onClick = { selectedId = bean.id },
                        name = bean.name,
                        origin = bean.country,
                        roast = bean.roast,
                        showExtraction = expanded,
                        onToggleExtraction = {
                            expandedBeanId = if (expandedBeanId == bean.id) null else bean.id
                        },
                        lastExtraction =
                            LastExtractionSection(
                                dose = bean.lastExtraction.dose,
                                yield = bean.lastExtraction.yield,
                                ratio = bean.lastExtraction.ratio,
                                time = bean.lastExtraction.time,
                                grinderModel = bean.lastExtraction.grinderModel,
                                grinderSetting = bean.lastExtraction.grinderSetting,
                                espressoMachine = bean.lastExtraction.espressoMachine,
                            ),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun BeansScreenPreview() {
    PuckPerfectTheme {
        BeansScreen(
            onNavigateToExtraction = { beanId ->
                println("Navigate to extraction with bean ID: $beanId")
            },
            onAddBean = { println("Add bean") },
        )
    }
}
