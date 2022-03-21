package com.example.task7.data.remote

import com.example.task7.data.remote.model.RequestBody
import com.example.task7.data.remote.model.Result
import com.example.task7.data.remote.model.form.FormDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

const val CLEVERTEC_BASE_URL = "http://test.clevertec.ru/tt/"

interface ClevertecApi {

    @GET("meta")
    suspend fun getForm(): FormDto

    @POST("data/")
    suspend fun sendForm(@Body form: RequestBody): Result
}
