package com.example.task7.data.remote.model.form

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FieldDto(
    val name: String,
    val title: String,
    val type: String,
    val values: List<OptionDto>?
)