package com.team_gori.gori.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.pretendard_bold
import gori.designsystem.generated.resources.pretendard_medium
import gori.designsystem.generated.resources.pretendard_semi_bold

@Composable
fun AppTypography(): Typography {
    val pretendard = FontFamily(
        Font(Res.font.pretendard_bold, FontWeight.Bold),
        Font(Res.font.pretendard_semi_bold, FontWeight.SemiBold),
        Font(Res.font.pretendard_medium, FontWeight.Medium),
    )

    return Typography(
        displayLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp,
            lineHeight = 48.sp,
            letterSpacing = (0.2).sp,
        ),
        displayMedium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            lineHeight = 43.sp,
            letterSpacing = (0.2).sp,
        ),
        titleLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            lineHeight = 38.sp,
            letterSpacing = 0.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            lineHeight = 34.sp,
            letterSpacing = 0.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
            lineHeight = 29.sp,
            letterSpacing = 0.sp,
        ),
        bodyLarge = TextStyle( // bodyLarge/Normal
            fontFamily = pretendard,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 26.sp,
            letterSpacing = 0.sp,
        ),
        headlineLarge = TextStyle( // bodyLarge/Reading
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            lineHeight = 33.sp,
            letterSpacing = 0.sp,
        ),
        headlineMedium = TextStyle( // bodyMedium/Reading
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 27.sp,
            letterSpacing = (-0.2).sp,
        ),
        bodyMedium = TextStyle( // bodyMedium/Normal
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 22.sp,
            letterSpacing = (-0.2).sp,
        ),
        labelLarge = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 19.sp,
            letterSpacing = (-0.2).sp,
        ),
        labelMedium = TextStyle(
            fontFamily = pretendard,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 17.sp,
            letterSpacing = (-0.2).sp,
        ),
    )
}