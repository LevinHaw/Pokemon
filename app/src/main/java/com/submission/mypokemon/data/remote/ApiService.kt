package com.submission.mypokemon.data.remote

import com.submission.mypokemon.data.response.DetailResponse
import com.submission.mypokemon.data.response.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon")
    fun getAllPokemon(
        @Query("limit") limit: Int = 100
    ): Call<PokemonResponse>

    @GET("pokemon/{name}")
    fun detailPokemon(
        @Path("name") name: String
    ): Call<DetailResponse>

}