package com.example.newsproject.Models

data class NewsModel (val status:String, val totalResults:Int, val articles:List<ArticleModel>)