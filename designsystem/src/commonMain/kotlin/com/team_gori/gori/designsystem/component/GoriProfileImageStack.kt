package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.semanticColors

@Composable
fun GoriProfileImageStack(
    modifier: Modifier = Modifier,
    painters: List<Painter?>,
    size: Dp = 56.dp,
    imageSize: Dp = 40.dp,
    contentScale: ContentScale = ContentScale.Crop,
    topBorderWidth: Dp = 2.dp,
    topBorderColor: Color = MaterialTheme.semanticColors.onSecondary,
    placeholder: Painter? = null
) {
    Box(modifier = modifier.size(size)) {
        // 아래(두 번째)
        painters.getOrNull(1) ?: placeholder
            ?.let { bottom ->
                Image(
                    painter = bottom,
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                        .clip(CircleShape)
                        .align(Alignment.BottomEnd),
                    contentScale = contentScale
                )
            }

        painters.getOrNull(0) ?: placeholder
            ?.let { top ->
                Image(
                    painter = top,
                    contentDescription = null,
                    modifier = Modifier
                        .size(imageSize)
                        .clip(CircleShape)
                        .border(topBorderWidth, topBorderColor, CircleShape)
                        .align(Alignment.TopStart),
                    contentScale = contentScale
                )
            }
    }
}