package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.InteractionDisable
import com.team_gori.gori.designsystem.theme.LabelAssistive
import com.team_gori.gori.designsystem.theme.semanticColors

@Composable
fun GoriRoundButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        shape = RoundedCornerShape(24.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.semanticColors.secondaryNormal,
            disabledContainerColor = InteractionDisable,
            disabledContentColor = LabelAssistive
        ),
    ) {
        Text(
            text = text,
            modifier = Modifier,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}