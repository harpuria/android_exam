package com.yhh.androidexam.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yhh.androidexam.R

/**
 *      - Fragment
 *      프래그먼트는 '조각' 을 의미하는데, 하나의 액티비티를 구성 할 때 여러 곳에서 프래그먼트를 가져와서 액티비티를 구성해줄 수 있다.
 *
 */

class FragmentActivity : AppCompatActivity() {
    lateinit var secondFragmentBtn:Button
    lateinit var delSecondFragmentBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        initView()

        // 동적으로 프래그먼트 붙이기
        val twoFragment:TwoFragment = TwoFragment()

        // 프래그먼트에 데이터 전달
        // 이렇게 전달한 데이터는 프래그먼트에서 onActivityCreated 에서 받아올 수 있다.
        val bundle:Bundle = Bundle()
        bundle.putString("Hello", "Hello")
        twoFragment.arguments = bundle

        // 버튼 클릭시 두번째 프래그먼트를 동적으로 붙이기
        secondFragmentBtn.setOnClickListener {
            val fragmentManager:FragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // 작업은 beginTransaction() 과 commit() 사이에서 진행해야 한다.
            fragmentTransaction.replace(R.id.container, twoFragment)

            fragmentTransaction.commit()
        }

        // 버튼 클릭시 프래그먼트 제거
        delSecondFragmentBtn.setOnClickListener {
            val fragmentManager:FragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            // detach 는 완전히 떼버리는 것 (다시 replace 해도 붙지 않음)
            fragmentTransaction.detach(twoFragment)

            // remove 는 제거하되 replace 를 호출하면 다시 붙음
            //fragmentTransaction.remove(twoFragment)

            fragmentTransaction.commit()
        }
    }

    fun initView(){
        secondFragmentBtn = findViewById(R.id.secondFragmentBtn)
        delSecondFragmentBtn = findViewById(R.id.delSecondFragmentBtn)
    }
}