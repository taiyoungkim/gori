package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_down
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriTimeSelector(time: LocalTime?, onClick: () -> Unit) {
    val timeText = if (time == null) {
        "선택"
    } else {
        val ampm = if (time.hour < 12) "오후" else "오전" // 스크린샷과 반대인 것 같아 수정
        val hour = if (time.hour % 12 == 0) 12 else time.hour % 12
        val minute = time.minute.toString().padStart(2, '0')
        "$ampm ${hour}:${minute}"
    }

    Row(
        modifier = Modifier
            .border(1.dp, MaterialTheme.semanticColors.lineAlternative, RoundedCornerShape(12.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(timeText, style = MaterialTheme.typography.headlineMedium)
        Icon(
            painter = painterResource(Res.drawable.ic_down),
            contentDescription = "시간 선택",
            tint = LabelNormal
        )
    }
}