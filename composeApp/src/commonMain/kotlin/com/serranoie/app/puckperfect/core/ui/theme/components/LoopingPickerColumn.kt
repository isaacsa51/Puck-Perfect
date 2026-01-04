package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun LoopingPickerColumn(
    choices: List<String>,
    selectedIndex: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    visibleCount: Int = 5,
    itemHeight: Dp = 48.dp,
    cornerRadius: Dp = 10.dp,
    highlightColor: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.primary,
) {
    require(choices.size > 1) { "Need at least 2 choices for looping picker." }
    val itemCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(
        initialPage = itemCount / 2 - itemCount / 2 % choices.size + selectedIndex,
        initialPageOffsetFraction = 0f,
        pageCount = { itemCount })
    val coroutineScope = rememberCoroutineScope()
    val pickerHeight = itemHeight * visibleCount

    LaunchedEffect(selectedIndex) {
        val target = itemCount / 2 - itemCount / 2 % choices.size + selectedIndex
        coroutineScope.launch { pagerState.scrollToPage(target) }
    }

    Box(
        modifier = modifier.height(pickerHeight).clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .border(2.dp, borderColor, RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        VerticalPager(
            state = pagerState,
            contentPadding = PaddingValues(vertical = pickerHeight / 2 - itemHeight / 2)
        ) { page ->
            val index = page % choices.size
            val isCenter = page == pagerState.currentPage
            val selectionCapsuleColor = MaterialTheme.colorScheme.tertiaryFixed.copy(alpha = 0.3f)
            if (isCenter) {
                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(selectionCapsuleColor)
                        .border(3.dp, Color.Black, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = choices[index].uppercase(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            letterSpacing = 2.sp
                        ),
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                Text(
                    text = choices[index].uppercase(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.32f),
                        letterSpacing = 2.sp
                    ),
                    maxLines = 1,
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                )
            }
        }
        Box(
            Modifier.align(Alignment.Center).height(itemHeight).fillMaxWidth()
                .border(2.dp, highlightColor, RoundedCornerShape(cornerRadius))
        ) {}
    }
    LaunchedEffect(pagerState.currentPage) {
        val idx = pagerState.currentPage % choices.size
        if (idx != selectedIndex) onValueChange(idx)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDateSlider() {
    PuckPerfectTheme {
        LoopingPickerColumn(
            choices = listOf("1", "2", "3", "4", "5"),
            selectedIndex = 2,
            onValueChange = {},
        )
    }
}
