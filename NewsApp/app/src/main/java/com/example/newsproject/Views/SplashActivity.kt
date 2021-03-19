package com.example.newsproject.Views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.newsproject.R



class SplashActivity : AppCompatActivity() {
    val  SPLASH_DISPLAY_LENGTH = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */
            val mainIntent = Intent(this, MainActivity::class.java)
            this.startActivity(mainIntent)
            this.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}