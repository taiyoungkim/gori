package com.team_gori.gori.feature_chat.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriMemberRow
import com.team_gori.gori.designsystem.component.GoriMultiColorText
import com.team_gori.gori.designsystem.theme.CustomTheme
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral40
import com.team_gori.gori.designsystem.theme.Opacity10
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_arrow_left
import gori.composeapp.generated.resources.ic_arrow_right
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSettingScreen(
    memberCount: Int,
    onNavigateBack: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    CustomTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "채팅방 Title",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Image(
                                painter = painterResource(Res.drawable.ic_arrow_left),
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
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "채팅방 정보 수정",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(vertical = 20.dp)
                    )
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        painter = painterResource(Res.drawable.ic_arrow_right),
                        contentDescription = "닫기",
                    )
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Opacity10
                )
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "앨범",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(vertical = 20.dp)
                    )
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        painter = painterResource(Res.drawable.ic_arrow_right),
                        contentDescription = "닫기",
                    )
                }
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Opacity10
                )
                Row(
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text(
                        text = "멤버",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(vertical = 20.dp)
                    )
                    Spacer(Modifier.width(5.dp))
                    GoriMultiColorText(
                        Pair(memberCount.toString(), LabelNormal),
                        Pair("/20", Neutral40),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(vertical = 20.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Column {
                    GoriMemberRow(
                        memberName = "시마스시존맛",
                        age = 61,
                        gender = "남",
                        isMe = true,
                        isAdmin = true,
                    )
                    Spacer(Modifier.height(16.dp))
                    GoriMemberRow(
                        memberName = "유저1",
                        age = 61,
                        gender = "남",
                        isMe = false,
                        isAdmin = false,
                    )
                    Spacer(Modifier.height(16.dp))
                    GoriMemberRow(
                        memberName = "유저2",
                        age = 61,
                        gender = "남",
                        isMe = false,
                        isAdmin = false,
                    )
                }
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "채팅방 나가기",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                            .padding(vertical = 20.dp)
                    )
                    Image(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        painter = painterResource(Res.drawable.ic_arrow_right),
                        contentDescription = "닫기",
                    )
                }
            }
        }
    }
}