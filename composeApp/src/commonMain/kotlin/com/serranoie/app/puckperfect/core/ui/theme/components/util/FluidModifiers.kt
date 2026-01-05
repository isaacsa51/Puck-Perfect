package com.serranoie.app.puckperfect.core.ui.theme.components.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * FluidModifiers - A comprehensive rem-like system with Golden Ratio scaling
 *
 * This file provides a fluid, scalable UI system that responds to user font size
 * preferences, similar to CSS rem units. It combines multiple approaches:
 *
 * 1. Dp-based: Direct scaling with Dp values
 * 2. Rem-based: Uses font size as base unit (1 rem = 16sp by default)
 * 3. ScaledSp: Direct sp-to-dp conversions
 * 4. Animations: Smooth transitions for fluid layouts
 * 5. Golden Ratio: Proportional scaling using φ ≈ 1.618 for visual harmony
 * 6. Optical Symmetry: Balanced visual weight through modular scale
 *
 * Golden Ratio Implementation:
 * - Typography: Each level is φ times the previous (e.g., heading = body × 1.618)
 * - Spacing: Follows φ progression for harmonious rhythm
 * - Shapes: Width/height ratios follow φ for pleasing proportions
 *
 * These modifiers help create responsive layouts that adapt to:
 * - User font scale preferences (accessibility settings)
 * - Device screen density
 * - Different phone sizes
 *
 * Usage:
 * ```
 * // Dp-based approach
 * Box(modifier = Modifier.fluidPadding(16.dp))
 *
 * // Rem-based approach
 * Box(modifier = Modifier.fluidPadding(1f))  // 1 rem = 16sp
 *
 * // ScaledSp approach
 * Box(modifier = Modifier.padding(16.scaledSp()))
 *
 * // Using FluidSpacing object with Golden Ratio
 * Row(horizontalArrangement = Arrangement.spacedBy(FluidSpacing.medium()))
 * ```
 */

/**
 * The Golden Ratio constant (φ ≈ 1.618).
 * Used for proportional scaling throughout the design system.
 *
 * ## Golden Ratio in UI Design
 *
 * The Golden Ratio creates natural, harmonious proportions found in nature,
 * art, and architecture. In this system:
 *
 * ### Typography Scale
 * - Each level is φ times the previous
 * - Body: 16sp (base)
 * - Subtitle: 16 × φ ≈ 25.9sp
 * - Title: 16 × φ² ≈ 41.9sp
 * - Headline: 16 × φ³ ≈ 67.8sp
 *
 * ### Spacing Scale
 * - Small: 1 / φ ≈ 0.618rem (≈10sp)
 * - Medium: 1rem (16sp) - base
 * - Large: φ ≈ 1.618rem (≈26sp)
 * - Extra Large: φ² ≈ 2.618rem (≈42sp)
 *
 * ### Optical Symmetry
 * - Container width = height × φ
 * - Horizontal padding = vertical padding × φ
 * - Creates balanced, aesthetically pleasing layouts
 *
 * ### Modular Scale
 * Use positive/negative steps from any base:
 * - Step +1: multiply by φ
 * - Step -1: divide by φ
 * - Step +2: multiply by φ²
 * - Step -2: divide by φ²
 *
 * ## Where Golden Ratio is Applied
 *
 * ### Automatic Golden Ratio Scaling
 * These components ALWAYS use Golden Ratio:
 * - `FluidSpacing` object (extraSmall, small, medium, large, extraLarge, huge)
 * - `FluidTypography` object (tiny, small, body, subtitle, title, headline, display)
 * - `goldenRatioSize()` modifier
 * - `goldenRatioPadding()` modifier
 * - `modularScale()` function
 * - `responsiveRemMultiplier()` for different screen sizes
 *
 * ### Optional Golden Ratio Scaling
 * These modifiers support `useGoldenRatio: Boolean` parameter:
 * - `fluidPadding(size, useGoldenRatio = true)`
 * - `fluidSize(size, useGoldenRatio = true)`
 * - `fluidWidth(width, useGoldenRatio = true)`
 * - `fluidHeight(height, useGoldenRatio = true)`
 * - `fluidSpace(space, useGoldenRatio = true)`
 *
 * ### No Golden Ratio (Font Scale Only)
 * These functions use simple font scale without Golden Ratio:
 * - Basic rem-based functions: `fluidPadding(Float)`, `fluidSize(Float)`
 * - Base conversion functions: `scaledSp()`, `rem()`
 * - Capped functions: `fluidHeightCapped()`, `fluidWidthCapped()`
 *
 * ## When to Use Golden Ratio
 *
 * ✅ USE Golden Ratio (FluidSpacing) for:
 * - Type hierarchy (use FluidTypography)
 * - Page-level layouts and major sections
 * - Hero sections and feature cards
 * - Marketing/landing pages
 * - Creating visual rhythm and harmony
 * - Generous whitespace designs
 *
 * ❌ DON'T use Golden Ratio - USE CompactSpacing for:
 * - Dense information cards (like BeanItemCard)
 * - List items and table rows
 * - Navigation menus and toolbars
 * - Forms with multiple fields
 * - Mobile-first compact layouts
 * - Data-heavy interfaces
 *
 * ## Spacing System Comparison
 *
 * | Size | FluidSpacing (φ-based) | CompactSpacing (linear) |
 * |------|------------------------|-------------------------|
 * | extraSmall | ~6sp (1/φ²) | 4sp (0.25rem) |
 * | small | ~10sp (1/φ) | 8sp (0.5rem) |
 * | medium | 16sp (1rem) | 12sp (0.75rem) |
 * | large | ~26sp (φ) | 16sp (1rem) |
 * | extraLarge | ~42sp (φ²) | 24sp (1.5rem) |
 *
 * **Rule of thumb:** Use FluidSpacing for "breathing room" designs,
 * CompactSpacing for information-dense UIs.
 */
