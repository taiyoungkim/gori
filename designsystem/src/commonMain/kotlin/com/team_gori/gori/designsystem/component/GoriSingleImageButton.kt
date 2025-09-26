package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.semanticColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriSingleImageButton(
    painter: DrawableResource,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonSize: Dp = 54.dp,
    enabled: Boolean = true,
) {
    Surface(
        onClick = { if (enabled) onClick() },
        modifier = modifier.size(buttonSize),
        enabled = enabled
    ) {
        Box(
            modifier = modifier
                .clickable { if (enabled) onClick() }
                .size(buttonSize)
                .background(
                    if (enabled) Color.Transparent else MaterialTheme.semanticColors.lineAlternative
                ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(painter),
                contentDescription = contentDescription,
                contentScale = ContentScale.Fit,
            )
        }
    }
}