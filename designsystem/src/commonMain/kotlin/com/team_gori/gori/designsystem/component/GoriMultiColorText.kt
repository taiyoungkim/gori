package com.team_gori.gori.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun GoriMultiColorText(
    vararg textWithColors: Pair<String, Color>,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.labelMedium
) {
    Text(buildAnnotatedString {
        textWithColors.forEach { (text, color) ->
            withStyle(style = SpanStyle(
                color = color
            )
            ) {
                append(text)
            }
        }
    },
        style = style,
        modifier = modifier
        )
}