package com.example.newsproject.Views

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.anurag.multiselectionspinner.MultiSelectionSpinnerDialog
import com.example.newsproject.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MultiSelectionSpinnerDialog.OnMultiSpinnerSelectionListener {


    lateinit var countryList: List<String>
    lateinit var categoryList: List<String>
    val selectedCategories : MutableList<String> = mutableListOf()
     val sharedPrefFile = "sharedpreference"
    var FIRST_VISIBLE=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setCountryData()
        setCategoryData()

        submit_btn_id.isEnabled=false
        submit_btn_id.setOnClickListener {
            setSharedPref()
            var intent = Intent(this, ArticlesActivity::class.java)
            intent.putExtra("COUNTRY",spinner_country_id.selectedItem.toString())
            intent.putExtra("CATEGORY1",selectedCategories[0])
            intent.putExtra("CATEGORY2",selectedCategories[1])
            intent.putExtra("CATEGORY3",selectedCategories[2])
            startActivity(intent)


        }
    }
    fun setSharedPref(){
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        FIRST_VISIBLE=1
        editor.putInt("first_visit",FIRST_VISIBLE)
        editor.putString("country_key",spinner_country_id.selectedItem.toString())
        editor.putString("category1_key",selectedCategories[0])
        editor.putString("category2_key",selectedCategories[1])
        editor.putString("category3_key",selectedCategories[2])
        editor.apply()
        editor.commit()
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
        spinner_category_id.setAdapterWithOutImage(this, categoryList, this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner_category_id.initMultiSpinner(this, spinner_category_id)
        }
    }

    override fun OnMultiSpinnerItemSelected(chosenItems: MutableList<String>?) {
        selectedCategories.clear()
        for (i in chosenItems!!.indices) {
            selectedCategories.add(chosenItems[i])
        }
        enableSubmitBtn()
    }

    fun enableSubmitBtn(){
        if(selectedCategories.size==3){
            submit_btn_id.isEnabled=true
            tv_hint_id.visibility=View.GONE
        }
        else{
            tv_hint_id.visibility=View.VISIBLE
            submit_btn_id.isEnabled=false
        }
    }
}