package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.UserRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class LoginApiService(private val httpClient: HttpClient) {
    suspend fun login(user: UserRequest): HttpResponse {
        return httpClient.post("login") {
            setBody(user)
        }
    }
}