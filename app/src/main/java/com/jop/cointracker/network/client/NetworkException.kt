package com.jop.cointracker.network.client

import java.io.IOException

class NetworkException(message: String?, responseCode: Int) : IOException(message) {
    var responseCode = -1

    init {
        this.responseCode = responseCode
    }
}