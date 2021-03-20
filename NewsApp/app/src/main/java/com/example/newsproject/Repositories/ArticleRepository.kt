package com.example.newsproject.Repositories

import com.example.newsproject.Models.NewsModel
import com.example.newsproject.Repositories.Server.ApiClient
import com.example.newsproject.Repositories.Server.ApiInterface
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class ArticleRepository {

    private val apiInterface: ApiInterface = ApiClient.getInstance()?.getApi!!


    fun getNews(country: String, category: String): Observable<NewsModel> {
        return apiInterface.getNews(country, category, "af1a8053ab83464cbaad07e5c518b592")
    }

    fun getAllNews(country: String, category1: String, category2: String, category3: String): Observable<NewsModel> {
        val observable1 = getNews(country, category1)
        val observable2 = getNews(country, category2)
        val observable3 = getNews(country, category3)


        val result = Observable.zip(
                observable1,
                observable2,
                BiFunction<NewsModel, NewsModel, NewsModel> { o1, o2 -> o1.apply { o2 } })

        return Observable.zip(
                result,
                observable3,
                BiFunction<NewsModel, NewsModel, NewsModel> { o1, o2 -> o1.apply { o2 } })
    }
}




