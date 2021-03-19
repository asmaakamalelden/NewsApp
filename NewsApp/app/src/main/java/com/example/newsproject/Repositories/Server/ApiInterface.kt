package com.example.newsproject.Repositories.Server

import com.example.newsproject.Models.NewsModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("v2/top-headlines")
    fun getNews(@Query("country") country:String,@Query("category") category:String,
                @Query("apiKey") apiKey: String): Observable<NewsModel>
}