package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.team_gori.gori.designsystem.theme.LabelDisable
import com.team_gori.gori.designsystem.theme.LabelNormal

@Composable
fun GoriTextButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean,
) {
    TextButton(
        onClick = { onClick() },
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            disabledContentColor = LabelDisable,
            contentColor = LabelNormal,
            containerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
        )
    }
}