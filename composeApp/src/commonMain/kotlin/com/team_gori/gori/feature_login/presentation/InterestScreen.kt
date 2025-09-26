package com.team_gori.gori.feature_login.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriDimCheckOverlay
import com.team_gori.gori.designsystem.component.GoriFilledButton
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral60
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun InterestScreen(
    viewModel: InterestViewModel = koinInject(),
    onClose: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    // 화면 이동/닫기 이벤트 처리
    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is InterestNavEvent.CloseScreen -> onClose()
            }
        }
    }

    // 화면 첫 진입 시 데이터 로딩
    LaunchedEffect(Unit) {
        viewModel.onEvent(InterestUiEvent.ScreenEntered)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            "관심 있는 주제를 선택하세요",
            style = MaterialTheme.typography.titleSmall,
            color = LabelNormal
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "설정에서 나중에 언제든 수정할 수 있어요",
            style = MaterialTheme.typography.headlineMedium,
            color = Neutral60
        )
        Spacer(modifier = Modifier.height(24.dp))

        // 주제 목록 그리드
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(uiState.topics) { topic ->
                InterestTopicItem(
                    topic = topic,
                    isSelected = topic.id in uiState.selectedTopicIds,
                    onClick = { viewModel.onEvent(InterestUiEvent.TopicClicked(topic.id)) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        GoriFilledButton(
            onClick = { viewModel.onEvent(InterestUiEvent.SaveClicked) },
            enabled = true,
            text = "저장하기",
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
        )
    }
}

@Composable
private fun InterestTopicItem(
    topic: InterestTopic,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val dimAlpha by animateFloatAsState(
        targetValue = if (isSelected) 0.4f else 0f,
        label = "dimAlpha"
    )
    val iconScale by animateFloatAsState(
        targetValue = if (isSelected) 1f else 0.8f,
        label = "iconScale"
    )

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
            modifier = Modifier
                .width(150.dp)
                .height(110.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFF0F0F0))
                .clickable(onClick = onClick),
        ) {
            GoriDimCheckOverlay(
                isSelected = isSelected,
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(150.dp)
                    .height(110.dp),
            ) {
                Image(
                    painter = painterResource(topic.imageRes),
                    contentDescription = topic.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = topic.title,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            color = LabelNormal
        )
    }
}
