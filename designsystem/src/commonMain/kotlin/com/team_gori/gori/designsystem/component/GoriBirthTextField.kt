package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.team_gori.gori.designsystem.theme.LabelNeutral
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral20
import com.team_gori.gori.designsystem.theme.Neutral30
import com.team_gori.gori.designsystem.theme.Neutral40
import com.team_gori.gori.designsystem.theme.Neutral50
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_x_circle
import org.jetbrains.compose.resources.painterResource
import kotlin.math.max
import kotlin.math.min

@Composable
fun GoriBirthTextField(
    modifier: Modifier = Modifier,
    birth6: String,
    onBirth6Change: (String) -> Unit,
    back7: String,
    onBack7Change: (String) -> Unit,
    enabled: Boolean = true,
    placeholderBirth: String = "6자리",
) {
    val shape = RoundedCornerShape(10.dp)
    val focusManager = LocalFocusManager.current

    val birthFocus = remember { FocusRequester() }
    val backFocus = remember { FocusRequester() }

    Box(
        modifier = modifier
            .clip(shape)
            .border(BorderStroke(1.dp, if (birth6.isEmpty() && back7.isEmpty()) Neutral40 else LabelNormal), shape)
            .background(Color.Transparent)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ===== Left: YYMMDD (6 digits) =====
            BasicTextField(
                value = birth6,
                onValueChange = { raw ->
                    val filtered = raw.filter(Char::isDigit).take(6)
                    onBirth6Change(filtered)
                    if (filtered.length == 6) {
                        backFocus.requestFocus()
                    }
                },
                enabled = enabled,
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = LabelNormal),
                cursorBrush = SolidColor(LabelNormal),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = androidx.compose.ui.text.input.ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { backFocus.requestFocus() }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(birthFocus),
                decorationBox = { inner ->
                    if (birth6.isEmpty()) {
                        Text(
                            text = placeholderBirth,
                            color = Neutral40,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    inner()
                }
            )

            // Hyphen
            Text(
                text = " - ",
                color = if (birth6.length >= 6) LabelNormal else Neutral40,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            // ===== Right: Back 7 (first visible, next 6 as dots) =====
            MaskedBack7Field(
                value = back7,
                onValueChange = { raw ->
                    val filtered = raw.filter(Char::isDigit).take(7)
                    onBack7Change(filtered)
                    if (filtered.length == 7) {
                        focusManager.clearFocus()
                    }
                },
                enabled = enabled,
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(backFocus)
                    .onPreviewKeyEvent { ev ->
                        if (ev.type == KeyEventType.KeyDown && ev.key == Key.Backspace && back7.isEmpty()) {
                            if (birth6.isNotEmpty()) {
                                onBirth6Change(birth6.dropLast(1))
                                birthFocus.requestFocus()
                            }
                            true
                        } else {
                            false
                        }
                    }
            )
        }
    }
}

/**
 * 오른쪽 7자리 입력 영역.
 * - 실제 텍스트는 보이지 않게 하고(투명), 오버레이로 표현을 커스텀
 * - 첫 글자만 평문으로 보여주고, 나머지는 6개의 점을 띄움
 * - 입력된 개수만큼 회색 점 -> 검은 점으로 채움
 */
@Composable
private fun MaskedBack7Field(
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    // 색상 정의: 기본 회색 점 / 채워진 검은 점
    val emptyDot = Neutral40
    val filledDot = LabelNormal

    // BasicTextField 로 포커스/키 입력을 받되, 텍스트는 보이지 않게 처리
    Box(modifier = modifier, contentAlignment = Alignment.CenterStart) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            textStyle = TextStyle(
                color = Color.Transparent, // 실제 입력 문자 숨김
                fontSize = 16.sp
            ),
            cursorBrush = SolidColor(Color.Transparent), // 커서도 숨김(원하면 보이게 바꿔도 됨)
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = androidx.compose.ui.text.input.ImeAction.Done
            ),
            keyboardActions = KeyboardActions.Default,
            modifier = Modifier
                .fillMaxWidth()
                .alpha(0.01f) // 포커스는 받되 거의 보이지 않게
        )

        // 가시 표현 오버레이
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 첫 글자(있으면 노출)
            val firstChar = value.firstOrNull()?.toString() ?: ""
            Box(
                modifier = Modifier.width(18.dp), // 1글자 자리 확보(고정폭)
                contentAlignment = Alignment.Center
            ) {
                if (firstChar.isNotEmpty()) {
                    Text(
                        text = firstChar,
                        color = LabelNormal,
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )
                } else {
                    // 아무것도 입력 안했을 때는 빈 공간 유지
                    Text(text = "", style = MaterialTheme.typography.headlineMedium,)
                }
            }

            Spacer(Modifier.width(8.dp))

            // 나머지 6개의 점(placeholder)
            val filledCount = max(0, value.length - 1) // 2~7번째 입력 개수
            DotRow(
                total = 6,
                filled = min(filledCount, 6),
                emptyColor = emptyDot,
                filledColor = filledDot,
                size = 8.dp,
                gap = 10.dp
            )
        }
    }
}

/** 점(●) 6개를 가로로 배치. filled 개수만큼 진하게 채움 */
@Composable
private fun DotRow(
    total: Int,
    filled: Int,
    emptyColor: Color,
    filledColor: Color,
    size: Dp,
    gap: Dp
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        repeat(total) { idx ->
            Box(
                modifier = Modifier
                    .size(size)
                    .background(
                        color = if (idx < filled) filledColor else emptyColor,
                        shape = MaterialTheme.shapes.small
                    )
                    .clip(MaterialTheme.shapes.small)
            )
            if (idx != total - 1) Spacer(Modifier.width(gap))
        }
    }
}
