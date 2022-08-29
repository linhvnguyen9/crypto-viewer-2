package com.linh.cryptoviewer2.data.remote.service

import com.linh.cryptoviewer2.data.remote.model.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search")
    suspend fun search(@Query("query") query: String): SearchResponse
}