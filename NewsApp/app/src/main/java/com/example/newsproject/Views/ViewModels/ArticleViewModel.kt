package com.example.newsproject.Views.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsproject.Models.ArticleModel
import com.example.newsproject.Repositories.ArticleDBRepository
import com.example.newsproject.Repositories.ArticleRepository
import com.example.newsproject.Repositories.RoomDB.ArticleEntity
import com.example.newsproject.Repositories.RoomDB.ArticleRoomDatabase
import com.example.newsproject.Views.Utils.application
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ArticleViewModel: ViewModel() {
    var articleRepository: ArticleRepository? = null
    var articleLiveData: MutableLiveData<List<ArticleModel>>? = null
    var disposable: Disposable? = null
    val articleDBRepository: ArticleDBRepository
    val allArticles: LiveData<List<ArticleEntity>>

    init{
        articleRepository= ArticleRepository()
        articleLiveData= MutableLiveData()
        val articleDao = ArticleRoomDatabase.getDatabase(application.ctx!!,viewModelScope).articleDao()
        articleDBRepository= ArticleDBRepository(articleDao)
        allArticles = articleDBRepository.allArticles

    }
  fun getZippedArticles(country:String,category1: String,category2:String,category3:String) {
    disposable= articleRepository?.getAllNews(country,category1, category2, category3)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                    {
                        response -> articleLiveData!!.setValue(response.articles) }
            ) { error -> showError(error.toString()) }
}

    fun getArticles(country:String,category: String) {
        disposable= articleRepository?.getNews(country,category)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe(
                        {
                            response -> articleLiveData!!.setValue(response.articles) }
                ) { error -> showError(error.toString()) }
    }


    fun showError(errorMsg: String?) {
        Log.d("Error Msg", errorMsg)
    }


    fun getArticleLiveData(): LiveData<List<ArticleModel>>? {
        return articleLiveData
    }
    fun getallArticlesLiveData(): LiveData<List<ArticleEntity>>? {
        return allArticles
    }

    fun disposeCalling(){
        disposable?.dispose()
    }
    fun insertTempDB(article: ArticleEntity) = viewModelScope.launch(Dispatchers.IO) {
        articleDBRepository.insert(article)
    }
}