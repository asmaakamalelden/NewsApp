package com.example.newsproject.Views

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsproject.Models.ArticleModel
import com.example.newsproject.R
import com.example.newsproject.Repositories.ArticleRepository
import com.example.newsproject.Views.ViewModels.ArticleViewModel
import kotlinx.android.synthetic.main.activity_articles.*
import java.util.*
import kotlin.collections.ArrayList


class ArticlesActivity : AppCompatActivity() {
    lateinit var categoryList: List<String>
    var articleList = ArrayList<ArticleModel>()
    lateinit var viewModel: ArticleViewModel
    lateinit var adapter:ArticleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        setCategoryData()
        adapter = ArticleAdapter(this)
        rv_article_id.adapter = adapter
        rv_article_id.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        viewModel.getArticles("de", "business")
        viewModel.getArticleLiveData()?.observe(this, Observer {
            articleList = it as ArrayList<ArticleModel>
            adapter.setArticles(articleList.sortedByDescending { it.publishedAt })
        })
    }

    fun setCategoryData() {
        categoryList = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
        val categoryAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_cat_id.setAdapter(categoryAdapter)
    }


}