package com.jop.cointracker.view.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jop.cointracker.ui.component.ItemCoin
import com.jop.cointracker.ui.component.ItemCoinLoading
import com.jop.cointracker.view.home.viewModel.HomeViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(navHostController: NavHostController, homeVM: HomeViewModel = hiltViewModel()){
    val stateData = homeVM.state.value
    val refreshState = rememberPullToRefreshState()

    Box(modifier = Modifier.fillMaxSize()){
        PullToRefreshBox(
            isRefreshing = stateData.isLoading,
            state = refreshState,
            indicator = {
                Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = stateData.isLoading,
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    color = MaterialTheme.colorScheme.onSecondary,
                    state = refreshState
                )
            },
            onRefresh = {
                homeVM.getCoins(refresh = true)
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight(),
                userScrollEnabled = !stateData.isLoading
            ) {
                if(stateData.isLoading){
                    items(count = 20) {
                        ItemCoinLoading()
                    }
                } else {
                    items(stateData.data?.size ?: 0){ index ->
                        val coin = stateData.data?.get(index)

                        if(index == (stateData.data?.lastIndex?.minus(5) ?: 0) && !stateData.isPaginationLoading) {
                            homeVM.getCoins()
                        }

                        if(coin != null){
                            ItemCoin(coin = coin) {

                            }
                        }
                    }
                }

                if(stateData.isPaginationLoading){
                    items(count = 3) {
                        ItemCoinLoading()
                    }
                }
            }
        }
    }
}