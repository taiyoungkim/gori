package com.team_gori.gori.feature_meeting.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.team_gori.gori.core.CategoryUtil
import com.team_gori.gori.core.model.Category
import com.team_gori.gori.designsystem.component.GoriCategoryChipsRow
import com.team_gori.gori.designsystem.component.GoriMeetingListItem
import com.team_gori.gori.designsystem.theme.Neutral10
import com.team_gori.gori.designsystem.theme.Neutral50
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_chevron_right
import gori.composeapp.generated.resources.ic_down
import gori.composeapp.generated.resources.ic_location
import gori.composeapp.generated.resources.ic_star
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun MeetingRoute(
    viewModel: MeetingViewModel = koinInject(),
    onEffect: (MeetingEffect) -> Unit
) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(Unit) { viewModel.effect.collect(onEffect) }
    MeetingScreen(state = state, onEvent = viewModel::onEvent)
}

@Composable
fun MeetingScreen(
    state: MeetingUiState,
    onEvent: (MeetingEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.statusBars),
        contentWindowInsets = WindowInsets.safeDrawing
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp).clickable {
                        onEvent(MeetingEvent.OnClickLocation)
                    }
                ) {
                    val painter = runCatching { painterResource(Res.drawable.ic_location) }.getOrNull()
                    if (painter != null)
                        Image(painter, null, Modifier.size(20.dp))
                    else
                        Icon(painterResource(Res.drawable.ic_location), null, Modifier.size(20.dp))
                    Spacer(Modifier.width(6.dp))
                    Text(
                        state.locationLabel,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(Modifier.width(6.dp))
                    Icon(
                        painterResource(Res.drawable.ic_down),
                        null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            item { Spacer(Modifier.height(16.dp)) }

            item {
                CategorySection(
                    selected = state.selectedCategory,
                    onSelect = { onEvent(MeetingEvent.OnSelectCategory(it)) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item { Spacer(Modifier.height(12.dp)) }

            if (state.showRecommendationBanner) {
                item {
                    RecommendationBanner(onClick = {}, onDismiss = { onEvent(MeetingEvent.OnDismissBanner) })
                }
            }

            item {
                SortRow(sortType = state.sortType, onChange = { onEvent(MeetingEvent.OnChangeSort(it)) })
            }

            items(state.items, key = { it.id }) { meeting ->
                val painter = rememberAsyncImagePainter(meeting.imageUrl)

                GoriMeetingListItem(
                    modifier = Modifier.fillMaxWidth(),
                    title = meeting.title,
                    dateTime = meeting.dateTime,
                    thumbnailPainter = painter,
                    maxParticipants = meeting.maxParticipants,
                    currentParticipants = meeting.currentParticipants,
                    location = meeting.location,
                    isClosed = meeting.isFull,
                    onClick = { onEvent(MeetingEvent.OnClickMeeting(meeting.id)) }
                )
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun CategorySection(
    selected: Category?,
    onSelect: (Category?) -> Unit,
    modifier: Modifier = Modifier
) {
    val chipItems = remember { CategoryUtil().buildMeetingChipItems() }

    GoriCategoryChipsRow(
        items = chipItems,
        selectedId = selected?.name,
        onSelect = { id ->
            val category = id?.let { runCatching { Category.valueOf(it) }.getOrNull() }
            onSelect(category)          // null이면 “전체”
        },
        modifier = modifier
    )
}

@Composable
private fun RecommendationBanner(
    onClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Neutral10)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(Res.drawable.ic_star),
            modifier = Modifier.size(20.dp),
            contentDescription = null,
            tint = Color(0xFFFCD53F)
        )
        Text(
            text = "관심사 설정하고 맞춤 모임을 추천받아요",
            style = MaterialTheme.typography.labelLarge,
            color = Neutral50,
            modifier = Modifier.weight(1f).padding(start = 8.dp)
        )
        IconButton(onClick = onDismiss) {
            Icon(
                painterResource(Res.drawable.ic_chevron_right),
                modifier = Modifier.size(20.dp),
                contentDescription = null
            )
        }
    }
    Spacer(Modifier.height(12.dp))
}

@Composable
private fun SortRow(
    sortType: SortType,
    onChange: (SortType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "• 최신순",
            style = MaterialTheme.typography.bodyMedium,
            color = if (sortType == SortType.Latest) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.clickable { onChange(SortType.Latest) }
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = "• 가까운 일정순",
            style = MaterialTheme.typography.bodyMedium,
            color = if (sortType == SortType.NearestDate) MaterialTheme.colorScheme.onSurface
            else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.clickable { onChange(SortType.NearestDate) }
        )
    }
}