package com.example.task7.data.repository

import com.example.task7.common.exception.EmptyOptionListException
import com.example.task7.common.exception.UnsupportedFieldType
import com.example.task7.data.remote.model.form.FormDto
import com.example.task7.data.remote.model.form.OptionDto
import com.example.task7.domain.model.ClevertecForm
import com.example.task7.domain.model.DropdownField
import com.example.task7.domain.model.Field
import com.example.task7.domain.model.FieldType
import com.example.task7.domain.model.Option

private const val TYPE_LIST = "LIST"
private const val TYPE_TEXT = "TEXT"
private const val TYPE_NUMERIC = "NUMERIC"

fun FormDto.toForm(): ClevertecForm {
    val imageUri = this.image
    val title = this.title
    val fields = this.fields.map { fieldDto ->
        val fieldId = fieldDto.name
        val fieldTitle = fieldDto.title
        val fieldType = when (fieldDto.type) {
            TYPE_LIST -> FieldType.LIST
            TYPE_TEXT -> FieldType.TEXT
            TYPE_NUMERIC -> FieldType.NUMERIC
            else -> throw UnsupportedFieldType(
                "Unknown type ${fieldDto.type} (name $fieldId, title $fieldTitle)"
            )
        }

        // return не обязателен, но так нагляднее
        return@map if (fieldType == FieldType.LIST) {
            DropdownField(
                id = fieldId,
                title = fieldTitle,
                type = fieldType,
                values = fieldDto.values?.map { it.toOption() } ?: throw EmptyOptionListException(
                    "No options in options list (name = $fieldId, title = $fieldTitle)"
                )
            )
        } else {
            Field(id = fieldDto.name, title = fieldDto.title, type = fieldType)
        }
    }

    return ClevertecForm(imageUri = imageUri, title = title, fields = fields)
}

fun OptionDto.toOption() = Option(id = name, value = value)
