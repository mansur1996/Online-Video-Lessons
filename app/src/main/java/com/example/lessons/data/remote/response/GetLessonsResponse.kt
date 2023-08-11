package com.example.lessons.data.remote.response

import java.io.Serializable

data class GetLessonsResponse(
    val lessons: List<Lesson> = emptyList(),
)

data class Lesson(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val thumbnail: String? = null,
    val video_url: String? = null,
) : Serializable