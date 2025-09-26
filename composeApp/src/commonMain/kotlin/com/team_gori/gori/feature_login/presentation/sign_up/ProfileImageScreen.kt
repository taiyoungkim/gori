package com.team_gori.gori.feature_login.presentation.sign_up

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import com.team_gori.gori.designsystem.component.GoriFilledButton
import com.team_gori.gori.designsystem.component.GoriProfileImage
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral60
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_arrow_left
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileImageScreen(
    onNavigateNext: () -> Unit,
    onNavigateBack: () -> Unit,
) {
    val selectedImageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

    val imagePickerLauncher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = rememberCoroutineScope(),
        onResult = { byteArrays ->
            selectedImageBitmap.value = byteArrays.firstOrNull()?.toImageBitmap()
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 제목 없음 */ },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Image(
                            painter = painterResource(Res.drawable.ic_arrow_left),
                            contentDescription = "닫기",
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "마지막으로,\n프로필 사진을 올려 주세요.",
                style = MaterialTheme.typography.titleSmall,
                color = LabelNormal
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "고리에서 대화를 나눌 때 친구들이 볼\n내 얼굴이에요",
                style = MaterialTheme.typography.bodyMedium,
                color = Neutral60,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                GoriProfileImage(
                    image = selectedImageBitmap.value,
                    onUploadClick = {
                        imagePickerLauncher.launch()
                    },
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            GoriFilledButton(
                onClick = {
                    onNavigateNext()
                },
                enabled = true,
                text = "다음",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
            )
        }
    }
}