const val GOLDEN_RATIO = 1.618f

/**
 * Converts an Int representing sp to a Dp value that scales with text size.
 * This is the key to rem-like behavior in Compose.
 *
 * Usage: 16.scaledSp() returns a Dp that grows/shrinks with font settings
 */
@Composable
fun Int.scaledSp(): Dp =
    with(LocalDensity.current) {
        this@scaledSp.sp.toDp()
    }

/**
 * Converts a Float representing sp to a Dp value that scales with text size.
 */
@Composable
fun Float.scaledSp(): Dp =
    with(LocalDensity.current) {
        this@scaledSp.sp.toDp()
    }

/**
 * Converts a Double representing sp to a Dp value that scales with text size.
 */
@Composable
fun Double.scaledSp(): Dp =
    with(LocalDensity.current) {
        this@scaledSp.sp.toDp()
    }

/**
 * Extension to create rem-like units based on a base unit size.
 *
 * Example: 1.rem() = 16.dp (scaled), 2.rem() = 32.dp (scaled)
 *
 * @param baseUnit Base unit size in sp (default 16)
 */
@Composable
fun Int.rem(baseUnit: Int = 16): Dp = (this * baseUnit).scaledSp()

/**
 * Float version of rem conversion.
 */
@Composable
fun Float.rem(baseUnit: Int = 16): Dp = (this * baseUnit).scaledSp()

/**
 * Double version of rem conversion.
 */
@Composable
fun Double.rem(baseUnit: Int = 16): Dp = (this * baseUnit).scaledSp()

/**
 * Applies fluid padding that scales with font scale
 * @param size The base padding size that will be scaled
 * @param useGoldenRatio If true, applies Golden Ratio scaling on top of font scale
 */
@Composable
fun Modifier.fluidPadding(
    size: Dp,
    useGoldenRatio: Boolean = true,
): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val multiplier = if (useGoldenRatio) fontScale * GOLDEN_RATIO else fontScale
    val scaledSize = size * multiplier
    return this.padding(scaledSize)
}

/**
 * Applies fluid padding with different values for horizontal and vertical
 * @param horizontal Base horizontal padding
 * @param vertical Base vertical padding
 * @param useGoldenRatio If true, applies Golden Ratio scaling on top of font scale
 */
@Composable
fun Modifier.fluidPadding(
    horizontal: Dp = 0.dp,
    vertical: Dp = 0.dp,
    useGoldenRatio: Boolean = true,
): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val multiplier = if (useGoldenRatio) fontScale * GOLDEN_RATIO else fontScale
    val scaledHorizontal = horizontal * multiplier
    val scaledVertical = vertical * multiplier
    return this.padding(horizontal = scaledHorizontal, vertical = scaledVertical)
}

/**
 * Applies fluid padding with individual values for each side
 * @param start Base start padding
 * @param top Base top padding
 * @param end Base end padding
 * @param bottom Base bottom padding
 * @param useGoldenRatio If true, applies Golden Ratio scaling on top of font scale
 */
