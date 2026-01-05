package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.util.AnimatedFluidSpacer
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidAnimatedVisibility
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidSpacing
import com.serranoie.app.puckperfect.core.ui.theme.components.util.ResponsiveContainer
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidAnimateContentSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidHeight
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidMargin
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidPadding
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidScale
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSpace
import com.serranoie.app.puckperfect.core.ui.theme.components.util.isCompact
import com.serranoie.app.puckperfect.core.ui.theme.components.util.isExpanded
import com.serranoie.app.puckperfect.core.ui.theme.components.util.isMedium
import com.serranoie.app.puckperfect.core.ui.theme.components.util.rememberAnimatedFluidDp
import com.serranoie.app.puckperfect.core.ui.theme.components.util.responsiveRemMultiplier
import com.serranoie.app.puckperfect.core.ui.theme.components.util.responsiveWidth
import com.serranoie.app.puckperfect.core.ui.theme.components.util.scaledSp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Comparison example showing both Dp-based and Rem-based fluid modifiers
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FluidModifiersComparisonExample(onBackClick: () -> Unit = {}) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Fluid Modifiers Demo",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Text(
                text = "Compare Approaches",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Multiple approaches for fluid scaling. All adapt to font size changes!",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Dp-based approach
            ComparisonSection(
                title = "Dp-Based Approach",
                description = "Uses Dp values directly (e.g., 16.dp)"
            ) {
                DpBasedExample()
            }

            // Rem-based approach
            ComparisonSection(
                title = "Rem-Based Approach",
                description = "Uses Float values as rem units (e.g., 1f = 16sp)"
            ) {
                RemBasedExample()
            }

            // ScaledSp approach
            ComparisonSection(
                title = "ScaledSp Approach",
                description = "Direct sp-to-dp conversion (e.g., 16.scaledSp())"
            ) {
                ScaledSpExample()
            }

            // FluidSpacing system
            ComparisonSection(
                title = "FluidSpacing System",
                description = "Predefined spacing tokens"
            ) {
                FluidSpacingExample()
            }

            // Animations
            ComparisonSection(
                title = "Fluid Animations",
                description = "Animated components with fluid scaling"
            ) {
                AnimationsExample()
            }

            // Responsive container example
            ComparisonSection(
                title = "Responsive Container",
                description = "Adapts layout based on available width"
            ) {
                ResponsiveExample()
            }

            // Scale indicators
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Current Font Scale: ${fluidScale()}x",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    Text(
                        text = "Change your device font size to see both approaches adapt!",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }
            }
        }
    }
}

@Composable
private fun DpBasedExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(fluidSpace(8.dp))
    ) {
        // Using Dp values
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .fluidHeight(48.dp) // Dp-based
        ) {
            Icon(
                imageVector = Icons.Default.Coffee,
                contentDescription = null,
                modifier = Modifier.fluidSize(24.dp) // Dp-based
            )
            Text(
                "Button with fluidHeight(48.dp)",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fluidPadding(horizontal = 16.dp, vertical = 12.dp) // Dp-based
            ) {
                Text(
                    "Card with fluidPadding(16.dp, 12.dp)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@Composable
private fun RemBasedExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Using rem values (Float)
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .fluidHeight(3f) // Rem-based (3 rem = 48sp default)
        ) {
            Icon(
                imageVector = Icons.Default.Coffee,
                contentDescription = null,
                modifier = Modifier.fluidSize(1.5f) // Rem-based (1.5 rem = 24sp)
            )
            Text(
                "Button with fluidHeight(3f)",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fluidPadding(1f) // Rem-based (1 rem = 16sp)
            ) {
                Text(
                    "Card with fluidPadding(1f) - rem units",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        // Using fluidMargin
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fluidMargin(horizontal = 1f, vertical = 0.5f) // Rem-based margin
                .background(
                    MaterialTheme.colorScheme.tertiaryContainer,
                    RoundedCornerShape(8.dp)
                )
                .fluidPadding(1f)
        ) {
            Text(
                "Box with fluidMargin(1f, 0.5f)",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }
    }
}

@Composable
private fun ResponsiveExample() {
    ResponsiveContainer(
        modifier = Modifier.fillMaxWidth()
    ) {
        val screenSize = when {
            this.isCompact -> "Compact"
            this.isMedium -> "Medium"
            this.isExpanded -> "Expanded"
            else -> "Unknown"
        }
        val currentWidth = this.maxWidth
        val remMultiplier = this.responsiveRemMultiplier()

        Card(
            modifier = Modifier
                .responsiveWidth(
                    fraction = 1f,
                    minRem = 15f,  // Min 240sp
                    maxRem = 30f   // Max 480sp
                ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Screen Size: $screenSize",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Width: $currentWidth",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Rem Multiplier: ${remMultiplier}x",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "This card uses responsiveWidth() with min/max constraints",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun ComparisonSection(
    title: String,
    description: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            content()
        }
    }
}

@Composable
private fun ScaledSpExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.scaledSp()) // Using scaledSp directly
        ) {
            Icon(
                imageVector = Icons.Default.Coffee,
                contentDescription = null,
                modifier = Modifier.padding(24.scaledSp())
            )
            Text(
                "Button with 48.scaledSp()",
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.scaledSp()) // Direct sp to dp
            ) {
                Text(
                    "Card with padding(16.scaledSp())",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}

@Composable
private fun FluidSpacingExample() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Using FluidSpacing tokens:",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold
        )

        listOf(
            "extraSmall" to FluidSpacing.extraSmall(),
            "small" to FluidSpacing.small(),
            "medium" to FluidSpacing.medium(),
            "large" to FluidSpacing.large()
        ).forEach { (name, spacing) ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(spacing)
                        .fillMaxWidth(0.3f)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(4.dp)
                        )
                )
                Text(
                    name,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
private fun AnimationsExample() {
    var expanded by remember { mutableStateOf(false) }
    var spacerHeight by remember { mutableStateOf(1f) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = {
                expanded = !expanded
                spacerHeight = if (spacerHeight == 1f) 3f else 1f
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Toggle Animations")
        }

        // Animated spacer
        AnimatedFluidSpacer(targetHeight = spacerHeight)

        // Animated visibility
        FluidAnimatedVisibility(visible = expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fluidAnimateContentSize(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(FluidSpacing.medium()),
                    horizontalArrangement = Arrangement.spacedBy(FluidSpacing.small()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .padding(rememberAnimatedFluidDp(if (expanded) 2f else 1f))
                            .background(
                                MaterialTheme.colorScheme.primary,
                                CircleShape
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Text(
                        "Animated with FluidAnimatedVisibility!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun FluidModifiersComparisonPreview() {
    PuckPerfectTheme {
        FluidModifiersComparisonExample()
    }
}
