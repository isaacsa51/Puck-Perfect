package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.displaySmallExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelMediumCondensed
import com.serranoie.app.puckperfect.core.ui.theme.labelSmallCondensed
import com.serranoie.app.puckperfect.core.ui.theme.titleLargeExpressive
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ShotFlavor { SWEET, BITTER, ACID }

@Composable
fun ShotItem(
    beanName: String,
    dateTime: String,
    grinder: String,
    grinderSetting: String,
    yield: String,
    time: String,
    flavor: ShotFlavor,
    underExtracted: Boolean = false
) {
    val surface = MaterialTheme.colorScheme.surfaceContainerHigh
    val error = MaterialTheme.colorScheme.error
    val outline = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
    val isWarning = (flavor == ShotFlavor.BITTER || flavor == ShotFlavor.ACID)
    val border = if (isWarning) error else outline
    val flavorLabel = when (flavor) {
        ShotFlavor.SWEET -> "SWEET"
        ShotFlavor.BITTER -> "BITTER"
        ShotFlavor.ACID -> "SOUR"
    }
    val chipColor = when (flavor) {
        ShotFlavor.SWEET -> MaterialTheme.colorScheme.surfaceVariant
        else -> error
    }
    val chipBg = when (flavor) {
        ShotFlavor.SWEET -> MaterialTheme.colorScheme.primaryContainer
        else -> Color.Transparent
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        shape = RoundedCornerShape(10.dp),
        border = if (isWarning) BorderStroke(1.25.dp, error) else null,
        colors = CardDefaults.cardColors(containerColor = surface)
    ) {
        Column(Modifier.padding(vertical = 18.dp, horizontal = 18.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                if (flavor == ShotFlavor.SWEET) {
                    Surface(
                        color = chipBg,
                        shape = CircleShape,
                        shadowElevation = 0.dp
                    ) {
                        Text(
                            flavorLabel,
                            color = chipColor,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                } else {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.errorContainer,
                        shadowElevation = 0.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                flavorLabel,
                                color = error,
                                fontWeight = FontWeight.Black,
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
                Spacer(Modifier.weight(1f))
                Text(
                    dateTime,
                    style = MaterialTheme.typography.labelMediumCondensed(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.73f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(5.dp))
            Text(
                beanName.uppercase(),
                style = MaterialTheme.typography.displaySmallExpressive(),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = 2.dp)
            )
            if (underExtracted && isWarning) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.Warning,
                        contentDescription = "Under extracted",
                        tint = error,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        "Under-extracted",
                        color = error,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.labelSmallCondensed(),
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

            }
            HorizontalDivider(Modifier.padding(vertical = 8.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ShotStatColumn(
                    label = grinder,
                    value = grinderSetting,
                    unit = "",
                    modifier = Modifier.weight(1f)
                )
                VerticalDivider()
                ShotStatColumn(
                    label = "YIELD",
                    value = yield,
                    unit = "g",
                    modifier = Modifier.weight(1f)
                )
                VerticalDivider()
                ShotStatColumn(
                    label = "TIME",
                    value = time,
                    unit = "s",
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ShotStatColumn(label: String, value: String, unit: String, modifier: Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            label.uppercase(),
            style = MaterialTheme.typography.labelSmallCondensed(),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                value,
                style = MaterialTheme.typography.titleLargeExpressive(),
                color = MaterialTheme.colorScheme.onSurface
            )
            if (unit.isNotEmpty()) {
                Spacer(Modifier.width(2.dp))
                Text(unit, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun VerticalDivider() {
    Box(
        Modifier
            .width(1.dp)
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.outline.copy(alpha = 0.14f))
    )
}

@Preview
@Composable
private fun ShotPreview() {
    PuckPerfectTheme {
        Column {

            ShotItem(
                beanName = "La Gracia Dulce",
                dateTime = "Today",
                grinder = "Mazzer Mini",
                grinderSetting = "18",
                yield = "30",
                time = "25",
                flavor = ShotFlavor.BITTER,
                underExtracted = true
            )

            ShotItem(
                beanName = "La Gracia Dulce",
                dateTime = "15 December",
                grinder = "Mazzer Mini",
                grinderSetting = "18",
                yield = "30",
                time = "25",
                flavor = ShotFlavor.SWEET,
                underExtracted = false
            )
        }
    }
}