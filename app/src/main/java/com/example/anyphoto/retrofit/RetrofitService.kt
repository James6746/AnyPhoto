package com.example.anyphoto.retrofit

import com.example.anyphoto.model.photos.Photo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface RetrofitService {

//    @GET("{photoType}?per_page=25")
//    fun listPost(@Path("photoType") photoType: String): Call<ArrayList<Photo>>?

    @GET("topics/{id}/photos}?per_page=25")
    fun listTopicPost(@Path("id") id: String, @Query("page") page: Int): Call<ArrayList<Photo>>?

    @GET("{photoType}?per_page=25")
    fun listPost(@Path("photoType") photoType: String, @Query("page") page: Int): Call<ArrayList<Photo>>?

    @GET("topics")
    fun listTopics(): Call<ArrayList<Photo>>?

    @GET("photos/{id}")
    fun getSingle(@Path("id") id: Int): Call<Photo>?
}