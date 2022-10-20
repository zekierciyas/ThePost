package com.zekierciyas.thepost.features.splash

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zekierciyas.thepost.R
import com.zekierciyas.thepost.databinding.ActivitySplashBinding
import com.zekierciyas.thepost.features.PostsActivity
import java.util.*

class SplashActivity : AppCompatActivity() {

    lateinit var topAnimation: Animation
    lateinit var bottomAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       val binding:ActivitySplashBinding = DataBindingUtil.setContentView<ActivitySplashBinding>(
           this,
           R.layout.activity_splash
       )
        supportActionBar!!.hide()
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_bottom)
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.anim_top)

        binding.textTop.animation = topAnimation
        binding.textBottom.animation = bottomAnimation
        binding.imageCamera.animation = bottomAnimation

        Timer().schedule(object : TimerTask() {
            override fun run() {
                startPostActivity()
                finish()
            }
        }, 2000)
    }

    /**
     *  Start home screen after splash is over.
     */
    private fun startPostActivity() {
        val intent = Intent(this, PostsActivity::class.java)
        startActivity(intent)
    }
}