package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GoriPreviewContent(modifier: Modifier = Modifier) {
    var selected1 by remember { mutableStateOf(false) }
    var selected2 by remember { mutableStateOf(true) }
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        Text("Selectable Chips")
        Spacer(Modifier.height(8.dp))
        Row {
            GoriSelectableChip(text = "미선택", selected = selected1, onClick = { selected1 = !selected1 })
            Spacer(Modifier.width(8.dp))
            GoriSelectableChip(text = "선택됨", selected = selected2, onClick = { selected2 = !selected2 })
        }
        Spacer(Modifier.padding(8.dp))
        Row {
            GoriSelectableChip(text = "미선택 비활성", selected = false, onClick = { }, enabled = false)
            Spacer(Modifier.width(8.dp))
            GoriSelectableChip(text = "선택됨 비활성", selected = true, onClick = { }, enabled = false)
        }
        Spacer(Modifier.padding(8.dp))
        Row {
            GoriCautionText(
                text = "경고 메세지"
            )
        }
        Spacer(Modifier.padding(8.dp))
        Row {
            GoriPrimaryButton(
                text = "완료",
                enabled = true
            )
        }
        Row {
            GoriPrimaryButton(
                text = "미완료",
                enabled = false
            )
        }
        Spacer(Modifier.padding(8.dp))
        Row {
            GoriRoundButton(
                text = "완료",
                enabled = true,
                onClick = { showDialog = true }
            )
        }
        Row {
            GoriRoundButton(
                text = "미완료",
                enabled = false
            )
        }
        Spacer(Modifier.padding(8.dp))
        Column {
            GoriOutlinedTextField(
                "",
                {},
                hint = "hint",
            )
            GoriOutlinedTextField(
                "값이 있는 경우",
                {},
                hint = "hint",
            )
        }
        Spacer(Modifier.padding(8.dp))
        var textValue by remember { mutableStateOf("") }
        GoriOutlinedLabelTextField(
            textValue,
            { textValue = it},
            label = { Text("label") })
        Spacer(Modifier.padding(8.dp))
        GoriOutlinedLabelTextField(
            "에러",
            {},
            label = { Text("label") },
            isError = true,
            supportingText = { GoriCautionText("오류 메세지") }
        )
        Spacer(Modifier.padding(8.dp))
        Row {
            GoriBadge(
                text = "뱃지/예시"
            )
        }
        Spacer(Modifier.padding(8.dp))
        CustomAlertDialog(
            title = "그만두기",
            message = "모임 만들기를 중단할까요?",
            confirmButtonText = "중단하기",
            dismissButtonText = "계속 만들기",
            onConfirm = { showDialog = false },
            onDismiss = { showDialog = false },
            onDismissRequest = {},
            showDialog = showDialog
        )
        Spacer(Modifier.padding(8.dp))
        GoriImageButton(
            imageBitmap = null,
            onAddClick = {},
            onRemoveClick = {}
        )

        GoriNoticeText(
            "공지입니다.",
            Modifier,
        )

        GoriMyChatBubble(
            "여러분~여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분여러분",
            "00:05",
            true
        )
        GoriYourChatBubble(
            "왜",
            "00:05",
            true
        )
    }
}