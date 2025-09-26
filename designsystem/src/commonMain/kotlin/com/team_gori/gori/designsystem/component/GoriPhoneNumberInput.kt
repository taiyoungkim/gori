package com.team_gori.gori.designsystem.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral40
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_check
import gori.designsystem.generated.resources.ic_down
import org.jetbrains.compose.resources.painterResource

// 1) 통신사 타입 & 기본 목록
enum class Carrier(val label: String) {
    SKT("SKT"), KT("KT"), LGU("LG U+"), MVNO("알뜰폰")
}

val DefaultCarriers = listOf(Carrier.SKT, Carrier.KT, Carrier.LGU, Carrier.MVNO)


// 2) 한 박스 안에: [통신사 ▼]  [전화번호 입력]  [✓]
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoriPhoneNumberInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    selectedCarrier: String,
    onCarrierSelected: (String) -> Unit,
    isReadOnly: Boolean,
    modifier: Modifier = Modifier,
    carriers: List<Carrier> = DefaultCarriers
) {
    val shape = RoundedCornerShape(10.dp)

    var showSheet by remember { mutableStateOf(false) }
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val phoneFocus = remember { FocusRequester() }

    // 포커스에 따라 외곽선 색을 바꾸고 싶다면 onFocusChanged로 상태를 받으면 됨
    var isPhoneFocused by remember { mutableStateOf(false) }

    // 한국 휴대폰 간단 유효성(010/011/016/017/018/019 + 10~11자리)
    val isPhoneValid = remember(phoneNumber, selectedCarrier) {
        val okPrefix = phoneNumber.startsWith("010")
                || phoneNumber.startsWith("011")
                || phoneNumber.startsWith("016")
                || phoneNumber.startsWith("017")
                || phoneNumber.startsWith("018")
                || phoneNumber.startsWith("019")
        okPrefix && phoneNumber.length == 11 && selectedCarrier.isNotBlank()
    }

    val carrier = Carrier.entries.find { it.name == selectedCarrier }
    Box(
        modifier = modifier
            .clip(shape)
            .border(
                BorderStroke(
                    width = 1.dp,
                    color = if (isPhoneFocused) LabelNormal
                    else Neutral40
                ),
                shape
            )
            .background(Color.Transparent, shape)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ── 왼쪽: 통신사 선택 버튼 ───────────────────────────────
            Row(
                modifier = Modifier
                    .defaultMinSize(minWidth = 72.dp)
                    .padding(end = 12.dp)
                    .clickable(enabled = !isReadOnly) {
                        showSheet = true
                        focusManager.clearFocus()
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = carrier?.label ?: "통신사",
                    style = MaterialTheme.typography.headlineMedium,
                    color = LabelNormal
                )
                Spacer(Modifier.width(4.dp))
                Icon(
                    painter = painterResource(Res.drawable.ic_down),
                    contentDescription = "통신사 선택",
                    tint = LabelNormal
                )
            }

            // ── 가운데: 전화번호 입력 ──────────────────────────────
            BasicTextField(
                value = phoneNumber,
                onValueChange = { raw ->
                    if (isReadOnly) return@BasicTextField
                    // 숫자만, 최대 11자리
                    val digits = raw.filter(Char::isDigit).take(11)
                    onPhoneNumberChange(digits)
                },
                enabled = !isReadOnly,
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = LabelNormal),
                cursorBrush = SolidColor(LabelNormal),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { keyboard?.hide() }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(phoneFocus)
                    .onFocusChanged { isPhoneFocused = it.isFocused },
                decorationBox = { inner ->
                    if (phoneNumber.isEmpty()) {
                        Text(
                            text = "01012345678",
                            style = MaterialTheme.typography.headlineMedium,
                            color = Neutral40,
                        )
                    }
                    inner()
                }
            )

            // ── 오른쪽: 체크 표시(유효 시 보라) ─────────────────────
            AnimatedVisibility(visible = isPhoneValid) {
                Icon(
                    painter = painterResource(Res.drawable.ic_check),
                    contentDescription = "유효",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }

    // ── 바텀시트: 통신사 리스트 ──────────────────────────────────
    if (showSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            Text(
                text = "통신사를 선택해 주세요.",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
            )
            carriers.forEach { c ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onCarrierSelected(c.label)
                            showSheet = false
                            // 선택 후 전화번호로 포커스 이동 + 키보드 열기
                            phoneFocus.requestFocus()
                            keyboard?.show()
                        }
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = c.label,
                        style = MaterialTheme.typography.headlineMedium,
                        color = LabelNormal,
                        modifier = Modifier.weight(1f)
                    )
                    if (carrier == c) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_check),
                            contentDescription = "선택됨",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
