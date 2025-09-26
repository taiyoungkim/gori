package com.team_gori.gori.feature_meeting.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.team_gori.gori.designsystem.component.GoriMemberRow
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_arrow_left
import gori.composeapp.generated.resources.ic_location
import gori.composeapp.generated.resources.ic_more
import gori.composeapp.generated.resources.ic_share
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf

@Composable
fun MeetingDetailRoute(
    meetingId: String,
    viewModel: MeetingDetailViewModel = koinInject(parameters = { parametersOf(meetingId) }),
    onEffect: (MeetingDetailEffect) -> Unit
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) { viewModel.effect.collect(onEffect) }
    MeetingDetailScreen(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun MeetingDetailScreen(
    state: MeetingDetailUiState,
    onEvent: (MeetingDetailEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val headerHeight: Dp = 220.dp
    val headerPx = with(LocalDensity.current) { headerHeight.toPx() }

    val progress by remember {
        derivedStateOf {
            val idx = listState.firstVisibleItemIndex
            if (idx > 0) 1f
            else (listState.firstVisibleItemScrollOffset / headerPx).coerceIn(0f, 1f)
        }
    }

    var isCollapsed by rememberSaveable { mutableStateOf(false) }
    val collapseThreshold = 0.65f
    val expandThreshold = 0.35f

    LaunchedEffect(listState, headerPx) {
        snapshotFlow { progress }
            .collect { p ->
                when {
                    // 헤더가 완전히 위로 넘어갔거나 65% 이상이면 접힘
                    p >= collapseThreshold || listState.firstVisibleItemIndex > 0 -> isCollapsed = true
                    // 35% 이하로 충분히 내려오면 펼침
                    p <= expandThreshold && listState.firstVisibleItemIndex == 0 -> isCollapsed = false
                    // 그 사이 영역(35%~65%)에서는 이전 상태 유지
                }
            }
    }

    val bgAlpha by animateFloatAsState(if (isCollapsed) 1f else 0f, label = "topbar-bg")
    val titleAlpha by animateFloatAsState(if (isCollapsed) 1f else 0f, label = "topbar-title")

    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        bottomBar = {
//            BottomJoinBar(state = state, onEvent = onEvent)
        }
    ) { inner ->
        Box(modifier = modifier.fillMaxSize()) {
            when (val c = state.content) {
                MeetingDetailUiState.Content.Loading -> {
                    // 심플 스켈레톤
                    LazyColumn(contentPadding = PaddingValues(bottom = 24.dp)) {
                        item {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(headerHeight)
                                    .background(Color(0xFFEFF1F5))
                            )
                        }
                        item { Spacer(Modifier.height(12.dp)) }
                        item { SkeletonLine(width = 240.dp) }
                        item { Spacer(Modifier.height(8.dp)) }
                        item { SkeletonLine(width = 180.dp) }
                        items(6) {
                            Spacer(Modifier.height(12.dp))
                            SkeletonLine(width = Dp.Unspecified)
                        }
                    }
                }

                is MeetingDetailUiState.Content.Error -> {
                    // 에러/재시도
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(c.message, style = MaterialTheme.typography.bodyLarge)
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { onEvent(MeetingDetailEvent.OnRetryLoad) }) {
                            Text("다시 시도")
                        }
                    }
                }

                is MeetingDetailUiState.Content.Success -> {
                    val data = c.data
                    LazyColumn(
                        state = listState,
                        contentPadding = PaddingValues(bottom = 24.dp)
                    ) {
                        // 1) 대표 이미지
                        item("header") {
                            val painter = rememberAsyncImagePainter(data.imageUrl)
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(headerHeight)
                            ) {
                                Image(
                                    painter = painter,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }

                        // 2) 태그/배지
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AssistChip(onClick = {}, label = { Text(data.categoryLabel) })
                                Spacer(Modifier.width(8.dp))
                                if (data.isFull) {
                                    AssistChip(
                                        onClick = {},
                                        label = { Text("정원마감") },
                                        colors = AssistChipDefaults.assistChipColors(
                                            labelColor = MaterialTheme.colorScheme.primary,
                                            containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                                        )
                                    )
                                }
                            }
                        }

                        // 3) 제목
                        item {
                            Text(
                                text = data.title,
                                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                        }

                        // 4) 요약 정보
                        val info = listOf(
                            Res.drawable.ic_location to data.dateTime,
                            Res.drawable.ic_location to data.region,
                            Res.drawable.ic_location to data.target,
                            Res.drawable.ic_location to data.capacity
                        )
                        items(info.size) { idx ->
                            val (iconRes, text) = info[idx]
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(iconRes),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(text, style = MaterialTheme.typography.bodyLarge)
                            }
                        }

                        // 5) 본문
                        item {
                            Spacer(Modifier.height(8.dp))
                            Divider()
                            Spacer(Modifier.height(12.dp))
                            Text(
                                text = data.description,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Spacer(Modifier.height(16.dp))
                        }

                        // ✅ 6) 모임장 소개

                        item {
                            Column(Modifier.fillMaxWidth()) {
                                Text(
                                    "모임장 소개",
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = LabelNormal
                                )
                                GoriMemberRow(
                                    memberName = data.host.name,
                                    age = data.host.age ?: 0,
                                    gender = data.host.gender?.label ?: "",
                                    isMe = true,
                                    isAdmin = true,
                                    isMore = false
                                )
                            }
                        }

                        item {
//                            SectionDivider()
                            Text(
                                text = "멤버 ${data.currentCount}/${data.maxCount}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = LabelNormal
                            )
                        }

                        // ✅ 8) 대기 멤버 배너 (있을 때만)
                        if (data.waitingCount > 0) {
                            item {
                                WaitingBanner(
                                    text = "참여를 기다리는 멤버가 있어요!",
                                    onClick = { onEvent(MeetingDetailEvent.OnClickSeeWaitingMembers) }
                                )
                            }
                        }

                        // ✅ 9) 멤버 리스트 일부
                        items(data.members, key = { it.id }) { member ->
                            GoriMemberRow(
                                memberName = member.name,
                                age = member.age ?: 0,
                                gender = member.gender?.label ?: "",
                                isMe = true,
                                isAdmin = false,
                                isMore = false
                            )
                        }

                        // ✅ 10) 멤버 모두 보기 (Pill 버튼)
//                        item {
//                            SeeAllMembersButton { onEvent(MeetingDetailEvent.OnClickSeeAllMembers) }
//                            Spacer(Modifier.height(8.dp))
//                        }
                    }
                }
            }

            // TopBar (제목/배경은 Success + 스크롤 시에만 드러남)
            TopBarOverlay(
                title = state.titleOnAppBar,
                isCollapsed = isCollapsed,
                bgAlpha = bgAlpha,
                titleAlpha = titleAlpha,
                onBack = { onEvent(MeetingDetailEvent.OnClickBack) },
                onShare = { onEvent(MeetingDetailEvent.OnClickShare) },
                onMore = { onEvent(MeetingDetailEvent.OnClickMore) }
            )
        }
    }
}

