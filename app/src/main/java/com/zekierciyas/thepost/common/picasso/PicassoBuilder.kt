package com.zekierciyas.thepost.common.picasso

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import com.zekierciyas.thepost.R
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import com.zekierciyas.thepost.common.connectivity.ConnectivityDetectorImpl


object PicassoBuilder {

    fun loadImage(
        imageUrl: String, mContext: Context, imageView: ImageView,
        errorImage: Int, radius: Int, margin: Int,
        progressBar:ProgressBar, connectivityDetector: ConnectivityDetectorImpl
    ) {

        // Connectivity check
        if (connectivityDetector.isConnected()) {
            try {
                Picasso.with(mContext)
                    .load(imageUrl)
                    .error(errorImage)
                    .transform(RoundedCornersTransformation(radius, margin))
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            progressBar.visibility = View.INVISIBLE
                        }

                        override fun onError() {
                            progressBar.visibility = View.INVISIBLE
                            imageView.setImageResource(R.drawable.ic_error)
                        }
                    })

            } catch (e: Exception) {
                e.printStackTrace()
                imageView.setImageResource(R.drawable.ic_error)
            }
        }else {
            try {
                Picasso.with(mContext)
                    .load(imageUrl)
                    .error(errorImage)
                    .networkPolicy(NetworkPolicy.OFFLINE) // Offline cache
                    .transform(RoundedCornersTransformation(radius, margin))
                    .into(imageView,object : Callback {
                        override fun onSuccess() {
                            progressBar.visibility = View.INVISIBLE
                        }

                        override fun onError() {
                            progressBar.visibility = View.INVISIBLE
                            imageView.setImageResource(R.drawable.ic_error)
                        }
                    })
            } catch (e: Exception){
                e.printStackTrace()
                imageView.setImageResource(R.drawable.ic_error)
            }
        }

    }
}