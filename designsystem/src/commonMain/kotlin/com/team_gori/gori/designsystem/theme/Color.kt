package com.team_gori.gori.designsystem.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.graphics.Color

// Neutral
val Neutral5 = Color(0xFFFAFAFA)
val Neutral10 = Color(0xFFF5F6F9)
val Neutral20 = Color(0xFFE9EBF1)
val Neutral30 = Color(0xFFD2D4DA)
val Neutral40 = Color(0xFFAEB2BA)
val Neutral50 = Color(0xFF8D9199)
val Neutral60 = Color(0xFF6E727A)
val Neutral70 = Color(0xFF53565C)
val Neutral80 = Color(0xFF2F3134)
val Neutral90 = Color(0xFF1A1B1D)
val Neutral100 = Color(0xFFF5F5F5)
val Neutral200 = Color(0xFFE9EBF1)
val Neutral300 = Color(0xFFD2D4DA)
val Neutral400 = Color(0xFFAEB2BA)
val Neutral500 = Color(0xFF8D9199)
val Neutral600 = Color(0xFF6E727A)
val Neutral700 = Color(0xFF53565C)
val Neutral800 = Color(0xFF2F3134)
val Neutral900 = Color(0xFF1A1B1D)

// Purple
val Purple5 = Color(0xFFEFEDFF)
val Purple10 = Color(0xFFDEDAFF)
val Purple20 = Color(0xFFCEC8FF)
val Purple30 = Color(0xFFADA3FF)
val Purple40 = Color(0xFF907FFF)
val Purple50 = Color(0xFF7463F6)
val Purple60 = Color(0xFF5E45FF)
val Purple70 = Color(0xFF4837C9)
val Purple80 = Color(0xFF352C77)
val Purple90 = Color(0xFF181433)
val Purple100 = Color(0xFFDDD8FF) // 이미지의 #DDD8F는 6자리가 아니므로 FF로 수정했습니다.
val Purple200 = Color(0xFFC9C0FF)
val Purple300 = Color(0xFFB4A9FF)
val Purple400 = Color(0xFFA091FF)
val Purple500 = Color(0xFF7762FF) // 로고 컬러
val Purple600 = Color(0xFF5E45FF)
val Purple700 = Color(0xFF4837C9)
val Purple800 = Color(0xFF352C77)
val Purple900 = Color(0xFF181433)

val Red50 = Color(0xFFC03C34)

// Orange
val Orange50 = Color(0xFFFFF4EE)
val Orange100 = Color(0xFFFEE3D4)
val Orange200 = Color(0xFFFED2BB)
val Orange300 = Color(0xFFFEC1A1)
val Orange400 = Color(0xFFFD8F54)
val Orange500 = Color(0xFFF86D22)
val Orange600 = Color(0xFFDD6422)
val Orange700 = Color(0xFFB85632)
val Orange800 = Color(0xFF653922)
val Orange900 = Color(0xFF331D11)

// Skyblue
val Blue50 = Color(0xFF4F98FF)
val Skyblue50 = Color(0xFFF6FAFF)
val Skyblue100 = Color(0xFFE8F2FF)
val Skyblue200 = Color(0xFFCBE1FF)
val Skyblue300 = Color(0xFFB4D4FF) // 이미지의 #B4D4FF는 오타로 보여 #B4D4FF로 수정했습니다.
val Skyblue400 = Color(0xFFA1C9FF) // 이미지의 #A1C9FF는 오타로 보여 #A1C9FF로 수정했습니다.
val Skyblue500 = Color(0xFF91B5E5)
val Skyblue600 = Color(0xFF718DB2)
val Skyblue700 = Color(0xFF506480)
val Skyblue800 = Color(0xFF303C4D)
val Skyblue900 = Color(0xFF171E2B)

// Brown
val Brown5 = Color(0xFFF7F2F2)
val Brown10 = Color(0xFFEBE0DE)
val Brown20 = Color(0xFFDFCDCA)
val Brown30 = Color(0xFFD3BAB6)
val Brown40 = Color(0xFFC7A8A2)
val Brown50 = Color(0xFFAF827A)
val Brown60 = Color(0xFF8C6862)
val Brown70 = Color(0xFF694E49)
val Brown80 = Color(0xFF463431)
val Brown90 = Color(0xFF231A18)
val Brown100 = Color(0xFFEBE0DE)
val Brown200 = Color(0xFFDFCDCA)
val Brown300 = Color(0xFFD3BAB6)
val Brown400 = Color(0xFFC7A8A2)
val Brown500 = Color(0xFFAF827A)
val Brown600 = Color(0xFF8C6862)
val Brown700 = Color(0xFF694E49)
val Brown800 = Color(0xFF463431)
val Brown900 = Color(0xFF231A18)

// [NEW] Palette / Common
val Common0 = Color(0xFFFFFFFF)
val Common100 = Color(0xFF000000)

// [NEW] Palette / Opacity
val Opacity6 = Color(0x0F2F3134)
val Opacity10 = Color(0x192F3134)
val Opacity16 = Color(0x282F3134)
val Opacity28 = Color(0x472F3134)
val Opacity36 = Color(0x5B2F3134)
val Opacity40 = Color(0x662F3134)
val Opacity88 = Color(0xE02F3134)

val PrimaryColor = Color(0xFF6F5CFA)

val ChipGrayBackground = Color(0xFFF1F2F3)
val ChipGrayText = Color(0xFFACB4B9)
val ChipPurpleBackground = Color(0xFFE4DEFE)
val ChipPurpleText = Color(0xFF5343D7)
val CautionText = Color(0xFFC03C34)
val Success = Color(0xFF4F98FF)

val BackgroundNormal = Color(0xFFFFFFFF)
val BackgroundAlternative = Color(0xFFF7F7F8)

