package com.smartcampus.crm.data.remote.apiServices

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
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
}