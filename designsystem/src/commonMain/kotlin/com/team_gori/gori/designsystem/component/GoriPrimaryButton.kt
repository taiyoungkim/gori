package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.*

@Composable
fun GoriPrimaryButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(67.dp),
        shape = RectangleShape,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryColor,
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