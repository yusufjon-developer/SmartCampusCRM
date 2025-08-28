// StudentProfileScreen.kt
package com.smartcampus.presentation.ui.screen.student.studentProfile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.presentation.core.components.form.EditableField
import com.smartcampus.presentation.core.components.form.GenericProfileScreen
import com.smartcampus.presentation.core.components.form.ProfileFormEditable
import com.smartcampus.presentation.core.components.form.ProfileFormReadonly
import com.smartcampus.presentation.core.components.form.ProfileHeader
import com.smartcampus.presentation.core.components.form.ReadOnlyField
import com.smartcampus.presentation.core.components.form.SectionTitle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StudentProfileScreen(
    id: Int,
    viewModel: StudentProfileViewModel = koinViewModel(),
    studentInfo: StudentInfo
) {
    // hoisted editable states for fields you want to allow editing:
    val nameState = remember { mutableStateOf(studentInfo.students.name) }
    val surnameState = remember { mutableStateOf(studentInfo.students.surname ?: "") }
    val lastnameState = remember { mutableStateOf(studentInfo.students.lastname ?: "") }
    val phoneState = remember { mutableStateOf(studentInfo.students.phoneNumber ?: "") }
    val emailState = remember { mutableStateOf(studentInfo.students.email ?: "") }
    val groupState = remember { mutableStateOf(studentInfo.students.groupId?.toString() ?: "") }
    val studyForm = remember { mutableStateOf(studentInfo.studyForm ?: "") }
    val studyType = remember { mutableStateOf(studentInfo.studyType ?: "") }
    val status = remember { mutableStateOf(studentInfo.status ?: "") }

    val readOnlyFields = listOf(
        ReadOnlyField("Имя", studentInfo.students.name ?: ""),
        ReadOnlyField("Фамилия", studentInfo.students.surname ?: ""),
        ReadOnlyField("Отчество", studentInfo.students.lastname ?: ""),
        ReadOnlyField("Телефон", studentInfo.students.phoneNumber ?: ""),
        ReadOnlyField("Email", studentInfo.students.email ?: ""),
        ReadOnlyField("Группа", studentInfo.students.groups?.name ?: ""),
        ReadOnlyField("Специальность", studentInfo.students.groups?.specialities?.name ?: ""),
        ReadOnlyField("Курс", studentInfo.students.groups?.course?.toString() ?: ""),
        ReadOnlyField("Форма обучения", studentInfo.studyForm ?: ""),
        ReadOnlyField("Тип обучения", studentInfo.studyType ?: ""),
        ReadOnlyField("Статус", studentInfo.status ?: ""),
        ReadOnlyField("№ студенческого", studentInfo.studentCardNumber ?: "")
    )

    val editableFields = listOf(
        EditableField("Имя", nameState),
        EditableField("Фамилия", surnameState),
        EditableField("Отчество", lastnameState),
        EditableField("Телефон", phoneState),
        EditableField("Email", emailState),
        EditableField("Группа", groupState),
        EditableField("Форма обучения", studyForm),
        EditableField("Тип обучения", studyType),
        EditableField("Статус", status)
    )

    GenericProfileScreen(
        onToggleEdit = { isEditing ->
            if (!isEditing) {
//                // Save pressed (exited editing): собрать данные и послать в viewModel
//                val updateReq = StudentUpdateRequest(
//                    surname = surnameState.value,
//                    name = nameState.value,
//                    lastname = lastnameState.value,
//                    birthday = studentInfo.students.birthday, // handle date conversion separately
//                    groupId = groupState.value.toIntOrNull(),
//                    phoneNumber = phoneState.value,
//                    photo = null,
//                    info = null
//                )
//                viewModel.setEvent(StudentProfileContract.Event.UpdateStudent(id, updateReq))

            } else {
                // Entering edit mode: ничего не делаем
            }
        },
        topContent = {
            ProfileHeader(
                imageUrl = studentInfo.students.photo,
                title = "${studentInfo.students.name ?: ""} ${studentInfo.students.surname ?: ""}",
                subtitle = studentInfo.students.email,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        },
        bodyContent = { isEditing ->
            Column(modifier = Modifier.padding(8.dp)) {
                SectionTitle("Базовая информация")
                if (isEditing) {
                    ProfileFormEditable(fields = editableFields)
                } else {
                    ProfileFormReadonly(fields = readOnlyFields)
                }

                SectionTitle("Доп. информация")
                if (isEditing) {
                    // create editable list from studentInfo.info if needed
                } else {
                    // show read-only additional info
                }
            }
        }
    )
}
