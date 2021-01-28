package com.yhh.androidexam.webView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import com.yhh.androidexam.R

/**
    간단하게 웹뷰 띄워주는 예제

    1) 웹뷰 사용을 위해서는 매니페스트 파일에서 인터넷 사용 권한 추가를 해야 한다.
       <uses-permission android:name="android.permission.INTERNET"/>

    2) URL 호출 시 ERR_CLEARTEXT_NOT_PERMITTED 이 출력되는 경우
       매니페스트 파일에 application 요소 안에 아래의 내용을 추가 한다.
       android:usesCleartextTraffic="true"

    웹뷰 관련해서 참조하기 좋은 블로그
    https://blog.yena.io/studynote/2020/05/13/Android-WebView.html
 */

class WebViewActivity : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var webViewBackBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        initView()
        initWebView()

        webViewBackBtn.setOnClickListener {
            finish()
        }
    }

    // 뷰 초기화 메소드
    fun initView(){
        webView = findViewById(R.id.webView)
        webViewBackBtn = findViewById(R.id.webViewBackBtn)
    }

    // 웹뷰 설정 메소드
    fun initWebView(){
        webView.webViewClient = WebViewClient()

        // WebSettings 는 웹뷰에서 사용하는 다양한 설정 가능
        // ㄴ 자바스크립트 허용여부, 메타태그, 새 창, 캐싱 등
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true // JavaScript 허용

        // 웹뷰에 지정된 URL 로 웹 페이지 띄워주기
        webView.loadUrl("https://apple.com")
    }
}