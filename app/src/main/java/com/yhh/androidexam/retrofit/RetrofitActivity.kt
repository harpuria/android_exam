package com.yhh.androidexam.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.yhh.androidexam.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *      - Retrofit
 *      레트로핏은 안드로이드에서 네트워크 통신을 편리하게 도와주는 라이브러리이다.
 *      사용하기 위해서는 별도로 의존성을 추가해야 한다.
 *
 *      레트로핏 공식 문서
 *      ㄴ https://square.github.io/retrofit/
 *
 *      1) RetrofitService 인터페이스 생성하기
 *      ㄴ 이 인터페이스에는 API 요청과 관련된 내용들(API URL 및 baseUrl, API Key 등등..)을 쭉 정의한다.
 *
 *      2) Retrofit 생성 후 사용하기
 *
 *
 *      - Coroutine
 *      이전에는 네트워킹 비동기 처리를 할 때 AsyncTask 를 사용했지만 Deprecated 되었음.
 *      그래서 RxJava 나 Coroutine 을 사용하는 것을 권장함 (특히 공식 개발문서에는 Coroutine 을 권장함)
 *      코루틴 역시 사용하기 위해서는 별도의 의존성을 추가해야 한다.
 *
 *      코루틴을 자세히 알려면 아래 사이트 참조
 *      ㄴ https://codechacha.com/ko/android-coroutine/
 *
 */

class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        // Retrofit 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/") // 기본이 되는 베이스 Url
            .addConverterFactory(GsonConverterFactory.create()) // 데이터를 변환해주는 컨버터 팩토리 (웬만하면 GsonConverterFactory 사용)
            .build()

        // Retrofit Service 생성
        val service = retrofit.create(RetrofitService::class.java)

        // Coroutine 으로 메인 스레드와 별개의 작업 단위에서 레트로핏 통신 수행하기
        Log.d("RETROFIT", "doing something in main thread")
        GlobalScope.launch {
            delay(3000)
            Log.d("RETROFIT", "done something in Coroutine")

            // GET 요청하기
            service.getStudentsList().enqueue(object:Callback<ArrayList<Students>>{
                // 통신 성공시
                override fun onResponse(
                    call: Call<ArrayList<Students>>,
                    response: Response<ArrayList<Students>>
                ) {
                    if(response.isSuccessful){
                        val student = response.body()
                        val code = response.code()
                        val error = response.errorBody()
                        val header = response.headers()
                        Log.d("RETROFIT", "response:" + student)
                        Log.d("RETROFIT", "code:" + code)
                        Log.d("RETROFIT", "error:" + error)
                        Log.d("RETROFIT", "header:" + header)
                    }
                }

                // 통신 실패시
                override fun onFailure(call: Call<ArrayList<Students>>, t: Throwable) {
                    Toast.makeText(this@RetrofitActivity, "통신 실패", Toast.LENGTH_SHORT).show()
                }
            })
        }
        Log.d("RETROFIT", "done in main thread")
    }
}
