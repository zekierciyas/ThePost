package com.zekierciyas.thepost.data.source.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPost(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)

@JsonClass(generateAdapter = true)
data class NetworkComment(
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
    val postId: Int
)

@JsonClass(generateAdapter = true)
data class NetworkPhoto(
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)