package com.ahmed.newpro.API;

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
//https://6sigma.site/
        const val BASE_URL = "http://192.168.1.12:3000/"
        private var retrofit: Retrofit? = null

        val getClient: ApiInterface
                get() {
                if (retrofit == null) {
                        retrofit = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                }
                        return retrofit!!.create(ApiInterface::class.java)
        }

}
