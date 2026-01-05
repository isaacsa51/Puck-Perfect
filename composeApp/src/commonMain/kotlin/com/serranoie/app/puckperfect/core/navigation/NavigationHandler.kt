package com.serranoie.app.puckperfect.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.serranoie.app.puckperfect.core.ui.theme.components.FluidModifiersComparisonExample
import com.serranoie.app.puckperfect.core.ui.theme.components.FluidModifiersExample
import com.serranoie.app.puckperfect.core.ui.theme.components.MainWrapper
import com.serranoie.app.puckperfect.feature.beans.BeansScreen
import com.serranoie.app.puckperfect.feature.beans.creation.CreationBeansScreen
import com.serranoie.app.puckperfect.feature.extraction.ExtractionScreen
import com.serranoie.app.puckperfect.feature.shots.ShotsScreen

enum class PPNavScreen { BEANS, SHOTS, GRINDERS, MACHINES, RECIPES, EXTRACTION, FLUID_MODIFIERS_DEMO, FLUID_MODIFIERS_COMPARISON }

@Composable
fun NavigationHost() {
    var selectedTab by remember { mutableStateOf(PPNavScreen.BEANS) }
    val navigationStack = remember { mutableStateListOf(PPNavScreen.BEANS) }
    var selectedBeanId by remember { mutableStateOf<Int?>(null) }
    var showBeanCreation by remember { mutableStateOf(false) }

    val currentScreen = navigationStack.lastOrNull() ?: PPNavScreen.BEANS

    // Pop back stack helper
    fun popBackStack() {
        if (navigationStack.size > 1) {
            navigationStack.removeLast()
        }
    }

    // Navigate to screen helper
    fun navigateTo(screen: PPNavScreen) {
        navigationStack.add(screen)
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

    if (currentScreen == PPNavScreen.FLUID_MODIFIERS_DEMO) {
        FluidModifiersExample(
            onBackClick = { popBackStack() },
            onNavigateToComparison = { navigateTo(PPNavScreen.FLUID_MODIFIERS_COMPARISON) },
        )
        return
    }

    if (currentScreen == PPNavScreen.FLUID_MODIFIERS_COMPARISON) {
        FluidModifiersComparisonExample(
            onBackClick = { popBackStack() },
        )
        return
    }

    if (currentScreen == PPNavScreen.EXTRACTION) {
        ExtractionScreen(onBackClick = {}, onComplete = { grams, ratio, time, flavor ->
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
        })
    } else if (showBeanCreation) {
        CreationBeansScreen(
            onBackClick = { showBeanCreation = false },
            onComplete = { name, roast, date, country, process -> },
        )
    } else {
        MainWrapper(
            title = "Puck Perfect",
            onSettingsClick = {
                navigateTo(PPNavScreen.FLUID_MODIFIERS_DEMO)
            },
            tabs = {
                NavigationTabs(
                    tabsList = tabsList,
                    selectedTab = selectedTab,
                    onTabSelected = { tab ->
                        selectedTab = tab
                        navigationStack.clear()
                        navigationStack.add(tab)
                    }
                )
            },
            content = {
                when (currentScreen) {
                    PPNavScreen.BEANS -> {
                        BeansScreen(onNavigateToExtraction = { beanId ->
                            selectedBeanId = beanId
                            navigateTo(PPNavScreen.EXTRACTION)
                        }, onAddBean = { showBeanCreation = true })
                    }

                    PPNavScreen.SHOTS -> {
                        ShotsScreen()
                    }

                    PPNavScreen.GRINDERS -> {
                        Text("Grinders Screen")
                    }

                    PPNavScreen.MACHINES -> {
                        Text("Machines Screen")
                    }

                    PPNavScreen.RECIPES -> {
                        Text("Recipes Screen")
                    }

                    PPNavScreen.EXTRACTION -> {
                        // This case is handled above, outside MainWrapper
                    }

                    else -> {}
                }
            },
        )
    }
}
