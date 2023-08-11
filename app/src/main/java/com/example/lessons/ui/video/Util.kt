package com.example.lessons.ui.video

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitleText(text: String) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = text,
        style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.secondary
        ),
        textAlign = TextAlign.Start
    )
}

@Composable
fun DescriptionText(text: String) {
    Text(
        modifier = Modifier.padding(horizontal = 16.dp),
        text = text,
        style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.secondary
        ),
        textAlign = TextAlign.Start,
    )
}