package com.example.newsproject.Views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.newsproject.Models.ArticleModel
import com.example.newsproject.Models.SourceModel
import com.example.newsproject.R
import com.example.newsproject.Repositories.RoomDB.ArticleEntity
import com.squareup.picasso.Picasso
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.article_item.view.*
import java.text.SimpleDateFormat
import kotlin.random.Random


class ArticleAdapter (val context: Context) : RecyclerView.Adapter<ArticleAdapter.MyViewHolder>() {
    var articleList = emptyList<ArticleModel>()
    val subject = PublishSubject.create<ArticleModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.article_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val articleToDisplay = articleList[position]
        holder.bind(articleToDisplay){
            subject.onNext(this.articleList[position])
            subject.onComplete()
        }
        if(articleList[position].urlToImage !=null) {
            Picasso.with(context).load(articleList[position].urlToImage)
                    .placeholder(R.drawable.ic_launcher_background).fit()
                    .into(holder.itemView.imgview_article)
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("googlechrome://navigate?url="+articleList[position].url))
            context.startActivity(intent);
        }

    }

    internal fun setArticles(articles: List<ArticleModel>) {
        this.articleList = articles
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(current: ArticleModel, onClickCallback: () -> Unit) {
            itemView.apply {
                itemView.tv_title.text = current.title
                val sdf = SimpleDateFormat("dd MMM yyyy HH:mm")
                val dateFormatted = sdf.format(current.publishedAt)
                itemView.tv_date.text = dateFormatted
                itemView.tv_source_newspaper.text = "Newspaper  " + current.source.name
                itemView.tv_desc.text = current.description
                itemView.imgbtn_save.setOnClickListener { onClickCallback.invoke() }
            }



        }
    }
}
