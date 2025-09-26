package com.team_gori.gori.feature_login.presentation.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriFilledButton
import com.team_gori.gori.designsystem.theme.LabelNeutral
import com.team_gori.gori.designsystem.theme.LabelNormal
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.sign_up_blacklist
import org.jetbrains.compose.resources.painterResource

@Composable
fun SignUpBlackListScreen(
    onClose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally // 모든 자식들을 수평 중앙 정렬
    ) {
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 중앙 이미지
            Image(
                painter = painterResource(Res.drawable.sign_up_blacklist),
                contentDescription = "가입 불가 안내",
                modifier = Modifier.size(270.dp)
            )
            Spacer(modifier = Modifier.height(35.dp))

            // 안내 텍스트
            Text(
                text = "고리 서비스 정책에 의해\n가입이 불가능한 정보예요",
                style = MaterialTheme.typography.titleSmall,
                color = LabelNormal,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "다음 기회에 함께해요!",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = LabelNeutral
            )
        }

        GoriFilledButton(
            onClick = {
                onClose()
            },
            enabled = true,
            text = "마치기",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(50.dp)
        )
    }
}