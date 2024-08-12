package com.mvc.tiktok.ui.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.mvc.tiktok.designsystem.TiktokVideoPlayer
import com.mvc.tiktok.ui.for_you.ListForYouVideoScreen
import com.mvc.tiktok.ui.video.composables.SideBarView
import com.mvc.tiktok.ui.video.composables.VideoInfoArea

@UnstableApi
@Composable
fun MainVideoPlayer(videoId:Int,viewModel:VideoDetailViewModel) {
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

@UnstableApi
@Composable
fun SecondVideoPlayer(player: Player,handleAction :(VideoDetailAction)-> Unit) {

   ConstraintLayout(modifier = Modifier
      .fillMaxSize()
      .clickable(
         onClick = {
            handleAction(VideoDetailAction.ToggleVideo)
         }
      )) {
      val (videoPlayerView, sideBar,videoInfo) = createRefs()
      TiktokVideoPlayer(player = player, modifier = Modifier.constrainAs(videoPlayerView) {
         top.linkTo(parent.top)
         bottom.linkTo(parent.bottom)
         start.linkTo(parent.start)
         end.linkTo(parent.end)
         width = Dimension.matchParent
         height = Dimension.matchParent
      })
      SideBarView(
         onAvatarClick = { /*TODO*/ },
         onLikeClick = { /*TODO*/ },
         onChatClick = { /*TODO*/ },
         onSaveClick = { /*TODO*/ },
         onShareClick = {/*TODO*/ },
         modifier = Modifier.constrainAs(sideBar) {
            end.linkTo(parent.end, margin = 16.dp)
            bottom.linkTo(parent.bottom, margin = 16.dp)
         }
      )
      VideoInfoArea(
         accountName = "PHIHe",
         videoName = "Clone Tiktok UI",
         hashTags = "856489495###",
         songName = "NO nO No",
         modifier = Modifier.constrainAs(videoInfo ){
         start.linkTo(parent.start, margin = 16.dp)
         bottom.linkTo(parent.bottom, margin = 16.dp)

      }
      )
   }
}
