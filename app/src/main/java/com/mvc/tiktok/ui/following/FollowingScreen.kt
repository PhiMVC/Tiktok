package com.mvc.tiktok.ui.following

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.lerp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.mvc.tiktok.R
import com.mvc.tiktok.designsystem.TiktokVideoPlayer
import kotlin.math.absoluteValue

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FollowingScreen() {

   val pagerState : PagerState = rememberPagerState(pageCount = 10)
    val cardWidth : Int = (LocalConfiguration.current.screenWidthDp * 2/3) + 24

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.Black),
    verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Spacer(modifier = Modifier.height(55.dp))
        Text(text = "Trending Creators",
            style = MaterialTheme.typography.h5.copy(color=Color.White))


        Text(text = "Follow an account to see their latest video here",
            style = MaterialTheme.typography.body1.copy(color=Color.White))

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .aspectRatio(0.8f),
            state = pagerState,
            itemSpacing = 12.dp,
        ) {page->
            Card (modifier = Modifier
                .width(cardWidth.dp)
                .graphicsLayer {
                    val pageOffset: Float = (
                            (pagerState.currentPage - page) + pagerState.currentPageOffset
                            ).absoluteValue

                    scaleY = lerp(
                        start = 0.7f.dp, stop = 1f.dp, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).value
                }
                .clip(RoundedCornerShape(16.dp))){
                CreatorCard(
                    name = "Phi Hê Hê $page",
                    nickName = "ZZZ $page",
                    onFollow = {},
                    onClose = {}
                )
            }
        }

    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun CreatorCard(
    modifier: Modifier = Modifier,
    name:String,
    nickName:String,
    onFollow :()->Unit,
    onClose:()->Unit
){
    val videoPlayer : ExoPlayer = ExoPlayer.Builder(LocalContext.current).build()
    videoPlayer.repeatMode = Player.REPEAT_MODE_ALL
    videoPlayer.playWhenReady = true
    videoPlayer.prepare()
      ConstraintLayout(
          modifier = modifier
              .fillMaxSize()
              .clip(RoundedCornerShape(16.dp))
      ) {
       val (videoIntro,btnClose,imgAvatar,tvName,tvNickName,btnFollow) = createRefs()
          TiktokVideoPlayer(player = videoPlayer, modifier = Modifier.constrainAs(videoIntro) {
              start.linkTo(parent.start)
              end.linkTo(parent.end)
              top.linkTo(parent.top)
              bottom.linkTo(parent.bottom)
              width = Dimension.fillToConstraints
              height = Dimension.fillToConstraints
          })

          IconButton(onClick = onClose, modifier = Modifier
              .constrainAs(btnClose) {

                  top.linkTo(parent.top, margin = 12.dp)
                  end.linkTo(parent.end, margin = 12.dp)
              }
              .size(16.dp)) {
              Icon(Icons.Sharp.Close,contentDescription = "close icon", tint = Color.White)
          }
          Button(onClick = onFollow, modifier = Modifier
              .constrainAs(btnFollow) {
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
                  bottom.linkTo(parent.bottom, margin = 24.dp)
              }
              .padding(horizontal = 48.dp, vertical = 12.dp),
              colors = ButtonDefaults.buttonColors(
                  backgroundColor = Color(0xFFE94359) ,
                  contentColor = Color.White
          ))
          {
              Text(text = "Follow",
                  style = MaterialTheme.typography.body1.copy(color = Color.White))
          }

          Text(text = nickName,
              style = MaterialTheme.typography.subtitle1.copy(color = Color.Gray),
              modifier =  Modifier.constrainAs(tvNickName){
                  bottom.linkTo(btnFollow.top, margin = 8.dp)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
              }
          )
          Text(text = name,
              style = MaterialTheme.typography.body1.copy(color = Color.White),
              modifier =  Modifier.constrainAs(tvName){
                  bottom.linkTo(tvNickName.top, margin = 8.dp)
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
              }
          )
          AvatarFollowing(
              modifier = Modifier.constrainAs(imgAvatar){

                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
                  bottom.linkTo(tvName.top, margin = 8.dp)
              }
          )
          val uri : Uri = RawResourceDataSource.buildRawResourceUri(R.raw.test2)
          val mediaItem : MediaItem = MediaItem.fromUri(uri)
          videoPlayer.setMediaItem(mediaItem)
          SideEffect {
              videoPlayer.play()
          }

      }

}

@Composable
fun AvatarFollowing(modifier: Modifier = Modifier) {
    val sizeAvatar : Double = LocalConfiguration.current.screenWidthDp * 0.2;
    Image(
        painter = painterResource(id = R.drawable.ic_dog),
        contentDescription = "avatar",
        modifier = modifier
            .size(sizeAvatar.dp)
            .background(color = Color.White, shape = CircleShape)
            .border(color = Color.White, width = 2.dp, shape = CircleShape)
            .clip(CircleShape)

    )
}




