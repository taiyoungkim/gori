package com.team_gori.gori.my_page.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.team_gori.gori.designsystem.component.GoriMyPageEmptyView

@Composable
fun MyChattingTabContent(

) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
            .fillMaxSize()
            .align(Alignment.CenterHorizontally)
        ) {
            GoriMyPageEmptyView(
                "아직 소속된 채팅방이 없어요.",
                "모임이나 채팅방에 참여해 보세요.",
                "탐색하기",
                {

                }
            )
        }
    }
}