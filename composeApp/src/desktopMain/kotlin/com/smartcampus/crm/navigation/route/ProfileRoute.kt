package com.smartcampus.crm.navigation.route

import com.smartcampus.crm.domain.models.student.StudentInfo
import kotlinx.serialization.Serializable

@Serializable
sealed interface ProfileRoute {
    @Serializable
    data class BasicInfo(val studentInfo: StudentInfo)

    @Serializable
    data class Education(val studentInfo: StudentInfo)

    @Serializable
    data class AdditionalInfo(val studentInfo: StudentInfo)

    @Serializable
    data object Loading
}