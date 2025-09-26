package com.team_gori.gori.feature_meeting.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriFilledButton
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_x
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(
    viewModel: LocationViewModel = koinInject(), // Koin 등으로 주입
    onNavigateBack: () -> Unit,
    onComplete: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(LocationUiEvent.ScreenEntered)
        viewModel.navEvent.collect { event ->
            when (event) {
                is LocationNavEvent.GoBackWithResult -> {
                    onComplete(event.selectedLocation)
                }
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Image(
                            painter = painterResource(Res.drawable.ic_x),
                            contentDescription = "닫기",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("지역 선택", style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("현재 서울만 선택할 수 있어요", style = MaterialTheme.typography.labelLarge)
                    Spacer(modifier = Modifier.height(24.dp))

                    // "서울" / "전체" 선택 영역
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("서울", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.weight(1f))
                        Text("전체 >", style = MaterialTheme.typography.headlineMedium)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 자치구 선택 그리드
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3), // 스크린샷에 맞춰 3열로 변경
                        modifier = Modifier.weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.districts) { district ->
                            DistrictChip(
                                text = district,
                                isSelected = district == uiState.selectedDistrict,
                                onClick = { viewModel.onEvent(LocationUiEvent.DistrictSelected(district)) }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    GoriFilledButton(
                        onClick = { viewModel.onEvent(LocationUiEvent.ConfirmClicked) },
                        enabled = uiState.isConfirmEnabled,
                        text = "완료",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(52.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun DistrictChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
    val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.LightGray

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, style = MaterialTheme.typography.headlineMedium, color = textColor, textAlign = TextAlign.Center)
    }
}
