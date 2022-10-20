# The Post

A sample application that presents the listing of current posts from the API, with up-to-date libraries and dependencies, and clean architecture principles.

### Api Service
```
    @GET("photos")
    fun getAllPhotosAsync(): Deferred<List<NetworkPhoto>>

    @GET("posts")
    fun getAllPostsAsync(): Deferred<List<NetworkPost>>

    @GET("comments")
    fun getAllCommentsAsync(): Deferred<List<NetworkComment>>

    @GET("comments")
    fun getCommentsByPostIdAsync(@Query("postId") postId: String): Deferred<List<NetworkComment>>

    @GET("photos")
    fun getPhotosByPostIdAsync(@Query("postId") postId: String): Deferred<List<NetworkPhot
```

## Dependencies

* [Room] - Database
* [Navigation] - Navigation Component for navigate transactions
* [Retrofit] - HTTP client
* [Moshi] - JSON Parser
* [Lifecycle] - ViewModel and LiveData
* [Coroutines] - Threading
* [Work Manager] - Background working
* [Timber] - Logging
* [Stetho] - Inspect DB

[Kotlin Udacity]: <https://classroom.udacity.com/courses/ud9012>
[rifqimfahmi]: <https://github.com/rifqimfahmi/android-mvvm-coroutine>
[Retrofit]: <https://square.github.io/retrofit/>
[Moshi]: <https://github.com/square/moshi>
[Room]: <https://developer.android.com/topic/libraries/architecture/room>
[Lifecycle]: <https://developer.android.com/topic/libraries/architecture>
[Coroutines]: <https://developer.android.com/topic/libraries/architecture/coroutines>
[Work Manager]: <https://developer.android.com/topic/libraries/architecture/workmanager>
[Navigation]: <https://developer.android.com/guide/navigation>
[Navigation Component]: <https://developer.android.com/guide/navigation>
[Timber]: <https://github.com/JakeWharton/timber>
[Stetho]: <https://github.com/facebook/stetho>
[git-karma]: <http://karma-runner.github.io/4.0/dev/git-commit-msg.html>

