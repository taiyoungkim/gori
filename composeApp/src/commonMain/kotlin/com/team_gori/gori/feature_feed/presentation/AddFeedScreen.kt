package com.team_gori.gori.feature_feed.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.team_gori.gori.core.model.Category
import com.team_gori.gori.designsystem.component.GoriChipRadioGroup
import com.team_gori.gori.designsystem.component.GoriTextButton
import com.team_gori.gori.designsystem.component.GoriTitleText
import com.team_gori.gori.designsystem.component.ImagePickerLayout
import com.team_gori.gori.designsystem.component.GoriMultiColorText
import com.team_gori.gori.designsystem.theme.CustomTheme
import com.team_gori.gori.designsystem.theme.LabelDisable
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral40
import com.team_gori.gori.designsystem.theme.Opacity10
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_x
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFeedScreen(
    onNavigateBack: () -> Unit
) {
    var completed by remember { mutableStateOf(false) }
    var content by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("") }
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val selectedImageBitmaps = remember { mutableStateListOf<ImageBitmap>() }
    val selectedByteArrays = remember { mutableStateListOf<ByteArray>() }

    val scope = rememberCoroutineScope()

    CustomTheme {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "글쓰기",
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
                    actions = {
                        GoriTextButton(
                            text = "완료",
                            enabled = completed,
                            onClick = {

                            }
                        )
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
                ,
            ) {
                GoriTitleText(
                    text = "어떤 주제의 이야기인가요?",
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
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    color = Opacity10
                )
                GoriTitleText(
                    text = "일상 글 작성",
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 181.dp)
                        .imePadding()
                        .bringIntoViewRequester(bringIntoViewRequester)
                        .onFocusEvent { focusState ->
                            if (focusState.isFocused) {
                                scope.launch {
                                    bringIntoViewRequester.bringIntoView()
                                }
                            }
                        },
                    value = content,
                    onValueChange = {
                        content = it
                    },
                    textStyle = MaterialTheme.typography.headlineMedium,
                    decorationBox = { innerTextField ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            if (content.isEmpty()) {
                                Text(
                                    text = "고리 이웃들과 이야기를 나눠보세요.",
                                    color = LabelDisable,
                                    style = MaterialTheme.typography.headlineMedium,
                                )
                            }
                        }
                        innerTextField()
                    }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    GoriMultiColorText(
                        Pair(content.length.toString(), LabelNormal),
                        Pair("/5,000", Neutral40),
                    )
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp),
                    color = Opacity10
                )
                ImagePickerLayout(
                    setImageBitmapLists = { imageBitmaps, byteArrays ->
                        selectedImageBitmaps.addAll(imageBitmaps)
                        selectedByteArrays.addAll(byteArrays)
                    },
                    scope = scope
                )
            }
        }
    }
}
