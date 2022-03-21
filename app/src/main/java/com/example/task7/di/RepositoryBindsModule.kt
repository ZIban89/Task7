package com.example.task7.di

import com.example.task7.data.repository.ClevertecRepositoryImpl
import com.example.task7.data.repository.ResourceRepository
import com.example.task7.domain.repository.ClevertecRepository
import com.example.task7.domain.repository.ResourceProvider
import dagger.Binds
import dagger.Module

@Module(includes = [NetworkModule::class])
interface RepositoryBindsModule {

    @Binds
    fun bindClevertecRepository(repo: ClevertecRepositoryImpl) : ClevertecRepository

    //не знаю есть ли смысл выносить
    @Binds
    fun bindResourceProvider(provider: ResourceRepository): ResourceProvider
}
