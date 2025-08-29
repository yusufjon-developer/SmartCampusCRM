package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.security.UpdateUserPermissions
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class UserApiService(
    private val httpClient: HttpClient
) {
    suspend fun getUserList(page: Int, size: Int, sortedBy: String?): HttpResponse {
        val url = buildString {
            append("/crm/system-admin/users?page=$page&size=$size")
            sortedBy?.let { append("&sortedBy=$it") }
        }
        return httpClient.get(url)
    }

    suspend fun getUserById(id: Int): HttpResponse =
        httpClient.get("/crm/system-admin/users/$id")

    suspend fun updateUserPermission(userId: Int, request: UpdateUserPermissions): HttpResponse =
        httpClient.put("/crm/system-admin/users/$userId") {
            setBody(request)
        }
}