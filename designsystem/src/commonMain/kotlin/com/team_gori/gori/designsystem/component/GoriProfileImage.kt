package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_camera
import gori.designsystem.generated.resources.ic_default_avatar
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriProfileImage(
    image: ImageBitmap? = null,
    onUploadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val avatarSize = 210.dp
    val buttonHeight = 52.dp

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(avatarSize + buttonHeight),
        contentAlignment = Alignment.TopCenter // ⬅️ 박스 내부 중앙 정렬
    ) {
        // 원형 아바타
        Surface(
            modifier = Modifier
                .size(avatarSize)
                .clip(CircleShape)
                .clickable { onUploadClick() },
            shape = CircleShape,
        ) {
            Box(
                Modifier
                    .size(avatarSize)
                    .background(Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                when {
                    image != null -> {
                        Image(
                            bitmap = image,
                            contentDescription = "프로필 사진",
                            modifier = Modifier.size(avatarSize),
                            contentScale = ContentScale.Crop
                        )
                    }
                    else -> {
                        Image(
                            painter = painterResource(Res.drawable.ic_default_avatar),
                            contentDescription = "기본 프로필 아이콘",
                            modifier = Modifier.size(avatarSize),
                        )
                    }
                }
            }
        }

        // 원 하단에 겹치는 알약 버튼
        Button(
            onClick = { onUploadClick() },
            shape = RoundedCornerShape(32.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.semanticColors.backgroundNormal),
            modifier = Modifier
                .align(Alignment.TopCenter)
                // Top에서 시작해 원의 거의 바닥 위치로 이동
                .offset(y = avatarSize - buttonHeight / 2)
                .height(buttonHeight)
                .defaultMinSize(minWidth = 0.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_camera),
                contentDescription = null
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "사진 올리기",
                style = MaterialTheme.typography.headlineMedium,
                color = LabelNormal
            )
        }
    }
}
