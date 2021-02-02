package com.yhh.androidexam.retrofit

import java.io.Serializable

// 직렬화를 위해서 Serializable 를 상속받는다.
class Students(
    var id: Int? = null,
    var name: String? = null,
    var age: Int? = null,
    var intro: String? = null
):Serializable {}