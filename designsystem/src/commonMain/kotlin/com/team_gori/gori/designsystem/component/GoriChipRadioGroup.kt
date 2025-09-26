package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.CustomTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf

/**
 * 여러 개의 GoriSelectableChip 중에서 하나만 선택할 수 있도록 하는 라디오 그룹 컴포넌트.
 *
 * @param items 표시할 칩들의 텍스트 목록.
 * @param selectedItem 현재 선택된 아이템의 텍스트. null이면 아무것도 선택되지 않은 상태.
 * @param onItemSelect 아이템이 선택되었을 때 호출될 콜백. 선택된 아이템의 텍스트를 인자로 받습니다.
 * @param modifier Modifier 인스턴스.
 * @param horizontalArrangement 칩들 사이의 수평 간격 정렬 방식.
 * @param verticalArrangement 칩들 사이의 수직 간격 정렬 방식 (FlowRow 사용 시).
 */
@Composable
fun GoriChipRadioGroup(
    items: List<String>,
    selectedItem: String?,
    onItemSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp), // 칩 사이 수평 간격
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(8.dp)   // 칩 사이 수직 간격 (FlowRow)
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalArrangement = verticalArrangement
    ) {
        items.forEach { itemText ->
            GoriSelectableChip(
                text = itemText,
                selected = selectedItem == itemText,
                onClick = {
                    onItemSelect(itemText)
                }
                // modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}