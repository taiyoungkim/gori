package com.team_gori.gori.home.presentation.model

import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_home
import gori.composeapp.generated.resources.ic_calender
import gori.composeapp.generated.resources.ic_sms
import gori.composeapp.generated.resources.ic_daily
import gori.composeapp.generated.resources.ic_person
import org.jetbrains.compose.resources.DrawableResource

sealed class MainTab(val title: String, val icon: DrawableResource, val route: String) {
    data object Home : MainTab("홈", Res.drawable.ic_home, "home")
    data object Meeting : MainTab("모임", Res.drawable.ic_calender, "meeting")
    data object Chatting : MainTab("대화", Res.drawable.ic_sms, "chatting")
    data object Daily : MainTab("일상", Res.drawable.ic_daily, "daily")
    data object MyActivity : MainTab("나의 고리", Res.drawable.ic_person, "my_activity")
}