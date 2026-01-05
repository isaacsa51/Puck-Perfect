package com.serranoie.app.puckperfect.core.navigation

import androidx.compose.runtime.Composable

@Composable
actual fun BackHandler(enabled: Boolean, onBack: () -> Unit) {
    // iOS doesn't have a system back button like Android
    // Back navigation is handled through UI elements (back buttons in nav bar)
    // This is a no-op on iOS
}
