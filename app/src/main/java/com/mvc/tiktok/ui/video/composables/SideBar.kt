package com.mvc.tiktok.ui.video.composables
import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.mvc.tiktok.R
import com.mvc.tiktok.ui.theme.TikTokTheme

@Composable
fun AvatarView(modifier: Modifier = Modifier,onClick: ()-> Unit) {
     ConstraintLayout(
         modifier = modifier.clickable { onClick() }){

         val (imgAvatar,addIcon)= createRefs()
         Image(
             painter = painterResource(id = R.drawable.ic_dog ),
             contentDescription = "icon_avatar",
             modifier = Modifier
                 .size(48.dp)
                 .background(
                     color = Color.White,
                     shape = CircleShape,
                 )
                 .border(
                     width = 2.dp,
                     shape = CircleShape,
                     color = Color.White
                 )
                 .clip(shape = CircleShape)
                 .constrainAs(imgAvatar) {
                     top.linkTo(parent.top)
                     bottom.linkTo(parent.bottom)
                     start.linkTo(parent.start)
                     end.linkTo(parent.end)
                 },
             )
         Box(
             modifier = Modifier
                 .size(24.dp)
                 .background(
                     color = MaterialTheme.colorScheme.error, shape = CircleShape
                 )
                 .constrainAs(addIcon)
                 {
                     top.linkTo(parent.top)
                     bottom.linkTo(parent.bottom)
                     start.linkTo(parent.start)
                     end.linkTo(parent.end)
                 }
                 , contentAlignment = Alignment.Center
         ) {
             Icon(
                 imageVector = Icons.Default.Add,
                 contentDescription ="icon add",
                 tint = Color.White,
                 modifier = Modifier.size(12.dp) )
         }
     }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun AudioTrackView(modifier: Modifier = Modifier,) {
    val infiniteTransition = rememberInfiniteTransition()
    val rotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = InfiniteRepeatableSpec(
            repeatMode = RepeatMode.Restart,
            animation = tween(durationMillis = 5000, easing = LinearEasing),

        )
    )
            Image(
                modifier = Modifier
                    .size(30.dp)
                    .rotate(rotate),
                painter = painterResource(
                    id = R.drawable.ic_audio_track),
                contentDescription = "audio track")
}

@Composable
fun VideoAttractiveInfoItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon : Int,
    text:String,
    onClick:()->Unit
                            ) {
    Column (
        modifier = Modifier.clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Icon(painter = painterResource(id = icon), contentDescription ="Icon"
        , modifier = Modifier.size(30.dp), tint = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text,style=MaterialTheme.typography.bodyMedium.copy(color = Color.White))
    }
}



@Composable
fun SideBarView(
    modifier: Modifier = Modifier,
    onAvatarClick: ()->Unit,
    onLikeClick: ()->Unit,
    onChatClick: ()->Unit,
    onSaveClick: ()->Unit,
    onShareClick: ()->Unit,
) {
    Column(
        modifier = modifier.wrapContentHeight(),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
     AvatarView() {
         onAvatarClick()
     }
        Spacer(modifier = Modifier.size(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_heart, text = "1.6M" ) {
            onLikeClick()
        }
        Spacer(modifier = Modifier.size(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_chat, text = "1.6M" ) {
            onChatClick()
        }
        Spacer(modifier = Modifier.size(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_bookmark, text = "1.6M" ) {
            onSaveClick()
        }
        Spacer(modifier = Modifier.size(16.dp))
        VideoAttractiveInfoItem(icon = R.drawable.ic_share, text = "1.6M" ) {
            onShareClick()
        }
        Spacer(modifier = Modifier.size(16.dp))
        AudioTrackView()
    }
}