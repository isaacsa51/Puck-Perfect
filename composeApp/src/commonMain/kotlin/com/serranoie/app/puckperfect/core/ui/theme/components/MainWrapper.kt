package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidSpacing
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidAnimateContentSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidPadding
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.scaledSp
import com.serranoie.app.puckperfect.core.ui.theme.displaySmallExpressive
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainWrapper(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onSettingsClick: () -> Unit = {},
    title: String,
    tabs: @Composable () -> Unit = {},
) {
    val gradientColors =
        listOf(
            MaterialTheme.colorScheme.tertiaryContainer,
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.surface,
        )
    val gradientBrush = Brush.verticalGradient(colors = gradientColors)

    Box(
        modifier = modifier.fillMaxSize().background(brush = gradientBrush),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(FluidSpacing.extraSmall()),
        ) {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.displaySmallExpressive(),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fluidAnimateContentSize(),
                    )
                },
                actions = {
                    IconButton(
                        onClick = { onSettingsClick() },
                        modifier = Modifier.fluidAnimateContentSize(),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.fluidSize(16.dp),
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                modifier = Modifier.fluidAnimateContentSize(),
            )

            tabs()

            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .fluidPadding(horizontal = 8.dp)
                        .fluidAnimateContentSize(),
                color = MaterialTheme.colorScheme.surface,
                shape =
                    RoundedCornerShape(
                        topStart = 16.scaledSp(),
                        topEnd = 16.scaledSp(),
                    ),
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
        MainWrapper(title = "Buenos Dias", onSettingsClick = {}, tabs = {
            Row(
                modifier = Modifier.fluidPadding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(FluidSpacing.extraSmall()),
            ) {
                TabAnimation(
                    index = 0,
                    selectedIndex = selectedTab,
                    onClick = {},
                    content = { Text("Tab A") },
                )
                TabAnimation(
                    index = 1,
                    selectedIndex = selectedTab,
                    onClick = {},
                    content = { Text("Tab B") },
                )
            }
        }, content = {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .fluidPadding(horizontal = 16.dp)
                        .background(MaterialTheme.colorScheme.surface),
            ) {
                Text("MainWrapper Preview", color = MaterialTheme.colorScheme.onSurface)
            }
        })
    }
}
