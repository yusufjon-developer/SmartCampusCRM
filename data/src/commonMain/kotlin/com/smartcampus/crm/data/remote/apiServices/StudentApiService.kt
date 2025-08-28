package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.crm.domain.models.student.StudentInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class StudentApiService(
    private val httpClient: HttpClient
) {
    suspend fun getStudentsList(page: Int, size: Int): HttpResponse {
        val url = buildString { append("/students?page=$page&size=$size") }
        return httpClient.get(url).body()
    }

    suspend fun getStudentById(id: Int): HttpResponse {
        return httpClient.get("/students/$id").body()
    }

    suspend fun getStudentInfoById(id: Int): HttpResponse {
        return httpClient.get("/students/$id/info").body()
    }

    suspend fun updateStudent(student: Student): HttpResponse {
        return httpClient.post { setBody(student) }
    }

    suspend fun updateStrudentInfo(studentInfo: StudentInfo): HttpResponse {
        return httpClient.post { setBody(studentInfo) }
    }
}