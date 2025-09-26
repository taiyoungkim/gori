package com.team_gori.gori.feature_meeting.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap
import com.team_gori.gori.designsystem.component.GoriChipRadioGroup
import com.team_gori.gori.designsystem.component.GoriFilledButton
import com.team_gori.gori.designsystem.component.GoriOutlinedTextField
import com.team_gori.gori.designsystem.component.GoriTimeSelector
import com.team_gori.gori.designsystem.theme.LabelNeutral
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.designsystem.component.GoriCalendarView
import com.team_gori.gori.designsystem.component.GoriTwoButton
import com.team_gori.gori.designsystem.theme.Neutral60
import com.team_gori.gori.designsystem.theme.semanticColors
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_x
import gori.composeapp.generated.resources.ic_chevron_right
import gori.composeapp.generated.resources.ic_minus
import gori.composeapp.generated.resources.ic_plus
import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMeetingScreen(
    viewModel: CreateMeetingViewModel = koinInject(),
    onClose: () -> Unit,
    onComplete: (String) -> Unit,
    onNavigateToLocation: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val timeSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is CreateMeetingNavEvent.CloseScreen -> onClose()
                is CreateMeetingNavEvent.CreationComplete -> onComplete(event.meetingId)
                is CreateMeetingNavEvent.NavigateToLocation -> onNavigateToLocation()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(CreateMeetingUiEvent.ScreenEntered)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* 제목 없음 */ },
                navigationIcon = {
                    IconButton(
                        onClick = { viewModel.onEvent(CreateMeetingUiEvent.BackClicked) }) {
                        Image(
                            painter = painterResource(Res.drawable.ic_x),
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
        ) {
            ProgressIndicator(currentStep = uiState.currentStep)

            // 2. 단계별 콘텐츠
            AnimatedContent(
                targetState = uiState.currentStep,
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            ) { step ->
                when (step) {
                    CreateMeetingStep.STEP1 -> Step1Content(
                        uiState = uiState,
                        onEvent = viewModel::onEvent
                    )
                    CreateMeetingStep.STEP2 -> Step2Content(
                        uiState = uiState,
                        onEvent = viewModel::onEvent
                    )
                    CreateMeetingStep.STEP3 -> Step3Content(
                        uiState = uiState,
                        onEvent = viewModel::onEvent
                    )
                    CreateMeetingStep.STEP4 -> Step4Content(
                        uiState = uiState,
                        onEvent = viewModel::onEvent
                    )
                }
            }

            GoriFilledButton(
                onClick = { viewModel.onEvent(CreateMeetingUiEvent.NextClicked) },
                enabled = uiState.isNextButtonEnabled && !uiState.isLoading,
                text = if (uiState.currentStep == CreateMeetingStep.entries.last()) "완료" else "다음",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp),
            )

            if (uiState.showTimePicker) {
                TimePickerBottomSheet(
                    sheetState = timeSheetState,
                    initialTime = uiState.selectedTime,
                    isTimeUndecided = uiState.isTimeUndecided,
                    onDismiss = { viewModel.onEvent(CreateMeetingUiEvent.ShowDialog(DialogType.TIME, false)) },
                    onConfirm = { time, isUndecided ->
                        viewModel.onEvent(CreateMeetingUiEvent.TimeConfirmed(time, isUndecided))
                    }
                )
            }
        }
    }
}

@Composable
private fun ProgressIndicator(currentStep: CreateMeetingStep) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CreateMeetingStep.entries.forEach { step ->
            val color = if (step.ordinal <= currentStep.ordinal) MaterialTheme.colorScheme.primary else Color.LightGray
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .background(color, RoundedCornerShape(6.dp))
            )
        }
    }
}

