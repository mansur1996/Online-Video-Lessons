package com.example.lessons.ui.lessons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.lessons.R
import com.example.lessons.data.remote.response.Lesson

@Composable
fun AppBottomBar(isPaid: Boolean, onClick: () -> Unit = {}) {
    AnimatedVisibility(visible = !isPaid,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .height(56.dp)
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(id = R.dimen.standard))
                    .clickable { onClick.invoke() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.payment), style = TextStyle(
                        fontSize = 24.sp, fontWeight = FontWeight.Medium
                    )
                )
            }
        })
}

@Composable
fun AppTopBar() {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.inversePrimary)
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = dimensionResource(id = R.dimen.standard)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.lessons), style = TextStyle(
                fontSize = 24.sp, fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
fun ListItem(
    item: Lesson, itemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .clickable {
                itemClick.invoke()
            }) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                item.thumbnail?.let { thumbnail ->
                    LoadImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(4 / 3f)
                            .wrapContentHeight(),
                        path = thumbnail
                    )
                }
                Icon(
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                item.name?.let {
                    TitleText(it)
                }
                Spacer(modifier = Modifier.height(8.dp))
                item.description?.let {
                    DescriptionText(it)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun LockedListItem(
    item: Lesson, itemClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .clickable {
                itemClick.invoke()
            }) {
            Box(
                modifier = Modifier.fillMaxWidth(),
            ) {
                item.thumbnail?.let { thumbnail ->
                    LoadImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(4 / 3f)
                            .wrapContentHeight(),
                        path = thumbnail,
                        colorFilter = ColorFilter.colorMatrix(
                            ColorMatrix(
                                colorMatrix
                            )
                        )
                    )
                }
                Icon(
                    modifier = Modifier
                        .size(180.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.White,
                )
            }
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Spacer(modifier = Modifier.height(8.dp))
                item.name?.let {
                    TitleText(it)
                }
                Spacer(modifier = Modifier.height(8.dp))
                item.description?.let {
                    DescriptionText(it)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TitleText(text : String){
    Text(
        text = text, style = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.secondary
        ), textAlign = TextAlign.Center
    )
}

@Composable
fun DescriptionText(text : String){
    Text(
        text = text, style = TextStyle(
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            fontFamily = FontFamily.SansSerif,
            color = MaterialTheme.colorScheme.secondary
        ), textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun LoadImage(
    modifier: Modifier = Modifier, path: String, colorFilter: ColorFilter? = null
) {
    GlideImage(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(4 / 3f)
            .wrapContentHeight(),
        colorFilter = colorFilter,
        model = path,
        contentDescription = "loadImage",
        contentScale = ContentScale.FillBounds
    )
}

val colorMatrix = floatArrayOf(
    0.7f,
    0f,
    0f,
    0f,
    0f,
    0f,
    0.7f,
    0f,
    0f,
    0f,
    0f,
    0f,
    0.9f,
    0f,
    0f,
    0f,
    0f,
    0f,
    0.7f,
    0f //0.9 is 90% black
)