package com.cookbook.network

import retrofit2.http.GET
import retrofit2.http.Header

interface RecipeService {
    @GET("search")
    suspend fun search(
        @Header("Authorization") token: String
    )
}