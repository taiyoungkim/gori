package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.ChipPurpleBackground
import com.team_gori.gori.designsystem.theme.ChipPurpleText

@Composable
fun GoriBadge(
    text: String,
    backgroundColor: Color = ChipPurpleBackground,
    textColor: Color = ChipPurpleText,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(vertical = 12.dp, horizontal = 16.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = MaterialTheme.typography.headlineLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}