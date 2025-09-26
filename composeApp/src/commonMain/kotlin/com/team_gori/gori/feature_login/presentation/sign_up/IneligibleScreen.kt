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
import gori.composeapp.generated.resources.warning
import org.jetbrains.compose.resources.painterResource

@Composable
fun IneligibleScreen(
    onClose: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally // 모든 자식들을 수평 중앙 정렬
    ) {
        // 1. 중앙에 위치할 콘텐츠들을 별도의 Column으로 감쌉니다.
        Column(
            // 2. 이 Column이 버튼을 제외한 모든 남은 공간을 차지하도록 weight(1f)를 줍니다.
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            // 3. 차지한 공간 내에서 콘텐츠들을 수직 중앙 정렬합니다.
            verticalArrangement = Arrangement.Center
        ) {
            // 중앙 이미지
            Image(
                painter = painterResource(Res.drawable.warning),
                contentDescription = "가입 불가 안내",
                modifier = Modifier.size(270.dp)
            )
            Spacer(modifier = Modifier.height(35.dp))

            // 안내 텍스트
            Text(
                text = "고리는 50대 이상만\n가입이 가능해요",
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