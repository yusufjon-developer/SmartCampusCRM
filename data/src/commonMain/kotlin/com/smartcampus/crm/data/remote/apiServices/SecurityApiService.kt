package com.smartcampus.crm.data.remote.apiServices

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class SecurityApiService(private val httpClient: HttpClient) {
    suspend fun getPermissionList(page: Int, size: Int, sortedBy: String?): HttpResponse {
        val url = buildString {
            append("/crm/system-admin/permissions?page=$page&size=$size")
            sortedBy?.let { append("&sortedBy=$it") }
        }
        return httpClient.get(url)
    }
}