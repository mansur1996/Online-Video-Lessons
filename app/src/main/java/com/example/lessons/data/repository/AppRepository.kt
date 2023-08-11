package com.example.lessons.data.repository

import android.util.Log
import com.example.lessons.data.remote.ApiService
import com.example.lessons.data.remote.response.GetLessonsResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val api: ApiService,
){
    suspend fun getLessons() : Response<GetLessonsResponse>{
        return api.getLessons()
    }
}