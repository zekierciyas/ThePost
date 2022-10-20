package com.zekierciyas.thepost.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import retrofit2.HttpException
import com.zekierciyas.thepost.data.source.database.getDatabase
import com.zekierciyas.thepost.repository.PostsRepository

class RefreshDataWork(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = PostsRepository(database)
        return try {
            repository.refreshPosts()
            repository.refreshComments()
            repository.refreshPhotos()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}