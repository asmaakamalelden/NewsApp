package com.example.newsproject.Views

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
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
import android.widget.AdapterView.OnItemSelectedListener


class ArticlesActivity : AppCompatActivity() {
    lateinit var categoryList: List<String>
    var articleList = ArrayList<ArticleModel>()
    lateinit var viewModel: ArticleViewModel
    lateinit var adapter:ArticleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        val country=intent.getStringExtra("COUNTRY")
        val category1=intent.getStringExtra("CATEGORY1")
        val category2=intent.getStringExtra("CATEGORY2")
        val category3=intent.getStringExtra("CATEGORY3")
        setCategoryData()
        adapter = ArticleAdapter(this)
        rv_article_id.adapter = adapter
        rv_article_id.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        viewModel.getZippedArticles(country, category1,category2,category3)
        viewModel.getArticleLiveData()?.observe(this, Observer {
            articleList = it as ArrayList<ArticleModel>
            adapter.setArticles(articleList.sortedByDescending { it.publishedAt })
        })

        spinner_cat_id.onItemSelectedListener =object : OnItemSelectedListener  {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
              var category:String = parent.getItemAtPosition(position).toString()
                viewModel.getArticles(country, category)
                viewModel.getArticleLiveData()?.observe(this@ArticlesActivity, Observer {
                    articleList = it as ArrayList<ArticleModel>
                    adapter.setArticles(articleList.sortedByDescending { it.publishedAt })
                })
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    fun setCategoryData() {
        categoryList = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
        val categoryAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_cat_id.setAdapter(categoryAdapter)
    }


}