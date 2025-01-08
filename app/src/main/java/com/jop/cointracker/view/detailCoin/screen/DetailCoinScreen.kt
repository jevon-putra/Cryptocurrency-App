package com.jop.cointracker.view.detailCoin.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.data.model.Tag
import com.jop.cointracker.data.model.getAllTimePeriods
import com.jop.cointracker.ui.component.ChipTag
import com.jop.cointracker.ui.component.CustomToolbar
import com.jop.cointracker.ui.component.LineChart
import com.jop.cointracker.ui.theme.PADDING_HALF
import com.jop.cointracker.ui.theme.PADDING_MAIN
import com.jop.cointracker.view.detailCoin.viewModel.DetailCoinViewModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DetailCoinScreen(navHostController: NavHostController, detailVM: DetailCoinViewModel = hiltViewModel()){
    val format = NumberFormat.getCurrencyInstance(Locale("en", "US"))
    val timePeriodState = remember { mutableStateOf(Tag("24H", "24h")) }
    val df = DecimalFormat("0.##")

    LaunchedEffect(Unit) {
        val coin = navHostController.previousBackStackEntry?.savedStateHandle?.get<Coin>("coin")
        detailVM.coin.value = coin

        detailVM.getCoinHistoryPrice(timePeriodState.value.value)
    }

    Scaffold(
        topBar = {
            CustomToolbar(
                title = "",
                canNavigateBack = true,
                navigateUp = { navHostController.popBackStack() },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(start = PADDING_MAIN, end = PADDING_MAIN, bottom = PADDING_MAIN),
            verticalArrangement = Arrangement.spacedBy(PADDING_MAIN)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(PADDING_HALF)
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = detailVM.coin.value?.name.toString(),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = detailVM.coin.value?.symbol.toString(),
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MaterialTheme.colorScheme.outline
                        )
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = format.format(detailVM.coin.value?.price ?: 0.0),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = "${if(detailVM.percentageIncrease.value > 0) "+" else ""}${df.format(detailVM.percentageIncrease.value)}%",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = if(detailVM.percentageIncrease.value >= 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            if(detailVM.stateCoinHistoryPrice.value.data?.isNotEmpty() == true){
                LineChart(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth(),
                    list = detailVM.stateCoinHistoryPrice.value.data!!.sortedBy { it.timestamp }.map { it.price },
                    isShowDifference = true
                )
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                items(getAllTimePeriods()) { tag ->
                    ChipTag(tag = tag, isSelected = timePeriodState.value == tag) {
                        timePeriodState.value = tag
                        detailVM.getCoinHistoryPrice(timePeriodState.value.value)
                    }
                }
            }
        }
    }
}
