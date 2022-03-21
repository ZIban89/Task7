package com.example.task7.data.remote.model.form

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FormDto(
    val fields: List<FieldDto>,
    val image: String,
    val title: String
)