package com.example.lessons.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

@Module
@InstallIn(ViewModelComponent::class)
class VideoPlayerModule {
    @Provides
    @ViewModelScoped
    fun provideVideoPlayer(app: Application): Player {
        return ExoPlayer.Builder(app)
            .build()
    }
}