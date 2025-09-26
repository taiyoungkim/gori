package com.team_gori.gori.feature_chat.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team_gori.gori.core.model.Category
import com.team_gori.gori.designsystem.component.GoriChipRadioGroup
import com.team_gori.gori.designsystem.component.GoriOutlinedTextField
import com.team_gori.gori.designsystem.component.GoriTitleText
import com.team_gori.gori.designsystem.component.GoriMultiColorText
import com.team_gori.gori.designsystem.component.GoriTwoButton
import com.team_gori.gori.designsystem.theme.CustomTheme
import com.team_gori.gori.designsystem.theme.Neutral40
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_x
import gori.composeapp.generated.resources.ic_plus
import gori.composeapp.generated.resources.ic_minus
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddChatRoomScreen (
    onNavigateBack: () -> Unit
) {
    var completed by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }
    var chatRoom by remember { mutableStateOf("") }
    var chatMember by remember { mutableStateOf(0) }
    var chatRoomDescription by remember { mutableStateOf("") }

    CustomTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "채팅방 만들기",
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Image(
                                painter = painterResource(Res.drawable.ic_x),
                                contentDescription = "닫기",
                            )
                        }
                    },
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
            ) {
                GoriTitleText(
                    text = "어떤 주제의 채팅방인가요?",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                // category list
                val list = Category.entries.toTypedArray().map { it.name }
                GoriChipRadioGroup(
                    items = listOf(
                        "건강/운동",
                        "맛집/카페",
                        "취미",
                        "나들이/여행",
                        "재테크",
                        "일상",
                        "기타",
                    ),
                    selectedItem = selectedCategory,
                    onItemSelect = {
                        selectedCategory = it
                    },
                )
                GoriTitleText(
                    text = "채팅방 이름",
                    modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
                )
                GoriOutlinedTextField(
                    value = chatRoom,
                    onValueChange = {
                        chatRoom = it
                    },
                    modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                    hint = "채팅방 이름을 입력해주세요.",
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    GoriMultiColorText(
                        Pair(chatRoom.length.toString(), Neutral40),
                        Pair("/10", Neutral40),
                    )
                }
                GoriTitleText(
                    text = "채팅방 인원 (최대 100명)",
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "나를 포함해서 총",
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Start
                    )
                    Text(
                        "${chatMember + 1}명",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    GoriOutlinedTextField(
                        modifier = Modifier.weight(1f).height(54.dp),
                        value = chatMember.toString(),
                        onValueChange = { chatMember = it.toInt() },
                        suffixText = "명"
                    )
                    GoriTwoButton(
                        leftImagePainter = Res.drawable.ic_minus,
                        rightImagePainter = Res.drawable.ic_plus,
                        onLeftClick = {
                            if (chatMember > 0)
                                chatMember--
                        },
                        onRightClick = {
                            if (chatMember < 100)
                                chatMember++
                        },
                        leftImageContentDescription = "인원 마이너스",
                        rightImageContentDescription = "인원 플러스",
                        leftEnabled = chatMember > 0,
                        rightEnabled = chatMember < 100,
                    )
                }
                GoriTitleText(
                    text = "채팅방을 소개해 주세요.",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                GoriOutlinedTextField(
                    chatRoomDescription,
                    { chatRoomDescription = it },
                    modifier = Modifier.heightIn(min = 300.dp).fillMaxWidth(),
                    hint = "·어떤 주제로 이야기 하실건가요?\n·어떤 규칙이 있나요?",
                    singleLine = false,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    GoriMultiColorText(
                        Pair(chatRoomDescription.length.toString(), Neutral40),
                        Pair("/100", Neutral40),
                    )
                }
            }
        }
    }
}