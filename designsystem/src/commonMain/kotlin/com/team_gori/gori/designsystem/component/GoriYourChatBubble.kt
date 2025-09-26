package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelAssistive
import com.team_gori.gori.designsystem.theme.semanticColors

@Composable
fun GoriYourChatBubble(
    text: String,
    time: String,
    isFirst: Boolean,
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        // 상대방 말풍선 텍스트
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .background(
                    color = MaterialTheme.semanticColors.backgroundNormal,
                    shape = RoundedCornerShape(
                        topStart = if (isFirst) 2.dp else 24.dp,
                        topEnd = 24.dp,
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )
                )
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
            color = MaterialTheme.semanticColors.secondaryNormal
        )

        // 시간 텍스트
        Text(
            text = time,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = LabelAssistive,
        )
    }
}