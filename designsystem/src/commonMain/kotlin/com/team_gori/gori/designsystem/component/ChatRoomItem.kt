package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_official
import org.jetbrains.compose.resources.painterResource

@Composable
fun ChatRoomItem(
    id: String,
    title: String,
    participantCount: Int,
    lastMessage: String,
    lastActiveTime: String,
    unreadCount: Int,
    profileImageUrls: List<Painter>,
    isOfficial: Boolean = false,
    onClick: (String) -> Unit
) {

    Column(modifier = Modifier.clickable { onClick(id) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 왼쪽: 프로필 이미지
            GoriProfileImageStack(painters = profileImageUrls)

            Spacer(modifier = Modifier.width(12.dp))

            // 중앙: 제목, 마지막 메시지
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // 왕관 아이콘 (Official)
                    if (isOfficial) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_official),
                            contentDescription = "Official Chat",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                    }
                    // 채팅방 제목 (요청 스타일: bodyLarge)
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    // 인원 수 (요청 스타일: headlineMedium)
                    Text(
                        text = participantCount.toString(),
                        style = MaterialTheme.typography.headlineMedium, // 스크린샷과 유사하게 하려면 bodyMedium or bodySmall이 적절할 수 있습니다.
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                // 최근 채팅 내용 (요청 스타일: bodyMedium)
                Text(
                    text = lastMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // 오른쪽: 시간, 안 읽은 개수
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 최근 업데이트 시간 (요청 스타일: labelLarge)
                Text(
                    text = lastActiveTime,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
                UnreadBadge(count = unreadCount)
            }
        }
        HorizontalDivider(modifier = Modifier.padding(start = 84.dp, end = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
    }
}