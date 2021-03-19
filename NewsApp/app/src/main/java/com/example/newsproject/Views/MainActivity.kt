package com.example.newsproject.Views

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.newsproject.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var countryList: List<String>
    lateinit var categoryList: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setCountryData()
        setCategoryData()

        submit_btn_id.setOnClickListener {
            val intent = Intent(this, ArticlesActivity::class.java)
            startActivity(intent)
        }
    }

    fun setCountryData() {
        countryList = listOf("ae", "ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu",
                "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr",
                "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru",
                "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za")
        val countryAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countryList)
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_country_id.setAdapter(countryAdapter)
    }

    fun setCategoryData() {
        categoryList = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
        val categoryAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryList)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_category_id.setAdapter(categoryAdapter)
    }
}