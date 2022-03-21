package com.example.task7.data.remote.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class RequestBody(
    val form: Map<String, String>
)