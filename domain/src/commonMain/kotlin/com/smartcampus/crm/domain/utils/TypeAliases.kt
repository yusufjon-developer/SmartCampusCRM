package com.smartcampus.crm.domain.utils

import kotlinx.coroutines.flow.Flow

typealias RemoteWrapper<T> = Flow<Either<NetworkError, T>>