package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.security.RoleRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class SecurityApiService(private val httpClient: HttpClient) {
    suspend fun getRoleList(page: Int, size: Int, sortedBy: String?): HttpResponse {
        val url = buildString {
            append("/crm/system-admin/roles?page=$page&size=$size")
            sortedBy?.let { append("&sortedBy=$it") }
        }
        return httpClient.get(url)
    }

    suspend fun getRole(id: Int): HttpResponse {
        return httpClient.get("/crm/system-admin/roles/$id")
    }

    suspend fun createRole(role: RoleRequest): HttpResponse {
        return httpClient.post("/crm/system-admin/roles") {
            setBody(role)
        }
    }

    suspend fun deleteRole(id: Int): HttpResponse {
        return httpClient.delete("/crm/system-admin/roles/$id")
    }
}