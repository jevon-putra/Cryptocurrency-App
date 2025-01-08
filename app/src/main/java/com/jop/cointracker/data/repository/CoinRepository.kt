package com.jop.cointracker.data.repository

import com.jop.cointracker.data.Resource
import com.jop.cointracker.data.model.Coin
import com.jop.cointracker.data.model.CoinDetail
import com.jop.cointracker.data.model.CoinHistoryPrice
import com.jop.cointracker.network.api.CoinAPI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoinRepository @Inject constructor(private val coinAPI: CoinAPI) {

    fun getCoins(limit: Int, offset: Int, symbol: String = "", tags: List<String> = listOf()) : Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val request = coinAPI.getCoins(limit, offset, if(symbol.isNotEmpty()) listOf(symbol) else listOf(), tags)
            emit(Resource.Success(data = request.data?.coins ?: mutableListOf()))
        } catch (e: Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun getCoinDetail(uuid: String) : Flow<Resource<CoinDetail?>> = flow {
        try {
            emit(Resource.Loading())
            val request = coinAPI.getCoinDetail(uuid)
            emit(Resource.Success(data = request.data?.coin))
        } catch (e: Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    fun getCoinHistoryPrice(uuid: String, timePeriod: String) : Flow<Resource<List<CoinHistoryPrice>?>> = flow {
        try {
            emit(Resource.Loading())
            val request = coinAPI.getCoinHistoryPrice(uuid, timePeriod)
            emit(Resource.Success(data = request.data?.history))
        } catch (e: Exception){
            emit(Resource.Error(message = e.message.toString()))
        }
    }

}