package com.example.lessons.domain

import com.example.lessons.data.remote.response.Lesson
import com.example.lessons.data.repository.AppRepository
import com.example.lessons.utils.isConnected
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLessonsUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    fun getLessons(): Flow<Result<List<Lesson>>> = flow{
        if (isConnected()) {
            val map = appRepository.getLessons()
            emit(Result.success(map.body()?.lessons ?: emptyList()))
        } else {
            emit(Result.failure(Throwable(message = "No Internet connection")))
        }
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)
}