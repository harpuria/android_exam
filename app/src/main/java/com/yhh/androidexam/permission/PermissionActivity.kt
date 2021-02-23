package com.yhh.androidexam.permission

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yhh.androidexam.R

/**
 *      - 권한(Permission)
 *      ㄴ 권한은 일반 권한과 위험 권한두 가지로 나뉘어져 있다.
 *      ㄴ 유저의 입장에서 특정 행동을 할 때 권한을 팝업으로 요청하는 권한들이 모두 위험 권한이라 보면 된다 (ex: 갤러리 접근, 카메라 접근, 전화 접근 등등)
 *      ㄴ 일반 권한들은 보통 설치하는 시점에 권한을 요청한다.
 *
 *      - AndroidManifest.xml 에서 권한 부여하기
 *      ㄴ  <uses-permission android:name="android.permission.PERMISSION_NAME" /> 을 작성하여 앱에 특정 권한을 부여할 수 있다.
 *
 *      - 일반 권한
 *      ㄴ ACCESS_NETWORK_STATE : 네트워크 연결 상태 확인
 *      ㄴ ACCESS_WIFI_STATE : 와이파이 상태 확인
 *      ㄴ BLUETOOTH : 블루투스 상태 확인
 *      ㄴ INTERNET : 네트워크 및 인터넷 사용
 *      ㄴ NFC : 기기간 근거리 통신 사용
 *      ㄴ SET_ALARM : 알람 설정
 *      ㄴ VIBRATE : 진동 설정
 *      ㄴ ....
 *
 *      - 위험 권한 (앱이 사용자의 개인정보와 관련된 데이터나 기능을 액세스하거나 다른 앱이나 기기 작동에 영향을 줄 우려가 있는 경우)
 *      ㄴ CAMERA : 카메라 접근
 *      ㄴ ACCESS_FINE_LOCATION or ACCESS_COARSE_LOCATION : 위치 정보 사용
 *      ㄴ READ_CONTACTS : 주소록 읽기
 *      ㄴ WRITE_CONTACTS : 주소록 쓰기
 *      ㄴ GET_ACCOUNTS : 계정정보 가져오기
 *      ㄴ READ_EXTERNAL_STORAGE : 안드로이드 공용 저장소 읽기
 *      ㄴ WRITE_EXTERNAL_STORAGE : 안드로이드 공용 저장소 읽기
 *      ㄴ ....
 *
 *      - 위험 권한의 경우 권한 그룹으로 묶여져 있는데, A 그룹에 속한 B, C 권한 중에 하나라도 권한 승인을 했다면 나머지 권한도 모두 허가된다
 *
 */

class PermissionActivity : AppCompatActivity() {
    lateinit var btnCamera:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        initView()

        btnCamera.setOnClickListener {
            checkPermission()
        }

    }

    fun initView(){
        btnCamera = findViewById(R.id.btnCamera)
    }

    // 1) 권한에 대한 사용자 승인 확인
    fun checkPermission(){
        val cameraPermission = ContextCompat.checkSelfPermission(this@PermissionActivity, Manifest.permission.CAMERA)

        if(cameraPermission == PackageManager.PERMISSION_GRANTED){
            // 승인된 상태면 아래 코드 수행
            startProcess()
        }else{
            // 승인되지 않은 상태면 권한 요청
            requestPermission()
        }
    }

    fun startProcess(){
        Log.d("PERMISSION", "카메라 허용 완료")
        Toast.makeText(this@PermissionActivity, "카메라를 실행합니다", Toast.LENGTH_SHORT).show()
    }

    // 2) 사용자에게 승인 요청
    fun requestPermission(){
        // 두번째 인자가 배열의 형태인 이유는, 권한이 하나가 아니라 복수개일수도 있기 때문이다.
        ActivityCompat.requestPermissions(this@PermissionActivity, arrayOf(Manifest.permission.CAMERA), 99)
    }

    // 3) 사용자 승인 후 처리
    // 권한 팝업창에서 허용(ALLOW), 거부(DENY)를 할 때 호출되는 메소드
    override fun onRequestPermissionsResult(
        requestCode: Int,                   // 요청 시 보내주는 코드 (위에 보면 99 라고 되어 있는 부분)
        permissions: Array<out String>,     // 요청한 권한의 목록
        grantResults: IntArray              // 권한 목록에 대한 승인, 미승인 값 (위 권한 목록의 개수와 같은 수의 값이 전달됨)
    ) {
        when(requestCode){
            99 -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startProcess()
                }else{
                    finish()
                }
            }
        }
    }
}