package com.example.lessons.ui.payment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.lessons.R
import com.example.lessons.ui.lessons.LessonViewModel
import com.example.lessons.ui.lessons.MainIntent
import kotlinx.coroutines.launch

object PaymentScreen : Screen {
    @Composable
    override fun Content() {
        val navigation = LocalNavigator.currentOrThrow
        val scope = rememberCoroutineScope()
        val viewModel = hiltViewModel<LessonViewModel>()

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(dimensionResource(id = R.dimen.standard))
                .fillMaxSize()
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    scope.launch {
                        viewModel.lessonIntent.send(MainIntent.DoingPayment)
                        navigation.pop()
                    }
                }) {
                Text(text = stringResource(id = R.string.pay))
            }
        }
    }
}