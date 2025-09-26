package com.team_gori.gori.feature_feed.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriCategoryChip
import com.team_gori.gori.designsystem.component.GoriFeedBottom
import com.team_gori.gori.designsystem.component.GoriFeedProfile
import com.team_gori.gori.designsystem.theme.LabelNormal
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_arrow_left
import gori.composeapp.generated.resources.ic_menu
import gori.composeapp.generated.resources.sample
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedDetailScreen (
    onNavigateBack: () -> Unit,
//    text: String,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
//                    Text(
//                        "채팅방 (${uiState.connectionState})",
//                        style = MaterialTheme.typography.bodyMedium,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis,
//                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Image(
                            painterResource(Res.drawable.ic_arrow_left),
                            contentDescription = "닫기",
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Image(
                            painterResource(Res.drawable.ic_menu),
                            contentDescription = "세팅",
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            GoriCategoryChip("건강/운동")
            Spacer(Modifier.height(12.dp))
            GoriFeedProfile(
                userName = "유저1",
                timestamp = "1일 전"
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "최근에 뚱냥이가 된 우리 냥이와 함께 운동할려구요! 글은 최대 5,000자 작성할 수 있어요. 일상 1depth에서 본문 글은 최대 165자 노출된 후 그보다 길어질 경우 더보기 버튼이 붙어요. 일상 1depth에서 본문 글은 최대 165자 노출된 후 그보다 길어질 경우 더보기 버튼이 붙어요.",
                style = MaterialTheme.typography.bodyMedium,
                color = LabelNormal,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(12.dp))
            Image(
                painter = painterResource(Res.drawable.sample),
                contentDescription = "선택된 이미지",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
            Spacer(Modifier.height(12.dp))
            GoriFeedBottom(
                like = 0,
                comment = 0,
                isLike = false,
                isComment = false,
                onLike = {},
                onComment = {},
                onShare = {},
            )
        }
    }
}