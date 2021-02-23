package com.yhh.androidexam.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yhh.androidexam.R

/**
 *      Recycler View
 *      리스트를 만들어주는 뷰 중에 가장 많이 사용하는 뷰
 *      이름에서 보여지듯이 뷰를 재사용하여 성능을 최적화 하였음 (요즘 기기에서는 큰 차이를 체감하기는 사실 어려움)
 */

class RecyclerViewActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        initView()

        // 임시 데이터 구성
        // 일반적으로는 외부 통신으로 받아온 데이터나 내부 데이터 (통상적으로 JSON 형태)를 사용한다.
        var tempList = mutableListOf<String>()
        for(i in 0 until 10){
            tempList.add("하하$i")
        }

        // 리사이클러 뷰에 어댑터 및 레이아웃 설정
        val adapter = RecyclerViewAdapter()
        adapter.dataList = tempList
        recyclerView.adapter = adapter
        // 리사이클러 뷰에는 세가지 레이아웃 매니저를 사용할 수 있다
        // LinearLayoutManager : 세로(기본), 가로 배치 레이아웃
        // GridLayoutManager : 데이터의 사이즈에 따라 그리드의 크기가 결정됨. 한 줄에 몇개의 아이템을 표시할 것인지 지정 가능
        // StaggeredGridLayoutManager : 컨텍스트를 사용하지 않는 레이아웃. 리니어와 유사하지만 가로, 세로의 아이템 표시 갯수를 자유롭게 지정할 수 있다.
        recyclerView.layoutManager = LinearLayoutManager(this@RecyclerViewActivity)

    }

    // 뷰 초기화
    fun initView(){
        recyclerView = findViewById(R.id.recyclerView)
    }
}

// 리사이클러뷰 어댑터 정의하기
class RecyclerViewAdapter(): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    var dataList = mutableListOf<String>()

    // 뷰를 구성하는 아이템의 총 갯수 반환
    override fun getItemCount(): Int {
        return dataList.size
    }
    // 리사이클러뷰를 구성하는 아이템뷰 레이아웃을 가져와서 뷰홀더 객체를 인스턴스하여 반환
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent,false)
        return ViewHolder(view)
    }

    // 뷰의 아이템을 하나씩 바인딩 해주기 위해서 정의하는 메소드
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList.get(position)
        holder.setData(data)
    }

    // 뷰홀더 클래스 정의
    // 이 곳에서 아이템뷰 레이아웃에 속한 뷰의 속성들을 정의해준다
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun setData(data: String){
            val itemText = itemView.findViewById<TextView>(R.id.item_text)
            itemText.text = data
        }
    }
}