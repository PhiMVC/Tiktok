package com.mvc.tiktok.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.mvc.tiktok.R
import com.mvc.tiktok.ui.following.FollowingScreen
import com.mvc.tiktok.ui.for_you.ListForYouVideoScreen
import com.mvc.tiktok.ui.user.ProfileScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = 3, initialPage = 1)
    val scrollPage: (Boolean)-> Unit = { isForYou->
             val page :Int = if(isForYou) 1 else 0
             coroutineScope.launch {
             pagerState.scrollToPage(page = page)
        }
    }
    var isShowTabContent  : Boolean by remember {
        mutableStateOf(true)
    }

    val toggleTabContent :(Boolean) -> Unit = { isShow: Boolean->
      if(isShowTabContent != isShow){
          isShowTabContent = isShow
      }
    }

    LaunchedEffect(key1 = pagerState){
        snapshotFlow {
            pagerState.currentPage
        }.collect(){page ->
            if(page == 2){
                toggleTabContent(false)
            }else{
                toggleTabContent(true)
            }
        }
    }

    Scaffold(
        modifier = Modifier,
        bottomBar =
        {
            TikTokBottomAppBar(
                onOpenHome= {},
                onAddVideo={},
            )
        }
        ){paddingValue ->
        ConstraintLayout(
            modifier = Modifier.fillMaxSize().padding(paddingValue)
        ) {
            val(tabContentView, body ) = createRefs()
            HorizontalPager(state = pagerState, modifier = Modifier.constrainAs(body){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }) {page ->
                when(page){
                    0 -> FollowingScreen()
                    1 -> ListForYouVideoScreen()
                    2 -> ProfileScreen()
                }
            }

            AnimatedVisibility(visible = isShowTabContent) {
                TabContentView(
                    isTabSelectedIndex = pagerState.currentPage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(tabContentView) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                    ,
                    onSelectedTab = {page->
                        scrollPage(page)
                    })
            }



        }
    }


}

@Composable
fun TikTokBottomAppBar(
    onOpenHome:()->Unit,
    onAddVideo:()-> Unit,

) {
    BottomAppBar(
        backgroundColor = Color.Black, contentColor = Color.White
    ) {
        BottomNavigationItem(
            icon = {
                   Icon(painterResource(id = R.drawable.ic_home),"Home")
            },
            label = {
                    Text(text = "Home")
            },
            selected = true,
            onClick = { /*TODO*/ })

        BottomNavigationItem(
            icon = {
                Icon(painterResource(id = R.drawable.ic_now),"Friends")
            },
            label = {
                Text(text = "Friends")
            },
            selected = false,
            onClick = { /*TODO*/ })

        BottomNavigationItem(
            icon = {
                Icon(painterResource(id = R.drawable.ic_add_video),"Add More Video")
            },

            selected = false,
            onClick = { /*TODO*/ })

        BottomNavigationItem(
            icon = {
                Icon(painterResource(id = R.drawable.ic_inbox),"Inbox Icon")
            },
            label = {
                Text(text = "Inbox")
            },
            selected = false,
            onClick = { /*TODO*/ })

        BottomNavigationItem(
            icon = {
                Icon(painterResource(id = R.drawable.ic_profile),"Profile")
            },
            label = {
                Text(text = "Profile")
            },
            selected = false,
            onClick = { /*TODO*/ })
    }
}


@Composable
fun TabContentItemView(
    modifier: Modifier = Modifier,
    title:String,
    isSelected: Boolean,
    isForYou: Boolean,
    onSelectedTab:(isForYou: Boolean)->Unit
) {
    val alpha : Float = if (isSelected)  1f else 0.6f

    Column(modifier = modifier
        .wrapContentSize()
        .clickable
        { onSelectedTab(isForYou) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title,style = MaterialTheme.typography.h6.copy(color = Color.White.copy(alpha = alpha)))
        Spacer(modifier = Modifier.height(2.dp))
        if(isSelected){
            Divider(color = Color.White, thickness = 2.dp, modifier = Modifier.width(50.dp))
        }
    }
}

@Composable
fun TabContentView(
    modifier: Modifier = Modifier,
    isTabSelectedIndex: Int,
    onSelectedTab: (isForYou: Boolean) -> Unit
) {
    ConstraintLayout(modifier = modifier) {
        val (tabContent,imgSearch) = createRefs()
        Row (modifier = Modifier
            .wrapContentSize()
            .constrainAs(tabContent) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }){


            TabContentItemView(
                title = "Following",
                isSelected = isTabSelectedIndex == 0,
                isForYou = false,
                onSelectedTab = onSelectedTab )
            Spacer(modifier = Modifier.width(12.dp))
            TabContentItemView(
                title = "For You",
                isSelected = isTabSelectedIndex == 1,
                isForYou = true,
                onSelectedTab = onSelectedTab )
        }
        IconButton(onClick = { /*TODO*/ },modifier = Modifier.constrainAs(imgSearch){
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end, margin = 16.dp)
        }) {
            Icon(
                Icons.Default.Search,
                contentDescription = "Icon Search",
                modifier = Modifier.size(24.dp),
                tint = Color.White)
        }
    }
}


