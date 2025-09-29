package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.LoginRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class LoginApiService(private val httpClient: HttpClient) {
    suspend fun login(request: LoginRequest): HttpResponse {
        return httpClient.post("/crm/auth/employee/signin") {
            setBody(request)
        }
    }
}