package com.example.task7.domain.repository

import com.example.task7.domain.model.ClevertecForm

interface ClevertecRepository {
    suspend fun getForm(): ClevertecForm
    suspend fun sendForm(values: Map<String, String>): String
}
