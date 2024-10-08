package com.mvc.tiktok.ui.video.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun VideoInfoArea(
    modifier: Modifier=Modifier,
    accountName: String,
    videoName: String,
    hashTags:String,
    songName:String
    ) {
    Column (
        modifier = modifier.wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ){
        Text(text = accountName,
            style = MaterialTheme.typography.titleLarge .copy(color = Color.White),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = videoName,
            style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
            maxLines = 1
            )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = hashTags,
            style = MaterialTheme.typography.titleMedium.copy(color = Color.White),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = songName,
            style = MaterialTheme.typography.titleSmall.copy(color = Color.White),
            maxLines = 1
        )
    }
}