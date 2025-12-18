package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import com.serranoie.app.puckperfect.core.ui.theme.headlineSmallExpressive
import com.serranoie.app.puckperfect.core.ui.theme.labelLargeExpressive
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Main wrapper for the extraction process that provides:
 * - Top App Bar with back navigation
 * - Stepped progress indicator
 * - Main content area (light background)
 * - Bottom dark section with question and action buttons
 *
 * Similar to MainWrapper but specialized for the extraction flow
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionnaireWrapper(
    currentStep: Int,
    totalSteps: Int = 4,
    questionText: String,
    onBackClick: () -> Unit,
    mainContent: @Composable () -> Unit,
    bottomActions: @Composable () -> Unit,
    bottomExtras: @Composable (() -> Unit)? = null,
    topExtras: @Composable (() -> Unit)? = null,
    previousStep: Int = currentStep
) {
    val surfaceColor = MaterialTheme.colorScheme.tertiaryContainer
    val darkSectionColor = MaterialTheme.colorScheme.surface

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize().background(darkSectionColor)
    ) {
        val screenHeight = maxHeight

        // Calculate content height ratio based on screen size
        // Smaller screens get less space for main content, more for bottom section
        val contentHeightRatio = when {
            screenHeight < 700.dp -> 0.625f  // Medium-small screens
            screenHeight < 800.dp -> 0.65f  // Medium screens
            screenHeight < 900.dp -> 0.7f  // Large screens (tablets, etc.)
            else -> 0.725f                    // Large screens (tablets, etc.)
        }

        println("QuestionnaireWrapper - Screen height: $screenHeight, Content ratio: $contentHeightRatio (${(contentHeightRatio * 100).toInt()}%)")

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.zIndex(1f)) {
                TopAppBar(
                    title = { }, navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = surfaceColor
                )
                )
                SteppedProgressIndicator(
                    currentStep = currentStep,
                    totalSteps = totalSteps,
                    modifier = Modifier.fillMaxWidth().background(surfaceColor)
                        .padding(horizontal = 24.dp, vertical = 4.dp)
                )

                topExtras?.let {
                    Box(
                        modifier = Modifier.fillMaxWidth().background(surfaceColor)
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    ) {
                        it()
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(contentHeightRatio).background(
                        color = surfaceColor,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    ).clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)),
                contentAlignment = Alignment.Center
            ) {
                mainContent()
            }

            val goingForward = currentStep > previousStep
            AnimatedContent(
                targetState = currentStep, transitionSpec = {
                    if (!goingForward) { // INVERTED: go right-to-left when going back
                        slideInHorizontally(
                            animationSpec = tween(420),
                            initialOffsetX = { it }) togetherWith slideOutHorizontally(
                            animationSpec = tween(420), targetOffsetX = { -it })
                    } else {
                        slideInHorizontally(
                            animationSpec = tween(420),
                            initialOffsetX = { -it }) togetherWith slideOutHorizontally(
                            animationSpec = tween(420), targetOffsetX = { it })
                    }
                }, label = "BottomSectionTransition"
            ) { _ ->
                Column(
                    modifier = Modifier.fillMaxWidth().background(darkSectionColor)
                        .navigationBarsPadding().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Question text
                    AnimatedContent(
                        targetState = questionText, transitionSpec = {
                            fadeIn(animationSpec = tween(230)) + scaleIn(initialScale = 0.96f) togetherWith fadeOut(
                                animationSpec = tween(230)
                            ) + scaleOut(targetScale = 1.08f)
                        }, label = "QuestionSharedElement"
                    ) { qText ->
                        Text(
                            text = qText,
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.headlineSmallExpressive(),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp).fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }

                    bottomActions()

                    bottomExtras?.invoke()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WrapperPreview() {
    PuckPerfectTheme {
        QuestionnaireWrapper(
            currentStep = 1,
            totalSteps = 4,
            questionText = "Title question",
            onBackClick = { },
            mainContent = {
                Text(
                    "Main content area\n(gram picker, ratio buttons, etc.)",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(32.dp)
                )
            },
            bottomActions = {
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Text(
                        "Action",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.labelLargeExpressive()
                    )
                }
            },
            bottomExtras = {
                Text(
                    "Optional extras go here",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            })
    }
}
