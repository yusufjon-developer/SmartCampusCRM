package com.smartcampus.presentation.ui.screen.student.studentProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smartcampus.crm.domain.models.GroupDto
import com.smartcampus.crm.domain.models.StudentDetailsDto
import com.smartcampus.crm.domain.models.StudentSensitiveDto
import com.smartcampus.crm.domain.models.StudentSensitiveUpdateRequest
import com.smartcampus.crm.domain.models.StudentUpdateRequest
import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.models.auth.StudentCreateRequest
import com.smartcampus.crm.domain.models.auth.StudentInfoCreateRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.components.form.EditableField
import com.smartcampus.presentation.core.components.form.ErrorDialog
import com.smartcampus.presentation.core.components.form.GenericProfileScreen
import com.smartcampus.presentation.core.components.form.PasswordField
import com.smartcampus.presentation.core.components.form.ProfileFormEditable
import com.smartcampus.presentation.core.components.form.ProfileFormReadonly
import com.smartcampus.presentation.core.components.form.ProfileHeader
import com.smartcampus.presentation.core.components.form.ReadOnlyField
import com.smartcampus.presentation.core.components.form.SectionTitle
import com.smartcampus.presentation.ui.widgets.groupDropdown.GroupDropdown
import org.koin.compose.viewmodel.koinViewModel
import java.util.logging.Logger

