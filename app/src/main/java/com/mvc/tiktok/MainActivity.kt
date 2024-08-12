package com.mvc.tiktok

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mvc.tiktok.data.repositories.VideoRepository
import com.mvc.tiktok.di.VideoPlayerModule
import com.mvc.tiktok.ui.for_you.ListForYouVideoScreen
import com.mvc.tiktok.ui.theme.TikTokTheme
import com.mvc.tiktok.ui.video.MainVideoPlayer
import com.mvc.tiktok.ui.video.VideoDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TikTokTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListForYouVideoScreen()
                }
            }
        }
    }
}

