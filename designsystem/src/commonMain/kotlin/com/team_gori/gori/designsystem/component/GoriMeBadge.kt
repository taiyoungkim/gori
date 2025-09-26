package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team_gori.gori.designsystem.theme.Neutral60
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.pretendard_medium

@Composable
fun GoriMeBadge() {
    Box(modifier = Modifier
        .width(18.dp)
        .height(18.dp)
        .background(color = Neutral60, shape = RoundedCornerShape(size = 1000.dp))
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "나",
            style = TextStyle(
                fontSize = 11.sp,
                lineHeight = 16.5.sp,
                fontFamily = FontFamily(
                    org.jetbrains.compose.resources.Font(
                        Res.font.pretendard_medium,
                        FontWeight.Medium
                    )),
                fontWeight = FontWeight(700),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
            )
        )
    }
}