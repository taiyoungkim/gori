package com.team_gori.gori.create.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriFeedItem
import com.team_gori.gori.designsystem.component.GoriSelectableChip
import com.team_gori.gori.feature_meeting.domain.model.MeetingCategory

@Composable
fun CreateScreen(
    onNavigateToFeedDetail: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val list: List<String> = MeetingCategory.entries.map { it.displayName }

        Column(
            modifier = Modifier.fillMaxSize().padding(
                horizontal = 16.dp,
            ),
        ) {
            LazyRow {
                items(
                    items = list,
                    key = { item -> item }
                ) { item ->
                    GoriSelectableChip(
                        text = item,
                        selected = false,
                        onClick = {

                        },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        isFeed = true
                    )
                }
            }
            Spacer(Modifier.height(20.dp))
            LazyColumn(

            ) {
                item {
                    GoriFeedItem(
                        userName = "유저1",
                        timestamp = "1일 전",
                        like = 10,
                        comment = 10,
                        text = "최근에 뚱냥이가 된 우리 냥이와 함께 운동할려구요!",
                        photoList = listOf(ByteArray(0), ByteArray(0)),
                        onClick = {
                            onNavigateToFeedDetail()
                        }
                    )
                }
                item {
                    Spacer(Modifier.height(20.dp))
                }
                item {
                    GoriFeedItem(
                        userName = "유저1",
                        timestamp = "1일 전",
                        like = 10,
                        comment = 10,
                        text = "최근에 뚱냥이가 된 우리 냥이와 함께 운동할려구요!",
                        photoList = listOf(ByteArray(0)),
                        onClick = {
                            onNavigateToFeedDetail()
                        }
                    )
                }
            }
        }
    }
}