package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.Neutral10
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_image
import gori.designsystem.generated.resources.ic_x_circle_black
import org.jetbrains.compose.resources.painterResource

/**
 * 이미지 미리보기 및 선택/제거 기능을 제공하는 컴포넌트.
 *
 * @param imageBitmap 표시할 이미지. null이면 이미지 추가 상태를 보여줍니다.
 * @param onAddClick 이미지가 없을 때 클릭하여 이미지 선택을 시작하는 콜백.
 * @param onRemoveClick 이미지가 있을 때 'X' 아이콘을 클릭하여 이미지를 제거하는 콜백.
 * @param modifier Modifier 인스턴스.
 * @param enabled 컴포넌트 활성화 여부.
 */
@Composable
fun GoriImageButton(
    imageBitmap: ImageBitmap?,
    onAddClick: () -> Unit,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val cornerRadius = 8.dp
    val shape = RoundedCornerShape(cornerRadius)

    Box(modifier = modifier) {
        Surface(
            modifier = Modifier
                .size(90.dp)
                .clip(shape)
                .clickable(enabled = enabled && imageBitmap == null) {
                    if (imageBitmap == null) {
                        onAddClick()
                    }
                },
            shape = shape,
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(shape),
                contentAlignment = Alignment.CenterStart,
            ) {
                if (imageBitmap == null) {
                    Image(
                        painter = painterResource(Res.drawable.ic_image),
                        contentDescription = "사진 추가",
                        modifier = Modifier.padding(24.dp),
                    )
                } else {
                    Image(
                        bitmap = imageBitmap,
                        contentDescription = "선택된 이미지",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
        IconButton(
            onClick = onRemoveClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(0.dp)
                .size(20.dp),
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_x_circle_black),
                contentDescription = "사진 삭제",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}