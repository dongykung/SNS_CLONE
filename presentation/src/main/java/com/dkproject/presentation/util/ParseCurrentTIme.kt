package com.dkproject.presentation.util

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun formatElapsedTime(dateTimeString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    val dateTime = ZonedDateTime.parse(dateTimeString, formatter)
    val now = ZonedDateTime.now()

    val difference = Duration.between(dateTime, now)
    val minutes = difference.toMinutes()
    val hours = difference.toHours()
    val days = difference.toDays()

    return when {
        minutes < 1 -> "방금 전"
        minutes < 60 -> "${minutes}분 전"
        hours < 24 -> "${hours}시간 전"
        days == 1L -> "어제"
        days < 7 -> "${days}일 전"
        else -> dateTime.format(DateTimeFormatter.ofPattern("MM월dd일"))
    }
}
