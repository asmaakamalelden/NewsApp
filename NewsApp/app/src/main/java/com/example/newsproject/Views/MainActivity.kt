package com.example.newsproject.Views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.newsproject.Models.ArticleModel
import com.example.newsproject.R
import com.example.newsproject.Views.ViewModels.ArticleViewModel

class MainActivity : AppCompatActivity() {
      var articleList = ArrayList<ArticleModel>()
      lateinit var viewModel: ArticleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        viewModel.getArticles("de","business")
        viewModel.getArticleLiveData()?.observe(this, Observer {
            articleList = it as ArrayList<ArticleModel>
    })
    }
}