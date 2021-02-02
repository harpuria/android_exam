package com.yhh.androidexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.yhh.androidexam.imagePicker.ImagePickerActivity
import com.yhh.androidexam.popup.PopupActivity
import com.yhh.androidexam.recyclerview.RecyclerViewActivity
import com.yhh.androidexam.retrofit.RetrofitActivity
import com.yhh.androidexam.sqlite.SqliteActivity
import com.yhh.androidexam.sqlite.SqliteHelper
import com.yhh.androidexam.webView.WebViewActivity

/**
    안드로이드 예제 모음
    파일 가독성을 위해 각각의 예제는 패키지로 나눠서 관리

    2021.01.28 처음 작성
 */

class MainActivity : AppCompatActivity() {
    lateinit var loadImgPickBtn: Button
    lateinit var loadWebViewBtn: Button
    lateinit var loadSqliteBtn: Button
    lateinit var loadPopupBtn: Button
    lateinit var loadRecyclerViewBtn: Button
    lateinit var loadRetrofitBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

        loadImgPickBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, ImagePickerActivity::class.java)
            startActivity(intent)
        }

        loadWebViewBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, WebViewActivity::class.java)
            startActivity(intent)
        }

        loadSqliteBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, SqliteActivity::class.java)
            startActivity(intent)
        }

        loadPopupBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, PopupActivity::class.java)
            startActivity(intent)
        }

        loadRecyclerViewBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, RecyclerViewActivity::class.java)
            startActivity(intent)
        }

        loadRetrofitBtn.setOnClickListener {
            val intent = Intent(this@MainActivity, RetrofitActivity::class.java)
            startActivity(intent)
        }
    }

    fun initView(){
        loadImgPickBtn = findViewById(R.id.loadImgPickBtn)
        loadWebViewBtn = findViewById(R.id.loadWebViewBtn)
        loadSqliteBtn = findViewById(R.id.loadSqliteBtn)
        loadPopupBtn = findViewById(R.id.loadPopupBtn)
        loadRecyclerViewBtn = findViewById(R.id.loadRecyclerViewBtn)
        loadRetrofitBtn = findViewById(R.id.loadRetrofitBtn)
    }
}