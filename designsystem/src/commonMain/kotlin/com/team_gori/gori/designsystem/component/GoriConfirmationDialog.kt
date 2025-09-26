package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.team_gori.gori.designsystem.theme.CustomTheme
import com.team_gori.gori.designsystem.theme.InteractionDisable
import com.team_gori.gori.designsystem.theme.LabelAssistive
import com.team_gori.gori.designsystem.theme.PrimaryColor
import com.team_gori.gori.designsystem.theme.semanticColors

@Composable
fun CustomAlert(
    title: String,
    message: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    dismissButtonText: String,
    onDismiss: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .background(Color.Transparent)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 24.dp),
            )
            DialogButtonRow(
                confirmButtonText = confirmButtonText,
                onConfirm = {
                    onConfirm()
                    onDismissRequest()
                },
                dismissButtonText = dismissButtonText,
                onDismiss = {
                    onDismiss()
                    onDismissRequest()
                }
            )
        }
    }
}

@Composable
fun CustomAlertDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    title: String,
    message: String,
    dismissButtonText: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (showDialog) {
        CustomTheme {
            Dialog(
                onDismissRequest = onDismissRequest,
                properties = DialogProperties(
                    usePlatformDefaultWidth = false,
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true
                )
            ) {
                CustomAlert(
                    title = title,
                    message = message,
                    dismissButtonText = dismissButtonText,
                    onDismiss = onDismiss,
                    confirmButtonText = confirmButtonText,
                    onConfirm = onConfirm,
                    onDismissRequest = onDismissRequest,
                    modifier = modifier
                )
            }
        }
    }
}

// 하단 더블 버튼
@Composable
private fun DialogButtonRow(
    confirmButtonText: String,
    onConfirm: () -> Unit,
    dismissButtonText: String,
    onDismiss: () -> Unit,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val buttonWidth = maxWidth / 2

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier
                    .width(buttonWidth),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.semanticColors.lineAlternative
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = PrimaryColor
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = dismissButtonText,
                    color = PrimaryColor,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }

            TextButton(
                onClick = onConfirm,
                modifier = Modifier
                    .width(buttonWidth),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = InteractionDisable,
                    disabledContentColor = LabelAssistive
                ),
            ) {
                Text(
                    confirmButtonText,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 5.dp)
                )
            }
        }
    }
}