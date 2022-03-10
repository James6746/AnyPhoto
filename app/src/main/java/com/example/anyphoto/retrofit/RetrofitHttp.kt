package com.example.anyphoto.retrofit

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHttp {
    var IS_TESTER = true

    var SERVER_DEVELOPMENT = "https://api.unsplash.com/"
    var SERVER_PRODUCTION = "https://api.unsplash.com/"

    var client = OkHttpClient.Builder().addInterceptor { chain ->
        val newRequest: Request = chain.request().newBuilder()
            .addHeader(
                "Authorization",
                "Client-ID 31qSvVnx9tvAGWfvNOUMtCVfywfkYPQGv51zR_EPLh4"
            )
            .build()
        chain.proceed(newRequest)
    }.build()

    var retrofit: Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(server())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var retrofitService: RetrofitService = retrofit.create(RetrofitService::class.java)

    private fun server(): String {
        return if (IS_TESTER) SERVER_DEVELOPMENT else SERVER_PRODUCTION
    }
}
