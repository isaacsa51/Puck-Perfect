package com.serranoie.app.puckperfect

import androidx.compose.runtime.Composable
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.serranoie.app.puckperfect.core.navigation.NavigationHost
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme

@Composable
@Preview
fun App() {
    PuckPerfectTheme {
        NavigationHost()
    }
}