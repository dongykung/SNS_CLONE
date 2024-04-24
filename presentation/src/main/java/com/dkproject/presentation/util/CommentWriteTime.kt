package com.dkproject.presentation.util

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

fun CommentWriteTime(): String {
    val now = OffsetDateTime.now()

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    return now.format(formatter)

}

fun main() {
    println(CommentWriteTime().toString())
}