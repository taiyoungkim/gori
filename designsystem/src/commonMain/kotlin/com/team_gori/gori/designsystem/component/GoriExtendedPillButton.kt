package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_plus
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriExtendedPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: Painter? = painterResource(Res.drawable.ic_plus),
    containerColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary,
    contentColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onPrimary,
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(28.dp), // 둥근 알약
        icon = {
            if (icon != null) {
                Icon(painter = icon, contentDescription = null)
                Spacer(Modifier.width(6.dp))
            }
        },
        text = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge // 버튼 내 텍스트 톤과 맞춤
            )
        },
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
    )
}
