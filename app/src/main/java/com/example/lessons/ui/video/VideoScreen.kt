package com.example.lessons.ui.video

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.ui.PlayerView
import cafe.adriel.voyager.core.screen.Screen
import com.example.lessons.data.remote.response.Lesson
import com.example.lessons.ui.lessons.LessonViewModel
import com.example.lessons.ui.lessons.MainIntent

data class VideoScreen(val lesson: Lesson? = null) : Screen {

    @Composable
    override fun Content() {
        val viewModel = hiltViewModel<LessonViewModel>()

        LaunchedEffect(Unit) {
            lesson?.video_url?.let {
                viewModel.lessonIntent.send(MainIntent.PlayVideo(it))
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                viewModel.player.pause()
                viewModel.player.seekToDefaultPosition()
                viewModel.player.clearMediaItems()
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).also {
                        it.player = viewModel.player
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            lesson?.name?.let {
                TitleText(text = it)
            }
            Spacer(modifier = Modifier.height(8.dp))
            lesson?.description?.let {
                DescriptionText(text = it)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}