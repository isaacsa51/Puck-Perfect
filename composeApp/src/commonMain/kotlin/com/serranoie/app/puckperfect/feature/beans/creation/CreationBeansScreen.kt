package com.serranoie.app.puckperfect.feature.beans.creation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.LocalFlorist
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
import com.serranoie.app.puckperfect.core.ui.theme.components.BeanProcessMethodCard
import com.serranoie.app.puckperfect.core.ui.theme.components.BeanProcessMethodCardList
import com.serranoie.app.puckperfect.core.ui.theme.components.CoffeeLabel
import com.serranoie.app.puckperfect.core.ui.theme.components.LoopingPickerColumn
import com.serranoie.app.puckperfect.core.ui.theme.components.ProcessType
import com.serranoie.app.puckperfect.core.ui.theme.components.QuestionnaireWrapper
import com.serranoie.app.puckperfect.core.ui.theme.titleLargeExpressive
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items

enum class RoastProfile(val displayName: String, val description: String, val color: Color) {
    LIGHT(
        "Light Roast",
        "Light body, pronounced acidity, more caffeine, no oil on beans.",
        Color(0xFFEBB97C)
    ),
    MEDIUM(
        "Medium Roast",
        "Balanced flavor and acidity, medium brown, popular US style.",
        Color(0xFFD98555)
    ),
    MEDIUM_DARK(
        "Medium-Dark Roast", "Rich, darker color, some oil, heavy body.", Color(0xFF764C29)
    ),
    DARK(
        "Dark Roast", "Bold, mellow body, dark brown, oily surface, low acidity.", Color(0xFF43290A)
    )

}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalTime::class
)
@Composable
fun CreationBeansScreen(
    onBackClick: () -> Unit = {},
    onComplete: (name: String, roast: String, date: String, country: String, process: String) -> Unit = { _, _, _, _, _ -> }
) {
    var currentStep by remember { mutableStateOf(0) }
    var previousStep by remember { mutableStateOf(0) }
    var name by remember { mutableStateOf("") }
    var roast by remember { mutableStateOf<RoastProfile?>(null) }
    var date by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var process by remember { mutableStateOf("") }

    val totalSteps = 5
    val questionTexts = listOf(
        "Coffee Bean Brand?",
        "What type of roast is it?",
        "Date of roast?",
        "Country of origin?",
        "All correct?"
    )

    // For looping date picker setup - use only kotlinx-datetime to get today's date
    val today = kotlin.time.Clock.System.todayIn(TimeZone.UTC)
    val defaultYear = today.year
    val defaultMonth = today.month.number - 1 // Jan (0-indexed)
    val defaultDay = today.day - 1   // 1st (0-indexed)
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
        date = ("" + yearInt.toString().padStart(4, '0') + "-" + monthInt.toString()
            .padStart(2, '0') + "-" + dayInt.toString().padStart(2, '0'))
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
                0 -> Column(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier.size(60.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Circle,
                            contentDescription = "Coffee bean icon",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "Specifying the correct coffee roaster/brand helps you identify your next espresso!",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.surface,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Bean name", style = MaterialTheme.typography.labelLarge) },
                        singleLine = true,
                        maxLines = 1,
                        trailingIcon = {
                            if (name.isNotBlank()) {
                                IconButton(onClick = { name = "" }) {
                                    Icon(
                                        imageVector = Icons.Filled.Circle, // Replace with close/clear if available
                                        contentDescription = "Clear"
                                    )
                                }
                            }
                        },
                        textStyle = MaterialTheme.typography.titleLargeExpressive()
                            .copy(fontWeight = FontWeight.SemiBold),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primary,
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                        )
                    )
                }

                1 -> Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                        .verticalScroll(rememberScrollState()).padding(horizontal = 6.dp)
                ) {
                    RoastProfile.entries.forEach { profile ->
                        RoastProfileCard(
                            profile = profile,
                            selected = roast == profile,
                            onClick = { roast = profile },
                            modifier = Modifier.fillMaxWidth().weight(1f).padding(vertical = 4.dp)
                        )
                    }
                }

                2 -> Column(
                    Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Select date of roast",
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(vertical = 14.dp)
                    )
                    Row(
                        Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        LoopingPickerColumn(
                            choices = (1..daysInMonth).map { it.toString() },
                            selectedIndex = selectedDay,
                            onValueChange = {
                                selectedDay = it
                                updateDateFromLoopPickers()
                            },
                            modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
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
                            modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
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
                            modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                            visibleCount = 5,
                            itemHeight = 48.dp,
                            cornerRadius = 16.dp,
                        )
                    }
                }

                3 -> Column(
                    modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        "Bean origins",
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    OutlinedTextField(
                        value = country,
                        onValueChange = { country = it },
                        label = { Text("Bean origin") },
                        singleLine = true,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth().height(80.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                            unfocusedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f),
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.20f),
                        ),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold, fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        "Processing Method",
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    val processOptions = listOf(
                        ProcessType(Icons.Filled.WaterDrop, "Washed", "Clean & crisp acidity"),
                        ProcessType(Icons.Filled.LocalFlorist, "Natural", "Sweet berry notes"),
                        ProcessType(Icons.Filled.Science, "Fermented", "Funky & complex"),
                        ProcessType(Icons.Filled.LocalFlorist, "Honey", "Balanced & silky mouthfeel"),
                        ProcessType(Icons.Filled.Science, "Anaerobic", "Intense, wild character"),
                        ProcessType(Icons.Filled.Terrain, "Wet-hulled", "Earthy & full-bodied"),
                        ProcessType(Icons.Filled.Science, "Carbonic Maceration", "Layered, wine-like notes"),
                        ProcessType(Icons.Filled.WaterDrop, "Double Washed", "Very clean acidity"),
                        ProcessType(Icons.Filled.LocalFlorist, "Black Honey", "Rich, syrupy, complex"),
                        ProcessType(Icons.Filled.LocalFlorist, "Red Honey", "Fruity & rounder body"),
                        ProcessType(Icons.Filled.Science, "Experimental", "Unusual, rare technique"),
                        ProcessType(Icons.Filled.Circle, "Not Specified", "No info on bag")
                    )
                    BeanProcessMethodCardList(
                        processOptions = processOptions,
                        selectedProcess = process,
                        onSelect = { process = it },
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                4 -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CoffeeLabel(
                        brand = name,
                        roast = roast?.displayName ?: "",
                        country = country,
                        date = date,
                        processing = process.ifBlank { "Non specified" },
                        modifier = Modifier.fillMaxWidth(0.7f)
                    )
                }
            }
        },
        bottomActions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (currentStep > 0) {
                    OutlinedButton(
                        onClick = {
                            currentStep--
                        },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            "Back", fontSize = 18.sp, fontWeight = FontWeight.Bold
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
                                process.ifBlank { "Non specified" })
                        }
                    },
                    modifier = Modifier.weight(if (currentStep > 0) 2f else 1f).height(56.dp),
                    shape = RoundedCornerShape(10.dp),
                    enabled = when (currentStep) {
                        0 -> name.isNotBlank() && name.length >= 3
                        1 -> roast != null
                        2 -> date.isNotBlank()
                        3 -> country.isNotBlank()
                        4 -> true
                        else -> true
                    }
                ) {
                    Text(
                        if (currentStep < totalSteps - 1) "Next" else "Finish",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        })
}

@Composable
fun RoastProfileCard(
    profile: RoastProfile, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    val borderColor =
        if (selected) MaterialTheme.colorScheme.primary.copy(alpha = 0.7f) else Color.Transparent
    Box(
        modifier = modifier.clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(width = 2.dp, color = borderColor, shape = RoundedCornerShape(10.dp))
            .clickable { onClick() }) {
        Box(
            Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Row(
                Modifier.padding(horizontal = 14.dp, vertical = 10.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.size(44.dp).background(profile.color, CircleShape),
                    contentAlignment = Alignment.Center
                ) {}

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    Modifier.weight(1f), verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = profile.displayName,
                        style = MaterialTheme.typography.titleLargeExpressive(),
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        maxLines = 1
                    )
                    Text(
                        text = profile.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2
                    )
                }
                // Trailing icon placeholder or real icon for fixed space
                if (selected) {
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Selected",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(26.dp)
                    )
                } else {
                    Box(Modifier.size(26.dp))
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
            RoastProfileCard(
                profile = RoastProfile.LIGHT, selected = true, onClick = {})

            RoastProfileCard(
                profile = RoastProfile.MEDIUM, selected = false, onClick = {})
        }
    }
}