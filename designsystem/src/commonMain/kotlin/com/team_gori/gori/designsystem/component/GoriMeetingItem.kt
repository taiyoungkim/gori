package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_add_photo
import gori.designsystem.generated.resources.ic_calender
import gori.designsystem.generated.resources.ic_people
import gori.designsystem.generated.resources.ic_location
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriMeetingItem(
    modifier: Modifier = Modifier,
    title: String,
    dateTime: String,
    maxParticipants: Int,
    currentParticipants: Int,
    location: String,
    id: String,
    thumbnailPainter: Painter? = null,
    onChatButtonClick: (String) -> Unit // 채팅방 이동을 위해 meeting id를 전달
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // 상단: 날짜, 제목, 이미지
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                IconWithText(
                    icon = Res.drawable.ic_calender,
                    text = dateTime,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    // weight(1f)로 인해 공간이 남을 경우 이미지가 밀려나지 않도록 가로 길이를 제한
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            // Coil 같은 이미지 로딩 라이브러리 사용을 권장합니다.
            Image(
                painter = thumbnailPainter ?: painterResource(Res.drawable.ic_add_photo),
                contentDescription = "${title} 썸네일",
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
        }

        // 중단: 참여 인원, 위치
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconWithText(
                icon = Res.drawable.ic_people,
                text = "${currentParticipants}/${maxParticipants}명"
            )
            Spacer(modifier = Modifier.width(12.dp))
            IconWithText(
                icon = Res.drawable.ic_location,
                text = location
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 하단: 모임 대화방 바로가기 버튼
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF3F5F7), // 밝은 회색 배경
            onClick = { onChatButtonClick(id) }
        ) {
            Box(
                modifier = Modifier.padding(vertical = 14.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "모임 대화방 바로가기",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.DarkGray
                )
            }
        }
    }
}