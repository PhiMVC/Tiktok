package com.mvc.tiktok.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
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
    val scrollPage: (Boolean)-> Unit = {isForYou->
    val page :Int = if(isForYou) 1 else 0
        coroutineScope.launch {
            pagerState.scrollToPage(page = page)

        }
    }

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
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
                2 -> ProfileScreen()
                else -> ListForYouVideoScreen()
            }
        }
        TabContentView(
            isTabSelectedIndex = pagerState.currentPage,
            modifier = Modifier.constrainAs(tabContentView){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }.background(color = Color.Blue),
            onSelectedTab = {page->
                scrollPage(page)
            })
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
    val thickness : Dp = if (isSelected)  2.dp else 0.dp
    Column(modifier = modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title,style = MaterialTheme.typography.h6.copy(color = Color.White.copy(alpha = alpha)))
        Spacer(modifier = Modifier.height(2.dp))
        Divider(color = Color.White, thickness = thickness, modifier = Modifier.width(50.dp))
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


