package com.team_gori.gori.feature_login.presentation.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriFilledButton
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral40
import com.team_gori.gori.designsystem.theme.Neutral60
import com.team_gori.gori.designsystem.theme.Success
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_arrow_left
import gori.composeapp.generated.resources.ic_corner_down_right
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NicknameScreen(
    viewModel: NicknameViewModel = koinInject(),
    onNavigateNext: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    // 화면 이동 이벤트 처리
    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is NicknameNavEvent.NavigateToNextScreen -> onNavigateNext()
            }
        }
    }

    // 화면 첫 진입 시 데이터 로딩
    LaunchedEffect(Unit) {
        viewModel.onEvent(NicknameUiEvent.ScreenEntered)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 제목 없음 */ },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Image(
                            painter = painterResource(Res.drawable.ic_arrow_left),
                            contentDescription = "닫기",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "어떤 이름으로\n활동하시겠어요?",
                style = MaterialTheme.typography.titleSmall,
                color = LabelNormal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "2~8글자로 된\n한글, 영어, 숫자 모두 가능해요",
                style = MaterialTheme.typography.bodyMedium,
                color = Neutral60,
            )
            Spacer(modifier = Modifier.height(32.dp))

            val interactionSource = remember { MutableInteractionSource() }

            BasicTextField(
                value = uiState.displayText,
                onValueChange = { viewModel.onEvent(NicknameUiEvent.NicknameChanged(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { focusState ->
                        viewModel.onEvent(NicknameUiEvent.FocusChanged(focusState.isFocused))
                    },
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = MaterialTheme.colorScheme.onSurface),
                decorationBox = { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = uiState.displayText,
                        innerTextField = innerTextField,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        isError = uiState.error != null,
                        placeholder = {
                            if (uiState.isEditing) {
                                Text("닉네임을 입력하세요", style = MaterialTheme.typography.headlineMedium)
                            }
                        },
                        trailingIcon = {
                            if (!uiState.isEditing) {
                                TextButton(onClick = { viewModel.onEvent(NicknameUiEvent.GetNewRecommendationClicked) }) {
                                    Text("딴거할래요")
                                }
                            }
                        },
                        colors = TextFieldDefaults.colors(
                            // 배경색을 투명하게 설정하여 밑줄만 보이도록 합니다.
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent,
                            // 밑줄(Indicator) 색상을 상태에 따라 설정합니다.
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = Color.Gray,
                            errorIndicatorColor = MaterialTheme.colorScheme.error,
                            // label
                            unfocusedLabelColor = if (uiState.isEditing) LabelNormal else Neutral40,
                            focusedLabelColor = if (uiState.isEditing) LabelNormal else Neutral40,
                            // cursor
                            cursorColor = MaterialTheme.colorScheme.primary,
                        ),
                        contentPadding = PaddingValues(0.dp)
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 에러 메시지 또는 추천 닉네임 안내 문구 표시
            if (uiState.error != null) {
                Text(
                    text = uiState.error!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelMedium
                )
            } else if (!uiState.isEditing && uiState.recommendedNickname.isNotEmpty()) {
                Row {
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(Res.drawable.ic_corner_down_right),
                        contentDescription = "닫기",
                        colorFilter = ColorFilter.tint(Success),
                    )
                    Text(
                        text = "추천 닉네임이에요!",
                        color = Success,
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }


            Spacer(modifier = Modifier.weight(1f))

            GoriFilledButton(
                onClick = { viewModel.onEvent(NicknameUiEvent.NextClicked) },
                enabled = uiState.isNextButtonEnabled && !uiState.isLoading,
                text = "다음",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            )
        }
    }
}
