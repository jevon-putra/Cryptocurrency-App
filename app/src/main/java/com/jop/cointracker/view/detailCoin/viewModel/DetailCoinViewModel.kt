package com.jop.cointracker.view.detailCoin.viewModel

import androidx.lifecycle.ViewModel
import com.jop.cointracker.data.repository.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailCoinViewModel @Inject constructor(repo: CoinRepository): ViewModel() {

}