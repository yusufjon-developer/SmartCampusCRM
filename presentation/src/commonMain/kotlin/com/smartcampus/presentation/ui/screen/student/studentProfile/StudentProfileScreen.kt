package com.smartcampus.presentation.ui.screen.student.studentProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.domain.models.student.StudentInfo
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.additional_information
import smartcampuscrm.presentation.generated.resources.address
import smartcampuscrm.presentation.generated.resources.basic_information
import smartcampuscrm.presentation.generated.resources.course
import smartcampuscrm.presentation.generated.resources.document_number
import smartcampuscrm.presentation.generated.resources.education_information
import smartcampuscrm.presentation.generated.resources.email
import smartcampuscrm.presentation.generated.resources.father_address
import smartcampuscrm.presentation.generated.resources.father_full_name
import smartcampuscrm.presentation.generated.resources.father_phone
import smartcampuscrm.presentation.generated.resources.groups
import smartcampuscrm.presentation.generated.resources.lastname
import smartcampuscrm.presentation.generated.resources.military
import smartcampuscrm.presentation.generated.resources.mother_address
import smartcampuscrm.presentation.generated.resources.mother_full_name
import smartcampuscrm.presentation.generated.resources.mother_phone
import smartcampuscrm.presentation.generated.resources.name
import smartcampuscrm.presentation.generated.resources.phone_number
import smartcampuscrm.presentation.generated.resources.school
import smartcampuscrm.presentation.generated.resources.speciality
import smartcampuscrm.presentation.generated.resources.status
import smartcampuscrm.presentation.generated.resources.student_card_number
import smartcampuscrm.presentation.generated.resources.study_form
import smartcampuscrm.presentation.generated.resources.study_type
import smartcampuscrm.presentation.generated.resources.surname

@Composable
fun ProfileHeader(
    studentInfo: StudentInfo,
    modifier: Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.primary,
                    CircleShape
                )
        )

        Spacer(modifier = Modifier.width(24.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "${studentInfo.students.name} ${studentInfo.students.surname} ${studentInfo.students.lastname}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = studentInfo.students.email,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun StudentProfileScreen(
    id: Int,
    navigateToEdit: (id: Int) -> Unit,
    viewModel: StudentProfileViewModel = koinViewModel(),
    studentInfo: StudentInfo
) {
    LaunchedEffect(id) {
        viewModel.setEvent(StudentProfileContract.Event.GetStudentInfo(id))
    }
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is StudentProfileContract.Effect.NavigateToEdit -> navigateToEdit(effect.id)
            }
        }
    }

    var readOnly by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            ProfileHeader(studentInfo = studentInfo, modifier = Modifier.padding(horizontal = 24.dp))
                        }
                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        item {
                            SectionTitle(stringResource(Res.string.basic_information))
                        }
                        item {
                            GridSection(BasicInfoFields(studentInfo), readOnly)
                        }

                        item {
                            SectionTitle(stringResource(Res.string.education_information))
                        }
                        item {
                            GridSection(EducationFields(studentInfo), readOnly)
                        }

                        item {
                            SectionTitle(stringResource(Res.string.additional_information))
                        }
                        item {
                            GridSection(AdditionalFields(studentInfo), readOnly)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                onClick = {
                    readOnly = if (readOnly) false else true
                },
                shape = RoundedCornerShape(9999.dp),
            ) {
                Icon(
                    imageVector = if (readOnly) Icons.Filled.Edit else Icons.Filled.Save,
                    contentDescription = if (readOnly) "Изменить" else "Сохранить",
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(text = if (readOnly) "Изменить" else "Сохранить")
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun GridSection(fields: List<Pair<String, String>>, readOnly: Boolean) {
    Column {
        for (i in fields.indices step 2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    label = { Text(fields[i].first) },
                    value = fields[i].second,
                    onValueChange = {},
                    readOnly = readOnly,
                    modifier = Modifier.weight(1f)
                )
                if (i + 1 < fields.size) {
                    OutlinedTextField(
                        label = { Text(fields[i + 1].first) },
                        value = fields[i + 1].second,
                        onValueChange = {},
                        readOnly = readOnly,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun BasicInfoFields(studentInfo: StudentInfo) = listOf(
    stringResource(Res.string.name) to studentInfo.students.name,
    stringResource(Res.string.surname) to studentInfo.students.surname,
    stringResource(Res.string.lastname) to studentInfo.students.lastname,
    stringResource(Res.string.phone_number) to studentInfo.students.phoneNumber,
    stringResource(Res.string.email) to studentInfo.students.email,
    stringResource(Res.string.groups) to studentInfo.students.groups.name,
    stringResource(Res.string.speciality) to studentInfo.students.groups.specialities.name,
    stringResource(Res.string.course) to studentInfo.students.groups.course.toString(),
    stringResource(Res.string.study_form) to studentInfo.studyForm,
    stringResource(Res.string.study_type) to studentInfo.studyType,
    stringResource(Res.string.status) to studentInfo.status,
    stringResource(Res.string.student_card_number) to studentInfo.studentCardNumber
)

@Composable
fun EducationFields(studentInfo: StudentInfo) = listOf(
    stringResource(Res.string.school) to studentInfo.school,
    stringResource(Res.string.document_number) to studentInfo.documentNumber
)

@Composable
fun AdditionalFields(studentInfo: StudentInfo) = listOf(
    stringResource(Res.string.address) to studentInfo.address,
    stringResource(Res.string.military) to studentInfo.military,
    stringResource(Res.string.father_full_name) to studentInfo.fatherFIO,
    stringResource(Res.string.father_phone) to studentInfo.fatherPhone,
    stringResource(Res.string.father_address) to studentInfo.fatherAddress,
    stringResource(Res.string.mother_full_name) to studentInfo.motherFIO,
    stringResource(Res.string.mother_phone) to studentInfo.motherPhone,
    stringResource(Res.string.mother_address) to studentInfo.motherAddress
)
