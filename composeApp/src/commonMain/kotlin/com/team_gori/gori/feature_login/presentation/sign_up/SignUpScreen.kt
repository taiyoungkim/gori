package com.team_gori.gori.feature_login.presentation.sign_up

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.component.GoriBirthTextField
import com.team_gori.gori.designsystem.component.GoriFilledButton
import com.team_gori.gori.designsystem.component.GoriOutlinedTextField
import com.team_gori.gori.designsystem.component.GoriPhoneNumberInput
import com.team_gori.gori.designsystem.theme.LabelNormal
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_x
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinInject(),
    onNavigateBack: () -> Unit,
    onNavigateToIneligible: () -> Unit,
    onNavigateBlackList: () -> Unit,
    onNavigateToVerifyCode: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    // 1. 스크롤, 포커스, 키보드 제어를 위한 상태 선언
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current

    // 각 TextField에 대한 FocusRequester 생성
    val (nameFocus, birthDateFocus, phoneFocus) = remember { FocusRequester.createRefs() }
    val title = when (uiState.currentStep) {
        SignUpStep.NAME -> {
            "이름을 입력해 주세요."
        }
        SignUpStep.BIRTH_DATE -> {
            "생년월일 포함\n앞 7자리를 입력해 주세요."
        }
        SignUpStep.PHONE_NUMBER -> {
            "휴대폰 정보를 입력해 주세요."
        }
        SignUpStep.COMPLETE -> {
            ""
        }
    }

    // 2. 'currentStep'이 변경될 때마다 스크롤과 포커스를 자동으로 이동
    LaunchedEffect(uiState.currentStep) {
        coroutineScope.launch {
            // 잠시 딜레이를 주어 UI가 렌더링될 시간을 확보
            kotlinx.coroutines.delay(100)
            scrollState.animateScrollTo(scrollState.maxValue)
        }

        when (uiState.currentStep) {
            SignUpStep.NAME -> nameFocus.requestFocus()
            SignUpStep.BIRTH_DATE -> birthDateFocus.requestFocus()
            SignUpStep.PHONE_NUMBER -> phoneFocus.requestFocus()
            SignUpStep.COMPLETE -> keyboardController?.hide() // 완료 시 키보드 숨기기
        }
    }

    // ViewModel의 NavEvent를 구독하고 처리
    LaunchedEffect(Unit) {
        viewModel.navEvent.collect { event ->
            when (event) {
                is SignUpNavEvent.GoBack -> onNavigateBack()
                is SignUpNavEvent.NavigateToIneligible -> onNavigateToIneligible()
                is SignUpNavEvent.NavigateToBlackList -> onNavigateBlackList()
                is SignUpNavEvent.NavigateToVerifyCode -> onNavigateToVerifyCode()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Image(
                                painterResource(Res.drawable.ic_x),
                                contentDescription = "닫기",
                            )
                        }
                    },
                )
            },
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Scrollable content area
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(16.dp),
                ) {
                    if (uiState.currentStep.ordinal == SignUpStep.NAME.ordinal) {
                        Text(
                            text = "본인인증이 필요해요",
                            style = MaterialTheme.typography.labelLarge,
                            color = LabelNormal
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall,
                        color = LabelNormal
                    )
                    Spacer(Modifier.height(32.dp))

                    // 1. 전화번호 입력 필드 (가장 먼저 코드에 위치하여 화면 상단에 표시)
                    AnimatedVisibility(
                        visible = uiState.currentStep.ordinal >= SignUpStep.PHONE_NUMBER.ordinal,
                        enter = slideInVertically { -it } + fadeIn(),
                    ) {
                        PhoneNumberInput(
                            phoneNumber = uiState.phoneNumber,
                            onPhoneNumberChange = { viewModel.onEvent(SignUpUiEvent.PhoneNumberChanged(it)) },
                            selectedCarrier = uiState.carrier,
                            onCarrierSelected = { viewModel.onEvent(SignUpUiEvent.CarrierSelected(it)) },
                            isReadOnly = uiState.currentStep != SignUpStep.PHONE_NUMBER,
                            modifier = Modifier.focusRequester(phoneFocus)
                        )
                    }
                    Spacer(Modifier.height(20.dp)) // 각 아이템 사이 간격

                    // 2. 생년월일 입력 필드
                    AnimatedVisibility(
                        visible = uiState.currentStep.ordinal >= SignUpStep.BIRTH_DATE.ordinal,
                        enter = slideInVertically { -it } + fadeIn(),
                    ) {
                        BirthDateInput(
                            birthDate = uiState.birthDate,
                            onBirthDateChange = { viewModel.onEvent(SignUpUiEvent.BirthDateChanged(it)) },
                            back = uiState.back,
                            onBackChange = { viewModel.onEvent(SignUpUiEvent.BackChanged(it)) },
                            isReadOnly = uiState.currentStep != SignUpStep.BIRTH_DATE,
                            modifier = Modifier.focusRequester(birthDateFocus)
                        )
                    }
                    Spacer(Modifier.height(20.dp)) // 각 아이템 사이 간격

                    // 3. 이름 입력 필드 (가장 나중에 코드에 위치하여 화면 하단에 표시)
                    // 이 필드는 항상 보이므로 AnimatedVisibility가 필요 없습니다.
                    NameInput(
                        name = uiState.name,
                        onNameChange = { viewModel.onEvent(SignUpUiEvent.NameChanged(it)) },
                        isReadOnly = uiState.currentStep != SignUpStep.NAME,
                        modifier = Modifier.focusRequester(nameFocus)
                    )

                    // 완료 화면 (필요 시 애니메이션 적용)
                    AnimatedVisibility(
                        visible = uiState.currentStep == SignUpStep.COMPLETE
                    ) {
                        SignUpComplete()
                    }
                } // End of Scrollable Column

                // Bottom Button Area
                GoriFilledButton(
                    onClick = viewModel::onNextClicked,
                    enabled = uiState.isNextButtonEnabled,
                    text = if (uiState.currentStep == SignUpStep.PHONE_NUMBER)
                        "인증문자 받기"
                    else
                        "다음",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(50.dp)
                )
            }
        }
    }
}

@Composable
fun NameInput(
    name: String,
    onNameChange: (String) -> Unit,
    isReadOnly: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        GoriOutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            hint = "이름",
            singleLine = true,
            cornerShape = 10.dp
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}

@Composable
private fun BirthDateInput(
    birthDate: String,
    onBirthDateChange: (String) -> Unit,
    back: String,
    onBackChange: (String) -> Unit,
    isReadOnly: Boolean,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier.fillMaxWidth(),
    ) {
        GoriBirthTextField(
            birth6 = birthDate,
            onBirth6Change = onBirthDateChange,
            back7 = back,
            onBack7Change = onBackChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PhoneNumberInput(
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    selectedCarrier: String,
    onCarrierSelected: (String) -> Unit,
    isReadOnly: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        GoriPhoneNumberInput(
            phoneNumber = phoneNumber,
            onPhoneNumberChange = onPhoneNumberChange,
            selectedCarrier = selectedCarrier,
            onCarrierSelected = onCarrierSelected,
            isReadOnly = isReadOnly,
//            modifier = Modifier.focusRequester(phoneFocus)
        )
    }
}

@Composable
private fun SignUpComplete() {
    /* 회원가입 완료 메시지 구현 */
}