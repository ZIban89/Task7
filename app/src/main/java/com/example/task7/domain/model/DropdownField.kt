package com.example.task7.domain.model

class DropdownField(
    id: String,
    title: String,
    type: FieldType,
    val values: List<Option>
) : Field(id, title, type)
