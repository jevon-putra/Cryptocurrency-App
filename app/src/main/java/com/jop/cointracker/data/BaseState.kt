package com.jop.cointracker.data

data class BaseState<T>(
        var data: T? = null,
        var isLoading: Boolean = false,
        var isPaginationLoading: Boolean = false,
        var error: String = "",
)