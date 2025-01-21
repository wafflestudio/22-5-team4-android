package com.example.interpark.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.*
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MoshiDateDeserializer {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    @RequiresApi(Build.VERSION_CODES.O)
    @FromJson
    fun fromJson(date: String): LocalDate {
        return LocalDate.parse(date, formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @ToJson
    fun toJson(date: LocalDate): String {
        return date.format(formatter)
    }
}

class MoshiDateTimeDeserializer {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @RequiresApi(Build.VERSION_CODES.O)
    @FromJson
    fun fromJson(dateTime: String): LocalDateTime {
        return LocalDateTime.parse(dateTime, formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @ToJson
    fun toJson(dateTime: LocalDateTime): String {
        return dateTime.format(formatter)
    }
}
