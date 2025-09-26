package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.Neutral50
import com.team_gori.gori.designsystem.theme.semanticColors

@Composable
fun GoriChatRoomDateSeparatorChip(
    text: String,
    modifier: Modifier,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        FilterChip(
            selected = false,
            onClick = {},
            modifier = modifier.align(Alignment.Center),
            label = {
                Text(
                    text = text,
                    color = Neutral50,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 12.dp)
                )
            },
            colors = FilterChipDefaults.filterChipColors(
                MaterialTheme.semanticColors.lineAlternative
            ),
            border = null,
            shape = RoundedCornerShape(24.dp),
        )
    }
}