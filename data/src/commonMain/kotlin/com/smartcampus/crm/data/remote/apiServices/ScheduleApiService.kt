package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.ScheduleCreateRequest
import com.smartcampus.crm.domain.models.ScheduleUpdateRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class ScheduleApiService(
    private val httpClient: HttpClient
) {
    suspend fun getSchedule(
        day: String,
        teacherId: Int?,
        groupId: Int?,
        auditoriumId: Int?
    ): HttpResponse {
        val url = buildString {
            append("/crm/schedule")
            append("?day=$day")
            teacherId?.let {
                append("&teacherId=$it")
            }
            groupId?.let {
                append("&groupId=$it")
            }
            auditoriumId?.let {
                append("&auditoriumId=$it")
            }
        }

        return httpClient.get(url)
    }

    suspend fun getScheduleById(id: Int) = httpClient.get("/crm/schedule/$id")

    suspend fun createSchedule(request: ScheduleCreateRequest) = httpClient
        .post("/crm/schedule") {
            setBody(request)
        }

    suspend fun updateSchedule(id: Int, request: ScheduleUpdateRequest) = httpClient
        .post("/crm/schedule/$id") {
            setBody(request)
        }

    suspend fun deleteSchedule(id: Int) = httpClient.delete("/crm/schedule/$id")

    suspend fun validateRequest(request: ScheduleCreateRequest): HttpResponse = httpClient
        .post("/crm/schedule/validate") {
            setBody(request)
        }
}