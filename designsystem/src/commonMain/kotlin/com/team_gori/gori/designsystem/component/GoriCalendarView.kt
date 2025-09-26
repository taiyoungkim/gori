package com.team_gori.gori.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.theme.Opacity36
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.designsystem.generated.resources.Res
import gori.designsystem.generated.resources.ic_chevron_right
import gori.designsystem.generated.resources.ic_chevron_left
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.painterResource

@Composable
fun GoriCalendarView(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    var currentMonth by remember { mutableStateOf(today.run { LocalDate(year, month, 1) }) }
    val daysInMonth = currentMonth.daysUntil(currentMonth.plus(1, DateTimeUnit.MONTH))
    val firstDayOfWeek = currentMonth.dayOfWeek.isoDayNumber // MONDAY is 1, SUNDAY is 7
    fun firstOfMonth(d: LocalDate) = LocalDate(d.year, d.month, 1)
    val emptyDays = (firstDayOfWeek - 1) % 7
    val maxMonth = today.plus(2, DateTimeUnit.MONTH).run { LocalDate(year, month, 1) }
    val minMonth = remember { firstOfMonth(today) }
    val canGoPrev by remember(currentMonth) { derivedStateOf { currentMonth > minMonth } }
    val canGoNext by remember(currentMonth) { derivedStateOf { currentMonth < maxMonth } }

    val dates = (1..emptyDays).map { null } + (1..daysInMonth).map {
        LocalDate(currentMonth.year, currentMonth.month, it)
    }

    LaunchedEffect(Unit) {
        if (selectedDate == null) {
            onDateSelected(today)
        }
    }

    Column {
        // 헤더: 년/월, 이전/다음 달 버튼
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${currentMonth.year}년 ${currentMonth.monthNumber}월",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.SemiBold
                ),
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = { currentMonth = firstOfMonth(currentMonth.minus(1, DateTimeUnit.MONTH)) },
                enabled = canGoPrev
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_chevron_left),
                    contentDescription = "이전 달",
                    modifier = Modifier.size(24.dp),
                    colorFilter = remember(canGoPrev) {
                        ColorFilter.tint(if (canGoPrev) LabelNormal else Opacity36)
                    }
                )
            }

            IconButton(
                onClick = { currentMonth = firstOfMonth(currentMonth.plus(1, DateTimeUnit.MONTH)) },
                enabled = canGoNext
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_chevron_right),
                    contentDescription = "다음 달",
                    modifier = Modifier.size(24.dp),
                    colorFilter = remember(canGoNext) {
                        ColorFilter.tint(if (canGoNext) LabelNormal else Opacity36)
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        // 요일 헤더
        Row(modifier = Modifier.fillMaxWidth()) {
            listOf("월", "화", "수", "목", "금", "토", "일").forEach { day ->
                Text(
                    text = day,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Column {
            dates.chunked(7).forEach { week ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    week.forEach { date ->
                        Row(modifier = Modifier.fillMaxWidth()) {
                            for (i in 0 until 7) {
                                val date = week.getOrNull(i)

                                Box(
                                    modifier = Modifier
                                        .weight(1f) // 모든 셀이 동일한 너비를 갖도록 설정
                                        .aspectRatio(1f)
                                ) {
                                    if (date != null) {
                                        val isDisabled = date < today
                                        val isSelected = date == selectedDate

                                        val backgroundColor = if (isSelected) MaterialTheme.semanticColors.secondaryNormal else Color.Transparent
                                        val textColor = when {
                                            isSelected -> MaterialTheme.colorScheme.onSecondary
                                            isDisabled -> Color.Gray
                                            else -> MaterialTheme.colorScheme.onSurface
                                        }
                                        val fontStyle = if (isSelected)
                                            MaterialTheme.typography.headlineMedium
                                        else
                                            MaterialTheme.typography.labelLarge

                                        Box(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(2.dp)
                                                .clip(CircleShape)
                                                .background(backgroundColor)
                                                .clickable(enabled = !isDisabled) { onDateSelected(date) },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = date.dayOfMonth.toString(),
                                                color = textColor,
                                                style = fontStyle
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}