@Composable
fun Modifier.fluidPadding(
    start: Dp = 0.dp,
    top: Dp = 0.dp,
    end: Dp = 0.dp,
    bottom: Dp = 0.dp,
    useGoldenRatio: Boolean = true,
): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val multiplier = if (useGoldenRatio) fontScale * GOLDEN_RATIO else fontScale
    return this.padding(
        start = start * multiplier,
        top = top * multiplier,
        end = end * multiplier,
        bottom = bottom * multiplier,
    )
}

/**
 * Applies fluid size (both width and height) that scales with font scale
 * @param size The base size that will be scaled
 * @param useGoldenRatio If true, applies Golden Ratio scaling on top of font scale
 */
@Composable
fun Modifier.fluidSize(
    size: Dp,
    useGoldenRatio: Boolean = true,
): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val multiplier = if (useGoldenRatio) fontScale * GOLDEN_RATIO else fontScale
    val scaledSize = size * multiplier
    return this.size(scaledSize)
}

/**
 * Applies fluid size with different width and height values
 * @param width Base width
 * @param height Base height
 * @param useGoldenRatio If true, applies Golden Ratio scaling on top of font scale
 */
@Composable
fun Modifier.fluidSize(
    width: Dp,
    height: Dp,
    useGoldenRatio: Boolean = true,
): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val multiplier = if (useGoldenRatio) fontScale * GOLDEN_RATIO else fontScale
    val scaledWidth = width * multiplier
    val scaledHeight = height * multiplier
    return this.size(width = scaledWidth, height = scaledHeight)
}

/**
 * Applies fluid width that scales with font scale
 * @param width The base width that will be scaled
 * @param useGoldenRatio If true, applies Golden Ratio scaling on top of font scale
 */
@Composable
fun Modifier.fluidWidth(
    width: Dp,
    useGoldenRatio: Boolean = true,
): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val multiplier = if (useGoldenRatio) fontScale * GOLDEN_RATIO else fontScale
    val scaledWidth = width * multiplier
    return this.width(scaledWidth)
}

/**
 * Applies fluid height that scales with font scale
 * @param height The base height that will be scaled
 * @param useGoldenRatio If true, applies Golden Ratio scaling on top of font scale
 */
@Composable
fun Modifier.fluidHeight(
    height: Dp,
    useGoldenRatio: Boolean = true,
): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val multiplier = if (useGoldenRatio) fontScale * GOLDEN_RATIO else fontScale
    val scaledHeight = height * multiplier
    return this.height(scaledHeight)
}

/**
 * Applies a fluid height with a maximum constraint
 * Useful for buttons and components that shouldn't grow too large
 * @param baseHeight The base height
 * @param maxHeight The maximum allowed height
 */
@Composable
fun Modifier.fluidHeightCapped(
    baseHeight: Dp,
    maxHeight: Dp,
): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val scaledHeight = (baseHeight * fontScale).coerceAtMost(maxHeight)
    return this.height(scaledHeight)
}

/**
 * Applies a fluid width with a maximum constraint
 * Useful for components that shouldn't grow too large
 * @param baseWidth The base width
 * @param maxWidth The maximum allowed width
 */
@Composable
fun Modifier.fluidWidthCapped(
    baseWidth: Dp,
    maxWidth: Dp,
): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val scaledWidth = (baseWidth * fontScale).coerceAtMost(maxWidth)
    return this.width(scaledWidth)
}

/**
 * Applies fluid spacing for arrangement gaps
 * Useful with Row/Column arrangement spacing
 * @param space The base spacing value
 * @param useGoldenRatio If true, applies Golden Ratio scaling on top of font scale
 * @return Scaled spacing value as Dp
 */
@Composable
fun fluidSpace(
    space: Dp,
    useGoldenRatio: Boolean = true,
): Dp {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val multiplier = if (useGoldenRatio) fontScale * GOLDEN_RATIO else fontScale
    return space * multiplier
}

/**
 * Applies capped fluid spacing for arrangement gaps
 * @param space The base spacing value
 * @param maxSpace The maximum allowed spacing
 * @return Scaled and capped spacing value as Dp
 */
@Composable
fun fluidSpaceCapped(
    space: Dp,
    maxSpace: Dp,
): Dp {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    return (space * fontScale).coerceAtMost(maxSpace)
}

