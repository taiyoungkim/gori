package com.team_gori.gori.my_page.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.team_gori.gori.designsystem.component.GoriMeetingItem
import com.team_gori.gori.designsystem.component.GoriMemberRow
import com.team_gori.gori.designsystem.component.GoriProfileImageStack
import com.team_gori.gori.designsystem.component.UnreadBadge
import com.team_gori.gori.designsystem.theme.Neutral10
import com.team_gori.gori.designsystem.theme.Neutral40
import com.team_gori.gori.designsystem.theme.Neutral50
import com.team_gori.gori.designsystem.theme.Neutral60
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_arrow_right
import gori.composeapp.generated.resources.ic_official
import org.jetbrains.compose.resources.painterResource

// ViewModel로부터 받아올 데이터 클래스 (예시)
data class UserProfile(val name: String, val age: Int, val gender: String, val description: String)
data class Meeting(
    val id: String,
    val dateTime: String, // 예: "내일, 오후 12시"
    val title: String, // 예: "도자기 원데이 클래스"
    val imageUrl: String, // 썸네일 이미지 URL
    val currentParticipants: Int,
    val maxParticipants: Int,
    val location: String // 예: "송파구"
)
data class ChatRoom(
    val id: String,
    val title: String,
    val participantCount: Int,
    val lastMessage: String,
    val lastActiveTime: String,
    val unreadCount: Int,
    val profileImageUrls: List<String>, // 프로필 이미지가 하나 또는 두 개일 수 있으므로 리스트로 관리
    val isOfficial: Boolean = false // 왕관 아이콘 표시 여부
)
data class DailyPost(val content: String, val date: String)

@Composable
fun MyPageScreen(

) {
    val userProfile = UserProfile("골프의신", 61, "남", "골프 경력 20년차 남성입니다^^ 골프라면 가슴이 뜨거워지는 5060 청춘 친구들을 만나고 싶습니다. 초보, 고수 상관없이 잘 지...")
    val upcomingMeeting = Meeting("도자기 원데이 클래스", "내일, 오후 12시", "송파구", "https://images.unsplash.com/photo-1519114056088-b877fe073a5e?q=80&w=2066&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", 4, 6, "송파구")
    val myPosts = emptyList<DailyPost>() // 작성한 일상이 없을 경우 빈 리스트

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp) // 각 섹션 사이의 간격
    ) {
        // 1. 프로필 섹션
        item {
            ProfileSection(profile = userProfile)
        }

        // 2. 다가오는 모임 섹션
        item {
            // 모임 데이터가 있을 때만 이 섹션을 보여줍니다.
            UpcomingMeetingSection(
                meeting = upcomingMeeting
            )
        }

        if (dummyChatRooms.isNotEmpty()) {
            // 섹션 제목
            item {
                // 이전에 만든 SectionTitle 재사용
                SectionTitle(
                    title = "참여한 대화방",
                    count = dummyChatRooms.size,
                    onClick = { /* 참여한 대화방 전체 목록으로 이동 */ }
                )
            }
            // 대화방 리스트
            items(dummyChatRooms) { chatRoom ->
                ChatRoomItem(
                    chatRoom = chatRoom,
                    onClick = { roomId ->
                        println("Clicked chat room: $roomId")
                        // roomId를 가지고 해당 채팅방으로 이동하는 로직
                    }
                )
            }
        }

        // 4. 작성한 일상 섹션
        item {
            SectionTitle(
                "작성한 일상",
                onClick = { /* '작성한 일상' 전체 목록 화면으로 이동 */ }
            )
        }

        item {
            if (myPosts.isEmpty()) {
                // 작성한 일상이 없을 때 보여줄 Empty View
//                MyPageEmptyView(
//                    message = "아직 공유한 일상이 없어요.",
//                    suggestion = "내 관심사에 맞는 모임을 가입해보세요.",
//                    buttonText = "모임 탐색하기",
//                    onButtonClick = { /* 모임 탐색하기 화면으로 이동 */ }
//                )
            }
        }
    }

}

