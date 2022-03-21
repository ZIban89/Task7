package com.example.task7.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Result(
    val result: String
)