@Composable
fun StudentProfileScreen(
    id: Int?,
    onAddStudent: (Int) -> Unit,
    viewModel: StudentProfileViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val error = remember { mutableStateOf<NetworkError?>(null) }
    val errorInfo = remember { mutableStateOf<NetworkError?>(null) }

    val form = rememberStudentFormStates(state.value.student, state.value.studentInfo)

    LaunchedEffect(Unit) {
        id?.let {
            viewModel.setEvent(StudentProfileContract.Event.GetStudent(id))
            viewModel.setEvent(StudentProfileContract.Event.GetStudentInfo(id))
        }
    }

    HandleProfileEffects(viewModel = viewModel, error = error, errorInfo = errorInfo, onAddStudent = { onAddStudent(it) })

    LaunchedEffect(state.value.student) {
        state.value.student?.let { s -> populateFromStudent(form, s) }
    }
    LaunchedEffect(state.value.studentInfo) {
        state.value.studentInfo?.let { si -> populateFromStudentInfo(form, si) }
    }

    val readOnlyBase = buildReadOnlyBaseInfo(state.value.student)
    val readOnlyAdd = buildReadOnlyAddInfo(state.value.student, state.value.studentInfo)
    val editableBase = buildEditableBaseInfo(form)
    val editableAdd = buildEditableAddInfo(form)

    GenericProfileScreen(
        initialReadOnly = false,
        onToggleEdit = { isEditing ->
            if (!isEditing) {
                val missing = validateFormBeforeSubmit(id, form)
                if (missing.isNotEmpty()) {
                    error.value = NetworkError.Unexpected("Заполните поля: ${missing.joinToString(", ")}")
                    return@GenericProfileScreen
                }
                // submit (create or update)
                submitForm(id, viewModel, form)
            }
        },

        topContent = {
            ProfileHeader(
                imageUrl = null,
                title = "${state.value.student?.name ?: ""} ${state.value.student?.surname ?: ""}",
                subtitle = null,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },

        bodyContent = { isEditing ->
            Column(modifier = Modifier.padding(8.dp).verticalScroll(scrollState)) {

                SectionTitle("Базовая информация")

                if (id == null) {
                    NewStudentCredentialsBlock(
                        usernameState = form.usernameState,
                        passwordState = form.passwordState,
                        emailState = form.emailState,
                        groupState = form.groupState,
                        onGroupSelected = { group ->
                            form.selectedGroup.value = group
                            form.specialityState.value = group?.speciality?.name ?: ""
                            form.courseState.value = (group?.course ?: 1).toString()
                        }
                    )
                }

                if (isEditing || id == null) {
                    ProfileFormEditable(fields = editableBase)
                } else {
                    ProfileFormReadonly(fields = readOnlyBase)
                }

                if (form.errorAccessDeniedIsNotSet()) {
                    SectionTitle("Доп. информация")
                    if (isEditing || id == null) {
                        ProfileFormEditable(fields = editableAdd)
                    } else {
                        ProfileFormReadonly(fields = readOnlyAdd)
                    }
                }

                error.value?.let { err ->
                    ErrorDialog(
                        title = "Ошибка",
                        message = err.toString(),
                        onDismiss = { error.value = null }
                    )
                }

                errorInfo.value?.let { errInfo ->
                    ErrorDialog(
                        title = "Ошибка загрузки доп. информации студента",
                        message = errInfo.toString(),
                        onDismiss = { errorInfo.value = null }
                    )
                }
            }
        }
    )
}

/* ----------------- вспомогательные приватные функции и структуры ----------------- */

private data class StudentFormStates(
    val usernameState: MutableState<String>,
    val passwordState: MutableState<String>,
    val emailState: MutableState<String>,

    val selectedGroup: MutableState<GroupDto?>,
    val nameState: MutableState<String>,
    val surnameState: MutableState<String>,
    val lastnameState: MutableState<String>,
    val birthdayState: MutableState<String>,
    val groupState: MutableState<String>,
    val specialityState: MutableState<String>,
    val courseState: MutableState<String>,
    val phoneState: MutableState<String>,

    val studyForm: MutableState<String>,
    val studyType: MutableState<String>,
    val status: MutableState<String>,
    val studentCardState: MutableState<String>,

    val addressState: MutableState<String>,
    val passportState: MutableState<String>,
    val schoolState: MutableState<String>,
    val documentState: MutableState<String>,
    val militaryState: MutableState<String>,

    val fatherNameState: MutableState<String>,
    val fatherPhoneState: MutableState<String>,
    val fatherAddressState: MutableState<String>,
    val motherNameState: MutableState<String>,
    val motherPhoneState: MutableState<String>,
    val motherAddressState: MutableState<String>,
) {
    fun errorAccessDeniedIsNotSet(): Boolean = true
}

@Composable
private fun rememberStudentFormStates(
    student: StudentDetailsDto?,
    studentInfo: StudentSensitiveDto?
): StudentFormStates {
    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }

    val selectedGroup = remember { mutableStateOf(student?.group) }

    val nameState = remember { mutableStateOf(student?.name ?: "") }
    val surnameState = remember { mutableStateOf(student?.surname ?: "") }
    val lastnameState = remember { mutableStateOf(student?.lastname ?: "") }
    val birthdayState = remember { mutableStateOf(student?.birthday ?: "") }
    val groupState = remember { mutableStateOf(student?.group?.name ?: "") }
    val specialityState = remember { mutableStateOf(student?.group?.speciality?.name ?: "") }
    val courseState = remember { mutableStateOf(student?.group?.course.toString()) }
    val phoneState = remember { mutableStateOf(student?.phoneNumber ?: "") }

    val studyForm = remember { mutableStateOf(studentInfo?.studyForm ?: "") }
    val studyType = remember { mutableStateOf(studentInfo?.studyType ?: "") }
    val status = remember { mutableStateOf(studentInfo?.status ?: "") }
    val studentCardState = remember { mutableStateOf(studentInfo?.studentCardNumber ?: "") }

    val addressState = remember { mutableStateOf(studentInfo?.address ?: "") }
    val passportState = remember { mutableStateOf(studentInfo?.passportNumber ?: "") }
    val schoolState = remember { mutableStateOf(studentInfo?.school ?: "") }
    val documentState = remember { mutableStateOf(studentInfo?.documentNumber ?: "") }
    val militaryState = remember { mutableStateOf(studentInfo?.military ?: "") }

    val fatherNameState = remember { mutableStateOf(studentInfo?.fatherFio ?: "") }
    val fatherPhoneState = remember { mutableStateOf(studentInfo?.fatherPhone ?: "") }
    val fatherAddressState = remember { mutableStateOf(studentInfo?.fatherAddress ?: "") }
    val motherNameState = remember { mutableStateOf(studentInfo?.motherFio ?: "") }
    val motherPhoneState = remember { mutableStateOf(studentInfo?.motherPhone ?: "") }
    val motherAddressState = remember { mutableStateOf(studentInfo?.motherAddress ?: "") }

    return StudentFormStates(
        usernameState, passwordState, emailState,
        selectedGroup,
        nameState, surnameState, lastnameState, birthdayState, groupState, specialityState, courseState, phoneState,
        studyForm, studyType, status, studentCardState,
        addressState, passportState, schoolState, documentState, militaryState,
        fatherNameState, fatherPhoneState, fatherAddressState, motherNameState, motherPhoneState, motherAddressState
    )
}

