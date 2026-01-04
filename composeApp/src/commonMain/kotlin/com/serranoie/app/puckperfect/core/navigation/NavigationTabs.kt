package com.serranoie.app.puckperfect.core.navigation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.components.TabAnimation
import com.serranoie.app.puckperfect.core.ui.theme.labelLargeCondensed
import com.serranoie.app.puckperfect.core.ui.theme.labelLargeExpressive

@Composable
fun NavigationTabs(
    tabsList: List<Pair<PPNavScreen, String>>,
    selectedTab: PPNavScreen,
    onTabSelected: (PPNavScreen) -> Unit
) {
    val selectedIndex = tabsList.indexOfFirst { it.first == selectedTab }

    Row(Modifier.horizontalScroll(rememberScrollState())) {
        tabsList.forEachIndexed { index, (tabEnum, labelStr) ->
            val isSelected = index == selectedIndex
            TabAnimation(
                index = index,
                selectedIndex = selectedIndex,
                onClick = { onTabSelected(tabEnum) },
                content = {
                    Text(
                        text = labelStr,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = if (isSelected) {
                            MaterialTheme.typography.labelLargeExpressive()
                        } else {
                            MaterialTheme.typography.labelLargeCondensed()
                        }
                    )
                }
            )
        }
    }
}
