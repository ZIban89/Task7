package com.example.task7.domain.repository

interface ResourceProvider {

    //Можно было сюда этот метод не включать, но мало ли где-то понадобится. Пусть будет.
    fun getString(id: Int): String

    fun getBackendExceptionMessage(): String

    fun getInternetExceptionMessage(): String

    fun getUnknownExceptionMessage(): String
}
