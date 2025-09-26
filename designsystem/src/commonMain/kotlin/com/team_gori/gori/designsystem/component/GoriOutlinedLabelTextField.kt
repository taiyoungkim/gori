package com.team_gori.gori.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import com.team_gori.gori.designsystem.theme.CautionText
import com.team_gori.gori.designsystem.theme.Neutral80
import com.team_gori.gori.designsystem.theme.PrimaryColor
import com.team_gori.gori.designsystem.theme.semanticColors

/**
 * 라벨과 내부 우측 타이머가 있는 OutlinedTextField 컴포넌트.
 *
 * @param value TextField에 표시될 현재 텍스트.
 * @param onValueChange 텍스트가 변경될 때 호출될 콜백.
 * @param modifier Modifier 인스턴스.
 * @param enabled TextField의 활성화 여부.
 * @param readOnly TextField를 읽기 전용으로 설정할지 여부.
 * @param label TextField 상단에 표시될 라벨 Composable.
 * @param timerSeconds 내부에 "mm:ss" 형식으로 표시될 총 시간(초 단위). null이면 타이머 미표시.
 * @param placeholder 입력 필드가 비어있고 포커스되었을 때 표시될 텍스트 Composable.
 * @param leadingIcon TextField 앞에 표시될 아이콘 Composable.
 * @param isError 오류 상태 여부. true이면 오류 강조 색상이 적용됩니다.
 * @param supportingText TextField 하단에 표시될 도움말 또는 오류 메시지 Composable.
 * @param visualTransformation 입력 값의 시각적 변환 (예: PasswordVisualTransformation).
 * @param keyboardOptions 키보드 옵션 (예: 키보드 타입, ImeAction).
 * @param keyboardActions 키보드 액션 처리.
 * @param singleLine TextField를 한 줄로 제한할지 여부.
 * @param maxLines 최대 줄 수.
 */
@Composable
fun GoriOutlinedLabelTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    label: @Composable (() -> Unit)? = null,
    timerSeconds: Int? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
) {
    fun formatTime(totalSeconds: Int): String {
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return "$minutes:$seconds"
//        return "%02d:%02d".format(minutes, seconds)
    }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = if (timerSeconds != null) {
            {
                Text(
                    text = formatTime(timerSeconds),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            null
        },
        isError = isError,
        supportingText = supportingText,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Neutral80,
            unfocusedTextColor = Neutral80,
            errorTextColor = CautionText,

            // 커서 색상
            cursorColor = PrimaryColor,
            errorCursorColor = CautionText,

            // 보더 색상
            focusedBorderColor = Neutral80,
            unfocusedBorderColor = MaterialTheme.semanticColors.lineAlternative,
            disabledBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            errorBorderColor = CautionText,

            // 라벨 색상
            focusedLabelColor = Neutral80,
            unfocusedLabelColor = MaterialTheme.semanticColors.lineAlternative,
            disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
            errorLabelColor = CautionText,

            // SupportingText 색상
            errorSupportingTextColor = CautionText
        )
    )
    // --- 커스텀 색상 적용 끝 ---
}