package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.util.CompactSpacing
import com.serranoie.app.puckperfect.core.ui.theme.components.util.GOLDEN_RATIO
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidSpacing
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidTypography
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidHeight
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidHeightCapped
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidPadding
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidScale
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSpace
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidWidth
import com.serranoie.app.puckperfect.core.ui.theme.components.util.goldenRatioPadding
import com.serranoie.app.puckperfect.core.ui.theme.components.util.goldenRatioSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.modularScale
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Example composable demonstrating Fluid modifier usage
 * This screen shows how fluid modifiers scale with font settings
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FluidModifiersExample(
    onBackClick: () -> Unit = {},
    onNavigateToComparison: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Fluid Modifiers",
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
                .fluidPadding(16.dp),
            verticalArrangement = Arrangement.spacedBy(fluidSpace(16.dp))
        ) {
            // Header
            Text(
                text = "Golden Ratio Fluid System",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fluidPadding(vertical = 8.dp)
            )

            Text(
                text = "This system uses the Golden Ratio (φ ≈ 1.618) for harmonious proportions. Typography, spacing, and shapes all scale following φ, creating optical symmetry and visual balance.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Compare button
            Button(
                onClick = onNavigateToComparison,
                modifier = Modifier
                    .fillMaxWidth()
                    .fluidHeight(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Compare,
                    contentDescription = null,
                    modifier = Modifier.fluidSize(24.dp)
                )
                Spacer(modifier = Modifier.fluidWidth(8.dp))
                Text(
                    "Compare Differences",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Golden Ratio info card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fluidPadding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(FluidSpacing.small())
                ) {
                    Text(
                        text = "The Golden Ratio: φ ≈ 1.618",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Text(
                        text = "Found in nature, art, and architecture. Each element is φ times larger than the previous, creating pleasing visual harmony.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            // Example 1: Typography Scale
            ExampleSection(
                title = "Golden Ratio Typography",
                description = "Each size is φ times the previous: body (16sp) → subtitle (~26sp) → title (~42sp)"
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(FluidSpacing.small())
                ) {
                    Text(
                        text = "Body Text (16sp)",
                        fontSize = FluidTypography.body,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Subtitle Text (~26sp)",
                        fontSize = FluidTypography.subtitle,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Title (~42sp)",
                        fontSize = FluidTypography.title,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            // Example 2: Spacing Scale Comparison
            ExampleSection(
                title = "FluidSpacing vs CompactSpacing",
                description = "φ-based spacing for breathing room vs linear spacing for dense layouts"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(FluidSpacing.medium())
                ) {
                    // FluidSpacing (Golden Ratio)
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fluidPadding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(FluidSpacing.small())
                        ) {
                            Text(
                                "FluidSpacing",
                                fontSize = FluidTypography.small,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                "~10sp gap",
                                fontSize = FluidTypography.tiny,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                "(1/φ)",
                                fontSize = FluidTypography.tiny,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                    // CompactSpacing (Linear)
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fluidPadding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(CompactSpacing.small())
                        ) {
                            Text(
                                "CompactSpacing",
                                fontSize = FluidTypography.small,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                "8sp gap",
                                fontSize = FluidTypography.tiny,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                "(0.5rem)",
                                fontSize = FluidTypography.tiny,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            // NEW: useGoldenRatio parameter demo
            ExampleSection(
                title = "Optional Golden Ratio Scaling",
                description = "All fluid modifiers support useGoldenRatio=true parameter"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(FluidSpacing.medium())
                ) {
                    // Without golden ratio
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fluidPadding(8.dp, useGoldenRatio = false),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(FluidSpacing.extraSmall())
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.fluidSize(24.dp, useGoldenRatio = false),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                "Normal",
                                fontSize = FluidTypography.small,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    // With golden ratio
                    Card(
                        modifier = Modifier.weight(1f),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fluidPadding(8.dp, useGoldenRatio = true),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(FluidSpacing.extraSmall())
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                modifier = Modifier.fluidSize(24.dp, useGoldenRatio = true),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                "× φ",
                                fontSize = FluidTypography.small,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // Example 3: Golden Ratio Shapes
            ExampleSection(
                title = "Golden Ratio Shapes",
                description = "Width = Height × φ creates visually pleasing rectangles"
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(FluidSpacing.medium()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Golden Ratio rectangle
                    Box(
                        modifier = Modifier
                            .goldenRatioSize(heightRem = 3.0f)
                            .background(
                                MaterialTheme.colorScheme.primaryContainer,
                                RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(FluidSpacing.extraSmall())
                        ) {
                            Text(
                                "φ",
                                fontSize = FluidTypography.subtitle,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                "W = H × 1.618",
                                fontSize = FluidTypography.small,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // Example 4: Optical Symmetry Padding
            ExampleSection(
                title = "Optical Symmetry Padding",
                description = "Horizontal padding = Vertical × φ creates balanced containers"
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .goldenRatioPadding(verticalRem = 1.0f),
                        horizontalArrangement = Arrangement.spacedBy(FluidSpacing.medium()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Coffee,
                            contentDescription = null,
                            modifier = Modifier.fluidSize(32.dp),
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Balanced Layout",
                                fontSize = FluidTypography.subtitle,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                            Text(
                                text = "H-padding = V-padding × φ",
                                fontSize = FluidTypography.small,
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }
                }
            }

            // Example 5: Modular Scale
            ExampleSection(
                title = "Modular Scale Steps",
                description = "Generate any size using φ steps from base unit"
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(FluidSpacing.small())
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // Step -2
                        Box(
                            modifier = Modifier
                                .width(modularScale(base = 1f, steps = -2))
                                .height(modularScale(base = 2f, steps = -2))
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                                    RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "-2",
                                fontSize = FluidTypography.tiny,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        // Step -1
                        Box(
                            modifier = Modifier
                                .width(modularScale(base = 1f, steps = -1))
                                .height(modularScale(base = 2f, steps = -1))
                                .background(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                    RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "-1",
                                fontSize = FluidTypography.small,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        // Step 0 (base)
                        Box(
                            modifier = Modifier
                                .width(modularScale(base = 1f, steps = 0))
                                .height(modularScale(base = 2f, steps = 0))
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "0",
                                fontSize = FluidTypography.body,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        // Step +1
                        Box(
                            modifier = Modifier
                                .width(modularScale(base = 1f, steps = 1))
                                .height(modularScale(base = 2f, steps = 1))
                                .background(
                                    MaterialTheme.colorScheme.secondary,
                                    RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "+1",
                                fontSize = FluidTypography.subtitle,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                        // Step +2
                        Box(
                            modifier = Modifier
                                .width(modularScale(base = 1f, steps = 2))
                                .height(modularScale(base = 2f, steps = 2))
                                .background(
                                    MaterialTheme.colorScheme.tertiary,
                                    RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "+2",
                                fontSize = FluidTypography.title,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        }
                    }
                    Text(
                        text = "Each step multiplies/divides by φ",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Complete Example: Coffee Card
            ExampleSection(
                title = "Complete Golden Ratio Example",
                description = "Typography, spacing, and padding all using φ"
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .goldenRatioPadding(verticalRem = 1.0f),
                        horizontalArrangement = Arrangement.spacedBy(FluidSpacing.large()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .goldenRatioSize(heightRem = 3.5f)
                                .background(
                                    MaterialTheme.colorScheme.primary,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocalCafe,
                                contentDescription = null,
                                modifier = Modifier.fluidSize(modularScale(base = 1.5f, steps = 1)),
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(FluidSpacing.small())
                        ) {
                            Text(
                                text = "Espresso Perfecto",
                                fontSize = FluidTypography.subtitle,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = "Colombia • Medium Roast",
                                fontSize = FluidTypography.body,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = "Every element follows φ",
                                fontSize = FluidTypography.small,
                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f)
                            )
                        }
                    }
                }
            }

            // Scale indicator
            val currentScale = fluidScale()
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .goldenRatioPadding(verticalRem = 1.0f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(FluidSpacing.small())
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(FluidSpacing.large()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "φ",
                                fontSize = FluidTypography.headline,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = GOLDEN_RATIO.toString().take(5),
                                fontSize = FluidTypography.body,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Font Scale",
                                fontSize = FluidTypography.small,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "${(currentScale * 100).toInt() / 100f}x",
                                fontSize = FluidTypography.subtitle,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    Text(
                        text = "Change device font size to see everything scale harmoniously",
                        fontSize = FluidTypography.small,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun ExampleSection(
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
                .fluidPadding(16.dp),
            verticalArrangement = Arrangement.spacedBy(fluidSpace(8.dp))
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
            Spacer(modifier = Modifier.fluidHeight(4.dp))
            content()
        }
    }
}

@Preview
@Composable
private fun FluidModifiersExamplePreview() {
    PuckPerfectTheme {
        FluidModifiersExample()
    }
}

// Note: fontScale parameter is not universally supported in Compose Multiplatform Preview
// Test with actual device font size settings for accurate results
