package com.serranoie.app.puckperfect.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import puckperfect.composeapp.generated.resources.Res
import puckperfect.composeapp.generated.resources.archivo
import puckperfect.composeapp.generated.resources.syne
import puckperfect.composeapp.generated.resources.syne
import puckperfect.composeapp.generated.resources.worksans

/**
 * Variable Font Configuration for Display Typography
 *
 * Note: Variable font axis variations are not fully supported across all platforms in Compose Multiplatform.
 * We use different font weights instead to achieve similar effects.
 */

// Display font configurations
object DisplayLargeConfig {
    const val WEIGHT = 900
}

object DisplayMediumConfig {
    const val WEIGHT = 800
}

object DisplaySmallConfig {
    const val WEIGHT = 700
}

// Condensed configurations
object DisplayLargeCondensedConfig {
    const val WEIGHT = 700
}

object DisplayMediumCondensedConfig {
    const val WEIGHT = 600
}

object DisplaySmallCondensedConfig {
    const val WEIGHT = 500
}

/**
 * Font families for the app
 * These use Compose Multiplatform resources to load fonts from commonMain/composeResources/font/
 */
@Composable
fun displayFontFamily() = FontFamily(
    Font(Res.font.syne, FontWeight.Normal),
    Font(Res.font.syne, FontWeight.Medium),
    Font(Res.font.syne, FontWeight.SemiBold),
    Font(Res.font.syne, FontWeight.Bold),
    Font(Res.font.syne, FontWeight.ExtraBold),
    Font(Res.font.syne, FontWeight.Black),
)

@Composable
fun condensedFontFamily() = FontFamily(
    Font(Res.font.archivo, FontWeight.Normal),
    Font(Res.font.archivo, FontWeight.Medium),
    Font(Res.font.archivo, FontWeight.SemiBold),
    Font(Res.font.archivo, FontWeight.Bold),
    Font(Res.font.archivo, FontWeight.ExtraBold),
)

@Composable
fun bodyFontFamily() = FontFamily(
    Font(Res.font.worksans, FontWeight.Normal),
    Font(Res.font.worksans, FontWeight.Medium),
    Font(Res.font.worksans, FontWeight.SemiBold),
    Font(Res.font.worksans, FontWeight.Bold),
)

/**
 * Creates the app typography with properly loaded fonts
 */
@Composable
fun AppTypography(): Typography {
    val displayFont = displayFontFamily()
    val bodyFont = bodyFontFamily()

    return Typography(
        // Display styles - expressive Syne font
        displayLarge = TextStyle(
            fontFamily = displayFont,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 57.sp,
            lineHeight = 64.sp,
            letterSpacing = (-0.25).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = displayFont,
            fontWeight = FontWeight.Bold,
            fontSize = 45.sp,
            lineHeight = 52.sp,
            letterSpacing = 0.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = displayFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 36.sp,
            lineHeight = 44.sp,
            letterSpacing = 0.sp,
        ),
        // Headline styles
        headlineLarge = TextStyle(
            fontFamily = displayFont,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 40.sp,
            letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = displayFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            lineHeight = 36.sp,
            letterSpacing = 0.sp,
        ),
        headlineSmall = TextStyle(
            fontFamily = displayFont,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.sp,
        ),
        // Title styles
        titleLarge = TextStyle(
            fontFamily = displayFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = displayFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.15.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = displayFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),
        // Body text with Work Sans
        bodyLarge = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.25.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.4.sp,
        ),
        // Label styles
        labelLarge = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.1.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = bodyFont,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp,
        ),
    )
}

/**
 * Extension properties for expressive typography styles
 * Use these for impactful, attention-grabbing text with bolder weights
 */
@Composable
fun Typography.displayLargeExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        fontWeight = FontWeight.Black,
    )
}

@Composable
fun Typography.displayMediumExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.ExtraBold,
    )
}

@Composable
fun Typography.displaySmallExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Typography.headlineLargeExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.ExtraBold,
    )
}

@Composable
fun Typography.headlineMediumExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Typography.headlineSmallExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun Typography.titleLargeExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Typography.titleMediumExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun Typography.titleSmallExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun Typography.labelLargeExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun Typography.labelMediumExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun Typography.labelSmallExpressive(): TextStyle {
    val displayFont = displayFontFamily()
    return TextStyle(
        fontFamily = displayFont,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium,
    )
}

/**
 * Extension properties for condensed typography styles
 * Use these for space-efficient, sleek text with Archivo font
 */
@Composable
fun Typography.displayLargeCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Typography.displayMediumCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 45.sp,
        lineHeight = 52.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun Typography.displaySmallCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun Typography.headlineLargeCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
fun Typography.headlineMediumCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 28.sp,
        lineHeight = 36.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun Typography.headlineSmallCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun Typography.titleLargeCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Composable
fun Typography.titleMediumCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun Typography.titleSmallCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun Typography.labelLargeCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun Typography.labelMediumCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium,
    )
}

@Composable
fun Typography.labelSmallCondensed(): TextStyle {
    val condensedFont = condensedFontFamily()
    return TextStyle(
        fontFamily = condensedFont,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.Medium,
    )
}
