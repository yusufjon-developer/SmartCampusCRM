package com.smartcampus.crm.data.base.pagination

interface PagingItemList<T : Any> {
    val listItems: List<T>
}