package com.yhh.androidexam.sqlite

/**
 *      데이터 클래스 (Model, DTO 등 다양하게 불리기도 한다)
 *      데이터를 가공하기 위한 클래스. 테이블과 유사한 형태로 클래스를 만들어주면 된다.
 */

data class User(var userNo:Long?, var id:String, var password:String)