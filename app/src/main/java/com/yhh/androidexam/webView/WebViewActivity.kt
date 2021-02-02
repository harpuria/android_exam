package com.yhh.androidexam.webView

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import com.yhh.androidexam.R

/**
 *       - WebView
 *      안드로이드 앱에서 웹 페이지를 보여주는 기술
 *      웹뷰를 띄워서 페이지를 보여주기만 하거나 다양한 설정(javascript 허용, cache 허용 등등)으로 웹뷰를 다룰 수 있다.
 *
 *      1) 웹뷰 사용을 위해서는 매니페스트 파일에서 인터넷 사용 권한 추가를 해야 한다.
 *        <uses-permission android:name="android.permission.INTERNET"/>
 *
 *      2) URL 호출 시 ERR_CLEARTEXT_NOT_PERMITTED 이 출력되는 경우
 *        매니페스트 파일에 application 요소 안에 아래의 내용을 추가 한다.
 *        android:usesCleartextTraffic="true"
 *
 *      웹뷰 관련해서 참조하기 좋은 블로그
 *      ㄴ https://blog.yena.io/studynote/2020/05/13/Android-WebView.html
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
        webView.clearCache(true) // 캐시 제거
        webView.requestFocus(View.FOCUS_DOWN) // 포커스 요청 (웹뷰에서 input 눌렀을 때 키보드가 보이지 않을 때)

        // 웹뷰에서 자바스크립트를 통해 안드로이드 앱을 제어할 수 있게 한다 (보안문제 우려)
        webView.addJavascriptInterface(WebBride(this@WebViewActivity, webView), "android")

        // WebSettings 는 웹뷰에서 사용하는 다양한 설정 가능
        // ㄴ 자바스크립트 허용여부, 메타태그, 새 창, 캐싱 등
        val webSettings = webView.settings

        // JavaScript 허용
        webSettings.javaScriptEnabled = true

        // 웹뷰에서 ERR_CACHE_MISS 문제 방지
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        // 웹뷰에서 캐시를 불러오지 않기 위한 설정
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE

        // 유저 에이전트 값 설정 (에이전트 값은 특별히 요구하는 값이 있을 수 있음)
        webSettings.userAgentString = webSettings.userAgentString

        // HTML 에서 선언한 viewport 값을 사용
        webSettings.useWideViewPort = true

        // 여러개의 윈도우를 사용할 수 있도록 설정
        webSettings.setSupportMultipleWindows(true)

        // javascript 중 window.open() 을 사용할 수 있게 설정
        webSettings.javaScriptCanOpenWindowsAutomatically = true

        // DOM 내부의 스토리지를 허용할 것인지 여부 설정
        webSettings.domStorageEnabled = true

        // 컨텐츠 사이즈 맞추기
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL

        // HTML 의 컨텐츠가 웹뷰보다 큰 경우 스크린 크기에 맞게 조정
        webSettings.loadWithOverviewMode = true

        // 화면 확대, 축소 허용
        webSettings.setSupportZoom(true)

        // 화면 확대, 축소 허용
        webSettings.builtInZoomControls = true

        // 줌인 줌아웃 컨트롤 표시 여부
        webSettings.displayZoomControls = true

        // 웹뷰에 지정된 URL 로 웹 페이지 띄워주기
        webView.loadUrl("https://apple.com")
    }
}

// 자바스크립트 인터페이스를 위한 클래스
class WebBride(val context:Activity, val webView:WebView){
    // 창 닫기
    @JavascriptInterface
    fun closeHome(){
        this.context.finish()
    }
}