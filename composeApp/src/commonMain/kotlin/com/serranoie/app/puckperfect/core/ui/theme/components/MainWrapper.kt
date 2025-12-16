@file:OptIn(ExperimentalMaterial3Api::class)

package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MainWrapper(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onSettingsClick: () -> Unit = {},
    title: String,
    tabs: @Composable () -> Unit = {},
) {
    val gradientColors = listOf(
        MaterialTheme.colorScheme.tertiaryContainer,
        MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.surface
    )
    val gradientBrush = Brush.verticalGradient(colors = gradientColors)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = gradientBrush)
    ) {
        Column(Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 40.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(
                        onClick = { onSettingsClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )

            tabs()

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun MainWrapperPreview() {
    PuckPerfectTheme {
        val selectedTab = 0
        MainWrapper(
            title = "Buenos Dias",
            onSettingsClick = {},
            tabs = {
                // Render two pretend tabs for preview using mocked TabAnimation
                Row(Modifier.padding(8.dp)) {
                    TabAnimation(
                        index = 0,
                        selectedIndex = selectedTab,
                        onClick = {},
                        content = { Text("Tab A", fontWeight = FontWeight.Bold) }
                    )
                    TabAnimation(
                        index = 1,
                        selectedIndex = selectedTab,
                        onClick = {},
                        content = { Text("Tab B", fontWeight = FontWeight.Bold) }
                    )
                }
            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .background(MaterialTheme.colorScheme.surface),
                ) {
                    Text("MainWrapper Preview", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        )
    }
}