@Composable
private fun Step1Content(
    uiState: CreateMeetingUiState,
    onEvent: (CreateMeetingUiEvent) -> Unit
) {

    val imagePickerLauncher = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = rememberCoroutineScope(),
        onResult = { byteArrays ->
            onEvent(CreateMeetingUiEvent.CoverImageSelected(byteArrays.firstOrNull()?.toImageBitmap()))
        }
    )

    var selectedCategory by remember { mutableStateOf("") }

    Column {
        Text(
            "어떤 모임을 만들까요?",
            style = MaterialTheme.typography.titleSmall,
            color = LabelNormal
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "모임명",
            style = MaterialTheme.typography.bodyLarge,
            color = LabelNormal
        )
        Spacer(modifier = Modifier.height(16.dp))
        GoriOutlinedTextField(
            value = uiState.meetingName,
            onValueChange = { onEvent(CreateMeetingUiEvent.MeetingNameChanged(it)) },
            modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
            hint = "쉽고 짧을수록 좋아요.",
            singleLine = true,
            cornerShape = 10.dp
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "모임 종류",
            style = MaterialTheme.typography.bodyLarge,
            color = LabelNormal
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            "어떤 모임인지 하나 골라주세요",
            style = MaterialTheme.typography.labelLarge,
            color = LabelNeutral,
        )
        Spacer(modifier = Modifier.height(16.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GoriChipRadioGroup(
                items = uiState.categories.map { it.name },
                selectedItem = selectedCategory,
                onItemSelect = { selected ->
                    val select = uiState.categories.find { it.name == selected }
                    onEvent(CreateMeetingUiEvent.CategorySelected(select?.id ?: ""))
                    onEvent(CreateMeetingUiEvent.CategoryResource(select?.img!!))
                    selectedCategory = selected
                },
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "대표 사진",
            style = MaterialTheme.typography.bodyLarge,
            color = LabelNormal
        )
        Text(
            "모임을 보여줄 사진을 올려주세요",
            style = MaterialTheme.typography.labelLarge,
            color = LabelNeutral
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.8f)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    imagePickerLauncher.launch()
                },
            contentAlignment = Alignment.Center
        ) {
            if (uiState.selectedImageBitmap != null) {
                Image(
                    bitmap = uiState.selectedImageBitmap,
                    contentDescription = "대표 사진",
                    contentScale = ContentScale.Crop
                )
            } else {
                if (uiState.selectedCategoryResource != null) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(uiState.selectedCategoryResource),
                        contentDescription = "대표 사진",
                        contentScale = ContentScale.Crop
                    )
                    Text("🖼️ 사진 고르기")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
private fun Step2Content(
    uiState: CreateMeetingUiState,
    onEvent: (CreateMeetingUiEvent) -> Unit
) {
    var isPickerVisible by remember { mutableStateOf(false) }

    Column {
        Text(
            "언제 어디서 모일까요?",
            style = MaterialTheme.typography.titleSmall,
            color = LabelNormal
        )
        Spacer(modifier = Modifier.height(32.dp))

        // 날짜 및 시간 선택 영역
        InfoRow(
            title = "날짜 및 시간",
            value = formatDateTime(uiState.selectedDate, uiState.selectedTime),
            valueColor = if (uiState.isDateSetByUser) LabelNormal else Neutral60,
            onClick = { isPickerVisible = !isPickerVisible }
        )

        // 달력 및 시간 선택기가 확장/축소되는 영역
        AnimatedVisibility(visible = isPickerVisible) {
            Column {
                val datePickerState = rememberDatePickerState()

                // 선택된 날짜가 변경되면 ViewModel에 이벤트를 전달합니다.
                LaunchedEffect(datePickerState.selectedDateMillis) {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val localDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                        val localDate = localDateTime.date
                        onEvent(CreateMeetingUiEvent.DateSelected(localDate))
                    }
                }

                GoriCalendarView(
                    selectedDate = uiState.selectedDate,
                    onDateSelected = { onEvent(CreateMeetingUiEvent.DateSelected(it)) }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "시간",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Neutral60
                    )
                    GoriTimeSelector(
                        time = uiState.selectedTime,
                        onClick = { onEvent(CreateMeetingUiEvent.ShowDialog(DialogType.TIME, true)) }
                    )
                }
            }
        }

        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // 장소 선택 영역
        InfoRow(
            title = "장소",
            value = uiState.selectedLocation,
            onClick = { onEvent(CreateMeetingUiEvent.LocationRowClicked) },
            valueColor = if (uiState.isLocationSetByUser) LabelNormal else Neutral60
        )
    }
}

