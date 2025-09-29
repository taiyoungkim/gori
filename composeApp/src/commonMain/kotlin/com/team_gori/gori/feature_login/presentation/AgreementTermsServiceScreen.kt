package com.team_gori.gori.feature_login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriCheckBox
import com.team_gori.gori.designsystem.theme.Neutral30
import com.team_gori.gori.designsystem.theme.Neutral40
import com.team_gori.gori.designsystem.theme.Opacity10
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_arrow_left
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgreementTermsServiceScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    viewModel: AgreementTermsServiceViewModel = koinInject(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                AgreementTermsServiceNavEvent.NavigateToSignUp -> onNavigateToSignUp()
            }
        }
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
                                painterResource(Res.drawable.ic_arrow_left),
                                contentDescription = "닫기",
                            )
                        }
                    },
                )
            },
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(60.dp))
                Text(
                    text = "서비스 이용 약관에\n동의해 주세요.",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Start
                )
                Spacer(Modifier.height(24.dp))
                GoriCheckBox(
                    text = "네, 모두 동의합니다.",
                    checked = uiState.isAllAgreedChecked,
                    onCheckChange = { viewModel.onEvent(AgreementTermsServiceUiEvent.AllAgreementToggled(it)) }
                )
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    color = Opacity10
                )
                GoriCheckBox(
                    text = "(필수) 서비스 이용약관 동의",
                    checked = uiState.termsAgreed,
                    onCheckChange = { viewModel.onEvent(AgreementTermsServiceUiEvent.TermsAgreementToggled(it)) }
                )
                GoriCheckBox(
                    text = "(필수) 개인정보 수집 이용 동의",
                    checked = uiState.privacyAgreed,
                    onCheckChange = { viewModel.onEvent(AgreementTermsServiceUiEvent.PrivacyAgreementToggled(it)) }
                )
                GoriCheckBox(
                    text = "(선택) 홍보 및 마케팅 이용 동의",
                    checked = uiState.marketingAgreed,
                    onCheckChange = { viewModel.onEvent(AgreementTermsServiceUiEvent.MarketingAgreementToggled(it)) }
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            color = Neutral30,
                            shape = RoundedCornerShape(size = 30.dp)
                        ),
                    onClick = { viewModel.onEvent(AgreementTermsServiceUiEvent.NextClicked) },
                    content = {
                        Text(
                            "다음",
                            color = MaterialTheme.semanticColors.onSecondary,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    },
                    enabled = uiState.isNextButtonEnabled,
                    colors = ButtonColors(
                        containerColor = MaterialTheme.semanticColors.labelNormal,
                        contentColor = MaterialTheme.semanticColors.onSecondary,
                        disabledContainerColor = MaterialTheme.semanticColors.labelDisabled,
                        disabledContentColor = MaterialTheme.semanticColors.onSecondary,
                    )
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    "선택 사항에 동의하지 않아도 서비스 이용이 가능합니다.\n" +
                            "개인정보 수집 및 이용에 대한 동의를 거부할 권리가 있으며,\n" +
                            " 동의 거부시 서비스 이용에 제한됩니다.",
                    style = MaterialTheme.typography.labelMedium,
                    color = Neutral40,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
