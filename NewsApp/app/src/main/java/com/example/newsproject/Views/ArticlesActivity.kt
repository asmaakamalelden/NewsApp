package com.example.newsproject.Views

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsproject.Models.ArticleModel
import com.example.newsproject.Models.SourceModel
import com.example.newsproject.R
import com.example.newsproject.Repositories.RoomDB.ArticleEntity
import com.example.newsproject.Views.ViewModels.ArticleViewModel
import kotlinx.android.synthetic.main.activity_articles.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList


class ArticlesActivity : AppCompatActivity() {
    lateinit var categoryList: List<String>
    var articleList = ArrayList<ArticleModel>()
    var articleDBList = ArrayList<ArticleEntity>()
    lateinit var viewModel: ArticleViewModel
    lateinit var adapter: ArticleAdapter
    lateinit var articleDBItem: ArticleEntity
    val sharedPrefFile = "sharedpreference"


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        showSavedArticles()
        return true
    }

    fun showSavedArticles() {
        viewModel.getallArticlesLiveData()?.observe(this, Observer {
            articleDBList = it as ArrayList<ArticleEntity>
            for (x in articleDBList) {
                    val date = SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy").parse(x.publishedAt)
                var article = ArticleModel(SourceModel(Math.random().toString(), x.sourceName), "", x.title, x.description, x.url, x.urlToImage, date, "")
                articleList.add(article)
            }
            adapter.setArticles(articleList.sortedByDescending { it.publishedAt })
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_articles)

        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val country = sharedPreferences.getString("country_key","ae")
        val category1 = sharedPreferences.getString("category1_key","general")
        val category2 = sharedPreferences.getString("category2_key","health")
        val category3 = sharedPreferences.getString("category3_key","science")
        setCategoryData()
        adapter = ArticleAdapter(this)
        rv_article_id.adapter = adapter
        rv_article_id.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        viewModel.getZippedArticles(country!!, category1!!, category2!!, category3!!)
        viewModel.getArticleLiveData()?.observe(this, Observer {
            articleList = it as ArrayList<ArticleModel>
            adapter.setArticles(articleList.sortedByDescending { it.publishedAt })
            adapter.subject
                    .subscribe { rowText ->
                        viewModel.insertTempDB(ArticleEntity(null, rowText.title, rowText.description
                                ?: "", rowText.publishedAt.toString(), rowText.url, rowText.urlToImage
                                ?: "", rowText.source.name))
                    }
        })


        spinner_cat_id.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
            ) {
                var category: String = parent.getItemAtPosition(position).toString()
                viewModel.getArticles(country, category)
                viewModel.getArticleLiveData()?.observe(this@ArticlesActivity, Observer {
                    articleList = it as ArrayList<ArticleModel>
                    adapter.setArticles(articleList.sortedByDescending { it.publishedAt })
                })
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
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