package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.AuditoriumCreateRequest
import com.smartcampus.crm.domain.models.AuditoriumUpdateRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class AuditoriumApiService(
    private val httpClient: HttpClient
) {
    suspend fun getAuditoriums(page: Int, size: Int, sortedBy: String?, isAvailable: Boolean?, day: String): HttpResponse {
        val url = buildString {
            append("/auditoriums?page=$page&size=$size")
            sortedBy?.let { append("&sortedBy=$it") }
            isAvailable?.let {
                append("&isAvailable=$it")
                append("&day=$day")
            }
        }
        return httpClient.get(url)
    }

    suspend fun getAuditoriumById(id: Int): HttpResponse = httpClient.get("/auditoriums/$id")

    suspend fun createAuditorium(request: AuditoriumCreateRequest): HttpResponse = httpClient
        .post("/auditoriums") {
            setBody(request)
        }

    suspend fun updateAuditorium(id: Int, request: AuditoriumUpdateRequest): HttpResponse = httpClient
        .put("/auditoriums/$id") {
            setBody(request)
        }

    suspend fun deleteAuditorium(id: Int): HttpResponse = httpClient.delete("/auditoriums/$id")
}