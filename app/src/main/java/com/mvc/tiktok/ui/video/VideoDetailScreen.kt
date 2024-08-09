package com.mvc.tiktok.ui.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.mvc.tiktok.designsystem.TiktokVideoPlayer

@UnstableApi
@Composable
fun MainVideoPlayer(videoId:Int,viewModel:VideoDetailViewModel = hiltViewModel()) {
    val uiState = viewModel.uiState.collectAsState()
   if (uiState.value == VideoDetailUiState.Default){
      viewModel.handleAction(VideoDetailAction.LoadData(videoId))
   }
   FirstVideoPlayer(uiState = uiState.value,player = viewModel.videoPlayer){
      action-> viewModel.handleAction(action)
   }
}
@UnstableApi
@Composable
fun FirstVideoPlayer(
   uiState: VideoDetailUiState,
   player: Player,handleAction :(VideoDetailAction)-> Unit
) {
  when(uiState){
     is VideoDetailUiState.Loading ->{
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
           Text(text = "loading...")
        }
     }
     is VideoDetailUiState.Success -> {
        SecondVideoPlayer(player = player, handleAction = handleAction)
     }
     else -> {
        Text(text = "error...")
     }
  }
}

@OptIn(ExperimentalComposeUiApi::class)
@UnstableApi
@Composable
fun SecondVideoPlayer(player: Player,handleAction :(VideoDetailAction)-> Unit) {

   ConstraintLayout(modifier = Modifier.fillMaxSize().clickable(
      onClick =  {
         handleAction(VideoDetailAction.ToggleVideo)
      }
   )) {
      val (videoPlayerView, sideBar) = createRefs()
      TiktokVideoPlayer(player = player, modifier = Modifier.constrainAs(videoPlayerView) {
         top.linkTo(parent.top)
         bottom.linkTo(parent.bottom)
         start.linkTo(parent.start)
         end.linkTo(parent.end)
         width = Dimension.matchParent
         height = Dimension.matchParent
      })
   }
}