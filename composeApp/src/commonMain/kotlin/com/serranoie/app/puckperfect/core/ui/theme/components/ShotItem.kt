package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.components.util.AureaSpacing
import com.serranoie.app.puckperfect.core.ui.theme.components.util.FluidAnimatedVisibility
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidAnimateContentSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.fluidSize
import com.serranoie.app.puckperfect.core.ui.theme.components.util.scaledSp
import com.serranoie.app.puckperfect.core.ui.theme.displaySmallExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelMediumCondensed
import com.serranoie.app.puckperfect.core.ui.theme.labelSmallCondensed
import com.serranoie.app.puckperfect.core.ui.theme.titleLargeExpressive
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class ShotFlavor { SWEET, BITTER, ACID }

@Composable
fun ShotItem(
    modifier: Modifier = Modifier,
    beanName: String,
    dateTime: String,
    grinder: String,
    grinderSetting: String,
    yield: String,
    time: String,
    flavor: ShotFlavor,
    underExtracted: Boolean = false,
) {
    val surface = MaterialTheme.colorScheme.surfaceContainerHigh
    val error = MaterialTheme.colorScheme.error
    val isWarning = (flavor == ShotFlavor.BITTER || flavor == ShotFlavor.ACID)
    val flavorLabel =
        when (flavor) {
            ShotFlavor.SWEET -> "SWEET"
            ShotFlavor.BITTER -> "BITTER"
            ShotFlavor.ACID -> "SOUR"
        }
    val chipColor =
        when (flavor) {
            ShotFlavor.SWEET -> MaterialTheme.colorScheme.surfaceVariant
            else -> error
        }
    val chipBg =
        when (flavor) {
            ShotFlavor.SWEET -> MaterialTheme.colorScheme.primary
            else -> Color.Transparent
        }

    Card(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(4.dp),
        shape = RoundedCornerShape(10.dp),
        border = if (isWarning) BorderStroke(1.25.dp, error) else null,
        colors = CardDefaults.cardColors(containerColor = surface),
    ) {
        Column(
            Modifier.padding(AureaSpacing.current.s),
            verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(AureaSpacing.current.s),
            ) {
                if (flavor == ShotFlavor.SWEET) {
                    Surface(
                        color = chipBg,
                        shape = CircleShape,
                        shadowElevation = 0.dp,
                        modifier = Modifier.fluidAnimateContentSize(),
                    ) {
                        Text(
                            flavorLabel,
                            color = chipColor,
                            fontWeight = FontWeight.Black,
                            modifier = Modifier.padding(horizontal = AureaSpacing.current.s, vertical = AureaSpacing.current.xs),
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                } else {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.errorContainer,
                        shadowElevation = 0.dp,
                        modifier = Modifier.fluidAnimateContentSize(),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = AureaSpacing.current.s, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
                        ) {
                            Text(
                                flavorLabel,
                                color = error,
                                fontWeight = FontWeight.Black,
                                style = MaterialTheme.typography.labelMedium,
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
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Text(
                beanName.uppercase(),
                style = MaterialTheme.typography.displaySmallExpressive(),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            FluidAnimatedVisibility(
                visible = underExtracted && isWarning,
                modifier = Modifier.fluidAnimateContentSize(),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = "Under extracted",
                        tint = error,
                        modifier = Modifier.fluidSize(12.dp),
                    )
                    Text(
                        "Under-extracted",
                        color = error,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.labelSmallCondensed(),
                    )
                }
            }

            HorizontalDivider(Modifier.padding(vertical = 4.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                ShotStatColumn(
                    label = grinder,
                    value = grinderSetting,
                    unit = "",
                    modifier = Modifier.weight(1f),
                )
                VerticalDivider()
                ShotStatColumn(
                    label = "YIELD",
                    value = yield,
                    unit = "g",
                    modifier = Modifier.weight(1f),
                )
                VerticalDivider()
                ShotStatColumn(
                    label = "TIME",
                    value = time,
                    unit = "s",
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun ShotStatColumn(
    label: String,
    value: String,
    unit: String,
    modifier: Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(AureaSpacing.current.xs),
        modifier = modifier,
    ) {
        Text(
            label.uppercase(),
            style = MaterialTheme.typography.labelSmallCondensed(),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(2.scaledSp()),
        ) {
            Text(
                value,
                style = MaterialTheme.typography.titleLargeExpressive(),
                color = MaterialTheme.colorScheme.onSurface,
            )
            if (unit.isNotEmpty()) {
                Text(
                    unit,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Preview()
@Composable
private fun ShotPreview() {
    PuckPerfectTheme {
        Column {
            ShotItem(
                modifier = Modifier,
                beanName = "La Gracia Dulce",
                dateTime = "Today",
                grinder = "Mazzer Mini",
                grinderSetting = "18",
                yield = "30",
                time = "25",
                flavor = ShotFlavor.BITTER,
                underExtracted = true,
            )

            ShotItem(
                modifier = Modifier,
                beanName = "La Gracia Dulce",
                dateTime = "15 December",
                grinder = "Mazzer Mini",
                grinderSetting = "18",
                yield = "30",
                time = "25",
                flavor = ShotFlavor.SWEET,
                underExtracted = false,
            )
        }
    }
}
