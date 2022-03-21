package com.example.task7.data.repository

import com.example.task7.data.remote.ClevertecApi
import com.example.task7.data.remote.model.RequestBody
import com.example.task7.domain.repository.ClevertecRepository
import javax.inject.Inject

class ClevertecRepositoryImpl @Inject constructor(
    private val clevertecApi: ClevertecApi
) : ClevertecRepository {

    override suspend fun getForm() = clevertecApi.getForm().toForm()

    override suspend fun sendForm(values: Map<String, String>): String {
        return clevertecApi.sendForm(RequestBody(values)).result
    }
}
