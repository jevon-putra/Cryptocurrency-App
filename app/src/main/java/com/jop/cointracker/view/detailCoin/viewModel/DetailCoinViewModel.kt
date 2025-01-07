package com.jop.cointracker.view.detailCoin.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jop.cointracker.data.BaseState
import com.jop.cointracker.data.Resource
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.data.model.CoinDetail
import com.jop.cointracker.data.model.CoinHistoryPrice
import com.jop.cointracker.data.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailCoinViewModel @Inject constructor(private val repository: CoinRepository): ViewModel() {
    private val _stateDetailCoin = mutableStateOf(BaseState<CoinDetail>())
    val stateDetailCoin : State<BaseState<CoinDetail>> = _stateDetailCoin
    private val _stateCoinHistoryPrice = mutableStateOf(BaseState<List<CoinHistoryPrice>>())
    val stateCoinHistoryPrice : State<BaseState<List<CoinHistoryPrice>>> = _stateCoinHistoryPrice
    private val _percentageIncrease = mutableDoubleStateOf(0.0)
    val percentageIncrease: MutableState<Double> = _percentageIncrease
    val coin: MutableState<Coin?> = mutableStateOf(null)

    fun getCoinDetail(uuid: String){
        repository.getCoinDetail(uuid).onEach {
            when(it){
                is Resource.Loading -> {
                    _stateDetailCoin.value = BaseState(isLoading = true)
                }
                is Resource.Success -> {
                    _stateDetailCoin.value = BaseState(data = it.data)
                }
                is Resource.Error -> {
                    _stateDetailCoin.value = BaseState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getCoinHistoryPrice(timePeriod: String = ""){
        repository.getCoinHistoryPrice(coin.value?.uuid ?: "", timePeriod).onEach {
            when(it){
                is Resource.Loading -> {
                    _stateCoinHistoryPrice.value = BaseState(isLoading = true)
                }
                is Resource.Success -> {
                    _stateCoinHistoryPrice.value = BaseState(data = it.data?.toMutableList())
                    percentageIncrease()
                }
                is Resource.Error -> {
                    _stateCoinHistoryPrice.value = BaseState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun percentageIncrease(){
        val listPrice = _stateCoinHistoryPrice.value.data!!.sortedBy { it.timestamp }.map { it.price }
        val minPrice = listPrice.first()
        val maxPrice = listPrice.last()

        _percentageIncrease.doubleValue = ((maxPrice - minPrice) / minPrice) * 100
    }
}