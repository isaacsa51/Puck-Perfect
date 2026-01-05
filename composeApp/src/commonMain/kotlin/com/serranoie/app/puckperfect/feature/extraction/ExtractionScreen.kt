package com.serranoie.app.puckperfect.feature.extraction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.GramsSlider
import com.serranoie.app.puckperfect.core.ui.theme.components.QuestionnaireWrapper
import com.serranoie.app.puckperfect.core.ui.theme.components.TimerDisplay
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidSpacing
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidAnimateContentSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidHeight
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidPadding
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSpace
import com.serranoie.app.puckperfect.core.ui.theme.components.util.scaledSp
import com.serranoie.app.puckperfect.core.ui.theme.labelLargeCondensed
import com.serranoie.app.puckperfect.core.ui.theme.labelLargeExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelMediumCondensed
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class FlavorProfile {
    SWEET, ACID, BITTER
}

private fun formatFloat(value: Float): String {
    return if (value % 1.0f == 0.0f) {
        value.toInt().toString()
    } else {
        val intPart = value.toInt()
        val decimalPart = ((value - intPart) * 10).toInt()
        "$intPart.$decimalPart"
    }
}

@Composable
fun ExtractionScreen(
    onBackClick: () -> Unit = {},
    onComplete: (grams: Float, ratio: String, time: Int, flavor: FlavorProfile) -> Unit = { _, _, _, _ -> }
) {
    var currentStep by remember { mutableIntStateOf(0) }
    var previousStep by remember { mutableIntStateOf(0) }

    var selectedGrams by remember { mutableStateOf(18.0f) }
    var selectedRatio by remember { mutableStateOf("1:2") }
    var selectedTime by remember { mutableIntStateOf(30) }
    var selectedFlavor by remember { mutableStateOf<FlavorProfile?>(null) }
    var showDecimals by remember { mutableStateOf(true) }
    var showCustomDialog by remember { mutableStateOf(false) }
    var customGramsInput by remember { mutableStateOf("") }

    var remainingTime by remember { mutableIntStateOf(30) }
    var isTimerRunning by remember { mutableStateOf(false) }
    var isTimerFinished by remember { mutableStateOf(false) }

    val totalSteps = 5

    val questionTexts = listOf(
        "How many grams for this shot?",
        "Ratio for this shot?",
        "Time to extract",
        "Shot timer",
        "What was the taste?"
    )

    // Timer countdown effect
    LaunchedEffect(isTimerRunning, remainingTime) {
        if (isTimerRunning && remainingTime > 0) {
            delay(1000L)
            remainingTime--
            if (remainingTime == 0) {
                isTimerRunning = false
                isTimerFinished = true
            }
        }
    }

    if (showCustomDialog) {
        AlertDialog(
            onDismissRequest = { showCustomDialog = false }, 
            title = {
                Text("Custom Grams Value")
            }, 
            text = {
                Column(
                    modifier = Modifier.fluidAnimateContentSize(),
                    verticalArrangement = Arrangement.spacedBy(FluidSpacing.small())
                ) {
                    Text(
                        "Enter a custom grams value:", 
                        modifier = Modifier.fluidPadding(bottom = 8.dp)
                    )
                    OutlinedTextField(
                        value = customGramsInput,
                        onValueChange = { customGramsInput = it },
                        label = { Text("Grams") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }, 
            confirmButton = {
            TextButton(
                onClick = {
                    val customValue = customGramsInput.toFloatOrNull()
                    if (customValue != null && customValue > 0) {
                        selectedGrams = customValue
                        showCustomDialog = false
                        customGramsInput = ""
                    }
                }) {
                Text("Apply")
            }
        }, dismissButton = {
            TextButton(
                onClick = {
                    showCustomDialog = false
                    customGramsInput = ""
                }) {
                Text("Cancel")
            }
        })
    }

    QuestionnaireWrapper(
        currentStep = currentStep,
        totalSteps = totalSteps,
        questionText = questionTexts[currentStep],
        onBackClick = {
            if (currentStep > 0) {
                previousStep = currentStep
                currentStep--
            } else {
                onBackClick()
            }
        },
        previousStep = previousStep,
        topExtras = {
            if (currentStep == 0) {
                Row(
                    modifier = Modifier
                        .clickable { showCustomDialog = true }
                        .fluidPadding(vertical = 4.dp)
                        .fillMaxWidth()
                        .fluidAnimateContentSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(FluidSpacing.small(), Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "Custom value",
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit custom value",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fluidSize(20.dp)
                    )
                }
            }
        },
        mainContent = {
            when (currentStep) {
                0 -> {
                    // Grams question
                    GramsSlider(
                        currentGrams = selectedGrams,
                        minGrams = 10f,
                        maxGrams = 25f,
                        showDecimals = showDecimals,
                        stepSize = if (showDecimals) 0.1f else 1f,
                        onValueChanged = { selectedGrams = it })
                }

                1 -> {
                    // Ratio question
                    RatioSelector(
                        selectedRatio = selectedRatio,
                        onRatioSelected = { selectedRatio = it },
                        gramsOfCoffee = selectedGrams
                    )
                }

                2 -> {
                    // Time selector
                    TimeSelector(
                        selectedTime = selectedTime, onTimeSelected = { selectedTime = it })
                }

                3 -> {
                    // Timer display
                    val progress = if (selectedTime > 0) {
                        1f - (remainingTime.toFloat() / selectedTime.toFloat())
                    } else {
                        0f
                    }
                    TimerDisplay(
                        remainingSeconds = remainingTime,
                        progress = progress,
                        showCompletedMessage = true,
                        isRunning = isTimerRunning
                    )
                }

                4 -> {
                    // Flavor question
                    FlavorSelector(
                        selectedFlavor = selectedFlavor, onFlavorSelected = { selectedFlavor = it })
                }
            }
        },
        bottomActions = {
            if (currentStep == 3) {
                // Timer controls - layout changes based on timer state
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fluidAnimateContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(fluidSpace(12.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (isTimerFinished) {
                        // When finished: Reset (30%) + Next (70%)
                        OutlinedButton(
                            onClick = {
                                remainingTime = selectedTime
                                isTimerRunning = false
                                isTimerFinished = false
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fluidHeight(56.dp)
                                .fluidAnimateContentSize(),
                            shape = RoundedCornerShape(10.scaledSp())
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset",
                                modifier = Modifier
                                    .fluidSize(22.dp)
                                    .fluidPadding(end = 4.dp)
                            )
                            Text(
                                "Reset",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLargeExpressive()
                            )
                        }

                        Button(
                            onClick = {
                                previousStep = currentStep
                                currentStep++
                            },
                            modifier = Modifier
                                .weight(2f)
                                .fluidHeight(56.dp)
                                .fluidAnimateContentSize(),
                            shape = RoundedCornerShape(10.scaledSp())
                        ) {
                            Text(
                                "Next",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLargeExpressive()
                            )
                        }
                    } else {
                        // When stopped or paused: Reset (30%) + Play/Pause (70%)
                        OutlinedButton(
                            onClick = {
                                remainingTime = selectedTime
                                isTimerRunning = false
                                isTimerFinished = false
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fluidHeight(56.dp)
                                .fluidAnimateContentSize(),
                            shape = RoundedCornerShape(10.scaledSp())
                        ) {
                            Text(
                                "Reset",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLargeExpressive()
                            )
                        }

                        Button(
                            onClick = {
                                if (remainingTime > 0) {
                                    isTimerRunning = !isTimerRunning
                                }
                            },
                            modifier = Modifier
                                .weight(2f)
                                .fluidHeight(56.dp)
                                .fluidAnimateContentSize(),
                            shape = RoundedCornerShape(10.scaledSp()),
                            enabled = remainingTime > 0
                        ) {
                            Icon(
                                imageVector = if (isTimerRunning) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isTimerRunning) "Pause" else "Play",
                                modifier = Modifier
                                    .fluidSize(22.dp)
                                    .fluidPadding(end = 4.dp)
                            )
                            Text(
                                if (isTimerRunning) "Pause" else "Play",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLargeExpressive()
                            )
                        }
                    }
                }
            } else {
                // Regular navigation buttons for other steps
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fluidAnimateContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(fluidSpace(12.dp))
                ) {
                    // Back button (show on all steps except first)
                    if (currentStep > 0) {
                        OutlinedButton(
                            onClick = {
                                previousStep = currentStep
                                currentStep--
                                // Reset timer when going back from timer step
                                if (currentStep == 2) {
                                    remainingTime = selectedTime
                                    isTimerRunning = false
                                    isTimerFinished = false
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fluidHeight(56.dp),
                            shape = RoundedCornerShape(10.scaledSp())
                        ) {
                            Text(
                                "Back",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.labelLargeExpressive()
                            )
                        }
                    }

                    // Next/Finish button
                    Button(
                        onClick = {
                            previousStep = currentStep
                            if (currentStep < totalSteps - 1) {
                                // Initialize timer when moving to timer step
                                if (currentStep == 2) {
                                    remainingTime = selectedTime
                                    isTimerRunning = false
                                    isTimerFinished = false
                                }
                                currentStep++
                            } else {
                                onComplete(
                                    selectedGrams,
                                    selectedRatio,
                                    selectedTime,
                                    selectedFlavor ?: FlavorProfile.SWEET
                                )
                            }
                        },
                        modifier = Modifier
                            .weight(if (currentStep > 0) 2f else 1f)
                            .fluidHeight(56.dp),
                        shape = RoundedCornerShape(10.scaledSp()),
                        enabled = when (currentStep) {
                            4 -> selectedFlavor != null // Flavor step is now step 4
                            else -> true
                        }
                    ) {
                        Text(
                            if (currentStep < totalSteps - 1) "Next" else "Finish",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.labelLargeExpressive()
                        )
                    }
                }
            }
        },
        bottomExtras = {
            when (currentStep) {
                0 -> {
                    Row(
                        modifier = Modifier
                            .fluidPadding(top = 16.dp)
                            .fluidAnimateContentSize(),
                        horizontalArrangement = Arrangement.spacedBy(FluidSpacing.medium(), Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Show decimals",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelLargeCondensed()
                        )
                        Switch(
                            checked = showDecimals, 
                            onCheckedChange = { showDecimals = it }
                        )
                    }
                }
                1 -> {
                    Text(
                        text = "Ratio is the quantity of water to espresso.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMediumCondensed(),
                        modifier = Modifier.fluidPadding(8.dp)
                    )
                }
                3 -> {
                    Text(
                        text = "Suggested time to extraction is between 25 to 30 seconds.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelMediumCondensed(),
                        modifier = Modifier.fluidPadding(8.dp)
                    )
                }
            }
        })
}

@Composable
private fun RatioSelector(
    selectedRatio: String,
    onRatioSelected: (String) -> Unit,
    gramsOfCoffee: Float,
    modifier: Modifier = Modifier
) {
    val ratios = listOf("1:1", "1:1.5", "1:2", "1:2.5", "1:3")

    // Calculate espresso output based on selected ratio
    fun calculateEspresso(ratio: String): Float {
        val multiplier = ratio.substringAfter(":").toFloatOrNull() ?: 2f
        return gramsOfCoffee * multiplier
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .fluidPadding(24.dp)
            .fluidAnimateContentSize(),
        verticalArrangement = Arrangement.spacedBy(fluidSpace(12.dp), Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ratios.forEach { ratio ->
            FilterChip(
                selected = selectedRatio == ratio,
                onClick = { onRatioSelected(ratio) },
                label = {
                    Text(
                        text = ratio, fontSize = 20.sp, fontWeight = FontWeight.Bold
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fluidHeight(56.dp)
                    .fluidAnimateContentSize(),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Spacer(modifier = Modifier.fluidHeight(8.dp))

        // Show calculation for selected ratio
        val espressoOutput = calculateEspresso(selectedRatio)
        Row(
            horizontalArrangement = Arrangement.spacedBy(FluidSpacing.small(), Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fluidPadding(top = 8.dp)
        ) {
            Text(
                text = "${formatFloat(gramsOfCoffee)} g of coffee",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelLargeCondensed()
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fluidPadding(vertical = 4.dp, horizontal = 8.dp),
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "${formatFloat(espressoOutput)} g of espresso",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelLargeCondensed()
            )
        }
    }
}

@Composable
private fun TimeSelector(
    selectedTime: Int, onTimeSelected: (Int) -> Unit, modifier: Modifier = Modifier
) {
    val timeOptions = listOf(20, 25, 30, 35, 40, 45, 50, 55, 60)

    Column(
        modifier = modifier
            .fillMaxSize()
            .fluidPadding(24.dp)
            .fluidAnimateContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Show large current time display
        Text(
            text = "${selectedTime}s",
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fluidPadding(bottom = 32.dp)
                .fluidAnimateContentSize()
        )

        // Time options in grid
        timeOptions.chunked(3).forEach { rowTimes ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fluidPadding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(fluidSpace(12.dp), Alignment.CenterHorizontally)
            ) {
                rowTimes.forEach { time ->
                    FilterChip(
                        selected = selectedTime == time,
                        onClick = { onTimeSelected(time) },
                        label = {
                            Text(
                                text = "${time}s", fontSize = 18.sp, fontWeight = FontWeight.Bold
                            )
                        },
                        modifier = Modifier
                            .fluidHeight(64.dp)
                            .weight(1f)
                            .fluidAnimateContentSize(),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
                // Add empty spaces if row is not full
                repeat(3 - rowTimes.size) {
                    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // Custom time adjustment
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fluidPadding(top = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(FluidSpacing.medium(), Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { if (selectedTime > 10) onTimeSelected(selectedTime - 5) },
                modifier = Modifier.fluidAnimateContentSize()
            ) {
                Text("-5s", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Text(
                text = "Custom",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
            Button(
                onClick = { if (selectedTime < 120) onTimeSelected(selectedTime + 5) },
                modifier = Modifier.fluidAnimateContentSize()
            ) {
                Text("+5s", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun FlavorSelector(
    selectedFlavor: FlavorProfile?,
    onFlavorSelected: (FlavorProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .fluidPadding(24.dp)
            .fluidAnimateContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), 
            horizontalArrangement = Arrangement.spacedBy(fluidSpace(12.dp))
        ) {
            FlavorProfile.entries.forEach { flavor ->
                FilterChip(
                    selected = selectedFlavor == flavor,
                    onClick = { onFlavorSelected(flavor) },
                    label = {
                        Text(
                            text = flavor.name, fontSize = 18.sp, fontWeight = FontWeight.Bold
                        )
                    },
                    modifier = Modifier
                        .fluidHeight(80.dp)
                        .weight(1f)
                        .fluidAnimateContentSize(),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun ExtractionScreenPreview() {
    PuckPerfectTheme {
        ExtractionScreen(onBackClick = { }, onComplete = { grams, ratio, time, flavor -> })
    }
}
