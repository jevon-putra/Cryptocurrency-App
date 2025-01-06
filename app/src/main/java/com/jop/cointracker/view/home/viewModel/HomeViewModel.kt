package com.jop.cointracker.view.home.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jop.cointracker.data.BaseState
import com.jop.cointracker.data.Resource
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.data.model.Tag
import com.jop.cointracker.data.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: CoinRepository) : ViewModel(){
    private val _state = mutableStateOf(BaseState<MutableList<Coin>>())
    val state : State<BaseState<MutableList<Coin>>> = _state
    val maxItem = 20

    init {
        getCoins(true)
    }

    fun getCoins(refresh: Boolean = false, keyword: String = "", tags: List<Tag> = listOf()){
        val lastIndex = if(refresh) 0 else _state.value.data!!.size

        repository.getCoins(limit = maxItem, offset = lastIndex, symbol = keyword, tags = tags.map { it.value }).onEach {
            when(it){
                is Resource.Loading -> {
                    if(refresh) _state.value = BaseState(isLoading = true)
                    else _state.value = BaseState(isPaginationLoading = true, data = _state.value.data)
                }
                is Resource.Success -> {
                    if(refresh) _state.value = BaseState(data = it.data?.toMutableList())
                    else _state.value = BaseState(data = _state.value.data?.apply { addAll(it.data ?: mutableListOf()) })
                }
                is Resource.Error -> {
                    _state.value = BaseState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}