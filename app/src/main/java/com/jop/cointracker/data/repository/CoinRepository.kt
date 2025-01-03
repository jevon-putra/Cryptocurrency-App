package com.jop.cointracker.data.repository

import com.jop.cointracker.data.Resource
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.network.api.CoinAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepository @Inject constructor(private val coinAPI: CoinAPI) {

    fun getCoins(offset: Int) : Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val request = coinAPI.getCoins(offset)
            emit(Resource.Success(data = request.data ?: mutableListOf()))
        } catch (e: Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

}