package com.currencyconversion.rest

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

/*
 * Created by Sambhaji Karad on 25/09/2018
*/

object ApiClient {

    //Server URL
    private const val BASE_URL = "https://api.exchangeratesapi.io/"

    private var retrofit: Retrofit? = null
    private var retrofitString: Retrofit? = null

    private val gson = GsonBuilder()
            .setLenient()
            .create()

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
        }
        return retrofit
    }

    fun getClientString(): Retrofit? {
        if (retrofitString == null) {
            retrofitString = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
        }
        return retrofitString
    }
}
