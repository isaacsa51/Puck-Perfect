package com.serranoie.app.puckperfect.core.navigation

import androidx.compose.runtime.Composable

/**
 * Platform-specific back handler for navigation
 * Intercepts the system back button press when enabled
 */
@Composable
expect fun BackHandler(enabled: Boolean = true, onBack: () -> Unit)
