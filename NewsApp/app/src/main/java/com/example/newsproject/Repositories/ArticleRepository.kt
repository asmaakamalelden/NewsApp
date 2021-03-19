package com.example.newsproject.Repositories

import com.example.newsproject.Models.NewsModel
import com.example.newsproject.Repositories.Server.ApiClient
import com.example.newsproject.Repositories.Server.ApiInterface
import io.reactivex.Observable

class ArticleRepository {

    private val apiInterface: ApiInterface = ApiClient.getInstance()?.getApi!!


    fun getNews(country:String,category:String): Observable<NewsModel> {
        return apiInterface.getNews(country,category,"af1a8053ab83464cbaad07e5c518b592")
    }
}