package com.yhh.androidexam.imagePicker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.github.drjacky.imagepicker.ImagePicker
import com.yhh.androidexam.R

/**
    Image Picker 를 이용하여 이미지 선택
    많이 사용되는 Drjacky Image Picker 라이브러리 사용
    해당 라이브러리 의존성 추가 및 상세 정보는 아래 Github 참조

    https://github.com/Drjacky/ImagePicker?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=8208
 */

class ImagePickerActivity : AppCompatActivity() {
    lateinit var pickImgBtn: Button
    lateinit var imgView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)

        initView()

        // Pick Image 버튼 클릭시 이미지 피커가 열리게 처리
        pickImgBtn.setOnClickListener{
            pickingImage()
        }
    }

    // 이미지 피커가 닫히고 액티비티로 돌아오는 순간 호출되는 메소드
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            val fileUrl = data?.data
            imgView.setImageURI(fileUrl)
        }
    }

    // 뷰 초기화 메소드
    fun initView(){
        pickImgBtn = findViewById(R.id.pickImgBtn)
        imgView = findViewById(R.id.imgView)
    }

    // 이미지 피커를 열어주는 메소드
    fun pickingImage(){
        ImagePicker.with(this)
            .galleryOnly()
            .crop()	    			//Crop image(Optional), Check Customization for more option
            .cropOval()				//Allow dimmed layer to have a circle inside
            .compress(1024)			//Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }
}