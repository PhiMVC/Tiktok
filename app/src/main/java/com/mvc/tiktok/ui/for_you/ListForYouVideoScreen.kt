package com.mvc.tiktok.ui.for_you

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.mvc.tiktok.ui.video.MainVideoPlayer
import com.mvc.tiktok.ui.video.VideoDetailViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ListForYouVideoScreen(modifier : Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = 10)
    VerticalPager(state = pagerState, dragEnabled = true) { videoId ->
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            val viewModel : VideoDetailViewModel = hiltViewModel(key = videoId.toString())
            MainVideoPlayer(videoId = videoId, viewModel = viewModel )
        }
    }

}