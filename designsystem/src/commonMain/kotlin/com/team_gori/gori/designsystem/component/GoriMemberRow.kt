package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral40
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_default_avatar
import gori.designsystem.generated.resources.ic_room_admin
import gori.designsystem.generated.resources.ic_more
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriMemberRow(
    memberName: String,
    isAdmin: Boolean,
    age: Int,
    gender: String,
    isMe: Boolean,
    memberAvatar: DrawableResource = if (isAdmin) Res.drawable.ic_room_admin else Res.drawable.ic_default_avatar,
    avatarSize: Int = 54,
    isMore: Boolean = true
) {
    Row {
        Image(
            painter = painterResource(memberAvatar),
            contentDescription = "avatar",
            modifier = Modifier
                .size(avatarSize.dp)
                .clickable {}
        )
        Spacer(Modifier.width(10.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (isMe) {
                    GoriMeBadge()
                    Spacer(Modifier.width(4.dp))
                }
                Text(
                    text = memberName,
                    style = MaterialTheme.typography.bodyLarge,
                    color = LabelNormal
                )
            }
            Text(
                text = "${age}•$gender",
                style = MaterialTheme.typography.labelLarge,
                color = Neutral40
            )
        }
        if (isMore) {
            Image(
                alignment = Alignment.Center,
                painter = painterResource(Res.drawable.ic_more),
                contentDescription = "more",
                modifier = Modifier
                    .clickable {

                    }
                    .align(Alignment.CenterVertically)
            )
        }
    }
}