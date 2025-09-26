package com.team_gori.gori.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.team_gori.gori.designsystem.theme.LabelNormal

@Composable
fun GoriTitleText(
    text: String,
    color: Color = LabelNormal,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        color = color,
        modifier = modifier
    )
}