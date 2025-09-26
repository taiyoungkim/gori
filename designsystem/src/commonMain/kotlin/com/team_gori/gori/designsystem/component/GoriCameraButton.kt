package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.Neutral40
import com.team_gori.gori.designsystem.theme.Opacity10
import com.team_gori.gori.designsystem.theme.PrimaryColor
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_camera
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriCameraButton(
    onClick: () -> Unit,
    totalCount: Int = 0,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clickable(
                onClick = onClick,
            )
            .border(
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
                color = Opacity10
            )
            .padding(vertical = 16.dp, horizontal = 16.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_camera),
                contentDescription = "Camera icon",
                modifier = Modifier
                    .size(30.dp)
                    .padding(bottom = 6.dp)
            )
            GoriMultiColorText(
                Pair(totalCount.toString(), if (totalCount > 0) PrimaryColor else Neutral40),
                Pair("/10", Neutral40),
            )
        }
    }
}
