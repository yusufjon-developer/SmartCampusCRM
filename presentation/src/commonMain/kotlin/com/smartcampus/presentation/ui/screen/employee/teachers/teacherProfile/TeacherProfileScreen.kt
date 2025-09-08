package com.smartcampus.presentation.ui.screen.employee.teachers.teacherProfile

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
import com.smartcampus.crm.domain.models.TeacherDetailsDto
import com.smartcampus.crm.domain.models.TeacherSensitiveDto
import com.smartcampus.crm.domain.models.TeacherUpdateRequest
import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.models.auth.TeacherCreateRequest
import com.smartcampus.crm.domain.models.auth.TeacherInfoCreateRequest
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
import org.koin.compose.viewmodel.koinViewModel
import java.util.logging.Logger

@Composable
fun TeacherProfileScreen(
    id: Int?,
    onAddTeacher: (Int) -> Unit,
    onBackAfterDelete: () -> Unit,
    viewModel: TeacherProfileViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    val error = remember { mutableStateOf<NetworkError?>(null) }
    val errorInfo = remember { mutableStateOf<NetworkError?>(null) }

    val form = rememberTeacherFormStates(state.value.teacher, state.value.teacherInfo)

    LaunchedEffect(Unit) {
        id?.let {
            viewModel.setEvent(TeacherProfileContract.Event.GetTeacher(id))
            viewModel.setEvent(TeacherProfileContract.Event.GetTeacherInfo(id))
        }
    }

    HandleProfileEffects(
        viewModel = viewModel,
        error = error,
        errorInfo = errorInfo,
        onBackAfterDelete = { onBackAfterDelete() },
        onAddTeacher = { onAddTeacher(it) }
    )

    LaunchedEffect(state.value.teacher) {
        state.value.teacher?.let { s -> populateFromTeacher(form, s) }
    }
    LaunchedEffect(state.value.teacherInfo) {
        state.value.teacherInfo?.let { si -> populateFromTeacherInfo(form, si) }
    }

    val readOnly = buildReadOnlyTeacherBaseInfo(state.value.teacher)
    val readOnlyAdd = buildReadOnlyTeacherAddInfo(state.value.teacherInfo)
    val editable = buildEditableTeacherBaseInfo(form)
    val editableAdd = buildEditableTeacherAddInfo(form)

    GenericProfileScreen(
        initialReadOnly = false,

        onToggleEdit = { isEditing ->
            if (!isEditing) {
                val missing = validateFormBeforeSubmit(id, form)
                if (missing.isNotEmpty()) {
                    error.value = NetworkError.Unexpected("Заполните поля: ${missing.joinToString(", ")}")
                    return@GenericProfileScreen
                }
                submitTeacherForm(id, viewModel, form)
            }
        },

        topContent = {
            ProfileHeader(
                imageUrl = null,
                title = "${state.value.teacher?.name ?: ""} ${state.value.teacher?.surname ?: ""}",
                subtitle = null,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },

        bodyContent = { isEditing ->
            Column(modifier = Modifier.padding(8.dp).verticalScroll(scrollState)) {
                SectionTitle("Основная информация")

                if (id == null) {
                    NewTeacherCredentialsBlock(
                        usernameState = form.usernameState,
                        passwordState = form.passwordState,
                        emailState = form.emailState
                    )
                }

                if (isEditing) {
                    ProfileFormEditable(fields = editable)
                } else {
                    ProfileFormReadonly(fields = readOnly)
                }

                if (form.errorAccessDenuedIsNotSet()) {
                    SectionTitle("Доп. информация")
                    if (isEditing) {
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
                        title = "Ошибка загрузки доп. информации преподавателя",
                        message = errInfo.toString(),
                        onDismiss = { errorInfo.value = null }
                    )
                }
            }
        }
    )
}

/* ---------------- формы и хелперы ---------------- */

private data class TeacherFormStates(
    val usernameState: MutableState<String>,
    val passwordState: MutableState<String>,
    val emailState: MutableState<String>,

    val nameState: MutableState<String>,
    val surnameState: MutableState<String>,
    val lastnameState: MutableState<String>,
    val birthdayState: MutableState<String>,
    val phoneState: MutableState<String>,

    val addressState: MutableState<String>,
    val passportState: MutableState<String>,
    val highSchoolState: MutableState<String>,
    val documentState: MutableState<String>,
    val militaryState: MutableState<String>,

    val degreeState: MutableState<String>,
    val titleState: MutableState<String>,
    val positionState: MutableState<String>
) {
    fun errorAccessDenuedIsNotSet(): Boolean = true
}

@Composable
private fun rememberTeacherFormStates(
    teacher: TeacherDetailsDto?,
    info: TeacherSensitiveDto?
): TeacherFormStates {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }

    val name = remember { mutableStateOf(teacher?.name ?: "") }
    val surname = remember { mutableStateOf(teacher?.surname ?: "") }
    val lastname = remember { mutableStateOf(teacher?.lastname ?: "") }
    val birthday = remember { mutableStateOf(teacher?.birthday ?: "") }
    val phone = remember { mutableStateOf(teacher?.phoneNumber ?: "") }

    val address = remember { mutableStateOf(info?.address ?: "") }
    val passport = remember { mutableStateOf(info?.passportNumber ?: "") }
    val hs = remember { mutableStateOf(info?.highSchool ?: "") }
    val document = remember { mutableStateOf(info?.documentNumber ?: "") }
    val military = remember { mutableStateOf(info?.military ?: "") }

    val degree = remember { mutableStateOf(info?.degree ?: "") }
    val title = remember { mutableStateOf(info?.title ?: "") }
    val position = remember { mutableStateOf(info?.position ?: "") }

    return TeacherFormStates(
        username, password, email,
        name, surname, lastname, birthday, phone,
        address, passport, hs, document, military,
        degree, title, position
    )
}

