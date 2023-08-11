package com.example.lessons.ui.lessons

import com.example.lessons.data.remote.response.Lesson

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    data class LoadLessons(val lessons: List<Lesson>) : MainState()
    data class IsPaid(val isPaid: Boolean) : MainState()
    data class Error(val error: String?) : MainState()
}
