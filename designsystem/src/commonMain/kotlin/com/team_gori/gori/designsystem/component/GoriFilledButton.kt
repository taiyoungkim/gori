package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.Opacity36
import com.team_gori.gori.designsystem.theme.semanticColors

@Composable
fun GoriFilledButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonColors(
            containerColor = MaterialTheme.semanticColors.primaryNormal,
            contentColor = MaterialTheme.semanticColors.onPrimary,
            disabledContainerColor = MaterialTheme.semanticColors.primaryDisabled,
            disabledContentColor = Opacity36,
        ),
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(text)
    }
}