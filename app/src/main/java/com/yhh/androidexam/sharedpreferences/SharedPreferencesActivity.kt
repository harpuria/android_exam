package com.yhh.androidexam.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.preference.PreferenceManager
import com.yhh.androidexam.R

/**
 *      - SharedPreferences
 *      ㄴ 내부 저장소를 이용하여 간단한 데이터의 저장을 할 때 사용한다.
 *
 *      - 값을 저장할 때
 *      ㄴ 1) SharedPreference 생성
 *      ㄴ 2) Editor 꺼내기
 *      ㄴ 3) putInt(), putString() 메소드로 저장
 *      ㄴ 4) apply() 로 파일에 반영하기
 *
 *      - 값을 읽어올 때
 *      ㄴ 1) SharedPreference 생성
 *      ㄴ 2) getInt(), getString() 메소드로 읽기
 *
 *      - 이 예제에서는 androidX preference 라이브러리의 PreferenceFragment 를 사용해서 설정화면을 만들었다.
 *      ㄴ 이 설정화면에서 값을 설정하면 자동으로 SharedPreference 에 저장된다.
 */

class SharedPreferencesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shared_preferences)

        val shared = PreferenceManager.getDefaultSharedPreferences(this@SharedPreferencesActivity)
        val checkboxValue = shared.getBoolean("key_add_shortcut", false)
        val switchValue = shared.getBoolean("key_switch_on", false)
        val name = shared.getString("key_edit_name", "")
        val select = shared.getString("key_set_item", "")

        Log.d("SHARED_PREFERENCE", "" + checkboxValue)
        Log.d("SHARED_PREFERENCE", "" + switchValue)
        Log.d("SHARED_PREFERENCE", "" + name)
        Log.d("SHARED_PREFERENCE", "" + select)
    }
}