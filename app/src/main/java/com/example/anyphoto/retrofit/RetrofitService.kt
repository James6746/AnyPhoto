package com.example.anyphoto.retrofit


import com.example.anyphoto.model.photos.Photo
import com.example.anyphoto.model.photos.User
import com.example.anyphoto.model.search.SearchPhoto
import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

//    @GET("{photoType}?per_page=25")
//    fun listPost(@Path("photoType") photoType: String): Call<ArrayList<Photo>>?

    @GET("topics/{id}/photos}?per_page=25")
    fun listTopicPost(@Path("id") id: String, @Query("page") page: Int): Call<ArrayList<Photo>>?

    @GET("search/photos?per_page=25")
    fun listSearchPhotos(@Query("page") page: Int, @Query("query") query: String): Call<SearchPhoto>?

    @GET("{photoType}?per_page=25")
    fun listPost(@Path("photoType") photoType: String, @Query("page") page: Int): Call<ArrayList<Photo>>?

    @GET("topics")
    fun listTopics(): Call<ArrayList<Photo>>?

    @GET("photos/{id}")
    fun getSingle(@Path("id") id: Int): Call<Photo>?
/*
    @POST("users")
    fun cretueUser(@Body user: User)

    @FormUrlEncoded
    fun login(@Field("username") username: String)*/
}