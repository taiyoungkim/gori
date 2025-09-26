package com.team_gori.gori.feature_login.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelNeutral
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun LoginScreen(
    onNavigateToAgreementTermsScreen: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 위에서 아래로 UI 요소들을 배치하기 위해 Column을 사용합니다.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth(0.8f) // 화면 가로 너비의 80%를 차지하도록 설정
                .aspectRatio(1f / 1.1f), // 가로:세로 비율을 1:1.1로 설정,
            horizontalAlignment = Alignment.CenterHorizontally, // 자식들을 수평 중앙 정렬
            verticalArrangement = Arrangement.Center // 자식들을 수직으로 균등하게 배치
        ) {
            // 1. 로고 아이콘
            Icon(
                painter = painterResource(Res.drawable.ic_logo),
                contentDescription = "Logo Icon",
                tint = MaterialTheme.colorScheme.primary
            )
            // 2. 앱 문구
            Spacer(Modifier.height(20.dp))
            Text(
                text = "세련된 우리 세대 커뮤니티",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(Modifier.height(64.dp))
            Text(
                "카카오로 계속하기",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
                    .background(color = Color(0xFFFAE64D), shape = RoundedCornerShape(size = 30.dp))
                    .padding(horizontal = 24.dp, vertical = 14.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        onNavigateToAgreementTermsScreen()
                    },
                textAlign = TextAlign.Center
            ) // 사용하는 로그인 방식에 맞게 텍스트 변경
            Spacer(modifier = Modifier.height(32.dp))
            // 4. 비밀번호 찾기 버튼
            Text(
                "계정이 기억나지 않아요",
                style = MaterialTheme.typography.labelLarge.copy(
                    textDecoration = TextDecoration.Underline
                ),
                color = LabelNeutral,
                modifier = Modifier.clickable {

                }
            )
        }
    }
}