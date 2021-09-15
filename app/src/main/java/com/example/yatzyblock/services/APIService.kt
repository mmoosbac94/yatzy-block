package com.example.yatzyblock.services

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.PUT


private const val baseURL = "http://10.0.2.2:8080/api/"

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(baseURL)
    .build()


interface APIService {

    @PUT("update")
    suspend fun updatePlayers(@Body requestBody: RequestBody): Response<ResponseBody>
}


object PlayerAPI {
    val retrofitService: APIService by lazy {
        retrofit.create(APIService::class.java)
    }

}