package com.smartcampus.crm.data.remote.apiServices

import com.smartcampus.crm.domain.models.TeacherWorkloadCreateRequest
import com.smartcampus.crm.domain.models.TeacherWorkloadUpdateRequest
import com.smartcampus.crm.domain.models.WorkloadExecutionCreateRequest
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class WorkloadApiService(
    private val httpClient: HttpClient
) {
    private val url = "/crm/workloads"

    suspend fun getWorkloads(page: Int, size: Int, sortedBy: String?): HttpResponse {
        val url = buildString {
            append("$url?page=$page&size=$size")
            sortedBy?.let { append("&sortedBy=$it") }
        }
        return httpClient.get(url)
    }

    suspend fun getWorkloadById(id: Int): HttpResponse = httpClient.get("$url/$id")

    suspend fun createWorkload(request: TeacherWorkloadCreateRequest): HttpResponse = httpClient
        .post {
            setBody(request)
        }

    suspend fun updateWorkload(id: Int, request: TeacherWorkloadUpdateRequest): HttpResponse = httpClient
        .put("$url/$id") {
            setBody(request)
        }

    suspend fun deleteWorkload(id: Int): HttpResponse = httpClient.delete("$url/$id")

    suspend fun getExecutions(workloadId: Int): HttpResponse = httpClient.get("$url/$workloadId/executions")

    suspend fun createExecution(workloadId: Int, request: WorkloadExecutionCreateRequest): HttpResponse = httpClient
        .post("$url/$workloadId/executions") {
            setBody(request)
        }

    suspend fun getSum(workloadId: Int): HttpResponse = httpClient.get("$url/$workloadId/executions/sum")

    suspend fun getTeacherWorkload(teacherId: Int, academicYear: String?): HttpResponse = httpClient
        .get("$url/teacher/$teacherId?academicYear=$academicYear")
}