package com.currencyconversion.rest

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * Created by Sambhaji Karad on 25/09/2018
*/

interface ApiInterface {

    /*convert selected currency to other*/
    @GET("latest?")
    fun getCurrencyConvert(@Query("base") base: String,
            @Query("symbols") symbols: String): Call<String>
}