package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelNeutral
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral20
import com.team_gori.gori.designsystem.theme.Neutral30
import com.team_gori.gori.designsystem.theme.Neutral50
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_x_circle
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hint: String = "",
    suffixText: String? = null,
    singleLine: Boolean = true,
    cornerShape: Dp = 3.dp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = MaterialTheme.typography.headlineLarge,
        shape = RoundedCornerShape(cornerShape),
        placeholder = {
            Text(
                text = hint,
                color = Neutral50,
                style = MaterialTheme.typography.headlineLarge,
            )
        },
        trailingIcon = if (suffixText.isNullOrEmpty() && isFocused && value.isNotEmpty()) {
            {
                Image(
                    painter = painterResource(Res.drawable.ic_x_circle),
                    contentDescription = "Clear text",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onValueChange("") }
                )
            }
        } else {
            null
        },
        suffix = if (!suffixText.isNullOrEmpty()) {
            {
                Text(
                    text = suffixText,
                    style = MaterialTheme.typography.headlineMedium,
                    color = LabelNeutral,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        } else {
            null
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Neutral30,
            unfocusedBorderColor = Neutral20,
            focusedTextColor = LabelNormal,
            unfocusedTextColor = LabelNormal,
            cursorColor = Neutral30,
        ),
        interactionSource = interactionSource,
        singleLine = singleLine,
    )
}