@Composable
fun ProfileSection(profile: UserProfile) {
    Column(modifier = Modifier) {
        Column (
            Modifier.padding(horizontal = 16.dp)
        ) {
            GoriMemberRow(
                memberName = profile.name,
                age = profile.age,
                gender = profile.gender,
                isMe = true,
                isAdmin = false,
                avatarSize = 64,
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "골프 경력 20년차 남성입니다^^\n" +
                        "골프라면 가슴이 뜨거워지는 5060청춘 친구들을 만나고 싶습니다. 초보, 고수 상관없이 잘 지낼 수 있는 분들을 만나고 싶습니다.",
                style = MaterialTheme.typography.headlineMedium,
                color = Neutral60
            )
            Spacer(Modifier.height(6.dp))
            // 더보기
            Text(
                text = "...더보기",
                style = MaterialTheme.typography.headlineMedium,
                color = Neutral40
            )
            Spacer(Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.semanticColors.lineAlternative,
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .background(color = Neutral10)
                    .padding(start = 24.dp, top = 14.dp, end = 24.dp, bottom = 14.dp)
                    .align(Alignment.CenterHorizontally),
                text = "프로필 수정",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun UpcomingMeetingSection(
    modifier: Modifier = Modifier,
    meeting: Meeting? // 모임이 없을 수도 있으므로 Nullable
) {
    // 다가오는 모임 데이터가 있을 때만 UI를 그립니다.
    if (meeting != null) {
        Column {
            // 섹션 제목 (이전 답변에서 만든 Composable 재사용)
            SectionTitle(title = "다가오는 모임", count = 1, onClick = {
                // '다가오는 모임' 전체 목록 화면으로 이동
            })
            Spacer(modifier = Modifier.height(12.dp))
            Column(modifier = modifier.padding(horizontal = 16.dp)) {
                MeetingCard(
                    meeting = meeting,
                    onChatButtonClick = { meetingId ->
                        // 클릭된 모임의 ID(meetingId)를 가지고 채팅방으로 이동
                        println("Go to Chat Room for meeting: $meetingId")
                    }
                )
            }
        }
    }
}

@Composable
fun MeetingCard(
    modifier: Modifier = Modifier,
    meeting: Meeting,
    onChatButtonClick: (String) -> Unit // 채팅방 이동을 위해 meeting id를 전달
) {
    val painter = rememberAsyncImagePainter(model = meeting.imageUrl)
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.semanticColors.backgroundNormal)
    ) {
        GoriMeetingItem(
            modifier = Modifier,
            title = meeting.title,
            dateTime = meeting.dateTime,
            thumbnailPainter = painter,
            maxParticipants = meeting.maxParticipants,
            currentParticipants = meeting.currentParticipants,
            location = meeting.location,
            id = meeting.id,
            onChatButtonClick = onChatButtonClick
        )
    }
}

val dummyChatRooms = listOf(
    ChatRoom("1", "파크 골프 모집", 100, "다음 파크 골프 일정 공유드립니다. 5월 24일 신논현역 6번...", "10분 전", 100, listOf("url1", "url2"), isOfficial = true),
    ChatRoom("2", "사진, 그리고 음악", 100, "다음 파크 골프 일정 공유드립니다. 5월 24일 신논현역 6번 출구 앞...", "10분 전", 1, listOf("url1", "url2")),
    ChatRoom("3", "허니브레드", 0, "안녕하세요", "30분 전", 1, listOf("url3"))
)

@Composable
fun ChatRoomItem(
    chatRoom: ChatRoom,
    onClick: (String) -> Unit
) {
    Column(modifier = Modifier.clickable { onClick(chatRoom.id) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val painters = chatRoom.profileImageUrls
                .take(2) // 최대 2장만 사용
                .map { url -> rememberAsyncImagePainter(model = url) }
            // 왼쪽: 프로필 이미지
            GoriProfileImageStack(painters = painters)

            Spacer(modifier = Modifier.width(12.dp))

            // 중앙: 제목, 마지막 메시지
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // 왕관 아이콘 (Official)
                    if (chatRoom.isOfficial) {
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
                        text = chatRoom.title,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    // 인원 수 (요청 스타일: headlineMedium)
                    Text(
                        text = chatRoom.participantCount.toString(),
                        style = MaterialTheme.typography.headlineMedium, // 스크린샷과 유사하게 하려면 bodyMedium or bodySmall이 적절할 수 있습니다.
                        color = Color.Gray
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                // 최근 채팅 내용 (요청 스타일: bodyMedium)
                Text(
                    text = chatRoom.lastMessage,
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
                    text = chatRoom.lastActiveTime,
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
                UnreadBadge(count = chatRoom.unreadCount)
            }
        }
        HorizontalDivider(modifier = Modifier.padding(start = 84.dp, end = 16.dp), thickness = 0.5.dp, color = Color.LightGray)
    }
}

// 각 섹션의 제목 Composable (재사용)
@Composable
fun SectionTitle(
    title: String,
    count: Int = 0,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = Neutral50
            )
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_right),
                contentDescription = "더보기",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}


// 데이터가 없을 때 보여줄 Empty View
@Composable
fun MyPageEmptyView(
    message: String,
    suggestion: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = message)
        Text(text = suggestion)
        Button(onClick = onButtonClick) {
            Text(buttonText)
        }
    }
}