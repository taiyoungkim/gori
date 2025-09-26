package com.team_gori.gori.my_page.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.team_gori.gori.designsystem.component.GoriMyPageEmptyView

@Composable
fun MyMeetingTabContent(

) {
    Column {
        Box(
            Modifier
            .fillMaxSize()
            .align(Alignment.CenterHorizontally)
        ) {
            GoriMyPageEmptyView(
                "아직 가입한 모임이 없어요.",
                "내 관심사에 맞는 모임을 가입해보세요.",
                "모임 탐색하기",
                {

                }
            )
        }
    }
}