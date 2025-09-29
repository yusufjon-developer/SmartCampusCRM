package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.SpecialityCreateRequest
import com.smartcampus.crm.domain.models.SpecialityUpdateRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class SpecialityApiService(
    private val httpClient: HttpClient
) {
    suspend fun getSpecialities(page: Int, size: Int, sortedBy: String?): HttpResponse {
        val url = buildString {
            append("/specialities?page=$page&size=$size") // 💡 ИЗМЕНЕНИЕ: Путь
            sortedBy?.let { append("&sortedBy=$it") }
        }

        return httpClient.get(url)
    }

    suspend fun getSpecialityById(id: Int): HttpResponse = httpClient.get("/specialities/$id")

    suspend fun createSpeciality(request: SpecialityCreateRequest): HttpResponse = httpClient
        .post("/specialities") {
            setBody(request)
        }

    suspend fun updateSpeciality(id: Int, request: SpecialityUpdateRequest): HttpResponse = httpClient
        .put("/specialities/$id") {
            setBody(request)
        }

    suspend fun deleteSpeciality(id: Int): HttpResponse = httpClient.delete("/specialities/$id")
}