@Composable
private fun Step3Content(
    uiState: CreateMeetingUiState,
    onEvent: (CreateMeetingUiEvent) -> Unit
) {
    Column {
        Text(
            "언제 어디서 모일까요?",
            style = MaterialTheme.typography.titleSmall,
            color = LabelNormal
        )
        Spacer(modifier = Modifier.height(32.dp))
        SectionWithTitle(title = "나를 포함해서 총") {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
//                // 인원수 표시 (bodyLarge)
//                Text(
//                    text = "${uiState.maxParticipants}명",
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier.weight(1f)
//                )
//                // +/- 버튼
//                Row {
//                    OutlinedButton(onClick = { onEvent(CreateMeetingUiEvent.MaxParticipantsChanged(uiState.maxParticipants - 1)) }) {
//                        Text("-")
//                    }
//                    Spacer(modifier = Modifier.width(8.dp))
//                    OutlinedButton(onClick = { onEvent(CreateMeetingUiEvent.MaxParticipantsChanged(uiState.maxParticipants + 1)) }) {
//                        Text("+")
//                    }
//                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    GoriOutlinedTextField(
                        modifier = Modifier.weight(1f).height(54.dp),
                        value = uiState.maxParticipants.toString(),
                        onValueChange = {
                            onEvent(CreateMeetingUiEvent.MaxParticipantsChanged(it.toInt())) },
                        suffixText = "명",
                        cornerShape = 10.dp
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    GoriTwoButton(
                        leftImagePainter = Res.drawable.ic_minus,
                        rightImagePainter = Res.drawable.ic_plus,
                        onLeftClick = {
                            if (uiState.maxParticipants > 0)
                                onEvent(CreateMeetingUiEvent.MaxParticipantsChanged(uiState.maxParticipants - 1))
                        },
                        onRightClick = {
                            if (uiState.maxParticipants < 100)
                                onEvent(CreateMeetingUiEvent.MaxParticipantsChanged(uiState.maxParticipants + 1))
                        },
                        leftImageContentDescription = "인원 마이너스",
                        rightImageContentDescription = "인원 플러스",
                        leftEnabled = uiState.maxParticipants > 0,
                        rightEnabled = uiState.maxParticipants < 100,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 성별
        SectionWithTitle(title = "성별") {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                SingleSelectionChipGroup(
                    items = GenderOption.entries,
                    selectedItem = uiState.selectedGender,
                    onItemSelected = { onEvent(CreateMeetingUiEvent.GenderSelected(it)) },
                    itemLabel = { it.displayName }
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 나이
        SectionWithTitle(title = "나이") {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val ageOptions = AgeOption.entries

                SingleSelectionChipGroup(
                    items = ageOptions.take(3),
                    selectedItem = uiState.selectedAge,
                    onItemSelected = { onEvent(CreateMeetingUiEvent.AgeSelected(it)) },
                    itemLabel = { it.displayName }
                )

                SingleSelectionChipGroup(
                    items = ageOptions.drop(3),
                    selectedItem = uiState.selectedAge,
                    onItemSelected = { onEvent(CreateMeetingUiEvent.AgeSelected(it)) },
                    itemLabel = { it.displayName }
                )
            }
        }

        AnimatedVisibility(visible = uiState.selectedAge == AgeOption.CUSTOM) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))
                if (uiState.customAgeRange <= 0) {
                    Text(
                        "나이를 입력해주세요",
                        style = MaterialTheme.typography.labelLarge,
                        color = Neutral60
                    )
                }
                GoriOutlinedTextField(
                    value = uiState.customAgeRange.toString(),
                    onValueChange = { onEvent(CreateMeetingUiEvent.CustomAgeChanged(it.toIntOrNull() ?: 50)) },
                    hint = "나이를 입력해주세요",
                    cornerShape = 10.dp,
                    modifier = Modifier.fillMaxWidth(),
                    suffixText = "대"
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // 가입 방법
        SectionWithTitle(
            title = "가입 방법",
            subtitle = "선택하면 다시 바꿀 수 없어요" // 서브타이틀 (headlineMedium)
        ) {
            Column {
                JoinMethod.values().forEach { method ->
                    RadioRow(
                        text = method.displayName,
                        selected = uiState.selectedJoinMethod == method,
                        onClick = { onEvent(CreateMeetingUiEvent.JoinMethodSelected(method)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun Step4Content(
    uiState: CreateMeetingUiState,
    onEvent: (CreateMeetingUiEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            "모임을 어떻게 운영하실건가요?",
            style = MaterialTheme.typography.titleSmall,
            color = LabelNormal
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            "모임 상세 내용",
            style = MaterialTheme.typography.headlineMedium,
            color = LabelNormal
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.meetingDescription,
            onValueChange = {
                // 글자 수 제한 (1000자)
                if (it.length <= 1000) {
                    onEvent(CreateMeetingUiEvent.DescriptionChanged(it))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            placeholder = {
                Text(
                    "어떤 활동을 할 예정인지 자세하게 적어줄수록\n모임원들은 많은 관심을 가질 거예요!",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.Gray
                )
            },
            isError = uiState.descriptionError != null,
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 글자 수 카운터 및 에러 메시지
        Box(modifier = Modifier.fillMaxWidth()) {
            if (uiState.descriptionError != null) {
                Text(
                    text = uiState.descriptionError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            Text(
                text = "${uiState.meetingDescription.length}/1000",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerBottomSheet(
    sheetState: SheetState,
    initialTime: LocalTime?,
    isTimeUndecided: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (time: LocalTime?, isUndecided: Boolean) -> Unit
) {
    var tempTime by remember { mutableStateOf(initialTime ?: LocalTime(12, 0)) }
    var tempIsUndecided by remember { mutableStateOf(isTimeUndecided) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                "시간 선택",
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(24.dp))

            // 중앙 하이라이트가 있는 타임피커
            HighlightingTimePicker(
                initialTime = tempTime,
                onTimeChange = { newTime ->
                    tempTime = newTime
                    // 시간을 수동으로 조작하면 '미정'은 자동으로 해제
                    if (tempIsUndecided) {
                        tempIsUndecided = false
                    }
                },
                enabled = !tempIsUndecided // '미정'일 때 비활성화
            )

            Spacer(modifier = Modifier.height(24.dp))

            // '미정' 체크박스
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { tempIsUndecided = !tempIsUndecided }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("미정", style = MaterialTheme.typography.headlineMedium)
                Checkbox(
                    checked = tempIsUndecided,
                    onCheckedChange = { tempIsUndecided = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 완료 버튼
            Button(
                onClick = {
                    onConfirm(if (tempIsUndecided) null else tempTime, tempIsUndecided)
                    onDismiss()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("완료")
            }
        }
    }
}

@Composable
private fun HighlightingTimePicker(
    initialTime: LocalTime,
    onTimeChange: (LocalTime) -> Unit,
    enabled: Boolean
) {
    val ampmValues = listOf("오전", "오후")
    val hourValues = (1..12).map { it.toString() }
    val minuteValues = (0..59 step 10).map { it.toString().padStart(2, '0') }

    var selectedAmPm by remember { mutableStateOf(if (initialTime.hour < 12) "오전" else "오후") }
    var selectedHour by remember { mutableStateOf((if (initialTime.hour % 12 == 0) 12 else initialTime.hour % 12).toString()) }
    var selectedMinute by remember { mutableStateOf(initialTime.minute.toString().padStart(2, '0')) }

    LaunchedEffect(selectedAmPm, selectedHour, selectedMinute) {
        val hour24 = when (selectedAmPm) {
            "오전" -> if (selectedHour == "12") 0 else selectedHour.toInt()
            "오후" -> if (selectedHour == "12") 12 else selectedHour.toInt() + 12
            else -> 0
        }
        onTimeChange(LocalTime(hour24, selectedMinute.toInt()))
    }

    val textColor = if (enabled) MaterialTheme.colorScheme.onSurface else Color.Gray

    Box(contentAlignment = Alignment.Center) {
        // 중앙 하이라이트 배경
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HighlightingPicker(
                items = ampmValues,
                initialItem = selectedAmPm,
                onItemSelected = { selectedAmPm = it },
                textColor = textColor,
                modifier = Modifier.weight(1f)
            )
            HighlightingPicker(
                items = hourValues,
                initialItem = selectedHour,
                onItemSelected = { selectedHour = it },
                textColor = textColor,
                modifier = Modifier.weight(1f)
            )
            HighlightingPicker(
                items = minuteValues,
                initialItem = selectedMinute,
                onItemSelected = { selectedMinute = it },
                textColor = textColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun HighlightingPicker(
    items: List<String>,
    initialItem: String,
    onItemSelected: (String) -> Unit,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState(Int.MAX_VALUE / 2 - (Int.MAX_VALUE / 2 % items.size) + items.indexOf(initialItem))

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val centerItemInfo = listState.layoutInfo.visibleItemsInfo
                .minByOrNull { kotlin.math.abs(it.offset + it.size / 2 - listState.layoutInfo.viewportSize.height / 2) }
            if (centerItemInfo != null) {
                val targetOffset = - (listState.layoutInfo.viewportSize.height / 2 - centerItemInfo.size / 2)

                listState.animateScrollToItem(centerItemInfo.index, targetOffset)

                onItemSelected(items[centerItemInfo.index % items.size])
            }
        }
    }

    LazyColumn(state = listState, modifier = modifier) {
        items(Int.MAX_VALUE) { index ->
            val itemIndex = index % items.size
            val isCenter by remember {
                derivedStateOf {
                    val centerInfo = listState.layoutInfo.visibleItemsInfo
                        .minByOrNull { kotlin.math.abs(it.offset + it.size / 2 - listState.layoutInfo.viewportSize.height / 2) }
                    centerInfo?.index == index
                }
            }

            Text(
                text = items[itemIndex],
                style = if (isCenter) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.headlineMedium,
                color = if (isCenter) textColor else textColor.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun InfoRow(title: String, value: String, onClick: () -> Unit, valueColor: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                title,
                style = MaterialTheme.typography.headlineMedium,
                color = Neutral60
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                Text(
                    value,
                    style = MaterialTheme.typography.titleSmall,
                    color = valueColor
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(Res.drawable.ic_chevron_right),
                    contentDescription = "chevron_right",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {}
                )
            }
        }
    }
}

private fun formatDateTime(date: LocalDate?, time: LocalTime?): String {
    if (date == null) return "선택해주세요"

    // DayOfWeek enum을 한국어 요일로 직접 매핑합니다.
    val dayOfWeekInKorean = when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> "월"
        DayOfWeek.TUESDAY -> "화"
        DayOfWeek.WEDNESDAY -> "수"
        DayOfWeek.THURSDAY -> "목"
        DayOfWeek.FRIDAY -> "금"
        DayOfWeek.SATURDAY -> "토"
        DayOfWeek.SUNDAY -> "일"
        else -> ""
    }

    if (time == null) return "${date.monthNumber}월 ${date.dayOfMonth}일 ($dayOfWeekInKorean)"

    val ampm = if (time.hour < 12) "오전" else "오후"
    // 12시와 0시(자정)를 12로 표시하고, 나머지는 12로 나눈 나머지로 계산합니다.
    val hour = if (time.hour == 0 || time.hour == 12) 12 else time.hour % 12
    val minute = time.minute.toString().padStart(2, '0')

    // `monthNumber`와 `dayOfMonth`를 직접 사용하여 문자열을 조합합니다.
    return "${date.monthNumber}월 ${date.dayOfMonth}일 ($dayOfWeekInKorean) $ampm ${hour}:${minute}"
}

@Composable
private fun SectionWithTitle(
    title: String,
    subtitle: String? = null,
    content: @Composable () -> Unit
) {
    Column {
        Text(title, style = MaterialTheme.typography.headlineMedium)
        subtitle?.let {
            Spacer(modifier = Modifier.height(4.dp))
            Text(it, style = MaterialTheme.typography.headlineMedium, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}

// 재사용 가능한 싱글 선택 칩 그룹
@Composable
private fun <T> SingleSelectionChipGroup(
    items: List<T>,
    selectedItem: T,
    onItemSelected: (T) -> Unit,
    itemLabel: (T) -> String
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            val isSelected = item == selectedItem
            FilterChip(
                selected = isSelected,
                onClick = { onItemSelected(item) },
                label = {
                    Text(
                        itemLabel(item),
                        style = MaterialTheme.typography.headlineMedium,
                        color = if (isSelected) MaterialTheme.semanticColors.onSecondary else MaterialTheme.semanticColors.labelNormal
                    ) },
                modifier = Modifier.height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = FilterChipDefaults.filterChipColors(
                    labelColor = if (isSelected) MaterialTheme.semanticColors.onSecondary else MaterialTheme.semanticColors.labelNormal,
                    selectedContainerColor = MaterialTheme.semanticColors.labelNormal,
                    selectedLabelColor = MaterialTheme.semanticColors.onSecondary,
                )
            )
        }
    }
}

// 재사용 가능한 라디오 버튼 행
@Composable
private fun RadioRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.weight(1f))
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = LabelNormal,
            )
        )
    }
}