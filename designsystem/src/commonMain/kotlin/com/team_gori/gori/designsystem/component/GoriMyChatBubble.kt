package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelAssistive
import com.team_gori.gori.designsystem.theme.PrimaryColor

@Composable
fun GoriMyChatBubble(
    text: String,
    time: String,
    isFirst: Boolean,
    isDimmed: Boolean = false,
) {
    val alpha = if (isDimmed) 0.3f else 1.0f

    Row(
        modifier = Modifier.alpha(alpha),
        verticalAlignment = Alignment.Bottom,
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.height(21.dp)
                .align(Alignment.Bottom)
                .padding(horizontal = 8.dp),
            color = LabelAssistive,
        )

        // 말풍선 텍스트
        Text(
            text = text,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .background(
                    color = PrimaryColor,
                    shape = RoundedCornerShape(
                        topStart = 24.dp,
                        topEnd = 24.dp,
                        bottomStart = 24.dp,
                        bottomEnd = if (isFirst) 2.dp else 24.dp
                    )
                )
                .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}