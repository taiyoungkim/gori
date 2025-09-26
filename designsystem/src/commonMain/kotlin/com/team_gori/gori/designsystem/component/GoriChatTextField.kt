package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import com.team_gori.gori.designsystem.theme.Neutral5
import com.team_gori.gori.designsystem.theme.Neutral50
import com.team_gori.gori.designsystem.theme.PrimaryColor
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.designsystem.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import gori.designsystem.generated.resources.ic_add_photo
import gori.designsystem.generated.resources.ic_send
import kotlinx.coroutines.CoroutineScope

@Composable
fun GoriChatTextField(
    text: String,
    onTextChanged: (String) -> Unit,
    onSendMessage: () -> Unit,
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    setImageBitmapLists: (List<ImageBitmap>, List<ByteArray>) -> Unit,
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

    Surface(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .background(color = MaterialTheme.semanticColors.backgroundNormal)
                .padding(horizontal = 6.dp, vertical = 4.dp)
                .navigationBarsPadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    imagePickerLauncher.launch()
                },
                enabled = true,
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_add_photo),
                    contentDescription = "ic_add_photo",
                    modifier = Modifier
                )
            }
            BasicTextField(
                value = text,
                onValueChange = onTextChanged,
                modifier = modifier.weight(1f),
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = Neutral5,
                                shape = RoundedCornerShape(20.dp)
                            )
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        contentAlignment = Alignment.CenterStart,
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                text = "메세지를 입력해 주세요.",
                                color = Neutral50,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        innerTextField()
                    }
                }
            )
            IconButton(
                onClick = onSendMessage,
                enabled = text.isNotBlank(),
            ) {
                val tintColor = if (text.isNotBlank()) {
                    PrimaryColor
                } else {
                    Color(0x75828A61)
                }

                Image(
                    painter = painterResource(Res.drawable.ic_send),
                    contentDescription = "ic_send",
                    colorFilter = ColorFilter.tint(tintColor),
                )
            }
        }
    }
}