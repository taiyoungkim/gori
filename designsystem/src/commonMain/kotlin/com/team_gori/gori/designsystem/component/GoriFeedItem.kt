package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelAssistive
import com.team_gori.gori.designsystem.theme.LabelNormal
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_default_avatar
import gori.designsystem.generated.resources.ic_comment
import gori.designsystem.generated.resources.ic_like
import gori.designsystem.generated.resources.ic_share
import gori.designsystem.generated.resources.sample
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriFeedItem(
    profile: DrawableResource = Res.drawable.ic_default_avatar,
    userName: String,
    timestamp: String,
    text: String,
    photoList: List<ByteArray> = listOf(),
    like: Int,
    isLike: Boolean = false,
    comment: Int,
    isComment: Boolean = false,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 프로필
            Image(
                painter = painterResource(profile),
                contentDescription = "profile",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {}
            )
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
        Spacer(Modifier.height(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = LabelNormal,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        if (photoList.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                itemsIndexed(photoList) { index, byteArray ->
                    Image(
                        painter = painterResource(Res.drawable.sample),
                        contentDescription = "선택된 이미지",
                        modifier = Modifier
                            .size(160.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                    )
//                    GoriImageButton(
//                        imageBitmap = byteArray.toImageBitmap(),
//                        onAddClick = {},
//                        onRemoveClick = {}
//                    )
                }
            }
        }
        Spacer(Modifier.height(12.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier,
                painter = painterResource(Res.drawable.ic_like),
                contentDescription = "좋아요",
                contentScale = ContentScale.Fit,
            )
            if (like > 0) {
                Spacer(Modifier.width(4.dp))
                Text(
                    text = like.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = LabelNormal
                )
            }
            Spacer(Modifier.width(12.dp))
            Image(
                modifier = Modifier,
                painter = painterResource(Res.drawable.ic_comment),
                contentDescription = "댓글",
                contentScale = ContentScale.Fit,
            )
            if (comment > 0) {
                Spacer(Modifier.width(4.dp))
                Text(
                    text = comment.toString(),
                    style = MaterialTheme.typography.labelLarge,
                    color = LabelNormal
                )
            }
            Spacer(Modifier.width(12.dp))
            Image(
                modifier = Modifier,
                painter = painterResource(Res.drawable.ic_share),
                contentDescription = "공유",
                contentScale = ContentScale.Fit,
            )
        }
    }
}