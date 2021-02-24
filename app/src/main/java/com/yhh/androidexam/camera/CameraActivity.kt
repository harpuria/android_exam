package com.yhh.androidexam.camera

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yhh.androidexam.R
import java.io.FileOutputStream
import java.text.SimpleDateFormat

/**
 *      - 카메라와 갤러리 앱에 접근해서 이미지 가져오기
 *      ㄴ ImagePicker 라이브러리 사용을 하면 더 편리하지만, 레거시한(?) 방법으로 카메라 찍고 사진 가져오기와 갤러리에서 사진가져오기 예제
 *
 */

class CameraActivity : AppCompatActivity() {
    val CAMERA_PERMISSION = arrayOf(Manifest.permission.CAMERA)
    val STORAGE_PERMISSION = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    val FLAG_PERM_CAMERA = 99
    val FLAG_PERM_STORAGE = 98
    val FLAG_REQ_CAMERA = 101
    val FLAG_REQ_STORAGE = 102

    lateinit var btnCamera_2:Button
    lateinit var btnGallery:Button
    lateinit var cameraImgView:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        initView()

        if(checkPermission(STORAGE_PERMISSION, FLAG_PERM_STORAGE)){
            setViews()
        }
    }

    fun initView(){
        btnCamera_2 = findViewById(R.id.btnCamera_2)
        btnGallery = findViewById(R.id.btnGallery)
        cameraImgView = findViewById(R.id.cameraImgView)
    }

    // 권한 체크
    fun checkPermission(permissions: Array<out String>, flag:Int):Boolean{
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
           for(permission in permissions){
               if(ContextCompat.checkSelfPermission(this@CameraActivity, permission) != PackageManager.PERMISSION_GRANTED){
                   ActivityCompat.requestPermissions(this@CameraActivity, permissions, flag)
                   return false
               }
           }
        }
        return true
    }

    // 권한 설정이 완료된 후 실행되는 메소드
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            FLAG_PERM_STORAGE ->{
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this@CameraActivity, "저장소 권한을 승인바람", Toast.LENGTH_SHORT).show()
                        finish()
                        return
                    }
                }
                setViews()
            }
            FLAG_PERM_CAMERA -> {
                for(grant in grantResults){
                    if(grant != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this@CameraActivity, "카메라 권한 승인바람", Toast.LENGTH_SHORT).show()
                        return
                    }
                }
                openCamera()
            }
        }
    }

    // 버튼 뷰 설정 (버튼 클릭할 때 카메라 앱 실행할 수 있도록)
    fun setViews(){
        btnCamera_2.setOnClickListener {
            openCamera()
        }

        btnGallery.setOnClickListener {
            openGallery()
        }
    }

    // 카메라 앱 실행
    fun openCamera(){
        if(checkPermission(CAMERA_PERMISSION, FLAG_PERM_CAMERA)){
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, FLAG_REQ_CAMERA)
        }
    }

    // 갤러리 앱 실행
    fun openGallery(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, FLAG_REQ_STORAGE)
    }

    // 카메라로 사진을 찍고 난 뒤 ImageView 에 찍은 화면을 보여주기
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                FLAG_REQ_CAMERA -> {
                    if(data?.extras?.get("data") != null){
                        val bitmap = data?.extras?.get("data") as Bitmap
                        val uri = saveImageFile(newFileName(), "image/jpg", bitmap)
                        cameraImgView.setImageURI(uri)
                    }
                }
                FLAG_REQ_STORAGE ->{
                    val uri = data?.data
                    cameraImgView.setImageURI(uri)
                }
            }
        }
    }

    // 파일 이름 생성 (시간기반)
    fun newFileName() : String{
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())

        return "$filename.jpg"
    }

    // 이미지 파일을 저장하기 위한 메소드
    fun saveImageFile(filename: String, mimeType: String, bitmap: Bitmap) : Uri?{
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)

        // 안드로이드 버전 체크
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            values.put(MediaStore.Images.Media.IS_PENDING, 1)
        }

        // MediaStore 에 파일을 등록하고, 등록된 URI 를 가져오기
        val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

        try{
            if(uri != null){
                var descriptor = contentResolver.openFileDescriptor(uri, "w")
                if(descriptor != null){
                    val fos = FileOutputStream(descriptor.fileDescriptor)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.close()

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        values.clear()
                        values.put(MediaStore.Images.Media.IS_PENDING, 0)
                        contentResolver.update(uri, values, null, null)
                    }
                }
            }
        }catch(e: java.lang.Exception){
            Log.e("File", "error = ${e.localizedMessage}")
        }

        return uri
    }
}