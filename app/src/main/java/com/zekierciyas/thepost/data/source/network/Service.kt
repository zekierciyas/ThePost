package com.zekierciyas.thepost.data.source.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface PostsService {
    @GET("photos")
    fun getAllPhotosAsync(): Deferred<List<NetworkPhoto>>

    @GET("posts")
    fun getAllPostsAsync(): Deferred<List<NetworkPost>>

    @GET("comments")
    fun getAllCommentsAsync(): Deferred<List<NetworkComment>>

    @GET("comments")
    fun getCommentsByPostIdAsync(@Query("postId") postId: String): Deferred<List<NetworkComment>>

    @GET("photos")
    fun getPhotosByPostIdAsync(@Query("postId") postId: String): Deferred<List<NetworkPhoto>>
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

object Network {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val posts: PostsService = retrofit.create(PostsService::class.java)
}