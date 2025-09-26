package com.team_gori.gori.designsystem.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_up
import gori.designsystem.generated.resources.ic_down
import gori.designsystem.generated.resources.ic_notice
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriNoticeText(
    text: String,
    modifier: Modifier = Modifier,
) {
    // 확장/축소 상태를 내부적으로 관리
    var isExpanded by remember { mutableStateOf(false) }
    // 텍스트의 실제 줄 수를 저장하기 위한 상태
    var textLineCount by remember { mutableStateOf(0) }
    // lineCount가 1일 때만 isSingleLine을 true로 설정 (초기값 0은 다중 라인으로 간주)
    val isSingleLine = textLineCount == 1

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.semanticColors.onPrimary // 테마 색상 사용
        )
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp) // 내부 여백 12dp
                .fillMaxWidth()
                .heightIn(min = 24.dp) // 최소 높이 지정 (아이콘과 텍스트가 겹치지 않도록)
                .animateContentSize() // 컨텐츠 크기 변경 시 부드러운 애니메이션
        ) {
            // 좌측 상단 아이콘
            Image(
                painter = painterResource(Res.drawable.ic_notice),
                contentDescription = "공지사항",
                modifier = Modifier
                    .align(if (isSingleLine) Alignment.CenterStart else Alignment.TopStart)
                    .size(24.dp)
            )

            // 우측 상단 확장/축소 아이콘 버튼
            IconButton(
                onClick = { isExpanded = !isExpanded },
                modifier = Modifier
                    .align(if (isSingleLine) Alignment.CenterEnd else Alignment.TopEnd)
                    .size(24.dp)
            ) {
                // 텍스트가 1줄이면 확장/축소 버튼이 필요 없으므로 숨김 처리
                if (!isSingleLine) {
                    Image(
                        painter = if (isExpanded) painterResource(Res.drawable.ic_up) else painterResource(Res.drawable.ic_down),
                        contentDescription = if (isExpanded) "축소" else "확장",
                        modifier = Modifier
                    )
                }
            }

            // 중앙 텍스트
            Text(
                text = text,
                onTextLayout = { textLayoutResult ->
                    // 텍스트 레이아웃이 계산된 후 실제 줄 수를 상태에 업데이트
                    textLineCount = textLayoutResult.lineCount
                },
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    // 아이콘과 겹치지 않도록 충분한 수평 패딩 적용
                    .padding(horizontal = 32.dp),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                // isExpanded 상태와 isSingleLine 상태에 따라 maxLines 동적 변경
                maxLines = when {
                    isSingleLine -> 1 // 1줄짜리 텍스트는 항상 1줄
                    isExpanded -> 3   // 확장 시 최대 3줄
                    else -> 2         // 기본 상태에서는 최대 2줄
                },
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}