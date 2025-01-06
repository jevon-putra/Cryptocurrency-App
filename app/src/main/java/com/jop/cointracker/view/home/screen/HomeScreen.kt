package com.jop.cointracker.view.home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jop.cointracker.R
import com.jop.cointracker.data.model.Tag
import com.jop.cointracker.ui.component.EmptyStateScreen
import com.jop.cointracker.ui.component.ErrorStateScreen
import com.jop.cointracker.ui.component.FilterBottomSheet
import com.jop.cointracker.ui.component.ItemCoin
import com.jop.cointracker.ui.component.ItemCoinLoading
import com.jop.cointracker.ui.component.SearchTextField
import com.jop.cointracker.ui.route.Route
import com.jop.cointracker.ui.theme.PADDING_EXTRA
import com.jop.cointracker.ui.theme.PADDING_HALF
import com.jop.cointracker.ui.theme.PADDING_MAIN
import com.jop.cointracker.view.home.viewModel.HomeViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(navHostController: NavHostController, homeVM: HomeViewModel = hiltViewModel()){
    val etSearch = remember { mutableStateOf("") }
    val stateData = homeVM.state.value
    val focusManager = LocalFocusManager.current
    val refreshState = rememberPullToRefreshState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val stateSelectedTags = remember { mutableStateListOf<Tag>() }
    val stateBottomSheet = rememberModalBottomSheetState()

    Box(modifier = Modifier
        .fillMaxSize()
        .windowInsetsPadding(WindowInsets.safeContent)
    ){
        Column {
            Row(
                modifier = Modifier
                    .padding(PADDING_MAIN)
                    .height(IntrinsicSize.Min) ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(PADDING_HALF)
            ) {
                SearchTextField(
                    modifier = Modifier.weight(1f),
                    state = etSearch,
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            homeVM.getCoins(refresh = true, keyword = etSearch.value, stateSelectedTags.toList())
                        }
                    )
                )
                Image(
                    modifier = Modifier
                        .width(48.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(PADDING_EXTRA))
                        .clickable {
                            showBottomSheet.value = true
                        }
                        .padding(PADDING_HALF),
                    painter = painterResource(id =  R.drawable.ic_filter),
                    contentDescription = "Archive Note",
                    colorFilter = ColorFilter.tint(
                        color = MaterialTheme.colorScheme.outline
                    )
                )
            }
            PullToRefreshBox(
                modifier = Modifier.weight(1f),
                isRefreshing = stateData.isLoading,
                state = refreshState,
                indicator = {
                    Indicator(
                        modifier = Modifier.align(Alignment.TopCenter),
                        isRefreshing = stateData.isLoading,
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        state = refreshState
                    )
                },
                onRefresh = {
                    homeVM.getCoins(refresh = true, keyword = etSearch.value, stateSelectedTags.toList())
                }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = !stateData.isLoading,
                    verticalArrangement = if(stateData.data.isNullOrEmpty()) Arrangement.Center else Arrangement.Top
                ) {
                    focusManager.clearFocus()

                    if(stateData.error.isNotEmpty()){
                        item {
                            ErrorStateScreen(
                                modifier = Modifier,
                                message = stateData.error
                            )
                        }
                    } else if(!stateData.isLoading && stateData.data.isNullOrEmpty()){
                        item {
                            EmptyStateScreen(
                                title = "Ups!... no results found",
                                message = "Please try another search"
                            )
                        }
                    }

                    if(stateData.isLoading){
                        items(count = 20) {
                            ItemCoinLoading()
                        }
                    } else {
                        stateData.data?.size?.let {
                            items(it){ index ->
                                val coin = stateData.data?.get(index)

                                if(index == stateData.data!!.lastIndex.minus(5) && !stateData.isPaginationLoading && stateData.data!!.size >= homeVM.maxItem) {
                                    homeVM.getCoins()
                                }

                                if(coin != null){
                                    ItemCoin(coin = coin) {
                                        navHostController.currentBackStackEntry?.savedStateHandle?.set("coin", coin)
                                        navHostController.navigate(Route.DETAIL_COIN)
                                    }
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

        if(showBottomSheet.value){
            FilterBottomSheet(
                state = stateBottomSheet,
                showBottomSheet = showBottomSheet,
                stateSelectedTags = stateSelectedTags,
                confirmOrClearAction = { isConfirm ->
                    if(!isConfirm) stateSelectedTags.clear()
                    homeVM.getCoins(refresh = true, keyword = etSearch.value, stateSelectedTags.toList())
                },
            )
        }
    }
}