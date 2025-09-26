package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.Opacity10
import com.team_gori.gori.designsystem.theme.semanticColors
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun GoriTwoButton (
    leftImagePainter: DrawableResource,
    leftImageContentDescription: String?,
    leftEnabled: Boolean = true,
    onLeftClick: () -> Unit,
    rightImagePainter: DrawableResource,
    rightImageContentDescription: String?,
    rightEnabled: Boolean = true,
    onRightClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonModifier: Modifier = Modifier,
    buttonShape: Shape = RoundedCornerShape(8.dp),
    buttonBorder: BorderStroke = BorderStroke(1.dp, MaterialTheme.semanticColors.lineAlternative),
    buttonSize: Dp = 54.dp,
) {
    Surface(
        modifier = modifier,
        shape = buttonShape,
        border = buttonBorder,
        contentColor = Color.Transparent
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GoriSingleImageButton(
                painter = leftImagePainter,
                contentDescription = leftImageContentDescription,
                onClick = onLeftClick,
                modifier = buttonModifier,
                buttonSize = buttonSize,
                enabled = leftEnabled,
            )
            Spacer(
                modifier = Modifier.size(width = 1.dp, height = 54.dp).background(color = Opacity10)
            )
            GoriSingleImageButton(
                painter = rightImagePainter,
                contentDescription = rightImageContentDescription,
                onClick = onRightClick,
                modifier = buttonModifier,
                buttonSize = buttonSize,
                enabled = rightEnabled,
            )
        }
    }
}