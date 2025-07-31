package com.smartcampus.presentation.ui.screen.studentProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.input.InputMode.Companion.Keyboard
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.presentation.core.components.tabs.TabStrip
import com.smartcampus.presentation.core.components.texts.TabItem
import com.smartcampus.presentationCore.components.texts.InfoItem
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.additional_information
import smartcampuscrm.presentation.generated.resources.basic_information
import smartcampuscrm.presentation.generated.resources.education_information

// Модель для данных вкладки
private data class ProfileTabData(
    val title: String,
    val content: @Composable (studentInfo: StudentInfo) -> Unit
)

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

    val profileTabs = listOf(
        ProfileTabData(stringResource(Res.string.basic_information)) { info ->
            BasicInfoTabContent(info)
        },
        ProfileTabData(stringResource(Res.string.education_information)) { info ->
            EducationTabContent(info)
        },
        ProfileTabData(stringResource(Res.string.additional_information)) { info ->
            AdditionalInfoTabContent(info)
        }
    )
    val scrollState = rememberScrollState()

    var selectedTabIndex by remember { mutableStateOf(0) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .verticalScroll(scrollState)
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
                    // Верхняя секция с фото и основной информацией
                    ProfileHeader(
                        studentInfo = studentInfo,
                        modifier = Modifier.padding(horizontal = 24.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        TabStrip(
                            tabs = profileTabs.map { it.title },
                            selectedTab = profileTabs[selectedTabIndex].title,
                            onTabSelected = { tabTitle ->
                                selectedTabIndex = profileTabs.indexOfFirst { it.title == tabTitle }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterHorizontally),
                        ) { tab, isSelected, onClick ->
                            TabItem(
                                text = tab,
                                isSelected = isSelected,
                                onClick = { onClick() },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 4.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        profileTabs[selectedTabIndex].content(studentInfo)
                    }


                }
            }

            Button(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                onClick = { },
                shape = RoundedCornerShape(9999.dp),
            ) {
                Icon(Icons.Filled.Edit, "Изменить", modifier = Modifier.padding(end = 16.dp))
                Text("Изменить")
            }
        }
    }
}

@Composable
fun BasicInfoTabContent(
    studentInfo: StudentInfo
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        InfoItem(label = "Имя", value = studentInfo.students.name)
        InfoItem(label = "Фамилия", value = studentInfo.students.surname)
        InfoItem(label = "Отчество", value = studentInfo.students.lastname)
        InfoItem(label = "Номер телефона", value = studentInfo.students.phoneNumber)
        InfoItem(label = "Электронная почта", value = studentInfo.students.email)
        InfoItem(label = "Группа", value = studentInfo.students.groups.id.toString())
        InfoItem(label = "Направление", value = studentInfo.students.groups.name)
        InfoItem(label = "Курс", value = studentInfo.students.groups.course.toString())
        InfoItem(label = "Форма обучения", value = studentInfo.studyForm)
        InfoItem(label = "Тип обучения", value = studentInfo.studyType)
        InfoItem(label = "Статус", value = studentInfo.status)
        InfoItem(label = "Номер студ. карты", value = studentInfo.studentCardNumber)
    }
}

@Composable
fun EducationTabContent(
    studentInfo: StudentInfo
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        InfoItem(label = "Школа", value = studentInfo.school)
        InfoItem(label = "Номер аттестата", value = studentInfo.documentNumber)
    }
}

@Composable
fun AdditionalInfoTabContent(
    studentInfo: StudentInfo
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
    ) {
        InfoItem(label = "Адрес", value = studentInfo.address)
        InfoItem(label = "Военное положение", value = studentInfo.military)
        InfoItem(label = "День рождения", value = studentInfo.students.birthday)

        Spacer(modifier = Modifier.height(8.dp))

        InfoItem(label = "Отец (ФИО)", value = studentInfo.fatherFIO)
        InfoItem(label = "Отец (Телефон)", value = studentInfo.fatherPhone)
        InfoItem(label = "Отец (Адрес)", value = studentInfo.fatherAddress)

        Spacer(modifier = Modifier.height(8.dp))

        InfoItem(label = "Мать (ФИО)", value = studentInfo.motherFIO)
        InfoItem(label = "Мать (Телефон)", value = studentInfo.motherPhone)
        InfoItem(label = "Мать (Адрес)", value = studentInfo.motherAddress)
    }
}