package com.smartcampus.presentation.ui.screen.studentProfile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.domain.models.student.StudentInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun StudentProfileScreenOld(
    studentInfo: StudentInfo,
) {
    val scrollState = rememberScrollState()

    val currentDate = LocalDate.now()
    val dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMMM yyyy", Locale.ENGLISH)
    val formattedDate = currentDate.format(dateFormatter)

    // Основной контейнер экрана
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface) // Цвет фона, как на скриншоте
            .padding(16.dp)
    ) {
        // Заголовок "Welcome, Юсуфджон" и дата
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Text(
                text = "Welcome, ${studentInfo.students.name}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formattedDate,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Карточка профиля
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background), // Белый фон карточки
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                // Верхняя секция с фото и основной информацией
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Фото профиля
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                            .border(
                                2.dp,
                                MaterialTheme.colorScheme.primary,
                                CircleShape
                            )
                    ) { }

                    Spacer(modifier = Modifier.width(24.dp))

                    // Имя и email
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "${studentInfo.students.name} ${studentInfo.students.surname}",
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

                    // Кнопка "Edit"
                    Button(
                        onClick = { /* TODO: Обработка нажатия кнопки Edit */ },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Edit",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                // Информационные поля
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // 1 строка полей
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ProfileInputField(
                            label = "Full Name",
                            value = "${studentInfo.students.name} ${studentInfo.students.surname} ${studentInfo.students.lastname}",
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Nick Name",
                            value = studentInfo.students.name,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 2 строка полей
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ProfileInputField(
                            label = "Birthday",
                            value = studentInfo.students.birthday,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Email",
                            value = studentInfo.students.email,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 3 строка полей
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ProfileInputField(
                            label = "Phone Number",
                            value = studentInfo.students.phoneNumber,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Address",
                            value = studentInfo.address,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 4 строка полей
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ProfileInputField(
                            label = "School",
                            value = studentInfo.school,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Document Number",
                            value = studentInfo.documentNumber,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 5 строка полей
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ProfileInputField(
                            label = "Military",
                            value = studentInfo.military,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Student Card Number",
                            value = studentInfo.studentCardNumber,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 6 строка полей
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ProfileInputField(
                            label = "Study Type",
                            value = studentInfo.studyType,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Study Form",
                            value = studentInfo.studyForm,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 7 строка полей
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ProfileInputField(
                            label = "Status",
                            value = studentInfo.status,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 8 строка полей
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ProfileInputField(
                            label = "Father FIO",
                            value = studentInfo.fatherFIO,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Father Phone",
                            value = studentInfo.fatherPhone,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Father Address",
                            value = studentInfo.fatherAddress,
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // 9 строка полей
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        ProfileInputField(
                            label = "Mother FIO",
                            value = studentInfo.motherFIO,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Mother Phone",
                            value = studentInfo.motherPhone,
                            modifier = Modifier.weight(1f)
                        )

                        ProfileInputField(
                            label = "Mother Address",
                            value = studentInfo.motherAddress,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Секция "My email Address"
                Text(
                    text = "My email Address",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(
                            alpha = 0.3f
                        )
                    ), // Светлый фон для карточки email
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = studentInfo.students.email,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Text(
                                text = "1 month ago", // Можно сделать динамической датой последнего обновления email
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Кнопка "Add Email Address"
                TextButton(onClick = { /* TODO: Добавить логику для добавления email */ }) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add Email Address",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileInputField(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        OutlinedTextField(
            value = value,
            onValueChange = { /* В данном случае поле только для отображения */ },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            shape = RoundedCornerShape(8.dp),
        )
    }
}
