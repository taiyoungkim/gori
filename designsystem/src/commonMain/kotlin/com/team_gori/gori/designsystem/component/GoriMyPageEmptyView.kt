package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_chevron_right
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriMyPageEmptyView(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = LabelNormal,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = description,
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFFA3A8AD),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(20.dp))
        Row(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.semanticColors.lineAlternative,
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
                .clickable {
                    onClick()
                }
        ) {
            Text(
                text = buttonText,
                style = MaterialTheme.typography.headlineMedium,
                color = LabelNormal,
            )
            Image(
                painter = painterResource(Res.drawable.ic_chevron_right),
                contentDescription = "chevron_right",
                modifier = Modifier
                    .size(20.dp)
                    .clickable {}
            )
        }
    }
}