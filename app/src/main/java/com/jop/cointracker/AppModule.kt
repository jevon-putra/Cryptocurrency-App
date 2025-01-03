package com.jop.cointracker

import com.jop.cointracker.data.repository.CoinRepository
import com.jop.cointracker.network.api.CoinAPI
import com.jop.cointracker.network.client.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private fun buildRetrofit (): Retrofit {
        return NetworkModule.createRetrofit()
    }

    @Provides
    @Singleton
    fun buildCoinAPI(): CoinAPI {
        return buildRetrofit().create(CoinAPI::class.java)
    }

    @Provides
    @Singleton
    fun buildCoinRepository(coinAPI: CoinAPI): CoinRepository {
        return CoinRepository(coinAPI)
    }
}