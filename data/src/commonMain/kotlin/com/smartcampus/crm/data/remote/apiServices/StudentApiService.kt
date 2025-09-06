package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.models.StudentUpdateRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
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
        return httpClient.get("/students/$id")
    }

    suspend fun getStudentInfoById(id: Int): HttpResponse {
        return httpClient.get("/students/$id/info")
    }

    suspend fun updateStudentProfile(id: Int, student: StudentUpdateRequest): HttpResponse {
        return httpClient.put("/students/$id") {
            setBody(student)
        }.body()
    }

    suspend fun registerStudent(request: RegisterRequest): HttpResponse {
        return httpClient.post("/auth/student/signup") {
            setBody(request)
        }.body()
    }
}