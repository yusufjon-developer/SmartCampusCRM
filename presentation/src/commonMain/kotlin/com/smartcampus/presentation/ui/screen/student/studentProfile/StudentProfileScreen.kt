package com.smartcampus.presentation.ui.screen.student.studentProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.models.auth.StudentCreateRequest
import com.smartcampus.crm.domain.models.auth.StudentInfoCreateRequest
import com.smartcampus.crm.domain.models.student.StudentSensitiveUpdateRequest
import com.smartcampus.crm.domain.models.student.StudentUpdateRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.components.form.EditableField
import com.smartcampus.presentation.core.components.form.ErrorDialog
import com.smartcampus.presentation.core.components.form.GenericProfileScreen
import com.smartcampus.presentation.core.components.form.ProfileFormEditable
import com.smartcampus.presentation.core.components.form.ProfileFormReadonly
import com.smartcampus.presentation.core.components.form.ProfileHeader
import com.smartcampus.presentation.core.components.form.ReadOnlyField
import com.smartcampus.presentation.core.components.form.SectionTitle
import org.koin.compose.viewmodel.koinViewModel
import java.util.logging.Logger

@Composable
fun StudentProfileScreen(
    id: Int?,
    viewModel: StudentProfileViewModel = koinViewModel()
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val student = state.value.student
    val studentInfo = state.value.studentInfo

    val error = remember { mutableStateOf<NetworkError?>(null) }
    val errorInfo = remember { mutableStateOf<NetworkError?>(null) }

    val usernameState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }

    val nameState = remember { mutableStateOf(student?.name ?: "") }
    val surnameState = remember { mutableStateOf(student?.surname ?: "") }
    val lastnameState = remember { mutableStateOf(student?.lastname ?: "") }
    val birthdayState = remember { mutableStateOf(student?.birthday ?: "") }
    val phoneState = remember { mutableStateOf(student?.phoneNumber ?: "") }

    val groupState = remember { mutableStateOf(student?.groupId?.toString() ?: "") }
    val specialityState = remember { mutableStateOf(student?.groups?.specialities?.name ?: "") }
    val courseState = remember { mutableStateOf(student?.groups?.course?.toString() ?: "") }
    val studyForm = remember { mutableStateOf(studentInfo?.studyForm ?: "") }
    val studyType = remember { mutableStateOf(studentInfo?.studyType ?: "") }
    val status = remember { mutableStateOf(studentInfo?.status ?: "") }
    val studentCardState = remember { mutableStateOf(studentInfo?.studentCardNumber ?: "") }

    val adressState = remember { mutableStateOf(studentInfo?.address ?: "") }
    val passportState = remember { mutableStateOf(studentInfo?.passportNumber ?: "") }
    val schoolState = remember { mutableStateOf(studentInfo?.school ?: "") }
    val documentState = remember { mutableStateOf(studentInfo?.documentNumber ?: "") }
    val militaryState = remember { mutableStateOf(studentInfo?.military ?: "") }

    val fatherNameState = remember { mutableStateOf(studentInfo?.fatherFIO ?: "") }
    val fatherPhoneState = remember { mutableStateOf(studentInfo?.fatherPhone ?: "") }
    val fatherAddressState = remember { mutableStateOf(studentInfo?.fatherAddress ?: "") }
    val motherNameState = remember { mutableStateOf(studentInfo?.motherFIO ?: "") }
    val motherPhoneState = remember { mutableStateOf(studentInfo?.motherPhone ?: "") }
    val motherAddressState = remember { mutableStateOf(studentInfo?.motherAddress ?: "") }

    val editableAddStudent = listOf(
        EditableField("НикНейм", usernameState),
        EditableField("Пароль", passwordState),
        EditableField("Почта", emailState),
    )

    val readOnlyBaseInfo = listOf(
        // --- Базовая информация ---
        ReadOnlyField("Имя", student?.name ?: ""),
        ReadOnlyField("Фамилия", student?.surname ?: ""),
        ReadOnlyField("Отчество", student?.lastname ?: ""),
        ReadOnlyField("Дата рождения", student?.birthday ?: ""),
        ReadOnlyField("Телефон", student?.phoneNumber ?: ""),
    )

    val readOnlyAddInfo = listOf(
        // --- Учебная информация ---
        ReadOnlyField("Группа", student?.groups?.name ?: ""),
        ReadOnlyField("Специальность", student?.groups?.specialities?.name ?: ""),
        ReadOnlyField("Курс", student?.groups?.course?.toString() ?: ""),
        ReadOnlyField("Форма обучения", studentInfo?.studyForm ?: ""),
        ReadOnlyField("Тип обучения", studentInfo?.studyType ?: ""),
        ReadOnlyField("Статус", studentInfo?.status ?: ""),
        ReadOnlyField("№ студенческого", studentInfo?.studentCardNumber ?: ""),

        // --- Документы ---
        ReadOnlyField("Адрес", studentInfo?.address ?: ""),
        ReadOnlyField("Паспорт", studentInfo?.passportNumber ?: ""),
        ReadOnlyField("Школа", studentInfo?.school ?: ""),
        ReadOnlyField("Документ", studentInfo?.documentNumber ?: ""),
        ReadOnlyField("Воинский учет", studentInfo?.military ?: ""),

        // --- Родители ---
        ReadOnlyField("Отец", studentInfo?.fatherFIO ?: ""),
        ReadOnlyField("Телефон отца", studentInfo?.fatherPhone ?: ""),
        ReadOnlyField("Адрес отца", studentInfo?.fatherAddress ?: ""),
        ReadOnlyField("Мать", studentInfo?.motherFIO ?: ""),
        ReadOnlyField("Телефон матери", studentInfo?.motherPhone ?: ""),
        ReadOnlyField("Адрес матери", studentInfo?.motherAddress ?: "")
    )

    val editableBaseInfo = listOf(
        // --- Базовая информация ---
        EditableField("Имя", remember { mutableStateOf(student?.name ?: "") }),
        EditableField("Фамилия", remember { mutableStateOf(student?.surname ?: "") }),
        EditableField("Отчество", remember { mutableStateOf(student?.lastname ?: "") }),
        EditableField("Дата рождения", remember { mutableStateOf(student?.birthday ?: "") }),
        EditableField("Телефон", remember { mutableStateOf(student?.phoneNumber ?: "") }),
    )

    val editableAddInfo = listOf(
        // --- Учебная информация ---
        EditableField("Группа", remember { mutableStateOf(student?.groupId?.toString() ?: "") }),
        EditableField("Специальность", remember { mutableStateOf(student?.groups?.specialities?.name ?: "") }),
        EditableField("Курс", remember { mutableStateOf(student?.groups?.course?.toString() ?: "") }),
        EditableField("Форма обучения", remember { mutableStateOf(studentInfo?.studyForm ?: "") }),
        EditableField("Тип обучения", remember { mutableStateOf(studentInfo?.studyType ?: "") }),
        EditableField("Статус", remember { mutableStateOf(studentInfo?.status ?: "") }),
        EditableField("№ студенческого", remember { mutableStateOf(studentInfo?.studentCardNumber ?: "") }),

        // --- Документы ---
        EditableField("Адрес", remember { mutableStateOf(studentInfo?.address ?: "") }),
        EditableField("Паспорт", remember { mutableStateOf(studentInfo?.passportNumber ?: "") }),
        EditableField("Школа", remember { mutableStateOf(studentInfo?.school ?: "") }),
        EditableField("Документ", remember { mutableStateOf(studentInfo?.documentNumber ?: "") }),
        EditableField("Воинский учет", remember { mutableStateOf(studentInfo?.military ?: "") }),

        // --- Родители (по ситуации) ---
        EditableField("Отец", remember { mutableStateOf(studentInfo?.fatherFIO ?: "") }),
        EditableField("Телефон отца", remember { mutableStateOf(studentInfo?.fatherPhone ?: "") }),
        EditableField("Адрес отца", remember { mutableStateOf(studentInfo?.fatherAddress ?: "") }),
        EditableField("Мать", remember { mutableStateOf(studentInfo?.motherFIO ?: "") }),
        EditableField("Телефон матери", remember { mutableStateOf(studentInfo?.motherPhone ?: "") }),
        EditableField("Адрес матери", remember { mutableStateOf(studentInfo?.motherAddress ?: "") })
    )

    LaunchedEffect(Unit) {
        id?.let {
            viewModel.setEvent(StudentProfileContract.Event.GetStudent(id))
            viewModel.setEvent(StudentProfileContract.Event.GetStudentInfo(id))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is StudentProfileContract.Effect.ShowErrorStudent -> {
                    error.value = effect.error
                }

                is StudentProfileContract.Effect.ShowErrorStudentInfo -> {
                    if (effect.error != NetworkError.AccessDenied) {
                        errorInfo.value = effect.error
                    }
                }

                is StudentProfileContract.Effect.ShowSuccessMessage -> {
                    Logger.getLogger("Success").info(effect.message)
                }
            }
        }
    }

    LaunchedEffect(student) {
        if (student != null) {
            nameState.value = student.name.toString()
            surnameState.value = student.surname.toString()
            lastnameState.value = student.lastname.toString()
            birthdayState.value = student.birthday.toString()
            phoneState.value = student.phoneNumber.toString()
            groupState.value = student.groupId.toString()
            specialityState.value = student.groups?.specialities?.name.toString()
            courseState.value = student.groups?.course.toString()
        }
    }

    LaunchedEffect(studentInfo) {
        if (studentInfo != null) {
            studyForm.value = studentInfo.studyForm.toString()
            studyType.value = studentInfo.studyType.toString()
            status.value = studentInfo.status.toString()
            studentCardState.value = studentInfo.studentCardNumber.toString()
            adressState.value = studentInfo.address.toString()
            passportState.value = studentInfo.passportNumber.toString()
            schoolState.value = studentInfo.school.toString()
            documentState.value = studentInfo.documentNumber.toString()
            militaryState.value =  studentInfo.military.toString()

            fatherNameState.value = studentInfo.fatherFIO.toString()
            fatherPhoneState.value = studentInfo.fatherPhone.toString()
            fatherAddressState.value = studentInfo.fatherAddress.toString()
            motherNameState.value = studentInfo.motherFIO.toString()
            motherPhoneState.value = studentInfo.motherPhone.toString()
            motherAddressState.value = studentInfo.motherAddress.toString()
        }
    }

    GenericProfileScreen(
        initialReadOnly = false,

        onToggleEdit = { isEditing ->
            if (!isEditing) {
                if (id == null) {
                    val studentReq = StudentCreateRequest(
                        surname = surnameState.value,
                        name = nameState.value,
                        lastname = lastnameState.value,
                        birthday = birthdayState.value,
                        groupId = groupState.value.toIntOrNull(),
                        phoneNumber = phoneState.value
                    )

                    val studentInfoReq = StudentInfoCreateRequest(
                        address = adressState.value,
                        passportNumber = passportState.value,
                        school = schoolState.value,
                        documentNumber = documentState.value,
                        military = militaryState.value,
                        studentCardNumber = studentCardState.value,
                        studyType = studyType.value,
                        studyForm = studyForm.value,
                        status = status.value,
                        fatherFio = fatherNameState.value,
                        fatherPhone = fatherPhoneState.value,
                        fatherAddress = fatherAddressState.value,
                        motherFio = motherNameState.value,
                        motherPhone = motherPhoneState.value,
                        motherAddress = motherAddressState.value
                    )

                    val registerReq = RegisterRequest(
                        username = usernameState.value,
                        password = passwordState.value,
                        email = emailState.value,
                        fullName = "${surnameState.value} ${nameState.value} ${lastnameState.value}",
                        roleName = null,
                        studentProfile = studentReq,
                        studentInfo = studentInfoReq
                    )

                    viewModel.setEvent(StudentProfileContract.Event.AddStudent(registerReq))
                }
                else {
                    val studentSensitiveUpdateRequest = StudentSensitiveUpdateRequest(
                        address = adressState.value,
                        passportNumber = passportState.value,
                        school = schoolState.value,
                        documentNumber = documentState.value,
                        military = militaryState.value,
                        studentCardNumber = studentCardState.value,
                        studyType = studyType.value,
                        studyForm = studyForm.value,
                        status = status.value,
                        fatherFio = fatherNameState.value,
                        fatherPhone = fatherPhoneState.value,
                        fatherAddress = fatherAddressState.value,
                        motherFio = motherNameState.value,
                        motherPhone = motherPhoneState.value,
                        motherAddress = motherAddressState.value
                    )

                    val updateReq = StudentUpdateRequest(
                        surname = surnameState.value,
                        name = nameState.value,
                        lastname = lastnameState.value,
                        birthday = birthdayState.value,
                        groupId = groupState.value.toIntOrNull(),
                        phoneNumber = phoneState.value,
                        info = studentSensitiveUpdateRequest
                    )
                    viewModel.setEvent(StudentProfileContract.Event.UpdateStudent(updateReq))
                }
            }
        },

        topContent = {
            ProfileHeader(
                imageUrl = student?.photo,
                title = "${student?.name ?: ""} ${student?.surname ?: ""}",
                subtitle = student?.email,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },

        bodyContent = { isEditing ->
            Column(modifier = Modifier.padding(8.dp).verticalScroll(scrollState)) {

                SectionTitle("Базовая информация")

                if (id == null) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        item {
                            ProfileFormEditable(fields = editableAddStudent)
                        }
                    }
                }

                if (isEditing || id == null) {
                    ProfileFormEditable(fields = editableBaseInfo)
                } else {
                    ProfileFormReadonly(fields = readOnlyBaseInfo)
                }

                if (errorInfo.value != NetworkError.AccessDenied) {
                    SectionTitle("Доп. информация")
                    if (isEditing || id == null) {
                        ProfileFormEditable(fields = editableAddInfo)
                    } else {
                        ProfileFormReadonly(fields = readOnlyAddInfo)
                    }
                }

                error.value?.let { err ->
                    ErrorDialog(
                        title = "Ошибка загрузки студента",
                        message = err.toString(),
                        onDismiss = { error.value = null }
                    )
                }

                errorInfo.value?.let {
                    val message = errorInfo.value.toString()
                    error.value?.let { e ->
                        message + "\n$e"
                    }
                    ErrorDialog(
                        title = "Ошибка загрузки доп. информации студента",
                        message = errorInfo.value.toString(),
                        onDismiss = { errorInfo.value = null }
                    )
                }

            }
        }
    )
}
