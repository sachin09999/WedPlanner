package com.sachin.wedplanner.presentation.utils

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.sachin.wedplanner.domain.model.Banner
import com.valentinilk.shimmer.shimmer // Import the shimmer modifier

@Composable
fun BannerSlideshow(banners: List<Banner>) {
    if (banners.isEmpty()) return

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { banners.size }
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    ) { page ->
        val banner = banners[page]
        val painter = rememberAsyncImagePainter(model = banner.imageUrl)
        val isLoading = painter.state is AsyncImagePainter.State.Loading
        Log.d("BannerSlideshow", "Loading banner image URL: ${banner.imageUrl}")

        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF6F8)),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (isLoading) Modifier.shimmer() else Modifier
                    )
            ) {
                AsyncImage(
                    model = banner.imageUrl,
                    contentDescription = banner.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}