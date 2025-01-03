package com.jop.cointracker.view.home.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jop.cointracker.data.BaseState
import com.jop.cointracker.data.Resource
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.data.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel(){
    private val _state = mutableStateOf(BaseState<List<Coin>>())
    val state : State<BaseState<List<Coin>>> = _state

    init {
        getCoins()
    }

    fun getCoins(){
        repository.getCoins(_state.value.data?.lastIndex ?: 0).onEach {
            when(it){
                is Resource.Loading -> {
                    _state.value = BaseState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = BaseState(data = it.data)
                }
                is Resource.Error -> {
                    _state.value = BaseState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}