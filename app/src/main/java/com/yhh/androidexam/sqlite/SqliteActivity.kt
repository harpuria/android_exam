package com.yhh.androidexam.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.yhh.androidexam.R

/**
    Sqlite 는 안드로이드, iOS 에서 사용하는 임베드 데이터베이스이다.
    최근 안드로이드에서는 Sqlite 를 그대로 쓰는 것 대신 Room 이라는 라이브러리를 쓰는 것을 권장.
    Room 에 대한 예제 역시 추후 정리할 예정임.

    1) SQLiteHelper 클래스 생성 (SqliteHelper.kt)
       ㄴ 이 클래스에는 테이블 생성을 포함하여 CRUD 를 진행하는 함수들을 정의해둔다.

    2) 데이터를 담거나 사용하기 위한 데이터 클래스 생성 (UserData.kt)

 */

class SqliteActivity : AppCompatActivity() {

    lateinit var idEditText:EditText
    lateinit var pwEditText:EditText
    lateinit var insertBtn:Button
    lateinit var selectBtn:Button
    lateinit var updateBtn:Button
    lateinit var deleteBtn:Button

    // SQLiteHelper 객체 생성
    // 다른 곳에서도 이 객체를 사용하기 위해서는 싱글턴 패턴으로 만들면 더 편리할 것이다
    val helper: SqliteHelper = SqliteHelper(this@SqliteActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sqlite)

        initView()

        insertBtn.setOnClickListener {
            // 여기서 userNo 에 0 은 임의의 아무 값을 준 것이다.
            // Sqlite 에서 INTEGER 타입으로 생성된 PK 는 INSERT 될 떄 자동으로 값이 1씩 증가한다. (autoincrement)
            val user: User = User(0, idEditText.text.toString(), pwEditText.text.toString())
            val result = helper.insertUser(user)

            if(result){
                Toast.makeText(this@SqliteActivity, "회원 등록 완료", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@SqliteActivity, "회원 등록 실패", Toast.LENGTH_SHORT).show()
            }
        }

        selectBtn.setOnClickListener {
            val userList = helper.selectUser()
            Log.d("SELECT_USER", userList.toString())
        }

        updateBtn.setOnClickListener {
            // 예제 편의상 1번 유저만 갱신하는 것으로 처리 (실제 사용할 때 이러면 곤란함)
            val result = helper.updateUser(1, pwEditText.text.toString())

            if(result){
                Toast.makeText(this@SqliteActivity, "비밀번호 갱신 완료", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this@SqliteActivity, "비밀번호 갱신 실패", Toast.LENGTH_SHORT).show()
            }
        }

        deleteBtn.setOnClickListener {
            // 예제 편의상 1번 유저만 삭제하는 것으로 처리 (실제 사용할 때 이러면 곤란함)
            val result = helper.deleteUser(1)

            if(result){
                Toast.makeText(this@SqliteActivity, "회원 삭제 완료", Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this@SqliteActivity, "회원 삭제 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 뷰 초기화
    fun initView(){
        idEditText = findViewById(R.id.idEditText)
        pwEditText = findViewById(R.id.pwEditText)
        insertBtn = findViewById(R.id.insertBtn)
        selectBtn = findViewById(R.id.selectBtn)
        updateBtn = findViewById(R.id.updateBtn)
        deleteBtn = findViewById(R.id.deleteBtn)
    }
}