/**
 * Provides a fluid scale factor based on font scale
 * Useful for custom scaling logic
 * @return The current font scale factor
 */
@Composable
fun fluidScale(): Float {
    val density = LocalDensity.current
    return density.fontScale
}

/**
 * Alias for fluidScale() - helper to get the current scaled density factor.
 * Useful for debugging or custom scaling logic.
 */
@Composable
fun getScaleFactor(): Float = fluidScale()

/**
 * Creates a fluid Dp value that scales between min and max based on font scale.
 * Useful for creating responsive spacing that grows but has limits.
 *
 * @param base The base dp value at normal font scale
 * @param min Minimum dp value (at very small font scale)
 * @param max Maximum dp value (at very large font scale)
 */
@Composable
fun fluidDp(
    base: Int,
    min: Int = base / 2,
    max: Int = base * 2,
): Dp {
    val scale = getScaleFactor()
    val scaledValue = (base * scale).toInt().coerceIn(min, max)
    return scaledValue.dp
}

/**
 * Creates a fluid padding modifier using rem units that scales with text size.
 *
 * @param all Padding for all sides (in rem units, 1f = 16sp)
 */
@Composable
fun Modifier.fluidPadding(all: Float): Modifier = this.padding(all.rem())

/**
 * Creates a fluid size (equal width and height) using rem units.
 *
 * @param size Size in rem units (1f = 16sp)
 */
@Composable
fun Modifier.fluidSize(size: Float): Modifier = this.size(size.rem())

/**
 * Creates a fluid size with different width and height using rem units.
 *
 * @param width Width in rem units
 * @param height Height in rem units
 */
@Composable
fun Modifier.fluidSize(
    width: Float,
    height: Float,
): Modifier =
    this
        .width(width.rem())
        .height(height.rem())

/**
 * Creates a fluid width using rem units.
 *
 * @param width Width in rem units
 */
@Composable
fun Modifier.fluidWidth(width: Float): Modifier = this.width(width.rem())

/**
 * Creates a fluid height using rem units.
 *
 * @param height Height in rem units
 */
@Composable
fun Modifier.fluidHeight(height: Float): Modifier = this.height(height.rem())

/**
 * Creates a fluid margin modifier using rem units (external spacing).
 * Note: In Compose, margin is implemented using padding.
 *
 * @param all Margin for all sides (in rem units)
 */
@Composable
fun Modifier.fluidMargin(all: Float = 1f): Modifier = this.padding(all.rem())

/**
 * Creates a fluid margin modifier with horizontal and vertical values.
 *
 * @param horizontal Horizontal margin (in rem units)
 * @param vertical Vertical margin (in rem units)
 */
@Composable
fun Modifier.fluidMargin(
    horizontal: Float = 0f,
    vertical: Float = 0f,
): Modifier = this.padding(horizontal = horizontal.rem(), vertical = vertical.rem())

/**
 * Creates a fluid margin modifier with all sides specified.
 *
 * @param start Start margin (in rem units)
 * @param top Top margin (in rem units)
 * @param end End margin (in rem units)
 * @param bottom Bottom margin (in rem units)
 */
@Composable
fun Modifier.fluidMargin(
    start: Float = 0f,
    top: Float = 0f,
    end: Float = 0f,
    bottom: Float = 0f,
): Modifier =
    this.padding(
        start = start.rem(),
        top = top.rem(),
        end = end.rem(),
        bottom = bottom.rem(),
    )

/**
 * Creates a fluid minimum size constraint using rem units.
 *
 * @param minWidth Minimum width in rem units
 * @param minHeight Minimum height in rem units
 */
@Composable
fun Modifier.fluidMinSize(
    minWidth: Float = 0f,
    minHeight: Float = 0f,
): Modifier =
    this
        .widthIn(min = if (minWidth > 0f) minWidth.rem() else Dp.Unspecified)
        .heightIn(min = if (minHeight > 0f) minHeight.rem() else Dp.Unspecified)

/**
 * Creates a fluid maximum size constraint using rem units.
 *
 * @param maxWidth Maximum width in rem units
 * @param maxHeight Maximum height in rem units
 */
@Composable
fun Modifier.fluidMaxSize(
    maxWidth: Float = Float.MAX_VALUE,
    maxHeight: Float = Float.MAX_VALUE,
): Modifier =
    this
        .widthIn(max = if (maxWidth < Float.MAX_VALUE) maxWidth.rem() else Dp.Unspecified)
        .heightIn(max = if (maxHeight < Float.MAX_VALUE) maxHeight.rem() else Dp.Unspecified)