private fun populateFromStudent(form: StudentFormStates, student: StudentDetailsDto) {
    form.nameState.value = student.name ?: ""
    form.surnameState.value = student.surname ?: ""
    form.lastnameState.value = student.lastname ?: ""
    form.birthdayState.value = student.birthday ?: ""
    form.phoneState.value = student.phoneNumber ?: ""
    form.groupState.value = student.group?.name ?: ""
    form.courseState.value = student.group?.course.toString()
    form.specialityState.value = student.group?.speciality?.name ?: ""
    form.selectedGroup.value = student.group
}

private fun populateFromStudentInfo(form: StudentFormStates, info: StudentSensitiveDto) {
    form.studyForm.value = info.studyForm ?: ""
    form.studyType.value = info.studyType ?: ""
    form.status.value = info.status ?: ""
    form.studentCardState.value = info.studentCardNumber ?: ""
    form.addressState.value = info.address ?: ""
    form.passportState.value = info.passportNumber ?: ""
    form.schoolState.value = info.school ?: ""
    form.documentState.value = info.documentNumber ?: ""
    form.militaryState.value = info.military ?: ""

    form.fatherNameState.value = info.fatherFio ?: ""
    form.fatherPhoneState.value = info.fatherPhone ?: ""
    form.fatherAddressState.value = info.fatherAddress ?: ""
    form.motherNameState.value = info.motherFio ?: ""
    form.motherPhoneState.value = info.motherPhone ?: ""
    form.motherAddressState.value = info.motherAddress ?: ""
}

@Composable
private fun HandleProfileEffects(
    viewModel: StudentProfileViewModel,
    error: MutableState<NetworkError?>,
    errorInfo: MutableState<NetworkError?>,
    onAddStudent: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is StudentProfileContract.Effect.ShowErrorStudent -> {
                    val errorMessage = effect.error.toString()
                    error.value = if (errorMessage.contains("Username already taken")) {
                        NetworkError.Unexpected("Никнейм уже занят. Пожалуйста, выберите другой.")
                    } else {
                        effect.error
                    }
                }

                is StudentProfileContract.Effect.ShowErrorStudentInfo -> {
                    if (effect.error != NetworkError.AccessDenied) {
                        errorInfo.value = effect.error
                    }
                }

                is StudentProfileContract.Effect.ShowSuccessMessage -> {
                    Logger.getAnonymousLogger().info("Success")
                }

                is StudentProfileContract.Effect.AddStudent -> {
                    onAddStudent(effect.id)
                }
            }
        }
    }
}

private fun buildReadOnlyBaseInfo(student: StudentDetailsDto?) = listOf(
    ReadOnlyField("Имя", student?.name ?: ""),
    ReadOnlyField("Фамилия", student?.surname ?: ""),
    ReadOnlyField("Отчество", student?.lastname ?: ""),
    ReadOnlyField("Дата рождения", student?.birthday ?: ""),
    ReadOnlyField("Телефон", student?.phoneNumber ?: "")
)

