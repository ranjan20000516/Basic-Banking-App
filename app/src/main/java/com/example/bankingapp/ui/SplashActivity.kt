package com.example.bankingapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import com.example.bankingapp.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()


        val topAnimation= AnimationUtils.loadAnimation(this, R.anim.top_animation)
        val middleAnimation= AnimationUtils.loadAnimation(this, R.anim.middle_animation)
        val bottomAnimation= AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        firstLine.startAnimation(topAnimation)
        secondLine.startAnimation(topAnimation)
        thirdLine.startAnimation(topAnimation)
        fourthLine.startAnimation(topAnimation)
        fifthLine.startAnimation(topAnimation)
        sixthLine.startAnimation(topAnimation)
        seventhLine.startAnimation(topAnimation)
        splashLogo.startAnimation(middleAnimation)
        madeBy.startAnimation(bottomAnimation)

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },3000)

    }
}