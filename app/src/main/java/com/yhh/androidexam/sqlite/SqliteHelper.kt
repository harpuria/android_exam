package com.yhh.androidexam.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 *      SQliteHelper 클래스
 *      Sqlite 를 사용하기 위한 클래스라고 이해하면 좋다.
 *      이 클래스에서 테이블 생성 및 CRUD 작업을 수행하는 메소드들을 정의하면 된다.
 *
 *      주의 : 아래 코드는 예제로 중복 아이디 가입 불가 처리 등 세세한 작업은 하지 않았음.
 */

class SqliteHelper(context:Context): SQLiteOpenHelper(context, DB_NAME,null, DB_VER){

    // DB 가 생성될 때 처음 호출되는 메소드
    // 여기서 테이블 생성 관련 쿼리를 정의한다
    override fun onCreate(db: SQLiteDatabase?) {
        val userCreate = "CREATE TABLE USER (USER_NO INTEGER PRIMARY KEY, ID TEXT, PASSWORD TEXT, FILE_PATH TEXT)"
        db?.execSQL(userCreate)
    }

    // DB 버전이 올라갈 때 될 때 호출되는 메소드
    // 여기는 테이블의 변경점이 있는 경우 테이블을 삭제(DROP) 하고 재생성(CREATE) 할 때 사용
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val userDrop = "DROP TABLE USER"
        db?.execSQL(userDrop)
        onCreate(db)
    }

    // INSERT (C)
    fun insertUser(user:User): Boolean{
        // 컬럼에 넣을 값들을 세팅해준다
        val values = ContentValues()
        values.put("ID", user.id)
        values.put("PASSWORD", user.password)

        // 쓰기 데이터베이스 객체를 얻고, 위에 세팅한 values 의 값들을 table 에 insert 해준다
        val wd = writableDatabase
        val result = wd.insert("USER", null, values)

        // 다 쓴 객체는 반환해주어야 한다
        wd.close()

        return result >= 1L
    }

    // SELECT (R)
    fun selectUser():MutableList<User>{
        // 반환에 필요한 리스트
        val list = mutableListOf<User>()

        // 쿼리
        val query = "SELECT * FROM USER"

        // 읽기 데이터베이스 객체를 얻고, rawQuery 를 수행하면 그 결과가 Cursor! 객체로 반환
        val rd = readableDatabase
        val cursor = rd.rawQuery(query, null)

        // select 의 결과행이 많은 경우 커서를 이동시키면서 값을 리스트에 추가해주어야 한다.
        // 단, 한 건의 행을 조회하는 경우는 굳이 반복을 돌 필요가 없다.
        while(cursor.moveToNext()){
            val userNo = cursor.getLong(cursor.getColumnIndex("USER_NO"))
            val id = cursor.getString(cursor.getColumnIndex("ID"))
            val password = cursor.getString(cursor.getColumnIndex("PASSWORD"))

            list.add(User(userNo, id, password))
        }

        // 다 쓴 객체는 반환
        cursor.close()
        rd.close()

        return list
    }

    // UPDATE (U)
    fun updateUser(userNo: Long, modifyPw: String): Boolean{
        // 변경할 컬럼의 값을 세팅
        val values = ContentValues()
        values.put("PASSWORD", modifyPw)

        // 쓰기 데이터베이스 객체를 얻고..
        val wd = writableDatabase
        // 여기 보면 whereCaluse 인자가 보이는데 SQL 에서 WHERE 절을 말한다, 여기에 조건을 넣어 주면 된다
        // whereArgs 인자는 지정해야할 조건들이 많을 경우, whereClause 조건의 값을 ? 로 대체한 뒤에 ? 에 들어가야할 값들을 지정해주는 것이다
        val result = wd.update("USER", values, "USER_NO = $userNo", null)

        // 마찬가지로 닫고..
        wd.close()

        // update 나 delete, insert 의 경우 정수값이 반환되는데 (특이하게 insert 는 Long 타입이다)
        // 0 은 반환행 0건 (실패)
        // 1 이상은 반환행 1건 이상 (성공)
        return result >= 1
    }

    // DELETE (D)
    // UPDATE 와 유사하기 때문에 주석은 생략함
    fun deleteUser(userNo: Long): Boolean{
        val wd = writableDatabase
        val result = wd.delete("USER", "USER_NO = $userNo", null)

        wd.close()
        return result >= 1
    }

    // DB_VER 와 DB_NAME 은 상수로 미리 만들어두면 편리하게 사용할 수 있다.
    companion object{
        const val DB_VER = 1
        const val DB_NAME = "user.db"


        // 이 클래스를 싱글턴으로 활용하기
        @Volatile private var instance: SqliteHelper? = null
        fun getInstance(context: Context): SqliteHelper =
            instance ?: synchronized(this){
                instance ?: SqliteHelper(context).also {
                    instance = it
                }
            }
    }
}