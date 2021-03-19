package com.example.newsproject.Models

import java.util.*

data class ArticleModel (val source:SourceModel, val author:String, val title:String,
                         val description:String, val url:String, val urlToImage:String, val publishedAt:Date, val content:String)