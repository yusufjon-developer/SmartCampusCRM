package com.smartcampus.presentation.core.extensions

import androidx.compose.material3.CalendarLocale
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable

@ExperimentalMaterial3Api
@Composable
fun rememberCustomDatePickerState(
    initialSelectedDateMillis: Long? = null,
    initialDisplayedMonthMillis: Long? = initialSelectedDateMillis,
    yearRange: IntRange = DatePickerDefaults.YearRange,
    initialDisplayMode: DisplayMode = DisplayMode.Picker,
    selectableDates: SelectableDates = DatePickerDefaults.AllDates,
    locale: CalendarLocale
): DatePickerState {
    val dateFormatter = DatePickerDefaults.dateFormatter(
        yearSelectionSkeleton = "yMMMM", // Например, "Сентябрь 2025" для ru_RU
        selectedDateSkeleton = "yMMMd",  // Например, "27 сен 2025"
        selectedDateDescriptionSkeleton = "yMMMMEEEEd" // Например, "Суббота, 27 сентября 2025"
    )

    return rememberDatePickerState(
        initialSelectedDateMillis = initialSelectedDateMillis,
        initialDisplayedMonthMillis = initialDisplayedMonthMillis,
        yearRange = yearRange,
        initialDisplayMode = initialDisplayMode,
        selectableDates = selectableDates
    )
}