package com.smartcampus.presentation.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.getStartDay(): LocalDate {

    return if (this.dayOfWeek == DayOfWeek.SATURDAY && this.hour >= 13 ||
        this.dayOfWeek == DayOfWeek.SUNDAY) {
        val daysUntilNextMonday: Long = when (this.dayOfWeek) {
            DayOfWeek.SATURDAY -> 2
            DayOfWeek.SUNDAY -> 1
            else -> 0
        }
        this.plusDays(daysUntilNextMonday).toLocalDate()
    } else {
        val daysSinceMonday = (this.dayOfWeek.value - DayOfWeek.MONDAY.value).toLong()
        this.minusDays(daysSinceMonday).toLocalDate()
    }
}

fun LocalDateTime.getEndDay(): LocalDate {

    return if (this.dayOfWeek == DayOfWeek.SATURDAY && this.hour >= 13 ||
        this.dayOfWeek == DayOfWeek.SUNDAY) {
        val daysUntilNextMonday = when (this.dayOfWeek) {
            DayOfWeek.SATURDAY -> 2
            DayOfWeek.SUNDAY -> 1
            else -> 0
        }
        this.plusDays(daysUntilNextMonday.toLong() + 6).toLocalDate()
    } else {
        val daysSinceMonday = (this.dayOfWeek.value - DayOfWeek.MONDAY.value).toLong()
        this.minusDays(daysSinceMonday).plusDays(6).toLocalDate()
    }
}

fun LocalDate.toServerFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return this.format(formatter)
}