/**
 * Creates a width modifier that uses a percentage of the parent width.
 * Similar to CSS percentage widths.
 *
 * @param fraction Fraction of parent width (0.0 to 1.0)
 */
fun Modifier.widthPercent(fraction: Float): Modifier = this.fillMaxWidth(fraction)

/**
 * Creates a fluid width that's responsive to screen size.
 * Combines percentage-based width with min/max constraints.
 *
 * @param fraction Base fraction of parent width
 * @param minRem Minimum width in rem units
 * @param maxRem Maximum width in rem units
 */
@Composable
fun Modifier.responsiveWidth(
    fraction: Float,
    minRem: Float = 0f,
    maxRem: Float = Float.MAX_VALUE,
): Modifier =
    this
        .fillMaxWidth(fraction)
        .widthIn(
            min = if (minRem > 0f) minRem.rem() else Dp.Unspecified,
            max = if (maxRem < Float.MAX_VALUE) maxRem.rem() else Dp.Unspecified,
        )

/**
 * Responsive container that adapts based on available width.
 * Uses BoxWithConstraints to make layout decisions based on container size.
 *
 * @param content Content that receives the constraints scope
 */
@Composable
fun ResponsiveContainer(
    modifier: Modifier = Modifier,
    content: @Composable BoxWithConstraintsScope.() -> Unit,
) {
    BoxWithConstraints(modifier = modifier) {
        content()
    }
}

/**
 * Helper to determine if the current container is in "compact" mode (< 600dp).
 * Useful for responsive layouts.
 */
val BoxWithConstraintsScope.isCompact: Boolean
    get() = maxWidth < 600.dp

/**
 * Helper to determine if the current container is in "medium" mode (600-840dp).
 */
val BoxWithConstraintsScope.isMedium: Boolean
    get() = maxWidth in 600.dp..840.dp

/**
 * Helper to determine if the current container is in "expanded" mode (> 840dp).
 */
val BoxWithConstraintsScope.isExpanded: Boolean
    get() = maxWidth > 840.dp

/**
 * Gets a responsive rem multiplier based on screen width using Golden Ratio.
 * Larger screens scale up following φ proportions for visual consistency.
 * - Compact (< 600dp): 1.0 (base)
 * - Medium (600-840dp): 1.0 / φ⁰·⁵ ≈ 1.272
 * - Expanded (> 840dp): φ⁰·⁵ ≈ 1.618
 */
@Composable
fun BoxWithConstraintsScope.responsiveRemMultiplier(): Float =
    when {
        isCompact -> 1.0f

        isMedium -> kotlin.math.sqrt(GOLDEN_RATIO.toDouble()).toFloat()

        // φ^0.5
        isExpanded -> GOLDEN_RATIO

        else -> 1.0f
    }

/**
 * Fluid spacing system using Golden Ratio for harmonious proportions.
 * These values scale with the user's font size preference and follow
 * the Golden Ratio (φ ≈ 1.618) for visual balance.
 *
 * Base unit: 1rem = 16sp
 * Each level is approximately φ times the previous level.
 *
 * Usage:
 * ```
 * Column(verticalArrangement = Arrangement.spacedBy(FluidSpacing.medium()))
 * Box(modifier = Modifier.padding(FluidSpacing.large()))
 * ```
 */
object FluidSpacing {
    /**
     * Extra small spacing: ~6.1sp (0.382 rem)
     * Base unit / φ²
     * Use for minimal gaps in tight layouts
     */
    @Composable
    fun extraSmall(): Dp = (1.0 / (GOLDEN_RATIO * GOLDEN_RATIO)).rem()

    /**
     * Small spacing: ~9.9sp (0.618 rem)
     * Base unit / φ
     * Use for compact component spacing
     */
    @Composable
    fun small(): Dp = (1.0 / GOLDEN_RATIO).rem()

    /**
     * Medium spacing: 16sp (1 rem) - Base unit
     * The foundation of the modular scale
     * Use for standard component spacing
     */
    @Composable
    fun medium(): Dp = 1.rem()

    /**
     * Large spacing: ~25.9sp (1.618 rem)
     * Base unit × φ
     * Use for section spacing
     */
    @Composable
    fun large(): Dp = GOLDEN_RATIO.rem()

