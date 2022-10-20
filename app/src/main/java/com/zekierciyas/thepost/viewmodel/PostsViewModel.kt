package com.zekierciyas.thepost.viewmodel

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import com.zekierciyas.thepost.data.source.database.getDatabase
import com.zekierciyas.thepost.domain.Comment
import com.zekierciyas.thepost.domain.Photo
import com.zekierciyas.thepost.repository.PostsRepository

class PostsViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private val database = getDatabase(application)
    private val postsRepository = PostsRepository(database)

    var isLoaded = MutableLiveData<Boolean>()
    var resultMessage = MutableLiveData<Boolean>()

    init {
        refreshAllData()
    }

    val allPost = postsRepository.posts
    val allPhotos = postsRepository.photos

    fun getComments(postId: Int): LiveData<List<Comment>> {
        return postsRepository.getCommentsById(postId)
    }

    fun getPhotoById(photoId: Int):LiveData<List<Photo>>{
        return postsRepository.getPhotoById(photoId)
    }

    /**
     *  Fetching all data to refresh Daos.
     */
    private fun refreshAllData() {
        viewModelScope.launch {
            postsRepository.refreshPosts()
            postsRepository.refreshPhotos()
            postsRepository.refreshComments()
        }
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            with(modelClass) {
                when {
                    isAssignableFrom(PostsViewModel::class.java) -> PostsViewModel(app)
                    else ->
                        error("Invalid View Model class")
                }
            } as T
    }
}