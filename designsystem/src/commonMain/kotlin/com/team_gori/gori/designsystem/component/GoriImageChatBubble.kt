package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelAssistive

@Composable
fun GoriImageChatBubble(
    imageBitmap: ImageBitmap,
    time: String,
    isFirst: Boolean, // isFirst는 내 말풍선에만 적용되지만, 파라미터 통일을 위해 유지
) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = time,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = LabelAssistive,
        )
        Image(
            bitmap = imageBitmap,
            contentDescription = "전송된 이미지",
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomStart = 24.dp,
                        bottomEnd = if (isFirst) 2.dp else 24.dp
                    )
                ),
            contentScale = ContentScale.Fit
        )
    }
}