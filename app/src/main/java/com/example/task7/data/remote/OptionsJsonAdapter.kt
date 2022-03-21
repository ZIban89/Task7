package com.example.task7.data.remote

import com.example.task7.data.remote.model.form.OptionDto
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

class OptionsJsonAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): List<OptionDto> {
        val options = mutableListOf<OptionDto>()
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            val value = reader.nextString()
            options.add(OptionDto(name, value))
        }
        reader.endObject()
        return options.toList()
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: List<OptionDto>?) {
    }
}