    /**
     * Extra large spacing: ~41.9sp (2.618 rem)
     * Base unit × φ²
     * Use for major section breaks
     */
    @Composable
    fun extraLarge(): Dp = (GOLDEN_RATIO * GOLDEN_RATIO).rem()

    /**
     * Huge spacing: ~67.8sp (4.236 rem)
     * Base unit × φ³
     * Use for page-level spacing
     */
    @Composable
    fun huge(): Dp = (GOLDEN_RATIO * GOLDEN_RATIO * GOLDEN_RATIO).rem()
}

/**
 * Compact spacing system without Golden Ratio.
 * Uses simple linear progression for tight, condensed layouts.
 * Perfect for cards, lists, and dense information displays.
 *
 * Usage:
 * ```
 * // Use CompactSpacing for tight card layouts
 * Column(verticalArrangement = Arrangement.spacedBy(CompactSpacing.small()))
 * ```
 */
object CompactSpacing {
    /**
     * Extra small spacing: 4sp (0.25 rem)
     * Minimal gap for very tight layouts
     */
    @Composable
    fun extraSmall(): Dp = 0.25.rem()

    /**
     * Small spacing: 8sp (0.5 rem)
     * Standard tight spacing
     */
    @Composable
    fun small(): Dp = 0.5.rem()

    /**
     * Medium spacing: 12sp (0.75 rem)
     * Moderate compact spacing
     */
    @Composable
    fun medium(): Dp = 0.75.rem()

    /**
     * Large spacing: 16sp (1 rem)
     * Base unit for compact layouts
     */
    @Composable
    fun large(): Dp = 1.rem()

    /**
     * Extra large spacing: 24sp (1.5 rem)
     * Larger gaps in compact layouts
     */
    @Composable
    fun extraLarge(): Dp = 1.5.rem()
}

/**
 * Fluid typography sizes based on Golden Ratio modular scale.
 * Each size is approximately φ (1.618) times larger than the previous,
 * creating harmonious type hierarchy.
 *
 * Base: 16sp (body text)
 * Each level up: size × φ
 * Each level down: size / φ
 *
 * Usage:
 * ```
 * Text("Hello", fontSize = FluidTypography.body)
 * Text("Heading", fontSize = FluidTypography.headline)
 * ```
 */
object FluidTypography {
    /**
     * Tiny: ~6.1sp (body / φ²)
     * For micro-copy and metadata
     */
    val tiny: TextUnit = (16.0 / (GOLDEN_RATIO * GOLDEN_RATIO)).sp

    /**
     * Small: ~9.9sp (body / φ)
     * For captions and helper text
     */
    val small: TextUnit = (16.0 / GOLDEN_RATIO).sp

    /**
     * Body: 16sp (base unit)
     * Primary reading size
     */
    val body: TextUnit = 16.sp

    /**
     * Subtitle: ~25.9sp (body × φ)
     * For emphasized content
     */
    val subtitle: TextUnit = (16.0 * GOLDEN_RATIO).sp

    /**
     * Title: ~41.9sp (body × φ²)
     * For section headings
     */
    val title: TextUnit = (16.0 * GOLDEN_RATIO * GOLDEN_RATIO).sp

    /**
     * Headline: ~67.8sp (body × φ³)
     * For page titles
     */
    val headline: TextUnit = (16.0 * GOLDEN_RATIO * GOLDEN_RATIO * GOLDEN_RATIO).sp

    /**
     * Display: ~109.7sp (body × φ⁴)
     * For hero content and highlights
     */
    val display: TextUnit = (16.0 * GOLDEN_RATIO * GOLDEN_RATIO * GOLDEN_RATIO * GOLDEN_RATIO).sp
}

/**
 * Animates content size changes with a fluid, spring-based animation.
 * Perfect for components that expand/collapse with text scaling.
 */
fun Modifier.fluidAnimateContentSize(): Modifier = this.animateContentSize()

/**
 * Animated spacer that smoothly transitions between heights.
 * Height scales with font size.
 *
 * @param targetHeight Target height in rem units
 * @param animationSpec Animation specification
 */
@Composable
fun AnimatedFluidSpacer(
    targetHeight: Float,
    animationSpec: AnimationSpec<Dp> =
        spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
) {
    val height by animateDpAsState(
        targetValue = targetHeight.rem(),
        animationSpec = animationSpec,
        label = "fluid_spacer_animation",
    )
    Spacer(modifier = Modifier.height(height))
}

