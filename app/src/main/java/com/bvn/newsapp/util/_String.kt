package com.bvn.newsapp.util

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

fun String.formatDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd MMM yy")
    return ZonedDateTime.parse(this).format(formatter)
}