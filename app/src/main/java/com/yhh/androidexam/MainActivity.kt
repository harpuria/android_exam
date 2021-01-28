package com.yhh.androidexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.yhh.androidexam.imagePicker.ImagePickerActivity
import com.yhh.androidexam.webView.WebViewActivity

/**
    안드로이드 예제 모음
    파일 가독성을 위해 각각의 예제는 패키지로 나눠서 관리

    2021.01.28 처음 작성
 */

class MainActivity : AppCompatActivity() {
    lateinit var loadImgPickBtn: Button
    lateinit var loadWebViewBtn: Button

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
    }

    fun initView(){
        loadImgPickBtn = findViewById(R.id.loadImgPickBtn)
        loadWebViewBtn = findViewById(R.id.loadWebViewBtn)
    }
}