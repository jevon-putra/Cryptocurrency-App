package com.jop.cointracker.view.home.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jop.cointracker.ui.component.ItemCoin
import com.jop.cointracker.view.home.viewModel.HomeViewModel

@Composable
fun HomeScreen(navHostController: NavHostController, homeVM: HomeViewModel = hiltViewModel()){
    val state = homeVM.state.value

    Box(modifier = Modifier.fillMaxSize()){
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.data ?: listOf()){
                ItemCoin(coin = it) {
                    print(it)
                }
            }
        }
    }
}