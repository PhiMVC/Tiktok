package com.mvc.tiktok.ui.for_you

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.mvc.tiktok.ui.video.VideoDetailScreen
import com.mvc.tiktok.ui.video.VideoDetailViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ListForYouVideoScreen(modifier : Modifier = Modifier) {
    val pagerState = rememberPagerState(pageCount = 4)
    VerticalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState,
        dragEnabled = true,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically) { videoId ->

        val viewModel : VideoDetailViewModel = hiltViewModel(key = videoId.toString())
        VideoDetailScreen(videoId = videoId, viewModel = viewModel )

    }

}