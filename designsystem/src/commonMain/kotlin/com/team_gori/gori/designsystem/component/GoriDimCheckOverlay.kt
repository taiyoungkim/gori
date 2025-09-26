package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_check
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriDimCheckOverlay(
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(20.dp),
    dimAlpha: Float = 0.4f,
    dimColor: Color = MaterialTheme.semanticColors.secondaryNormal,
    iconSize:Dp = 30.dp,
    iconColor: Color = MaterialTheme.semanticColors.secondaryNormal,
    checkPainter: Painter = painterResource(Res.drawable.ic_check),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
    ) {
        content()

        if (isSelected) {
            Box(
                Modifier
                    .matchParentSize()
                    .background(dimColor.copy(alpha = dimAlpha))
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = checkPainter,
                    contentDescription = "선택됨",
                    alignment = Alignment.Center,
                    modifier = Modifier.size(iconSize),
                    colorFilter = ColorFilter.tint(iconColor),
                )
            }
        }
    }
}
