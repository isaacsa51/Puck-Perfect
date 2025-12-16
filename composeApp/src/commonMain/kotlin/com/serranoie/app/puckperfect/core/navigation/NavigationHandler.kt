package com.serranoie.app.puckperfect.core.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.components.MainWrapper
import com.serranoie.app.puckperfect.feature.beans.BeansScreen
import com.serranoie.app.puckperfect.feature.shots.ShotsScreen

enum class PPNavScreen { BEANS, SHOTS, GRINDERS, MACHINES, RECIPES }

@Composable
fun NavigationHost() {
    var selectedTab by remember { mutableStateOf(PPNavScreen.BEANS) }

    val tabsList = listOf(
        PPNavScreen.BEANS to "Beans",
        PPNavScreen.SHOTS to "Shots",
        PPNavScreen.GRINDERS to "Grinders",
        PPNavScreen.MACHINES to "Machines",
        PPNavScreen.RECIPES to "Recipes"
    )
    MainWrapper(
        title = "Puck Perfect",
        tabs = {
            NavigationTabs(
                tabsList = tabsList,
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )
        },
        content = {
            when (selectedTab) {
                PPNavScreen.BEANS -> BeansScreen()
                PPNavScreen.SHOTS -> ShotsScreen()
                PPNavScreen.GRINDERS -> Text("Grinders Screen")
                PPNavScreen.MACHINES -> Text("Machines Screen")
                PPNavScreen.RECIPES -> Text("Recipes Screen")
            }
        }
    )
}
