package com.jop.cointracker.view.detailCoin.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jop.cointracker.R
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.data.model.Tag
import com.jop.cointracker.data.model.getAllTimePeriods
import com.jop.cointracker.ui.component.ChipTag
import com.jop.cointracker.ui.component.CustomToolbar
import com.jop.cointracker.ui.component.LineChart
import com.jop.cointracker.ui.component.SocialCoinBottomSheet
import com.jop.cointracker.ui.theme.CoinTrackerTheme
import com.jop.cointracker.ui.theme.PADDING_HALF
import com.jop.cointracker.ui.theme.PADDING_MAIN
import com.jop.cointracker.util.formatCurrencyNumber
import com.jop.cointracker.util.formatSupplyCrypto
import com.jop.cointracker.util.formatNumberShort
import com.jop.cointracker.util.shimmerBackground
import com.jop.cointracker.view.detailCoin.viewModel.DetailCoinViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCoinScreen(navHostController: NavHostController, detailVM: DetailCoinViewModel = hiltViewModel()){
    val timePeriodState = remember { mutableStateOf(Tag("24H", "24h")) }
    val showBottomSheet = remember { mutableStateOf(false) }
    val stateBottomSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val df = DecimalFormat("0.##")

    LaunchedEffect(Unit) {
        val coin = navHostController.previousBackStackEntry?.savedStateHandle?.get<Coin>("coin")
        detailVM.coin.value = coin

        detailVM.getCoinDetail()
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
                .verticalScroll(rememberScrollState())
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
                        text = (detailVM.coin.value?.price ?: 0.0).formatCurrencyNumber(),
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

            Column(
                verticalArrangement = Arrangement.spacedBy(PADDING_MAIN)
            ) {
                LineChart(
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                        .shimmerBackground(detailVM.stateCoinHistoryPrice.value.isLoading),
                    list = detailVM.stateCoinHistoryPrice.value.data?.sortedBy { it.timestamp }?.map { it.price } ?: listOf(),
                    isShowDifference = true
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    items(getAllTimePeriods()) { tag ->
                        ChipTag(
                            tag = tag,
                            isSelected = timePeriodState.value == tag,
                            isLoading = detailVM.stateCoinHistoryPrice.value.isLoading
                        ) {
                            if(!detailVM.stateCoinHistoryPrice.value.isLoading){
                                timePeriodState.value = tag
                                detailVM.getCoinHistoryPrice(timePeriodState.value.value)
                            }
                        }
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(PADDING_HALF)
            ) {
                Text(
                    modifier = Modifier.shimmerBackground(detailVM.stateDetailCoin.value.isLoading),
                    text = "What is ${detailVM.coin.value?.name}?",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    text = detailVM.stateDetailCoin.value.data?.description ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    modifier = Modifier
                        .shimmerBackground(detailVM.stateDetailCoin.value.isLoading)
                        .clickable {
                            if (!detailVM.stateDetailCoin.value.isLoading) {
                                showBottomSheet.value = true
                            }
                        },
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Socials for ${detailVM.coin.value?.symbol}",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        ),
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = ""
                    )
                }

                Text(
                    modifier = Modifier
                        .shimmerBackground(detailVM.stateDetailCoin.value.isLoading)
                        .padding(top = PADDING_HALF),
                    text = "Statistics",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                ItemStatistic(
                    icon = R.drawable.ic_trophy,
                    title = "Rank",
                    value = if(detailVM.stateDetailCoin.value.isLoading) "#0" else "#${detailVM.stateDetailCoin.value.data?.rank}",
                    isLoading = detailVM.stateDetailCoin.value.isLoading
                )

                ItemStatistic(
                    icon = R.drawable.ic_pie_chart,
                    title = "Market Cap",
                    value = if(detailVM.stateDetailCoin.value.isLoading) "0.00" else "$${detailVM.stateDetailCoin.value.data?.marketCap?.formatNumberShort()}" ,
                    isLoading = detailVM.stateDetailCoin.value.isLoading
                )

                ItemStatistic(
                    icon = R.drawable.ic_bar_chart,
                    title = "Trading Volume",
                    value = if(detailVM.stateDetailCoin.value.isLoading) "0.00" else "$${detailVM.stateDetailCoin.value.data?.hVolume?.formatNumberShort()}" ,
                    isLoading = detailVM.stateDetailCoin.value.isLoading
                )

                ItemStatistic(
                    icon = R.drawable.ic_all_time_high,
                    title = "All Time High",
                    value = if(detailVM.stateDetailCoin.value.isLoading) "0.00" else "${detailVM.stateDetailCoin.value.data?.allTimeHigh?.price?.formatCurrencyNumber()}" ,
                    isLoading = detailVM.stateDetailCoin.value.isLoading
                )

                ItemStatistic(
                    icon = R.drawable.ic_paper,
                    title = "Exchange listings",
                    value = if(detailVM.stateDetailCoin.value.isLoading) "0.00" else "${detailVM.stateDetailCoin.value.data?.numberOfExchanges}" ,
                    isLoading = detailVM.stateDetailCoin.value.isLoading
                )

                Text(
                    modifier = Modifier
                        .shimmerBackground(detailVM.stateDetailCoin.value.isLoading)
                        .padding(top = PADDING_HALF),
                    text = "Supply",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                ItemStatistic(
                    title = "Circulating Supply",
                    value = if(detailVM.stateDetailCoin.value.isLoading) "0.00" else "${detailVM.stateDetailCoin.value.data?.supply?.circulating?.formatSupplyCrypto()} ${detailVM.coin.value?.symbol}",
                    isLoading = detailVM.stateDetailCoin.value.isLoading
                )

                ItemStatistic(
                    title = "Total Supply",
                    value = if(detailVM.stateDetailCoin.value.isLoading) "0.00" else "${detailVM.stateDetailCoin.value.data?.supply?.total?.formatSupplyCrypto()} ${detailVM.coin.value?.symbol}",
                    isLoading = detailVM.stateDetailCoin.value.isLoading
                )

                ItemStatistic(
                    title = "Max Supply",
                    value = if(detailVM.stateDetailCoin.value.isLoading) "0.00" else "${detailVM.stateDetailCoin.value.data?.supply?.max?.formatSupplyCrypto()} ${detailVM.coin.value?.symbol}",
                    isLoading = detailVM.stateDetailCoin.value.isLoading
                )
            }
        }

        if(showBottomSheet.value){
            SocialCoinBottomSheet(
                state = stateBottomSheet,
                showBottomSheet = showBottomSheet,
                coinSymbol = detailVM.coin.value?.symbol.toString(),
                socialsLink = detailVM.stateDetailCoin.value.data?.links ?: listOf()
            )
        }
    }
}

@Composable
fun ItemStatistic(@DrawableRes icon: Int? = null, title: String, value: String, isLoading: Boolean){
    Row(
        modifier = Modifier
            .padding(vertical = PADDING_HALF)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(PADDING_MAIN)
    ) {
        if(icon != null){
            Image(
                modifier = Modifier
                    .shimmerBackground(isLoading)
                    .width(24.dp),
                painter = painterResource(icon),
                colorFilter = ColorFilter.tint(color = if(isLoading) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface),
                contentDescription = "",
            )
        }
        Text(
            modifier = Modifier
                .shimmerBackground(isLoading)
                .weight(1f),
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if(isLoading) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface,
            )
        )
        Text(
            modifier = Modifier.shimmerBackground(isLoading),
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if(isLoading) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onSurface,
            )
        )
    }
}

@Preview
@Composable
fun PreviewItemStatic(){
    CoinTrackerTheme {
        Surface {
            ItemStatistic(icon = R.drawable.ic_trophy, title = "Rank", value = (123450.0).formatNumberShort(), isLoading = true)
        }
    }
}
