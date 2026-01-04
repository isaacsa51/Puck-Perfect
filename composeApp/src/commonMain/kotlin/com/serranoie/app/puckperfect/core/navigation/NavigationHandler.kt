package com.serranoie.app.puckperfect.core.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.serranoie.app.puckperfect.core.ui.theme.components.MainWrapper
import com.serranoie.app.puckperfect.feature.beans.BeansScreen
import com.serranoie.app.puckperfect.feature.beans.creation.CreationBeansScreen
import com.serranoie.app.puckperfect.feature.extraction.ExtractionScreen
import com.serranoie.app.puckperfect.feature.shots.ShotsScreen

enum class PPNavScreen { BEANS, SHOTS, GRINDERS, MACHINES, RECIPES, EXTRACTION }

@Composable
fun NavigationHost() {
    var selectedTab by remember { mutableStateOf(PPNavScreen.BEANS) }
    var currentScreen by remember { mutableStateOf(PPNavScreen.BEANS) }
    var selectedBeanId by remember { mutableStateOf<Int?>(null) }
    var showBeanCreation by remember { mutableStateOf(false) }

    val tabsList = listOf(
        PPNavScreen.BEANS to "Beans",
        PPNavScreen.SHOTS to "Shots",
        PPNavScreen.GRINDERS to "Grinders",
        PPNavScreen.MACHINES to "Machines",
        PPNavScreen.RECIPES to "Recipes",
    )
    // Show MainWrapper only for tab screens, not for extraction
    if (currentScreen == PPNavScreen.EXTRACTION) {
        // Show extraction screen without tabs
        ExtractionScreen(onBackClick = {
            currentScreen = PPNavScreen.BEANS
            selectedTab = PPNavScreen.BEANS
        }, onComplete = { grams, ratio, time, flavor ->
            // Handle extraction completion
            // You can save the data here or pass it to a ViewModel
            println(
                """
                    Extraction Complete!
                    Bean ID: $selectedBeanId
                    Grams: $grams
                    Ratio: $ratio
                    Time: ${time}s
                    Flavor: $flavor
                """.trimIndent()
            )
        })
    } else if (showBeanCreation) {
        CreationBeansScreen(
            onBackClick = { showBeanCreation = false },
            onComplete = { name, roast, date, country, process -> })
    } else {
        MainWrapper(title = "Puck Perfect", tabs = {
            NavigationTabs(
                tabsList = tabsList, selectedTab = selectedTab, onTabSelected = {
                    selectedTab = it
                    currentScreen = it
                })
        }, content = {
            when (currentScreen) {
                PPNavScreen.BEANS -> BeansScreen(onNavigateToExtraction = { beanId ->
                    selectedBeanId = beanId
                    currentScreen = PPNavScreen.EXTRACTION
                }, onAddBean = { showBeanCreation = true })

                PPNavScreen.SHOTS -> ShotsScreen()
                PPNavScreen.GRINDERS -> Text("Grinders Screen")
                PPNavScreen.MACHINES -> Text("Machines Screen")
                PPNavScreen.RECIPES -> Text("Recipes Screen")
                PPNavScreen.EXTRACTION -> {
                    // This case is handled above, outside MainWrapper
                }

                else -> {}
            }
        })
    }
}
