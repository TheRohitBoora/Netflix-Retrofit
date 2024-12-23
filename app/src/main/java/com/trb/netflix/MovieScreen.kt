package com.trb.netflix

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen() {
    val retroFitInstance = RetrofitHelper.getInstance()
    val retrofitRepo = RetroRepo(retroFitInstance)

    // Collecting movie data states
    val onlyOnNetflix by retrofitRepo.onlyOnNetflix.collectAsState()
    val blockBuster by retrofitRepo.blockBuster.collectAsState()
    val trendingNow by retrofitRepo.trendingNow.collectAsState()
    val watchlist by retrofitRepo.watchlist.collectAsState()

    LaunchedEffect(Unit) {
        retrofitRepo.getData()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Image(painter = painterResource(R.drawable.netflix_icon), "Netflix Icon")
                },
                actions = {
                    Icon(Icons.Default.Search, "Search", tint = Color.White)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        containerColor = Color.Black,
        content = { innerPadding ->
            Column(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(112.dp).padding(innerPadding))

                if (onlyOnNetflix.isEmpty()) {
                    Text("Loading...", color = Color.White)
                } else {
                    Text(
                        text = "Only On Netflix",
                        fontWeight = FontWeight.Light,
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(top = 12.dp, start = 6.dp)
                    )

                    LazyRow {
                        items(onlyOnNetflix) { list ->
                            Card(
                                onClick = {},
                                modifier = Modifier.padding(end = 8.dp, top = 16.dp),
                                elevation = CardDefaults.cardElevation(disabledElevation = 5.dp),
                                border = BorderStroke(1.dp, Color.White)
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(list.image)
                                        .crossfade(true)
                                        .build(),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.size(165.dp, 210.dp)
                                )
                            }
                        }
                    }

                    NetflixLayout("Block Buster", blockBuster)
                    NetflixLayout("Popular Now", trendingNow)
                    NetflixLayout("Watch List", watchlist)
                }
            }
        }
    )
}


@Composable
fun NetflixLayout(title: String, moviesList: List<Movies>) { // Use List<Movies> instead
    Spacer(Modifier.padding(top = 4.dp))
    Text(text = title,
        fontWeight = FontWeight.Light,
        fontSize = 20.sp,
        color = Color.White,
        modifier = Modifier.padding(top = 12.dp, start = 6.dp))

    LazyRow {
        items(moviesList) { list ->
            Card(
                onClick = {},
                modifier = Modifier.padding(end = 8.dp, top = 16.dp),
                elevation = CardDefaults.cardElevation(disabledElevation = 5.dp),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(list.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(125.dp, 170.dp)
                    )
                    Image(painter = painterResource(R.drawable.netflix_icon), "Netflix Icon", Modifier.size(28.dp).padding(4.dp))
                }
            }
        }
    }
}



@Preview
@Composable
fun Prev(){
    MovieScreen()
}