private fun populateFromTeacher(form: TeacherFormStates, teacher: TeacherDetailsDto) {
    form.nameState.value = teacher.name ?: ""
    form.surnameState.value = teacher.surname ?: ""
    form.lastnameState.value = teacher.lastname ?: ""
    form.birthdayState.value = teacher.birthday ?: ""
    form.phoneState.value = teacher.phoneNumber ?: ""
}

private fun populateFromTeacherInfo(form: TeacherFormStates, info: TeacherSensitiveDto) {
    form.addressState.value = info.address ?: ""
    form.passportState.value = info.passportNumber ?: ""
    form.highSchoolState.value = info.highSchool ?: ""
    form.documentState.value = info.documentNumber ?: ""
    form.militaryState.value = info.military ?: ""
    form.degreeState.value = info.degree ?: ""
    form.titleState.value = info.title ?: ""
    form.positionState.value = info.position ?: ""
}

@Composable
private fun HandleProfileEffects(
    viewModel: TeacherProfileViewModel,
    error: MutableState<NetworkError?>,
    errorInfo: MutableState<NetworkError?>,
    onBackAfterDelete: () -> Unit,
    onAddTeacher: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TeacherProfileContract.Effect.ShowErrorTeacher -> {
                    val errorMessage = effect.error.toString()
                    error.value = if (errorMessage.contains("Username already taken")) {
                        NetworkError.Unexpected("Никнейм уже занят. Пожалуйста, выберите другой.")
                    } else {
                        effect.error
                    }
                }
                is TeacherProfileContract.Effect.ShowErrorTeacherInfo -> {
                    if (effect.error != NetworkError.AccessDenied) {
                        errorInfo.value = effect.error
                    }
                }
                is TeacherProfileContract.Effect.ShowSuccessMessage -> {
                    Logger.getAnonymousLogger().info("Success")
                }
                is TeacherProfileContract.Effect.TeacherDeleted -> {
                    // вернуться назад или выполнить навигацию
                    onBackAfterDelete()
                }
                is TeacherProfileContract.Effect.AddTeacher -> {
                    Logger.getAnonymousLogger().warning("TeacherScreen, id: ${effect.id}")
                    onAddTeacher(effect.id)
                }
            }
        }
    }
}

private fun buildReadOnlyTeacherBaseInfo(teacher: TeacherDetailsDto?) = listOf(
    ReadOnlyField("Имя", teacher?.name ?: ""),
    ReadOnlyField("Фамилия", teacher?.surname ?: ""),
    ReadOnlyField("Отчество", teacher?.lastname ?: ""),
    ReadOnlyField("Дата рождения", teacher?.birthday ?: ""),
    ReadOnlyField("Телефон", teacher?.phoneNumber ?: "")
)

