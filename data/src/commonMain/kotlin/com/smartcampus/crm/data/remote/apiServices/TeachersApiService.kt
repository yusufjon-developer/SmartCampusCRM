package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.TeacherUpdateRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class TeachersApiService(
    private val httpClient: HttpClient
) {
    suspend fun getTeachers(page: Int, size: Int, sortedBy: String?): HttpResponse {
        val url = buildString {
            append("/teachers?page=$page&size=$size")
            sortedBy?.let { append("&sortedBy=$it") }
        }

        return httpClient.get(url)
    }

    suspend fun getTeacherById(id: Int): HttpResponse = httpClient.get("/teachers/$id")

    suspend fun getTeacherInfoById(id: Int): HttpResponse = httpClient.get("/teachers/$id/info")

    suspend fun updateTeacher(id: Int, request: TeacherUpdateRequest): HttpResponse = httpClient
        .put("/teachers/$id") {
            setBody(request)
        }

    suspend fun deleteTeacher(id: Int): HttpResponse = httpClient.delete("/teachers/$id")
}