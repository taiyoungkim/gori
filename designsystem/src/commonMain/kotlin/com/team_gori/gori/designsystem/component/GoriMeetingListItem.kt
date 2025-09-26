package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team_gori.gori.designsystem.theme.Neutral40
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_add_photo
import gori.designsystem.generated.resources.ic_location
import gori.designsystem.generated.resources.ic_people
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriMeetingListItem(
    modifier: Modifier = Modifier,
    title: String,
    dateTime: String,
    location: String,
    currentParticipants: Int,
    maxParticipants: Int,
    thumbnailPainter: Painter? = null,
    isClosed: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.Top, // 텍스트가 길어져도 상단 정렬 유지
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MeetingThumbnail(
            thumbnailPainter = thumbnailPainter,
            isClosed = isClosed
        )

        MeetingInfo(
            title = title,
            dateTime = dateTime,
            location = location,
            currentParticipants = currentParticipants,
            maxParticipants = maxParticipants
        )
    }
}

@Composable
private fun MeetingThumbnail(
    modifier: Modifier = Modifier,
    thumbnailPainter: Painter? = null,
    isClosed: Boolean
) {
    Box(
        modifier = modifier
            .size(70.dp)
            .clip(RoundedCornerShape(14.dp)),
        contentAlignment = Alignment.Center
    ) {
        // Coil을 사용한 비동기 이미지 로딩
        Image(
            painter = thumbnailPainter ?: painterResource(Res.drawable.ic_add_photo),
            contentDescription = "썸네일",
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        if (isClosed) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "정원마감",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.semanticColors.onSecondary,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun MeetingInfo(
    modifier: Modifier = Modifier,
    title: String,
    dateTime: String,
    location: String,
    currentParticipants: Int,
    maxParticipants: Int
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp) // 각 요소 사이의 간격을 일정하게 유지
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1, // 제목이 길 경우 ...으로 표시
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = dateTime,
            style = MaterialTheme.typography.headlineMedium,
            color = Neutral40
        )
        // 중단: 참여 인원, 위치
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconWithText(
                icon = Res.drawable.ic_people,
                text = "${currentParticipants}/${maxParticipants}명",
                iconSize = 20.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
            IconWithText(
                icon = Res.drawable.ic_location,
                text = location,
                iconSize = 20.dp
            )
        }
    }
}
