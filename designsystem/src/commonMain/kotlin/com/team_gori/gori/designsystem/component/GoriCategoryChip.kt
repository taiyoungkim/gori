package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.Neutral10
import com.team_gori.gori.designsystem.theme.semanticColors

@Composable
fun GoriCategoryChip (
    text: String,
) {
    Text(
        text = text,
        modifier = Modifier
            .border(width = 1.dp, color = MaterialTheme.semanticColors.lineAlternative, shape = RoundedCornerShape(size = 24.dp))
            .width(85.dp)
            .height(31.dp)
            .background(color = Neutral10, shape = RoundedCornerShape(size = 24.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        style = MaterialTheme.typography.labelLarge
    )
}