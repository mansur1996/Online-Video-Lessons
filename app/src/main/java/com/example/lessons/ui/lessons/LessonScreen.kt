package com.example.lessons.ui.lessons

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.lessons.data.remote.response.Lesson
import com.example.lessons.ui.payment.PaymentScreen
import com.example.lessons.ui.video.VideoScreen

object LessonScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigation = LocalNavigator.currentOrThrow
        val viewModel = hiltViewModel<LessonViewModel>()
        val context = LocalContext.current
        val lessons = remember { mutableStateListOf<Lesson>() }
        var showProgressBar by remember { mutableStateOf(true) }
        val state by viewModel.state.collectAsStateWithLifecycle()
        var isPaid by rememberSaveable { (mutableStateOf(false)) }

        LaunchedEffect(Unit) {
            viewModel.lessonIntent.send(MainIntent.FetchLessons)
        }
        LaunchedEffect(state) {
            when (state) {
                is MainState.Idle -> {}
                is MainState.Loading -> {
                    showProgressBar = true
                }

                is MainState.LoadLessons -> {
                    showProgressBar = false
                    lessons.clear()
                    lessons.addAll((state as MainState.LoadLessons).lessons)
                }

                is MainState.IsPaid -> {
                    isPaid = (state as MainState.IsPaid).isPaid
                }

                is MainState.Error -> {
                    showProgressBar = false
                    val error = (state as MainState.Error).error
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
        Scaffold(
            bottomBar = {
                AppBottomBar(isPaid) { navigation.push(PaymentScreen) }
            },
            topBar = {
                AppTopBar()
            }) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                AnimatedVisibility(visible = showProgressBar, enter = fadeIn(), exit = fadeOut()) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                AnimatedVisibility(visible = !showProgressBar, enter = fadeIn(), exit = fadeOut()) {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(lessons.size) { position ->
                            val contactItem = lessons[position]
                            if (isPaid) {
                                ListItem(
                                    item = contactItem, itemClick = {
                                        navigation.push(VideoScreen(contactItem))
                                    })
                            } else {
                                if (position < 3) {
                                    ListItem(
                                        item = contactItem, itemClick = {
                                            navigation.push(VideoScreen(contactItem))
                                        })
                                } else {
                                    LockedListItem(
                                        item = contactItem, itemClick = {
                                            navigation.push(PaymentScreen)
                                        })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}