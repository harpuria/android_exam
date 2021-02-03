package com.yhh.androidexam.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.yhh.androidexam.R

/**
 *      - ViewPager2
 *      페이저는 하나의 액티비티에서 스와이프(좌우 or 상하)로 여러개의 화면(프래그먼트)을 전환하는 것을 도와주는 기능이다.
 *      이전에는 ViewPager1 를 사용했으나, 지금은 ViewPager2 를 주로 사용하는 편이다.
 *      ㄴ 수직 스크롤링 지원, RecyclerView 기반으로 사용
 *
 */

class ViewPagerActivity : AppCompatActivity() {
    lateinit var tabLayout:TabLayout
    lateinit var viewPager:ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        initView()

        // 뷰 페이저에 뷰 페이저 어댑터 추가
        val pagerAdapter = ViewPagerAdapter(this@ViewPagerActivity)
        viewPager.adapter = pagerAdapter

        // 탭 레이아웃과 페이저를 붙이기
        // 탭 레이아웃이 없어도 페이저만으로도 페이지 전환이 가능하다. 상황에 따라 탭 레이아웃을 붙일지 말지 정하면 된다.
        TabLayoutMediator(tabLayout, viewPager){ tab, position ->
            when(position){
                0 -> tab.text = "One"
                1 -> tab.text = "Two"
                2 -> tab.text = "Three"
                else -> tab.text = ""
            }
        }.attach()
    }

    // 뷰 초기화
    fun initView(){
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)
    }

    // 뷰 페이저 어댑터 정의
    // 리사이클러뷰 어댑터의 정의와 유사하여 리사이클러뷰에 익숙해졌다면 사용하기 어렵지 않다.
    inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa){
        // 프래그먼트의 갯수
        override fun getItemCount(): Int {
            return 3
        }

        // 페이저 각각의 위치에 들어갈 프래그먼트를 지정해준다
        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> OnePagerFragment()
                1 -> TwoPagerFragment()
                2 -> ThreePagerFragment()
                else -> OnePagerFragment()
            }
        }
    }
}