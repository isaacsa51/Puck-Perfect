package com.serranoie.app.puckperfect.feature.beans.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.BeanProcessMethodCardList
import com.serranoie.app.puckperfect.core.ui.theme.components.CoffeeLabel
import com.serranoie.app.puckperfect.core.ui.theme.components.LoopingPickerColumn
import com.serranoie.app.puckperfect.core.ui.theme.components.ProcessType
import com.serranoie.app.puckperfect.core.ui.theme.components.QuestionnaireWrapper
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidAnimatedVisibility
import com.serranoie.app.puckperfect.core.ui.theme.components.util.AureaSpacing
import com.serranoie.app.puckperfect.core.ui.theme.components.util.SpacingLevel
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidAnimateContentSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidHeight
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSpace
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidWidth
import com.serranoie.app.puckperfect.core.ui.theme.components.util.scaledSp
import com.serranoie.app.puckperfect.core.ui.theme.titleLargeExpressive
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime

enum class RoastProfile(
    val displayName: String,
    val description: String,
    val color: Color,
) {
    LIGHT(
        "Light Roast",
        "Light body, pronounced acidity, more caffeine, no oil on beans.",
        Color(0xFFEBB97C),
    ),
    MEDIUM(
        "Medium Roast",
        "Balanced flavor and acidity, medium brown, popular US style.",
        Color(0xFFD98555),
    ),
    MEDIUM_DARK(
        "Medium-Dark Roast",
        "Rich, darker color, some oil, heavy body.",
        Color(0xFF764C29),
    ),
    DARK(
        "Dark Roast",
        "Bold, mellow body, dark brown, oily surface, low acidity.",
        Color(0xFF43290A),
    ),
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalTime::class,
)
@Composable
fun CreationBeansScreen(
    onBackClick: () -> Unit = {},
    onComplete: (name: String, roast: String, date: String, country: String, process: String) -> Unit = { _, _, _, _, _ -> },
) {
    var currentStep by remember { mutableStateOf(0) }
    var previousStep by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    var roast by remember { mutableStateOf<RoastProfile?>(null) }
    var date by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var process by remember { mutableStateOf("") }

    val totalSteps = 5
    val questionTexts =
        listOf(
            "Coffee Bean Brand?",
            "What type of roast is it?",
            "Date of roast?",
            "Country of origin?",
            "All correct?",
        )

    // For looping date picker setup - use only kotlinx-datetime to get today's date
    val today =
        kotlin.time.Clock.System
            .todayIn(TimeZone.UTC)
    val defaultYear = today.year
    val defaultMonth = today.month.number - 1 // Jan (0-indexed)
    val defaultDay = today.day - 1 // 1st (0-indexed)
    val months =
        listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")
    val daysInMonth = 31
    val years = (1980..(defaultYear + 20)).map { it.toString() }
    var selectedDay by remember { mutableStateOf(defaultDay) }
    var selectedMonth by remember { mutableStateOf(defaultMonth) }
    var selectedYear by remember { mutableStateOf(years.indexOf(defaultYear.toString())) }
    var didInitDatePickers by remember { mutableStateOf(false) }

    fun updateDateFromLoopPickers() {
        val yearInt = years[selectedYear].toInt()
        val monthInt = selectedMonth + 1
        val dayInt = (selectedDay + 1).coerceAtMost(31)
        date = (
            "" + yearInt.toString().padStart(4, '0') + "-" +
                monthInt
                    .toString()
                    .padStart(2, '0') + "-" + dayInt.toString().padStart(2, '0')
        )
    }

    LaunchedEffect(currentStep) {
        // Only initialize pickers if entering step 2 for the first time
        if (currentStep == 2 && !didInitDatePickers) {
            selectedDay = defaultDay
            selectedMonth = defaultMonth
            selectedYear = years.indexOf(defaultYear.toString())
            updateDateFromLoopPickers()
        }
    }

    QuestionnaireWrapper(
        currentStep = currentStep,
        totalSteps = totalSteps,
        questionText = questionTexts[currentStep],
        onBackClick = {
            if (currentStep > 0) {
                currentStep--
            } else {
                onBackClick()
            }
        },
        previousStep = previousStep,
        mainContent = {
            when (currentStep) {
                0 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = AureaSpacing.current.s)
                            .fluidAnimateContentSize(),
                        verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.m, Alignment.CenterVertically),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier =
                                Modifier
                                    .fluidSize(60.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                                    .fluidAnimateContentSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Circle,
                                contentDescription = "Coffee bean icon",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.fluidSize(32.dp),
                            )
                        }
                        Text(
                            text = "Specifying the correct coffee roaster/brand helps you identify your next espresso!",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.surface,
                            textAlign = TextAlign.Center,
                        )
                        OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = { Text("Bean name", style = MaterialTheme.typography.labelLarge) },
                            singleLine = true,
                            maxLines = 1,
                            trailingIcon = {
                                FluidAnimatedVisibility(visible = name.isNotBlank()) {
                                    IconButton(onClick = { name = "" }) {
                                        Icon(
                                            imageVector = Icons.Filled.Circle,
                                            contentDescription = "Clear",
                                            modifier = Modifier.fluidSize(20.dp)
                                        )
                                    }
                                }
                            },
                            textStyle =
                                MaterialTheme.typography
                                    .titleLargeExpressive()
                                    .copy(fontWeight = FontWeight.SemiBold),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .fluidAnimateContentSize(),
                            shape = RoundedCornerShape(12.scaledSp()),
                            colors =
                                TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                                    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                                    disabledContainerColor = MaterialTheme.colorScheme.primary,
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                                ),
                        )
                    }
                }

                1 -> {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .verticalScroll(rememberScrollState())
                                .padding(horizontal = AureaSpacing.current.xs)
                                .fluidAnimateContentSize(),
                        verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.s)
                    ) {
                        RoastProfile.entries.forEach { profile ->
                            RoastProfileCard(
                                profile = profile,
                                selected = roast == profile,
                                onClick = { roast = profile },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(vertical = 4.dp),
                            )
                        }
                    }
                }

                2 -> {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .fluidAnimateContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.m)
                    ) {
                        Text(
                            "Select date of roast",
                            style = MaterialTheme.typography.titleLargeExpressive(),
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(vertical = AureaSpacing.current.s),
                        )
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(fluidSpace(8.dp)),
                        ) {
                            LoopingPickerColumn(
                                choices = (1..daysInMonth).map { it.toString() },
                                selectedIndex = selectedDay,
                                onValueChange = {
                                    selectedDay = it
                                    updateDateFromLoopPickers()
                                },
                                modifier = Modifier.weight(1f),
                                visibleCount = 5,
                                itemHeight = 48.dp,
                                cornerRadius = 16.dp,
                            )
                            LoopingPickerColumn(
                                choices = months,
                                selectedIndex = selectedMonth,
                                onValueChange = {
                                    selectedMonth = it
                                    updateDateFromLoopPickers()
                                },
                                modifier = Modifier.weight(1f),
                                visibleCount = 5,
                                itemHeight = 48.dp,
                                cornerRadius = 16.dp,
                            )
                            LoopingPickerColumn(
                                choices = years,
                                selectedIndex = selectedYear,
                                onValueChange = {
                                    selectedYear = it
                                    updateDateFromLoopPickers()
                                },
                                modifier = Modifier.weight(1f),
                                visibleCount = 5,
                                itemHeight = 48.dp,
                                cornerRadius = 16.dp,
                            )
                        }
                    }
                }

                3 -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(AureaSpacing.current.s)
                            .fluidAnimateContentSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.m, Alignment.Top),
                    ) {
                        Text(
                            "Bean origins",
                            style = MaterialTheme.typography.titleLargeExpressive(),
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.align(Alignment.Start),
                        )
                        OutlinedTextField(
                            value = country,
                            onValueChange = { country = it },
                            label = { Text("Bean origin") },
                            singleLine = true,
                            maxLines = 1,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fluidHeight(80.dp)
                                .fluidAnimateContentSize(),
                            shape = RoundedCornerShape(16.scaledSp()),
                            colors =
                                TextFieldDefaults.colors(
                                    focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                                    unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                                    disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.20f),
                                ),
                            textStyle =
                                MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 18.sp,
                                ),
                        )
                        Text(
                            "Processing Method",
                            style = MaterialTheme.typography.titleLargeExpressive(),
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.align(Alignment.Start),
                        )
                        val processOptions =
                            listOf(
                                ProcessType(Icons.Filled.WaterDrop, "Washed", "Clean & crisp acidity"),
                                ProcessType(Icons.Filled.LocalFlorist, "Natural", "Sweet berry notes"),
                                ProcessType(Icons.Filled.Science, "Fermented", "Funky & complex"),
                                ProcessType(
                                    Icons.Filled.LocalFlorist,
                                    "Honey",
                                    "Balanced & silky mouthfeel",
                                ),
                                ProcessType(Icons.Filled.Science, "Anaerobic", "Intense, wild character"),
                                ProcessType(Icons.Filled.Terrain, "Wet-hulled", "Earthy & full-bodied"),
                                ProcessType(
                                    Icons.Filled.Science,
                                    "Carbonic Maceration",
                                    "Layered, wine-like notes",
                                ),
                                ProcessType(Icons.Filled.WaterDrop, "Double Washed", "Very clean acidity"),
                                ProcessType(
                                    Icons.Filled.LocalFlorist,
                                    "Black Honey",
                                    "Rich, syrupy, complex",
                                ),
                                ProcessType(
                                    Icons.Filled.LocalFlorist,
                                    "Red Honey",
                                    "Fruity & rounder body",
                                ),
                                ProcessType(
                                    Icons.Filled.Science,
                                    "Experimental",
                                    "Unusual, rare technique",
                                ),
                                ProcessType(Icons.Filled.Circle, "Not Specified", "No info on bag"),
                            )
                        BeanProcessMethodCardList(
                            processOptions = processOptions,
                            selectedProcess = process,
                            onSelect = { process = it },
                            modifier = Modifier
                                .padding(top = AureaSpacing.current.s)
                                .fluidAnimateContentSize(),
                        )
                    }
                }

                4 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .fluidAnimateContentSize(), 
                        contentAlignment = Alignment.Center
                    ) {
                        CoffeeLabel(
                            brand = name,
                            roast = roast?.displayName ?: "",
                            country = country,
                            date = date,
                            processing = process.ifBlank { "Non specified" },
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .fluidAnimateContentSize(),
                        )
                    }
                }
            }
        },
        bottomActions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fluidAnimateContentSize(),
                horizontalArrangement = Arrangement.spacedBy(fluidSpace(8.dp)),
            ) {
                if (currentStep > 0) {
                    OutlinedButton(
                        onClick = {
                            currentStep--
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fluidHeight(56.dp),
                        shape = RoundedCornerShape(10.scaledSp()),
                    ) {
                        Text(
                            "Back",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
                Button(
                    onClick = {
                        if (currentStep < totalSteps - 1) {
                            currentStep++
                        } else if (currentStep == totalSteps - 1) {
                            onComplete(
                                name,
                                roast?.displayName ?: "",
                                date,
                                country,
                                process.ifBlank { "Non specified" },
                            )
                        }
                    },
                    modifier = Modifier
                        .weight(if (currentStep > 0) 2f else 1f)
                        .fluidHeight(56.dp),
                    shape = RoundedCornerShape(10.scaledSp()),
                    enabled =
                        when (currentStep) {
                            0 -> name.isNotBlank() && name.length >= 3
                            1 -> roast != null
                            2 -> date.isNotBlank()
                            3 -> country.isNotBlank()
                            4 -> true
                            else -> true
                        },
                ) {
                    Text(
                        if (currentStep < totalSteps - 1) "Next" else "Finish",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        },
    )
}

@Composable
fun RoastProfileCard(
    profile: RoastProfile,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val borderColor =
        if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.7f) else Color.Transparent
    val borderWidth = if (selected) 2.dp else 0.dp
    
    Box(
        modifier =
            modifier
                .clip(RoundedCornerShape(10.scaledSp()))
                .background(MaterialTheme.colorScheme.surface)
                .border(width = borderWidth, color = borderColor, shape = RoundedCornerShape(10.scaledSp()))
                .clickable { onClick() }
                .fluidAnimateContentSize(),
    ) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            Row(
                Modifier
                    .padding(horizontal = AureaSpacing.current.s, vertical = AureaSpacing.current.s)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(fluidSpace(12.dp)),
            ) {
                Box(
                    modifier = Modifier
                        .fluidSize(44.dp)
                        .background(profile.color, CircleShape)
                        .fluidAnimateContentSize(),
                    contentAlignment = Alignment.Center,
                ) {}

                Column(
                    Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
                ) {
                    Text(
                        text = profile.displayName,
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1,
                    )
                    Text(
                        text = profile.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                    )
                }
                // Trailing icon with animation
                FluidAnimatedVisibility(visible = selected) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.fluidSize(26.dp),
                    )
                }
                if (!selected) {
                    Box(Modifier.fluidSize(26.dp))
                }
            }
        }
    }
}

@Composable
@Preview
private fun PreviewCreation() {
    PuckPerfectTheme {
        CreationBeansScreen()
    }
}

@Composable
@Preview(showBackground = true)
private fun RoastProfilePreview() {
    PuckPerfectTheme {
        Column {
            RoastProfileCard(profile = RoastProfile.LIGHT, selected = true, onClick = {})

            RoastProfileCard(profile = RoastProfile.MEDIUM, selected = false, onClick = {})
        }
    }
}
