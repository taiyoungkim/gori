package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team_gori.gori.designsystem.theme.semanticColors

@Composable
fun UnreadBadge(
    modifier: Modifier = Modifier,
    count: Int
) {
    // count가 0보다 클 때만 배지를 보여줌
    if (count > 0) {
        val text = if (count > 99) "99+" else count.toString()
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.semanticColors.primaryNormal),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = MaterialTheme.semanticColors.onPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            )
        }
    }
}