private fun buildReadOnlyAddInfo(
    student: StudentDetailsDto?,
    info: StudentSensitiveDto?
) = listOf(
    ReadOnlyField("Группа", student?.group?.name ?: ""),
    ReadOnlyField("Специальность", student?.group?.speciality?.name ?: ""),
    ReadOnlyField("Курс", student?.group?.course?.toString() ?: ""),
    ReadOnlyField("Форма обучения", info?.studyForm ?: ""),
    ReadOnlyField("Тип обучения", info?.studyType ?: ""),
    ReadOnlyField("Статус", info?.status ?: ""),
    ReadOnlyField("№ студенческого", info?.studentCardNumber ?: ""),
    ReadOnlyField("Адрес", info?.address ?: ""),
    ReadOnlyField("Паспорт", info?.passportNumber ?: ""),
    ReadOnlyField("Школа", info?.school ?: ""),
    ReadOnlyField("Документ", info?.documentNumber ?: ""),
    ReadOnlyField("Воинский учет", info?.military ?: ""),
    ReadOnlyField("Отец", info?.fatherFio ?: ""),
    ReadOnlyField("Телефон отца", info?.fatherPhone ?: ""),
    ReadOnlyField("Адрес отца", info?.fatherAddress ?: ""),
    ReadOnlyField("Мать", info?.motherFio ?: ""),
    ReadOnlyField("Телефон матери", info?.motherPhone ?: ""),
    ReadOnlyField("Адрес матери", info?.motherAddress ?: "")
)

private fun buildEditableBaseInfo(form: StudentFormStates) = listOf(
    EditableField("Имя", form.nameState),
    EditableField("Фамилия", form.surnameState),
    EditableField("Отчество", form.lastnameState),
    EditableField("Дата рождения", form.birthdayState),
    EditableField("Телефон", form.phoneState)
)

private fun buildEditableAddInfo(form: StudentFormStates) = listOf(
    EditableField("Группа", form.groupState),
    EditableField("Специальность", form.specialityState),
    EditableField("Курс", form.courseState),
    EditableField("Форма обучения", form.studyForm),
    EditableField("Тип обучения", form.studyType),
    EditableField("Статус", form.status),
    EditableField("№ студенческого", form.studentCardState),
    EditableField("Адрес", form.addressState),
    EditableField("Паспорт", form.passportState),
    EditableField("Школа", form.schoolState),
    EditableField("Документ", form.documentState),
    EditableField("Воинский учет", form.militaryState),
    EditableField("Отец", form.fatherNameState),
    EditableField("Телефон отца", form.fatherPhoneState),
    EditableField("Адрес отца", form.fatherAddressState),
    EditableField("Мать", form.motherNameState),
    EditableField("Телефон матери", form.motherPhoneState),
    EditableField("Адрес матери", form.motherAddressState)
)

