package com.example.lessons.ui.lessons

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.example.lessons.domain.GetLessonsUseCase
import com.example.lessons.utils.isConnected
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LessonViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val userUseCase: GetLessonsUseCase,
    val player: Player,
) : ViewModel() {
    val lessonIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state = _state.asStateFlow()

    init {
        handleIntent()
        player.prepare()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            lessonIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.FetchLessons -> getLessons()
                    is MainIntent.PlayVideo -> playVideo(it.uri)
                    is MainIntent.DoingPayment -> doingPayment()
                }
            }
        }
    }

    fun getLessons() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            userUseCase.getLessons().onEach {
                it.onSuccess { response ->
                    _state.value = MainState.LoadLessons(response)
                }
                it.onFailure { throwable ->
                    _state.value = MainState.Error(throwable.message)
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun playVideo(uri: String) {
        // Build the media item.
        val mediaItem = MediaItem.fromUri(uri)
        // Set the media item to be played.
        player.setMediaItem(mediaItem)
        // Prepare the player.
        player.prepare()
        // Start the playback.
        player.play()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }

    private fun doingPayment() {
        if (isConnected()) {
            _state.value = MainState.IsPaid(true)
        }
    }
}