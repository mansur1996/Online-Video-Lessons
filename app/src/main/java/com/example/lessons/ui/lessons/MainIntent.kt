package com.example.lessons.ui.lessons

sealed class MainIntent {
    object FetchLessons : MainIntent()
    data class PlayVideo(val uri : String) : MainIntent()
    object DoingPayment : MainIntent()
}