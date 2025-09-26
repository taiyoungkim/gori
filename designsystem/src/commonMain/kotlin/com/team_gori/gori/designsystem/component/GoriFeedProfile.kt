package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelAssistive
import com.team_gori.gori.designsystem.theme.LabelNormal
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_default_avatar
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriFeedProfile(
    profile: DrawableResource = Res.drawable.ic_default_avatar,
    userName: String,
    timestamp: String,
) {
    Row {
        // 프로필
        Image(
            painter = painterResource(profile),
            contentDescription = "profile",
            modifier = Modifier
                .size(40.dp)
                .clickable {}
        )
        Column {
            // 유저 이름
            Text(
                text = userName,
                style = MaterialTheme.typography.labelLarge,
                color = LabelNormal
            )
            Spacer(Modifier.width(4.dp))
            // 날짜
            Text(
                text = timestamp,
                style = MaterialTheme.typography.labelMedium,
                color = LabelAssistive
            )
        }
    }
}