/**
 * Animated visibility that fades and expands vertically.
 * Works great with fluid layouts.
 *
 * @param visible Whether content should be visible
 * @param modifier Modifier to apply to the animated visibility
 * @param content Content to show/hide
 */
@Composable
fun FluidAnimatedVisibility(
    visible: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter =
            fadeIn(
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
            ) +
                expandVertically(
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                ),
        exit =
            fadeOut(
                animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy),
            ) +
                shrinkVertically(
                    animationSpec = spring(dampingRatio = Spring.DampingRatioNoBouncy),
                ),
    ) {
        content()
    }
}

/**
 * Remember a fluid dp value that animates between scale changes.
 * Useful for custom animated components.
 *
 * @param targetRem Target size in rem units
 * @param animationSpec Animation specification
 * @return Animated Dp value
 */
@Composable
fun rememberAnimatedFluidDp(
    targetRem: Float,
    animationSpec: AnimationSpec<Dp> = spring(),
): Dp {
    val target = targetRem.rem()
    return animateDpAsState(
        targetValue = target,
        animationSpec = animationSpec,
        label = "animated_fluid_dp",
    ).value
}

/**
 * Creates a Golden Ratio sized box where width = height × φ.
 * Perfect for creating harmonious rectangular containers.
 *
 * @param height Base height in Dp
 * @return Modifier with width = height × φ and specified height
 */
@Composable
fun Modifier.goldenRatioSize(height: Dp): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val scaledHeight = height * fontScale
    val scaledWidth = scaledHeight * GOLDEN_RATIO
    return this.size(width = scaledWidth, height = scaledHeight)
}

/**
 * Creates a Golden Ratio sized box using rem units.
 * Width will be height × φ for pleasing proportions.
 *
 * @param heightRem Base height in rem units
 * @return Modifier with golden ratio proportions
 */
@Composable
fun Modifier.goldenRatioSize(heightRem: Float): Modifier {
    val height = heightRem.rem()
    val width = height * GOLDEN_RATIO
    return this.size(width = width, height = height)
}

/**
 * Applies Golden Ratio padding where horizontal = vertical × φ.
 * Creates optical symmetry in padded containers.
 *
 * @param vertical Base vertical padding
 * @return Modifier with golden ratio padding
 */
@Composable
fun Modifier.goldenRatioPadding(vertical: Dp): Modifier {
    val density = LocalDensity.current
    val fontScale = density.fontScale
    val scaledVertical = vertical * fontScale
    val scaledHorizontal = scaledVertical * GOLDEN_RATIO
    return this.padding(horizontal = scaledHorizontal, vertical = scaledVertical)
}

/**
 * Applies Golden Ratio padding using rem units.
 *
 * @param verticalRem Vertical padding in rem units
 * @return Modifier with horizontal = vertical × φ
 */
@Composable
fun Modifier.goldenRatioPadding(verticalRem: Float): Modifier {
    val vertical = verticalRem.rem()
    val horizontal = vertical * GOLDEN_RATIO
    return this.padding(horizontal = horizontal, vertical = vertical)
}

/**
 * Gets the next value up in the Golden Ratio scale.
 * Useful for creating harmonious progressions.
 *
 * @param value Current value
 * @return Value × φ
 */
fun goldenRatioUp(value: Float): Float = value * GOLDEN_RATIO

/**
 * Gets the next value down in the Golden Ratio scale.
 * Useful for creating harmonious progressions.
 *
 * @param value Current value
 * @return Value / φ
 */
fun goldenRatioDown(value: Float): Float = value / GOLDEN_RATIO

/**
 * Creates a modular scale value based on steps from a base.
 * Positive steps multiply by φ, negative steps divide by φ.
 *
 * @param base Base value in rem units (default 1.0)
 * @param steps Number of steps (can be negative)
 * @return Scaled value using Golden Ratio
 */
@Composable
fun modularScale(
    base: Float = 1.0f,
    steps: Int,
): Dp {
    val multiplier =
        when {
            steps > 0 -> {
                var result = 1.0
                repeat(steps) { result *= GOLDEN_RATIO }
                result
            }

            steps < 0 -> {
                var result = 1.0
                repeat(-steps) { result /= GOLDEN_RATIO }
                result
            }

            else -> {
                1.0
            }
        }
    return (base * multiplier).rem()
}
