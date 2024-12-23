package com.trb.netflix

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RetroRepo(val retrofitHelper: RetrofitHelper) {
    var onlyOnNetflix = MutableStateFlow<ArrayList<Movies>>(arrayListOf())
    var blockBuster = MutableStateFlow<ArrayList<Movies>>(arrayListOf())
    var trendingNow = MutableStateFlow<ArrayList<Movies>>(arrayListOf())
    var watchlist = MutableStateFlow<ArrayList<Movies>>(arrayListOf())
    fun getData() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val moviesList = retrofitHelper.movieList() // Directly fetch the list from Retrofit
                val allMovieList = arrayListOf<Movies>()
                val only = arrayListOf<Movies>()
                val block = arrayListOf<Movies>()
                val trend = arrayListOf<Movies>()
                val watch = arrayListOf<Movies>()

                // Distribute the movies into different categories
                for (i in 1..10) only.add(moviesList[i])
                onlyOnNetflix.value = only
                allMovieList.clear()

                for (i in 11..20) block.add(moviesList[i])
                blockBuster.value = block
                allMovieList.clear()

                for (i in 21..30) trend.add(moviesList[i])
                trendingNow.value = trend
                allMovieList.clear()

                for (i in 31..40) watch.add(moviesList[i])
                watchlist.value = watch
            } catch (e: Exception) {
                // Handle exceptions (e.g., network failure, parsing failure)
            }
        }
    }

}

