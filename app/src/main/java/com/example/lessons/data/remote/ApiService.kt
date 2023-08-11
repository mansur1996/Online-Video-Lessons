package com.example.lessons.data.remote

import com.example.lessons.data.remote.response.GetLessonsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("lessons")
    suspend fun getLessons(): Response<GetLessonsResponse>
}