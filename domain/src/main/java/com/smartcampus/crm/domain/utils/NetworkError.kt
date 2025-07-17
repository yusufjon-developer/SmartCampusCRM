package com.smartcampus.crm.domain.utils

sealed class NetworkError {
    class Unexpected(val reason: String) : NetworkError()
    class Api(val reason: String, val code: Int) : NetworkError()
    object NoInternetConnection : NetworkError()
    object ServerIsNotAvailable : NetworkError()
    class ResponseDeserialization(val message: String) : NetworkError()

    override fun toString(): String = when(this) {
        is Unexpected -> "Unexpected(reason: $reason)[${this.hashCode()}]"
        is Api -> "Api(reason: $reason, code: $code)[${this.hashCode()}]"
        NoInternetConnection -> "NoInternetConnection[${this.hashCode()}]"
        ServerIsNotAvailable -> "ServerIsNotAvailable[${this.hashCode()}]"
        is ResponseDeserialization -> "ResponseDeserialization(message: $message)[${this.hashCode()}]"
    }
}