@Composable
private fun NewStudentCredentialsBlock(
    usernameState: MutableState<String>,
    passwordState: MutableState<String>,
    emailState: MutableState<String>,
    groupState: MutableState<String>,
    onGroupSelected: (GroupDto?) -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = usernameState.value,
                onValueChange = { usernameState.value = it },
                label = { Text("Никнейм") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            PasswordField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = "Пароль",
                modifier = Modifier.weight(1f)
            )
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("Почта") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            GroupDropdown(
                selectGroup = null,
                onGroupSelected = { group ->
                    onGroupSelected(group)
                    groupState.value = group.name.toString()

                    Logger.getAnonymousLogger().info("2222222222222222222222222222222222222222222222")
                    Logger.getAnonymousLogger().info("Selected group: ${groupState.value}")
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun validateFormBeforeSubmit(
    id: Int?,
    form: StudentFormStates
): List<String> {
    val missing = mutableListOf<String>()
    if (id == null) {
        if (form.usernameState.value.isBlank()) missing.add("Никнейм")
        if (form.passwordState.value.isBlank()) missing.add("Пароль")
        if (form.emailState.value.isBlank()) missing.add("Почта")
    }
    if (form.surnameState.value.isBlank()) missing.add("Фамилия")
    if (form.nameState.value.isBlank()) missing.add("Имя")
    if (form.lastnameState.value.isBlank()) missing.add("Отчество")
    if (form.birthdayState.value.isBlank()) missing.add("Дата рождения")
    if (form.selectedGroup.value?.id == null) missing.add("Группа")
    if (form.phoneState.value.isBlank()) missing.add("Телефон")

    if (form.addressState.value.isBlank()) missing.add("Адрес")
    if (form.passportState.value.isBlank()) missing.add("Паспорт")
    if (form.schoolState.value.isBlank()) missing.add("Школа")
    if (form.documentState.value.isBlank()) missing.add("Документ")
    if (form.militaryState.value.isBlank()) missing.add("Воинский учет")
    if (form.studentCardState.value.isBlank()) missing.add("№ студенческого")

    if (form.studyForm.value.isBlank()) missing.add("Форма обучения")
    if (form.studyType.value.isBlank()) missing.add("Тип обучения")
    if (form.status.value.isBlank()) missing.add("Статус")

    if (form.fatherNameState.value.isBlank()) missing.add("Отец (ФИО)")
    if (form.fatherPhoneState.value.isBlank()) missing.add("Телефон отца")
    if (form.fatherAddressState.value.isBlank()) missing.add("Адрес отца")

    if (form.motherNameState.value.isBlank()) missing.add("Мать (ФИО)")
    if (form.motherPhoneState.value.isBlank()) missing.add("Телефон матери")
    if (form.motherAddressState.value.isBlank()) missing.add("Адрес матери")

    return missing
}

private fun submitForm(
    id: Int?,
    viewModel: StudentProfileViewModel,
    form: StudentFormStates
) {
    if (id == null) {
        val studentReq = StudentCreateRequest(
            surname = form.surnameState.value,
            name = form.nameState.value,
            lastname = form.lastnameState.value,
            birthday = form.birthdayState.value,
            groupId = form.selectedGroup.value?.id,
            phoneNumber = form.phoneState.value
        )

        val studentInfoReq = StudentInfoCreateRequest(
            address = form.addressState.value,
            passportNumber = form.passportState.value,
            school = form.schoolState.value,
            documentNumber = form.documentState.value,
            military = form.militaryState.value,
            studentCardNumber = form.studentCardState.value,
            studyType = form.studyType.value,
            studyForm = form.studyForm.value,
            status = form.status.value,
            fatherFio = form.fatherNameState.value,
            fatherPhone = form.fatherPhoneState.value,
            fatherAddress = form.fatherAddressState.value,
            motherFio = form.motherNameState.value,
            motherPhone = form.motherPhoneState.value,
            motherAddress = form.motherAddressState.value
        )

        val registerReq = RegisterRequest(
            username = form.usernameState.value,
            password = form.passwordState.value,
            email = form.emailState.value,
            fullName = "${form.surnameState.value} ${form.nameState.value} ${form.lastnameState.value}",
            roleName = null,
            studentProfile = studentReq,
            studentInfo = studentInfoReq
        )

        viewModel.setEvent(StudentProfileContract.Event.AddStudent(registerReq))
    } else {
        val studentSensitiveUpdateRequest = StudentSensitiveUpdateRequest(
            address = form.addressState.value,
            passportNumber = form.passportState.value,
            school = form.schoolState.value,
            documentNumber = form.documentState.value,
            military = form.militaryState.value,
            studentCardNumber = form.studentCardState.value,
            studyType = form.studyType.value,
            studyForm = form.studyForm.value,
            status = form.status.value,
            fatherFio = form.fatherNameState.value,
            fatherPhone = form.fatherPhoneState.value,
            fatherAddress = form.fatherAddressState.value,
            motherFio = form.motherNameState.value,
            motherPhone = form.motherPhoneState.value,
            motherAddress = form.motherAddressState.value
        )

        val updateReq = StudentUpdateRequest(
            surname = form.surnameState.value,
            name = form.nameState.value,
            lastname = form.lastnameState.value,
            birthday = form.birthdayState.value,
            groupId = form.selectedGroup.value?.id,
            phoneNumber = form.phoneState.value,
            info = studentSensitiveUpdateRequest
        )
        viewModel.setEvent(StudentProfileContract.Event.UpdateStudent(updateReq))
    }
}
