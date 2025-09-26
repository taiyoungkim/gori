package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import com.team_gori.gori.designsystem.theme.CustomTheme
import kotlinx.coroutines.CoroutineScope

@Composable
fun ImagePickerLayout(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    setImageBitmapLists: (List<ImageBitmap>, List<ByteArray>) -> Unit
) {
    val selectedImageBitmaps = remember { mutableStateListOf<ImageBitmap>() }
    val selectedByteArrays = remember { mutableStateListOf<ByteArray>() }

    val imagePickerLauncher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Multiple(10),
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.forEach { byteArray ->
                selectedByteArrays.add(byteArray)
                selectedImageBitmaps.add(byteArray.toImageBitmap())
            }
            setImageBitmapLists(
                selectedImageBitmaps, selectedByteArrays
            )
        }
    )

    CustomTheme {
        Column(modifier = modifier) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                item {
                    GoriCameraButton(
                        onClick = {
                            imagePickerLauncher.launch()
                        },
                        totalCount = selectedImageBitmaps.size,
                        modifier = Modifier.size(80.dp).padding(end = 10.dp)
                    )
                }
                itemsIndexed(selectedImageBitmaps) { index, bitmap ->
                    GoriImageButton(
                        imageBitmap = bitmap,
                        onAddClick = {
                            // GoriImageButton의 빈 상태는 여기서 사용되지 않음
                            // 이미지가 있다면 항상 채워진 상태로 표시됨
                        },
                        onRemoveClick = {
                            selectedImageBitmaps.removeAt(index)
                        }
                    )
                }
            }
        }
    }
}