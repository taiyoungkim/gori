package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * 액션 시트(바텀 시트)
 * @param onDismissRequest 시트가 닫힐 때 호출
 * @param actions 표시할 액션 목록 (Pair<텍스트, 클릭 시 동작>)
 * @param cancelButtonText 취소 버튼에 표시될 텍스트
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoriActionSheet(
    onDismissRequest: () -> Unit,
    actions: List<Pair<String, () -> Unit>>,
    cancelButtonText: String = "취소"
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        containerColor = Color.Transparent,
        dragHandle = null,
        scrimColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 16.dp), // 하단 여백 추가
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 액션 버튼 그룹
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
            ) {
                actions.forEachIndexed { index, (text, onClick) ->
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Normal
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // 코루틴을 사용해 시트가 닫히는 애니메이션과 액션 실행을 분리
                                scope.launch {
                                    sheetState.hide()
                                    onDismissRequest()
                                    onClick()
                                }
                            }
                            .padding(vertical = 16.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    if (index < actions.lastIndex) {
                        HorizontalDivider(color = Color.Gray.copy(alpha = 0.3f))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 취소 버튼
            Text(
                text = cancelButtonText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable {
                        scope.launch {
                            sheetState.hide()
                            onDismissRequest()
                        }
                    }
                    .padding(vertical = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}