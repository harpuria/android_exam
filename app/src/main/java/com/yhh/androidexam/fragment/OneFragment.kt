package com.yhh.androidexam.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yhh.androidexam.R

class OneFragment : Fragment() {

    lateinit var mContext: Context

    // onAttach() 프래그먼트가 액티비티에 붙을 때(Attach) 발생한다
    // 프래그먼트에는 컨텍스트가 없기 때문에 Attach 하는 시점에 액티비티의 컨텍스트를 가져와서 사용할 수 있게 된다.
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_one, container, false)
    }
}