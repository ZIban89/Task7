package com.example.task7.di

import com.example.task7.data.remote.CLEVERTEC_BASE_URL
import com.example.task7.data.remote.ClevertecApi
import com.example.task7.data.remote.OptionsJsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): Interceptor =
        HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: Interceptor) = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(OptionsJsonAdapter())
            .build()

    @Provides
    @Singleton
    fun provideConverterFactory(moshi: Moshi): Converter.Factory =
        MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideClevertecApi(
        converterFactory: Converter.Factory,
        client: OkHttpClient
    ): ClevertecApi = Retrofit.Builder()
        .baseUrl(CLEVERTEC_BASE_URL)
        .client(client)
        .addConverterFactory(converterFactory)
        .build()
        .create(ClevertecApi::class.java)
}
