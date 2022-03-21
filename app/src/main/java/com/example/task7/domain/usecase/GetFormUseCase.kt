package com.example.task7.domain.usecase

import com.example.task7.common.exception.ClevertecBackendException
import com.example.task7.domain.Resource
import com.example.task7.domain.model.ClevertecForm
import com.example.task7.domain.repository.ClevertecRepository
import com.example.task7.domain.repository.ResourceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class GetFormUseCase @Inject constructor(
    private val repo: ClevertecRepository,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(): Flow<Resource<ClevertecForm>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getForm()))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = when (e) {
                        is ClevertecBackendException -> {
                            resourceProvider.getBackendExceptionMessage()
                        }
                        is UnknownHostException -> {
                            resourceProvider.getInternetExceptionMessage()
                        }
                        else -> {
                            e.printStackTrace()
                            resourceProvider.getUnknownExceptionMessage()
                        }
                    },
                    exception = e
                )
            )
        }
    }
}
