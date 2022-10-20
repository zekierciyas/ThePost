package com.zekierciyas.thepost.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.zekierciyas.thepost.data.source.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import com.zekierciyas.thepost.data.source.network.Network
import com.zekierciyas.thepost.domain.Comment
import com.zekierciyas.thepost.domain.Photo
import com.zekierciyas.thepost.domain.Post

class PostsRepository(private val database: PostsDatabase) {

    var responseCode: String? = null

    /**----------------ONLINE NETWORK-----------------------
     *  Getting all post from Network layer.
     *  Inserting them to Dao.
     */
    suspend fun refreshPosts() {
        withContext(Dispatchers.IO) {
            try {
                val allPost = Network.posts.getAllPostsAsync().await()
                database.postDao.insertAllPosts(*allPost.map {
                    DatabasePost(
                        id = it.id,
                        title = it.title,
                        body = it.body,
                        userId = it.userId
                    )
                }.toTypedArray())
            } catch (exception: Exception) {
                Timber.e("Error ${exception.localizedMessage}")
            }
        }
    }


    /**
     * Getting all photos from Network layer.
     * Inserting them to Dao.
     */
    suspend fun refreshPhotos() {
        withContext(Dispatchers.IO) {
            try {
                val allPhotos = Network.posts.getAllPhotosAsync().await()
                database.postDao.insertAllPhotos(*allPhotos.map {
                    DatabasePhotos(
                            id = it.id,
                            title = it.title,
                            url = it.url,
                            thumbnailUrl = it.thumbnailUrl
                    )
                }.toTypedArray())
            } catch (exception: Exception) {
                Timber.e("Error ${exception.localizedMessage}")
            }
        }
    }

    /**
     *  Getting all comments from Network layer.
     */
    suspend fun refreshComments() {
        withContext(Dispatchers.IO) {
            try {
                val comments = Network.posts.getAllCommentsAsync().await()
                database.postDao.insertAllComments(*comments.map {
                    DatabaseComment(
                        id = it.id,
                        name = it.name,
                        email = it.email,
                        body = it.body,
                        postId = it.postId
                    )
                }.toTypedArray())
            } catch (exception: Exception) {
                Timber.e("Error ${exception.localizedMessage}")
            }
        }
    }

    /**
     * Getting photo by photoId from Network.
     */
    suspend fun getPhotosById(photoId: Int) {
        withContext(Dispatchers.IO) {
            try {
                val photos = Network.posts.getPhotosByPostIdAsync(photoId.toString()).await()
                database.postDao.insertAllPhotos(*photos.map {
                    DatabasePhotos(
                        id = it.id,
                        title = it.title,
                        url =  it.url,
                        thumbnailUrl =  it.thumbnailUrl
                    )
                }.toTypedArray())
            } catch (exception: Exception) {
                Timber.e("Error ${exception.localizedMessage}")
            }
        }
    }


    /**----------------------OFFLINE DAO---------------------------
     *
     *  Photo by ID from Dao
     */
    fun getPhotoById(id: Int) : LiveData<List<Photo>> {
        return Transformations.map(database.postDao.getPhotosById(id)){
            it.asDomainPhotoModel()
        }
    }

    /**
     *  All photos from Dao
     */
    fun getAllPhotos(photoId: Int) : LiveData<List<Photo>> {
        return Transformations.map(database.postDao.getAllPhotos()){
            it.asDomainPhotoModel()
        }
    }

    /**
     *  Comments by postId from Dao
     */
    fun getCommentsById(postId: Int): LiveData<List<Comment>> {
        return Transformations.map(database.postDao.getCommentsByPostId(postId)) {
            it.asDomainCommentModel()
        }
    }

    /**
     *  An object that gets and holds all posts from Dao
     */
    val posts: LiveData<List<Post>> =
        Transformations.map(database.postDao.getPosts()) {
            it.asDomainPostModel()
        }

    /**
     *  An object that gets and holds all photos from Dao
     */
    val photos: LiveData<List<Photo>> =
        Transformations.map(database.postDao.getAllPhotos()){
            it.asDomainPhotoModel()
        }

}