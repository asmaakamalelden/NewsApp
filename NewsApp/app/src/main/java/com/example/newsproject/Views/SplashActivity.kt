package com.example.newsproject.Views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.newsproject.R



class SplashActivity : AppCompatActivity() {
    val  SPLASH_DISPLAY_LENGTH = 2000
    val sharedPrefFile = "sharedpreference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val first_visit = sharedPreferences.getInt("first_visit",0)
        val mainIntent = Intent(this, MainActivity::class.java)
        val articleIntent=Intent(this, ArticlesActivity::class.java)
        Handler().postDelayed(Runnable { /* Create an Intent that will start the Menu-Activity. */
            if(first_visit==0) this.startActivity(mainIntent)
            else this.startActivity(articleIntent)
            this.finish()
        }, SPLASH_DISPLAY_LENGTH.toLong())
    }
}