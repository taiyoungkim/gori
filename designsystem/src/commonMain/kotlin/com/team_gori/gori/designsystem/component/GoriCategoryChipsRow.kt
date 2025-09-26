package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import org.jetbrains.compose.resources.DrawableResource
import androidx.compose.ui.unit.Dp
import com.team_gori.gori.designsystem.theme.semanticColors

data class ChipItemUi(
    val id: String?,
    val label: String,
    val icon: DrawableResource? = null
)

@Composable
fun GoriCategoryChipsRow(
    items: List<ChipItemUi>,
    selectedId: String?,
    onSelect: (String?) -> Unit,
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 16.dp
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(items) { item ->
            val selected = selectedId == item.id
            val bg = if (selected) MaterialTheme.semanticColors.backgroundTooltip else MaterialTheme.semanticColors.backgroundNormal
            val fg = if (selected) MaterialTheme.semanticColors.backgroundNormal else MaterialTheme.semanticColors.backgroundTooltip
            val border = if (selected) null else BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)

            Surface(
                onClick = { onSelect(item.id) },
                color = bg,
                contentColor = fg,
                border = border,
                shape = RoundedCornerShape(24.dp) // ← R=24dp
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 14.dp, top = 12.dp, end = 14.dp, bottom = 12.dp) // ← padding
                ) {
                    if (item.icon != null) {
                        Image(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp) // ← 아이콘 20dp
                        )
                        Spacer(Modifier.width(8.dp))
                    }
                    Text(item.label, style = MaterialTheme.typography.labelLarge) // ← 라벨 스타일
                }
            }
        }
    }
}
