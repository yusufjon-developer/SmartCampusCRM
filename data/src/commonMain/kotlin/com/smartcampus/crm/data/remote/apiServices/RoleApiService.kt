package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.security.RoleRequest
import com.smartcampus.crm.domain.models.security.UpdatePermissionStatus
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class RoleApiService(
    private val httpClient: HttpClient
) {
    suspend fun getRoleById(id: Int): HttpResponse = httpClient.get("/crm/system-admin/roles/$id")

    suspend fun createRole(request: RoleRequest): HttpResponse = httpClient.post("/crm/system-admin/roles") {
        setBody(request)
    }

    suspend fun getRoleList(page: Int, size: Int, sortedBy: String?): HttpResponse {
        val url = buildString {
            append("/crm/system-admin/roles?page=$page&size=$size")
            sortedBy?.let { append("&sortedBy=$it") }
        }
        return httpClient.get(url)
    }

    suspend fun deleteRole(id: Int): HttpResponse = httpClient.delete("/crm/system-admin/roles/$id")

    suspend fun updatePermissions(id: Int, request: UpdatePermissionStatus): HttpResponse =
        httpClient.post("/crm/system-admin/roles/$id/permissions") {
            setBody(request)
        }
}