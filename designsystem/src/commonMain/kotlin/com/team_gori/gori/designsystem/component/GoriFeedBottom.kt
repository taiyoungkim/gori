package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelNormal
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_comment
import gori.designsystem.generated.resources.ic_like
import gori.designsystem.generated.resources.ic_share
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriFeedBottom (
    like: Int,
    comment: Int,
    isLike: Boolean = false,
    isComment: Boolean = false,
    onLike: () -> Unit,
    onComment: () -> Unit,
    onShare: () -> Unit,
) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.clickable { onLike() },
            painter = painterResource(Res.drawable.ic_like),
            contentDescription = "좋아요",
            contentScale = ContentScale.Fit,
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = like.toString(),
            style = MaterialTheme.typography.labelLarge,
            color = LabelNormal
        )
        Spacer(Modifier.width(12.dp))
        Image(
            modifier = Modifier.clickable { onComment() },
            painter = painterResource(Res.drawable.ic_comment),
            contentDescription = "댓글",
            contentScale = ContentScale.Fit,
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = comment.toString(),
            style = MaterialTheme.typography.labelLarge,
            color = LabelNormal
        )
        Spacer(Modifier.width(12.dp))
        Image(
            modifier = Modifier.clickable { onShare() },
            painter = painterResource(Res.drawable.ic_share),
            contentDescription = "공유",
            contentScale = ContentScale.Fit,
        )
    }
}