val InteractionDisable = Color(0xFFF7F7F8)

val LabelNormal = Color(0xFF171719)
val LabelStrong = Color(0xFF000000)
val LabelNeutral = Color(0xFF6D6B6C)
val LabelAssistive = Color(0x37383C47)
val LabelDisable = Color(0x37383C29)

val secondaryDisabled = Color(0xFFC2C4C8)
val primaryDisabled = Color(0xFFE4E5EA)

val lightSemanticColors = SemanticColors(
    // === Semantic / Primary ===
    primaryNormal = Purple50,
    primaryVariant = Purple60,
    primaryPressed = Purple30,
    primaryDisabled = Neutral20,

    // === Semantic / Secondary ===
    secondaryNormal = Neutral90,
    secondaryPressed = Neutral60,
    secondaryDisabled = Neutral30,

    // === Semantic / Tertiary ===
    tertiaryNormal = Neutral10,
    tertiaryPressed = Neutral30,
    tertiaryDisabled = Opacity36,

    // === Semantic / Status ===
    statusSuccess = Blue50,
    statusError = Red50,

    labelNormal = Neutral90,
    labelStrong = Common100,
    labelNeutral = Neutral70,
    labelAlternative = Neutral50,
    labelDisabled = Opacity36,

    lineNormal = Opacity28,
    linePressed = Neutral90,
    lineNeutralLight = Opacity10,
    lineNeutralMedium = Opacity16,
    lineAlternative = Opacity6,

    backgroundNormal = Common0,
    backgroundChat = Neutral5,
    backgroundDimmer = Opacity40,
    backgroundAlternative = Neutral10,
    backgroundTooltip = Opacity88,

    onPrimary = Common0,
    onSecondary = Common0,
    onSuccess = Common0,
    onError = Common0,
)

// Dark Theme에 사용할 Semantic Color 조합 (예시)
val darkSemanticColors = SemanticColors(
    // === Semantic / Primary === (전반적으로 밝은 톤으로 변경)
    primaryNormal = Purple30,
    primaryVariant = Purple40,
    primaryPressed = Purple50,
    primaryDisabled = Neutral80, // 비활성화 배경은 어둡게

    // === Semantic / Secondary === (어두운 배경 위에 밝은 글씨/아이콘으로 반전)
    secondaryNormal = Neutral10,
    secondaryPressed = Neutral40,
    secondaryDisabled = Neutral70,

    // === Semantic / Tertiary === (어두운 배경 위에 밝은 외곽선/텍스트로 반전)
    tertiaryNormal = Neutral80,
    tertiaryPressed = Neutral70,
    tertiaryDisabled = Common0.copy(alpha = 0.36f), // 검은색 투명도 -> 흰색 투명도로

    // === Semantic / Status === (대비가 보이도록 톤 조정)
    statusSuccess = Blue50, // Blue 계열은 그대로 사용하거나 밝은 톤으로 조정
    statusError = Red50,    // Red 계열은 그대로 사용하거나 밝은 톤으로 조정

    // === Semantic / Label (UPDATED) === (어두운 배경 위의 밝은 텍스트 색상으로)
    labelNormal = Neutral10,
    labelStrong = Common0,
    labelNeutral = Neutral30,
    labelAlternative = Neutral50,
    labelDisabled = Common0.copy(alpha = 0.36f), // 검은색 투명도 -> 흰색 투명도로

    // === Semantic / Line (UPDATED) === (어두운 배경 위의 밝은 구분선으로)
    lineNormal = Common0.copy(alpha = 0.28f),
    linePressed = Neutral10,
    lineNeutralLight = Common0.copy(alpha = 0.10f),
    lineNeutralMedium = Common0.copy(alpha = 0.16f),
    lineAlternative = Common0.copy(alpha = 0.06f),

    // === Semantic / Background (UPDATED) === (전체적으로 어두운 배경으로)
    backgroundNormal = Common100, // 흰색 배경 -> 검은색 배경으로
    backgroundChat = Neutral90,
    backgroundDimmer = Opacity40, // Dimmer는 그대로 사용 가능
    backgroundAlternative = Neutral80,
    backgroundTooltip = Opacity88, // Tooltip도 그대로 사용 가능

    // === Content Colors === (배경이 밝아졌으므로 콘텐츠는 어둡게)
    onPrimary = Common100,
    onSecondary = Common100,
    onSuccess = Common100,
    onError = Common100,
)

@Immutable
data class SemanticColors(
    // Primary
    val primaryNormal: Color,
    val primaryVariant: Color,
    val primaryPressed: Color,
    val primaryDisabled: Color,

    // Secondary
    val secondaryNormal: Color,
    val secondaryPressed: Color,
    val secondaryDisabled: Color,

    // Tertiary
    val tertiaryNormal: Color,
    val tertiaryPressed: Color,
    val tertiaryDisabled: Color,

    // Status
    val statusSuccess: Color,
    val statusError: Color,

    // Content Colors (배경색 위에 올라가는 텍스트/아이콘 색상)
    val onPrimary: Color,
    val onSecondary: Color,
    val onSuccess: Color,
    val onError: Color,

    // label
    val labelNormal: Color,
    val labelStrong: Color,
    val labelNeutral: Color,
    val labelAlternative: Color,
    val labelDisabled: Color, // contentDisabled와 유사하지만 별도 정의

    // Line (Divider, Border) Colors
    val lineNormal: Color,
    val linePressed: Color,
    val lineNeutralLight: Color,
    val lineNeutralMedium: Color,
    val lineAlternative: Color,

    // Background Colors
    val backgroundNormal: Color,
    val backgroundChat: Color,
    val backgroundDimmer: Color,
    val backgroundAlternative: Color,
    val backgroundTooltip: Color
)