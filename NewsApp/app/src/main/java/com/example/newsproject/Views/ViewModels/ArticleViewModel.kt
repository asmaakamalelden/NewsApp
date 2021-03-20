package com.example.newsproject.Views.ViewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsproject.Models.ArticleModel
import com.example.newsproject.Repositories.ArticleRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ArticleViewModel: ViewModel() {
    var articleRepository: ArticleRepository? = null
    var articleLiveData: MutableLiveData<List<ArticleModel>>? = null
    var disposable: Disposable? = null

    init{
        articleRepository= ArticleRepository()
        articleLiveData= MutableLiveData()

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

    fun disposeCalling(){
        disposable?.dispose()
    }

}