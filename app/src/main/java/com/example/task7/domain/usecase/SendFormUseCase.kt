package com.example.task7.domain.usecase

import com.example.task7.domain.Resource
import com.example.task7.domain.repository.ClevertecRepository
import com.example.task7.domain.repository.ResourceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class SendFormUseCase @Inject constructor(
    private val repo: ClevertecRepository,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke(values: Map<String, String>): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.sendForm(values)))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = when (e) {
                        is UnknownHostException -> {
                            resourceProvider.getInternetExceptionMessage()
                        }
                        else -> {
                            resourceProvider.getUnknownExceptionMessage()
                        }
                    },
                    exception = e
                )
            )
        }
    }
}
