package com.example.task7.domain

sealed class Resource<T>(val data: T? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(val message: String, data: T? = null, val exception: Exception? = null) :
        Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