@Composable
private fun SkeletonLine(width: Dp) {
    Box(
        Modifier
            .padding(horizontal = 16.dp)
            .height(16.dp)
            .fillMaxWidth(if (width == Dp.Unspecified) 1f else 0f)
            .then(if (width != Dp.Unspecified) Modifier.width(width) else Modifier)
            .background(Color(0xFFE9EBEF), shape = MaterialTheme.shapes.small)
    )
}

@Composable
private fun TopBarOverlay(
    title: String,
    isCollapsed: Boolean,
    bgAlpha: Float,
    titleAlpha: Float,
    onBack: () -> Unit,
    onShare: () -> Unit,
    onMore: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = bgAlpha),
        tonalElevation = if (isCollapsed) 4.dp else 0.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (!isCollapsed) {
                // 확장 상태: 이미지 위에 동그란 버튼 3개만
                Row(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // 좌측: 뒤로가기
                    CircularIconButton(
                        onClick = onBack,
                        icon = { Icon(painterResource(Res.drawable.ic_arrow_left), contentDescription = null) }
                    )
                    // 우측: 공유 / 더보기
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        CircularIconButton(
                            onClick = {
                                onShare()
                            },
                            icon = { Icon(painterResource(Res.drawable.ic_share), contentDescription = "share") }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        CircularIconButton(
                            onClick = {
                                onMore()
                            },
                            icon = { Icon(painterResource(Res.drawable.ic_more), contentDescription = "more") }
                        )
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxSize().background(MaterialTheme.semanticColors.backgroundNormal).padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    // 접힘 상태: 일반 TopBar처럼 타이틀 + 평범한 IconButton
                    IconButton(onClick = onBack) {
                        Icon(painterResource(Res.drawable.ic_arrow_left), contentDescription = "back")
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.alpha(titleAlpha).weight(1f),
                        maxLines = 1,
                    )
                    Row(modifier = Modifier) {
                        IconButton(
                            onClick = { onShare() }
                        ) {
                            Icon(painterResource(Res.drawable.ic_share), contentDescription = "share")
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        IconButton(
                            onClick = { onMore() }
                        ) {
                            Icon(painterResource(Res.drawable.ic_more), contentDescription = "more")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CircularIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        shape = CircleShape,
        color = MaterialTheme.semanticColors.backgroundNormal,
        tonalElevation = 0.dp,
        shadowElevation = 6.dp,                  // 동그란 그림자
        modifier = modifier.size(40.dp)          // 버튼 지름
    ) {
        Box(contentAlignment = Alignment.Center) { icon() }
    }
}

@Composable
private fun WaitingBanner(text: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = Color(0xFFE8E3FF),
        contentColor = Color(0xFF5C45FF),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(Modifier.padding(horizontal = 14.dp, vertical = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(painterResource(Res.drawable.ic_location), contentDescription = null) // TODO: 대기 아이콘으로 교체
            Spacer(Modifier.width(8.dp))
            Text(text, style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
            Icon(painterResource(Res.drawable.ic_arrow_left), contentDescription = null) // 방향 아이콘으로 교체
        }
    }
}
