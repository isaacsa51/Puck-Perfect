package com.serranoie.app.puckperfect.core.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.MenuBook
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.rounded.Label
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.serranoie.app.puckperfect.core.ui.theme.PuckPerfectTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BottomBar(
    navNum: Int,
    onNavSelected: (Int) -> Unit,
    onFabClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        contentAlignment = Alignment.BottomCenter
    ) {
		Row(
			modifier =
				Modifier
					.fillMaxWidth()
					.clip(RoundedCornerShape(12.dp))
					.background(MaterialTheme.colorScheme.surface)
					.padding(vertical = 15.dp, horizontal = 15.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically,
		) {
			IconButton(onClick = { onNavSelected(0) }) {
				Icon(
					imageVector = Icons.Rounded.Label,
					contentDescription = "beans",
					tint = if (navNum == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
						alpha = 0.7f
					),
					modifier = Modifier.size(28.dp)
				)
			}
			Spacer(modifier = Modifier.width(20.dp))
			IconButton(onClick = { onNavSelected(1) }) {
				Icon(
					imageVector = Icons.Filled.CalendarMonth,
					contentDescription = "calendar",
					tint = if (navNum == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
						alpha = 0.7f
					),
					modifier = Modifier.size(28.dp)
				)
			}
			Spacer(modifier = Modifier.width(64.dp)) // Extra space for the FAB overlay
			IconButton(onClick = { onNavSelected(2) }) {
				Icon(
					imageVector = Icons.Filled.Chat,
					contentDescription = "grinders",
					tint = if (navNum == 2) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
						alpha = 0.7f
					),
					modifier = Modifier.size(28.dp)
				)
			}
			Spacer(modifier = Modifier.width(20.dp))
			IconButton(onClick = { onNavSelected(3) }) {
				Icon(
					imageVector = Icons.AutoMirrored.Rounded.MenuBook,
					contentDescription = "recipes",
					tint = if (navNum == 3) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
						alpha = 0.7f
					),
					modifier = Modifier.size(28.dp)
				)
			}
		}
		IconButton(
			onClick = onFabClick,
			modifier = Modifier
				.align(Alignment.TopCenter)
				.offset(y = (-32).dp)
				.background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(8.dp))
				.size(64.dp),
		) {
			Icon(
				imageVector = Icons.Filled.Add,
				contentDescription = "add",
				tint = MaterialTheme.colorScheme.onPrimary,
				modifier = Modifier.size(36.dp)
			)
		}
	}
}

@Preview
@Composable
fun BottomNavPreview() {
	PuckPerfectTheme {
		Box(modifier = Modifier.padding(vertical = 50.dp, horizontal = 10.dp)) {
			BottomBar(
				navNum = 0,
				onNavSelected = { /*TODO*/ },
				onFabClick = { /*TODO*/ },
				modifier = Modifier.align(Alignment.BottomCenter),
			)
		}
	}
}
