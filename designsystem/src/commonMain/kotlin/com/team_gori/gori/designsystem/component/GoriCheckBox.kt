package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.BackgroundNormal
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral30
import com.team_gori.gori.designsystem.theme.Neutral40

@Composable
fun GoriCheckBox(
    onCheckChange: (Boolean) -> Unit,
    checked: Boolean,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                onCheckChange(it)
            },
            enabled = true,
            colors = CheckboxColors(
                checkedBoxColor = LabelNormal,
                checkedCheckmarkColor = BackgroundNormal,
                checkedBorderColor = LabelNormal,
                uncheckedBoxColor = Color.Transparent,
                uncheckedBorderColor = Neutral40,
                uncheckedCheckmarkColor = Color.Transparent,
                disabledCheckedBoxColor = Neutral30,
                disabledBorderColor = Neutral30,
                disabledUncheckedBoxColor = Color.Transparent,
                disabledUncheckedBorderColor = Neutral30,
                disabledIndeterminateBoxColor = Neutral30,
                disabledIndeterminateBorderColor = Neutral30,
            )
        )
        Spacer(Modifier.width(9.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = LabelNormal
        )
    }
}