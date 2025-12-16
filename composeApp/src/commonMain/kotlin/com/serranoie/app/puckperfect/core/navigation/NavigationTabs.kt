package com.serranoie.app.puckperfect.core.navigation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.components.TabAnimation

@Composable
fun NavigationTabs(
    tabsList: List<Pair<PPNavScreen, String>>,
    selectedTab: PPNavScreen,
    onTabSelected: (PPNavScreen) -> Unit
) {
    val selectedIndex = tabsList.indexOfFirst { it.first == selectedTab }

    Row(Modifier.horizontalScroll(rememberScrollState())) {
        tabsList.forEachIndexed { index, (tabEnum, labelStr) ->
            TabAnimation(
                index = index,
                selectedIndex = selectedIndex,
                onClick = { onTabSelected(tabEnum) },
                content = {
                    Text(
                        text = labelStr,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            )
        }
    }
}
