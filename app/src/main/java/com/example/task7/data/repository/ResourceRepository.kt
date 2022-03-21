package com.example.task7.data.repository

import android.content.Context
import androidx.annotation.StringRes
import com.example.task7.R
import com.example.task7.domain.repository.ResourceProvider
import javax.inject.Inject

class ResourceRepository @Inject constructor(private val context: Context) : ResourceProvider {

    override fun getString(@StringRes id: Int) = context.getString(id)

    override fun getBackendExceptionMessage() = getString(R.string.backend_exception_message)

    override fun getInternetExceptionMessage() = getString(R.string.internet_exception_message)

    override fun getUnknownExceptionMessage() = getString(R.string.unknown_exception_message)
}
