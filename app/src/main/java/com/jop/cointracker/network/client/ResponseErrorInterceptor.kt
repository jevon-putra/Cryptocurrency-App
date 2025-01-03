package com.jop.cointracker.network.client

import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ResponseErrorInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val response: Response = try {
            chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            throw NetworkException("Oops, Koneksi Terputus", 401)
        } catch (e: UnknownHostException) {
            throw NetworkException("Oops, Koneksi Terputus", 401)
        }
        if (!response.isSuccessful) {
            var message = "Oops, cek koneksi internetmu."
            if (response.code in 400..499) {
                if (response.body != null) {
                    val responseBody = response.body
                    if (responseBody!!.contentLength() < Int.MAX_VALUE) {
                        message = parseResponse(responseBody.string())
                    }
                }
                throw NetworkException(message, response.code)
            } else if (response.code in 500..599) {
                if (response.body != null) {
                    val responseBody = response.body
                    if (responseBody!!.contentLength() < Int.MAX_VALUE) {
                        message = parseResponse(responseBody.string())
                    }
                }
                throw NetworkException(message, response.code)
            }
        }
        return response
    }

    private fun parseResponse(jsonString: String?): String {
        var errorMessage = "Unknown"
        try {
            val jsonObject = JSONObject(jsonString)
            if (jsonObject.has("message")) {
                errorMessage = jsonObject.getString("message")
            }
            if (jsonObject.has("data")) {
                val jsonObjectData = jsonObject.getJSONObject("data")
                if (jsonObjectData.has("errors")) {
                    val sb = StringBuilder()
                    val jsonArrayErrors = jsonObjectData.getJSONArray("errors")
                    for (i in 0 until jsonArrayErrors.length()) {
                        val error = jsonArrayErrors.getJSONObject(i)
                        val message = if (error.has("message")) error.getString("message") else ""
                        if (message.isNotEmpty()) {
                            sb.append(if (i == 0) "" else "\n").append(message)
                        }
                    }
                    errorMessage = sb.toString()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            return "Gagal terhubung ke server. Cek koneksimu ya...."
        }
        return errorMessage
    }
}
