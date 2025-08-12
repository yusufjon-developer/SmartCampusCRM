package com.smartcampus.crm.data.base.pagination

import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse<T: Any>(
    val items: List<T>,
    val totalItems: Int,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int
) : PagingItemList<T> {
    override val listItems: List<T>
        get() = items
}
