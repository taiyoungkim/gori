package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.ChipGrayBackground
import com.team_gori.gori.designsystem.theme.ChipGrayText
import com.team_gori.gori.designsystem.theme.LabelAssistive
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Neutral10
import com.team_gori.gori.designsystem.theme.Neutral20
import com.team_gori.gori.designsystem.theme.PrimaryColor
import com.team_gori.gori.designsystem.theme.Purple60
import com.team_gori.gori.designsystem.theme.semanticColors

/**
 * 선택 가능한 칩 컴포넌트. 텍스트를 표시하며, 선택 상태에 따라 색상이 변경됩니다.
 * 라이트/다크 모드를 지원하기 위해 MaterialTheme의 ColorScheme을 사용합니다.
 *
 * @param text 칩에 표시될 텍스트.
 * @param selected 현재 칩의 선택 여부.
 * @param onClick 칩을 클릭했을 때 호출될 람다. 보통 외부에서 selected 상태를 토글하는 데 사용됩니다.
 * @param modifier Modifier 인스턴스.
 * @param enabled 칩의 활성화 여부. false일 경우 비활성화 상태 색상이 적용되고 클릭되지 않습니다.
 */
@Composable
fun GoriSelectableChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isFeed: Boolean = false,
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 2.dp)
            )
        },
        border = FilterChipDefaults.filterChipBorder(
            borderColor = Neutral20,
            selectedBorderColor = if (isFeed) MaterialTheme.semanticColors.lineAlternative else PrimaryColor,
            borderWidth = 1.dp,
            selectedBorderWidth = 1.dp,
            disabledBorderColor = ChipGrayBackground.copy(alpha = 0.38f),
            disabledSelectedBorderColor = ChipGrayText.copy(alpha = 0.38f),
            selected = selected,
            enabled = enabled
        ),
        modifier = modifier,
        enabled = enabled,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color.Transparent,
            labelColor = if (isFeed) LabelNormal else LabelAssistive,
            selectedContainerColor = if (isFeed) Neutral10 else MaterialTheme.semanticColors.lineAlternative,
            selectedLabelColor = if (isFeed) LabelNormal else Purple60,
            disabledContainerColor = ChipGrayBackground.copy(alpha = 0.38f),
            disabledLabelColor = ChipGrayText.copy(alpha = 0.38f),
        ),

        shape = RoundedCornerShape(100.dp),
    )
}