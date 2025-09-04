package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.GroupCreateRequest
import com.smartcampus.crm.domain.models.GroupUpdateRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class GroupApiService(
    private val httpClient: HttpClient
) {
    suspend fun getGroups(page: Int, size: Int, sortedBy: String?): HttpResponse {
        val url = buildString {
            append("/groups?page=$page&size=$size")
            sortedBy?.let { append("&sortedBy=$it") }
        }

        return httpClient.get(url)
    }

    suspend fun getGroupById(id: Int): HttpResponse = httpClient.get("/groups/$id")

    suspend fun createGroup(request: GroupCreateRequest): HttpResponse = httpClient
        .post("/groups") {
            setBody(request)
        }

    suspend fun updateGroup(id: Int, request: GroupUpdateRequest): HttpResponse = httpClient
        .put("/groups/$id") {
            setBody(request)
        }

    suspend fun deleteGroup(id: Int): HttpResponse = httpClient.delete("/groups/$id")
}