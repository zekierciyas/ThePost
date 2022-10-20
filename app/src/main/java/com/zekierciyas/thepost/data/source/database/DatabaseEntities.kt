package com.zekierciyas.thepost.data.source.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.zekierciyas.thepost.domain.Comment
import com.zekierciyas.thepost.domain.Photo
import com.zekierciyas.thepost.domain.Post

@Entity
data class DatabasePost constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)

fun List<DatabasePost>.asDomainPostModel(): List<Post> {
    return map {
        Post(
            id = it.id,
            title = it.title,
            body = it.body,
            userId = it.userId
        )
    }
}

@Entity
data class DatabaseComment constructor(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val body: String,
    val postId: Int
)

fun List<DatabaseComment>.asDomainCommentModel(): List<Comment> {
    return map {
        Comment(
            id = it.id,
            name = it.name,
            email = it.email,
            body = it.body,
            postId = it.postId
        )
    }
}


@Entity
data class DatabasePhotos constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)

fun List<DatabasePhotos>.asDomainPhotoModel(): List<Photo> {
    return map {
        Photo(
            id = it.id,
            title = it.title,
            url = it.url,
            thumbnailUrl = it.thumbnailUrl
        )
    }
}