package com.team_gori.gori.feature_login.presentation.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.outlinedTextFieldColors
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriFilledButton
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral40
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_x
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyCodeScreen(
    viewModel: VerifyCodeViewModel = koinInject(),
    onNavigateBack: () -> Unit,
    onComplete: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is VerifyCodeNavEvent.NavigateToNextScreen -> onComplete()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(VerifyCodeUiEvent.ScreenEntered)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Image(
                                painterResource(Res.drawable.ic_x),
                                contentDescription = "닫기",
                            )
                        }
                    },
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .imePadding(),
            ) {
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "문자로 받은 인증번호를\n입력해주세요.",
                    style = MaterialTheme.typography.titleSmall,
                    color = LabelNormal
                )
                Spacer(Modifier.height(32.dp))
                OutlinedTextField(
                    textStyle = MaterialTheme.typography.headlineMedium,
                    value = uiState.code,
                    onValueChange = { viewModel.onEvent(VerifyCodeUiEvent.CodeChanged(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp)),
                    placeholder = { Text(
                        "인증번호",
                        style = MaterialTheme.typography.headlineMedium
                    ) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.isError,
                    trailingIcon = {
                        if (uiState.isCountingDown) {
                            Text(
                                text = uiState.remainingTimeFormatted,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineMedium,
                                modifier = Modifier.padding(end = 16.dp)
                                )
                        }
                    },
                    colors = outlinedTextFieldColors(
                        unfocusedBorderColor = Neutral40,
                        focusedBorderColor = LabelNormal,
                        errorBorderColor = MaterialTheme.colorScheme.error,
                        errorCursorColor = MaterialTheme.colorScheme.error,
                        errorLabelColor = MaterialTheme.colorScheme.error
                    ),
                    shape = RoundedCornerShape(14.dp)
                )
                if (uiState.isError) {
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "입력한 정보가 올바르지 않아요. 다시 확인해주세요.",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error,
                    )
                }

                Spacer(Modifier.height(32.dp))

                Text(
                    text = "인증번호 재전송",
                    color = Neutral40,
                    textDecoration = TextDecoration.Underline,
                    style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .fillMaxWidth()
                        .clickable(enabled = uiState.isResendEnabled) {
                        viewModel.onEvent(VerifyCodeUiEvent.ResendClicked)
                    }
                )

                Spacer(Modifier.weight(1f))

                GoriFilledButton(
                    onClick = { viewModel.onEvent(VerifyCodeUiEvent.NextClicked) },
                    enabled = uiState.code.length == 6,
                    text = "다음",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                )
            }
        }
    }
}