private fun buildReadOnlyTeacherAddInfo(info: TeacherSensitiveDto?) = listOf(
    ReadOnlyField("Адрес", info?.address ?: ""),
    ReadOnlyField("Паспорт", info?.passportNumber ?: ""),
    ReadOnlyField("Аттестат / школа", info?.highSchool ?: ""),
    ReadOnlyField("Документ", info?.documentNumber ?: ""),
    ReadOnlyField("Воинский учет", info?.military ?: ""),
    ReadOnlyField("Ученая степень", info?.degree ?: ""),
    ReadOnlyField("Звание", info?.title ?: ""),
    ReadOnlyField("Должность", info?.position ?: "")
)

private fun buildEditableTeacherBaseInfo(form: TeacherFormStates) = listOf(
    EditableField("Имя", form.nameState),
    EditableField("Фамилия", form.surnameState),
    EditableField("Отчество", form.lastnameState),
    EditableField("Дата рождения", form.birthdayState),
    EditableField("Телефон", form.phoneState),
)

private fun buildEditableTeacherAddInfo(form: TeacherFormStates) = listOf(
    EditableField("Адрес", form.addressState),
    EditableField("Паспорт", form.passportState),
    EditableField("Аттестат / школа", form.highSchoolState),
    EditableField("Документ", form.documentState),
    EditableField("Воинский учет", form.militaryState),
    EditableField("Ученая степень", form.degreeState),
    EditableField("Звание", form.titleState),
    EditableField("Должность", form.positionState),
)

@Composable
private fun NewTeacherCredentialsBlock(
    usernameState: MutableState<String>,
    passwordState: MutableState<String>,
    emailState: MutableState<String>
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

        Row(modifier = Modifier.fillMaxWidth())
        {
            OutlinedTextField(
                value = emailState.value,
                onValueChange = { emailState.value = it },
                label = { Text("Почта") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedTextField(
                value = "Преподаватель",
                onValueChange = { },
                label = { Text("Роль") },
                singleLine = true,
                readOnly = true,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

private fun validateFormBeforeSubmit(
    id: Int?,
    form: TeacherFormStates
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
    if (form.phoneState.value.isBlank()) missing.add("Телефон")

    if (form.addressState.value.isBlank()) missing.add("Адрес")
    if (form.passportState.value.isBlank()) missing.add("Паспорт")
    if (form.highSchoolState.value.isBlank()) missing.add("Школа")
    if (form.documentState.value.isBlank()) missing.add("Документ")
    if (form.militaryState.value.isBlank()) missing.add("Воинский учет")

    if (form.degreeState.value.isBlank()) missing.add("Учённая степень")
    if (form.titleState.value.isBlank()) missing.add("Звание")
    if (form.positionState.value.isBlank()) missing.add("Должность")

    return missing
}

private fun submitTeacherForm(
    id: Int?,
    viewModel: TeacherProfileViewModel,
    form: TeacherFormStates
) {
    if (id == null) {
        val teacherReq = TeacherCreateRequest(
            surname = form.surnameState.value,
            name = form.nameState.value,
            lastname = form.lastnameState.value,
            birthday = form.birthdayState.value,
            phoneNumber = form.phoneState.value
        )

        val teacherInfoReq = TeacherInfoCreateRequest(
            address = form.addressState.value,
            passportNumber = form.passportState.value,
            highSchool = form.highSchoolState.value,
            documentNumber = form.documentState.value,
            military = form.militaryState.value,

            degree = form.degreeState.value,
            title = form.titleState.value,
            position = form.positionState.value
        )

        val registerReq = RegisterRequest(
            username = form.usernameState.value,
            password = form.passwordState.value,
            email = form.emailState.value,
            fullName = "${form.surnameState.value} ${form.nameState.value} ${form.lastnameState.value}",
            roleName = null,
            teacherProfile = teacherReq,
            teacherInfo = teacherInfoReq
        )

        viewModel.setEvent(TeacherProfileContract.Event.AddTeacher(registerReq))
    } else {
        val update = TeacherUpdateRequest(
            surname = form.surnameState.value,
            name = form.nameState.value,
            lastname = form.lastnameState.value,
            birthday = form.birthdayState.value,
            phoneNumber = form.phoneState.value,
            info = TeacherSensitiveDto(
                address = form.addressState.value,
                passportNumber = form.passportState.value,
                highSchool = form.highSchoolState.value,
                documentNumber = form.documentState.value,
                military = form.militaryState.value,
                degree = form.degreeState.value,
                title = form.titleState.value,
                position = form.positionState.value
            )
        )

        viewModel.setEvent(TeacherProfileContract.Event.UpdateTeacher(update))
    }
}
