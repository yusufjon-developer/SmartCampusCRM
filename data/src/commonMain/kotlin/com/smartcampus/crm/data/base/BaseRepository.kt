package com.smartcampus.crm.data.base

import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.crm.domain.utils.NetworkError
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseRepository {

    protected inline fun <reified T: Any> doRequest(
        crossinline request: suspend () -> HttpResponse
    ): Flow<Either<NetworkError, T>> = flow {
        request().let {
            val responseCode = it.status.value
            when {
                it.status.isSuccess() -> {
                    val body = try { it.body<T>() } catch (_: Exception) { null }
                    if (body != null) {
                        emit(Either.Right(body))
                    } else {
                        emit(Either.Left(NetworkError.ResponseDeserialization("Empty or malformed response")))
                    }

                }
                responseCode == 403 ->
                    emit(Either.Left(NetworkError.AccessDenied))
                responseCode >= 500 ->
                    emit(Either.Left(NetworkError.ServerIsNotAvailable))
                else ->
                    emit(Either.Left(NetworkError.Unexpected(it.body())))
            }
        }
    }.flowOn(Dispatchers.IO).catch { exception ->
        val message = exception.localizedMessage ?: "Error Occurred!"

        when (exception) {
            is UnknownHostException -> {
                emit(Either.Left(NetworkError.NoInternetConnection))
            }

            is SocketTimeoutException -> {
                emit(Either.Left(NetworkError.ServerIsNotAvailable))
            }

            is NullPointerException, is IllegalArgumentException -> {
                emit(Either.Left(NetworkError.ResponseDeserialization(message)))
            }

            else -> {
                emit(Either.Left(NetworkError.Unexpected(message)))
            }
        }
    }
}