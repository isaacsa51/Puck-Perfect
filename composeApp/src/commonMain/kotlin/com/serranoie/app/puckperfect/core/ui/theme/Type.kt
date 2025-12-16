package com.serranoie.app.puckperfect.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit

// Placeholder for your custom fonts
val spaceGrotesk = FontFamily.Default // Replace with actual font loading
val workSans = FontFamily.Default // Replace with actual font loading

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = spaceGrotesk,
        fontSize = 57.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 64.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = spaceGrotesk,
        fontSize = 45.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 52.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = spaceGrotesk,
        fontSize = 36.sp,
        fontWeight = FontWeight.Black,
        lineHeight = 44.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = spaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = spaceGrotesk,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = spaceGrotesk,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = workSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = workSans,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelMedium = TextStyle(
        fontFamily = workSans,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
    ),
    labelSmall = TextStyle(
        fontFamily = workSans,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        fontWeight = FontWeight.Medium,
    ),
)

// Extension functions for Condensed/Expressive Display Styles
fun TextStyle.condensed(fontFamily: FontFamily = spaceGrotesk): TextStyle =
    this.copy(fontFamily = fontFamily, fontWeight = FontWeight.Bold, letterSpacing = (-1).sp)

fun TextStyle.expressive(fontFamily: FontFamily = spaceGrotesk): TextStyle =
    this.copy(fontFamily = fontFamily, fontWeight = FontWeight.Black, letterSpacing = (2).sp)
