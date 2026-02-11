package com.serranoie.app.puckperfect.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.serranoie.app.puckperfect.core.ui.theme.components.FluidModifiersComparisonExample
import com.serranoie.app.puckperfect.core.ui.theme.components.FluidModifiersExample
import com.serranoie.app.puckperfect.core.ui.theme.components.FabMenu
import com.serranoie.app.puckperfect.core.ui.theme.components.MainWrapper
import com.serranoie.app.puckperfect.core.ui.theme.components.BottomBar
import com.serranoie.app.puckperfect.feature.beans.BeansScreen
import com.serranoie.app.puckperfect.feature.beans.creation.CreationBeansScreen
import com.serranoie.app.puckperfect.feature.extraction.ExtractionScreen
import com.serranoie.app.puckperfect.feature.shots.ShotsScreen
import androidx.compose.ui.window.Popup
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.unit.dp

enum class PPNavScreen { BEANS, SHOTS, GRINDERS, MACHINES, RECIPES, EXTRACTION, FLUID_MODIFIERS_DEMO, FLUID_MODIFIERS_COMPARISON }

private data class ExtractionPrefill(
    val grams: Float,
    val timeSeconds: Int,
    val initialStep: Int = 3,
)

@Composable
fun NavigationHost() {
    var selectedTab by remember { mutableStateOf(PPNavScreen.BEANS) }
    val navigationStack = remember { mutableStateListOf(PPNavScreen.BEANS) }
    var selectedBeanId by remember { mutableStateOf<Int?>(null) }
    var showBeanCreation by remember { mutableStateOf(false) }
    var fabMenuOpen by remember { mutableStateOf(false) }
    var extractionPrefill by remember { mutableStateOf<ExtractionPrefill?>(null) }

    val currentScreen = navigationStack.lastOrNull() ?: PPNavScreen.BEANS

    // Pop back stack helper
    fun popBackStack() {
        if (navigationStack.size > 1) {
            val removed = navigationStack.removeLast()
            if (removed == PPNavScreen.EXTRACTION) {
                extractionPrefill = null
            }
        }
    }

    // Navigate to screen helper
    fun navigateTo(screen: PPNavScreen) {
        navigationStack.add(screen)
    }

    fun navigateToExtraction(prefill: ExtractionPrefill? = null) {
        extractionPrefill = prefill
        navigateTo(PPNavScreen.EXTRACTION)
    }

    // Handle system back button (Android only)
    BackHandler(enabled = navigationStack.size > 1) {
        popBackStack()
    }

    val tabsList =
        listOf(
            PPNavScreen.BEANS to "Beans",
            PPNavScreen.SHOTS to "Shots",
            PPNavScreen.GRINDERS to "Grinders",
            PPNavScreen.MACHINES to "Machines",
            PPNavScreen.RECIPES to "Recipes",
        )

    Box(modifier = androidx.compose.ui.Modifier.fillMaxSize()) {
        when {
            currentScreen == PPNavScreen.FLUID_MODIFIERS_DEMO -> {
                FluidModifiersExample(
                    onBackClick = { popBackStack() },
                    onNavigateToComparison = { navigateTo(PPNavScreen.FLUID_MODIFIERS_COMPARISON) },
                )
            }
            currentScreen == PPNavScreen.FLUID_MODIFIERS_COMPARISON -> {
                FluidModifiersComparisonExample(
                    onBackClick = { popBackStack() },
                )
            }
            currentScreen == PPNavScreen.EXTRACTION -> {
                ExtractionScreen(
                    onBackClick = { popBackStack() },
                    onComplete = { grams, ratio, time, flavor ->
                        println(
                            """
                            Extraction Complete!
                            Bean ID: $selectedBeanId
                            Grams: $grams
                            Ratio: $ratio
                            Time: ${time}s
                            Flavor: $flavor
                            """.trimIndent(),
                        )
                    },
                    initialStep = extractionPrefill?.initialStep ?: 0,
                    initialGrams = extractionPrefill?.grams,
                    initialTimeSeconds = extractionPrefill?.timeSeconds,
                )
            }
            showBeanCreation -> {
                CreationBeansScreen(
                    onBackClick = { showBeanCreation = false },
                    onComplete = { name, roast, date, country, process -> },
                )
            }
            else -> {
                MainWrapper(
                    title = "Puck Perfect",
                    onSettingsClick = {
                        navigateTo(PPNavScreen.FLUID_MODIFIERS_DEMO)
                    },
                    tabs = {},
                    content = {
                        when (currentScreen) {
                            PPNavScreen.BEANS -> {
                                BeansScreen(onNavigateToExtraction = { beanId ->
                                    selectedBeanId = beanId
                                    navigateToExtraction()
                                }, onAddBean = { showBeanCreation = true })
                            }
                            PPNavScreen.SHOTS -> {
                                ShotsScreen(
                                    onSwipeToExtraction = { grams: Float, timeSeconds: Int ->
                                        navigateToExtraction(
                                            ExtractionPrefill(
                                                grams = grams,
                                                timeSeconds = timeSeconds,
                                                initialStep = 3,
                                            ),
                                        )
                                    },
                                )
                            }
                            PPNavScreen.GRINDERS -> { Text("Grinders Screen") }
                            PPNavScreen.MACHINES -> { Text("Machines Screen") }
                            PPNavScreen.RECIPES -> { Text("Recipes Screen") }
                            PPNavScreen.EXTRACTION -> {}
                            else -> {}
                        }
                    },
                )
            }
        }
        // Show floating bar/fab except on special screens
        val floatingBarScreens = setOf(
            PPNavScreen.BEANS,
            PPNavScreen.SHOTS,
            PPNavScreen.GRINDERS,
            PPNavScreen.MACHINES,
            PPNavScreen.RECIPES,
        )
        if (currentScreen in floatingBarScreens && !showBeanCreation) {
            BottomBar(
                navNum = tabsList.indexOfFirst { it.first == selectedTab },
                onNavSelected = { navIndex ->
                    val target = tabsList.getOrNull(navIndex)?.first ?: PPNavScreen.BEANS
                    selectedTab = target
                    navigationStack.clear()
                    navigationStack.add(target)
                },
                onFabClick = { fabMenuOpen = !fabMenuOpen },
                modifier = androidx.compose.ui.Modifier
                    .align(androidx.compose.ui.Alignment.BottomCenter)
            )
        }
        // Overlay menu always
        if (fabMenuOpen) {
            FabMenu(
                screen = selectedTab,
                onMenuAction = { action ->
                    fabMenuOpen = false
                    action()
                },
                onDismiss = { fabMenuOpen = false },
                onNavigateToExtraction = { navigateToExtraction() },
                onNavigateToBeanCreation = { showBeanCreation = true }
            )
        }
    }
}
