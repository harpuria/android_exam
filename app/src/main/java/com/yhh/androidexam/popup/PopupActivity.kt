package com.yhh.androidexam.popup

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.yhh.androidexam.R

/**
 *      Popup
 *      안드로이드에서 팝업은 다이얼로그(대화상자)의 형태로 만들 수 있다.
 *
 *      1) 다이얼로그
 *         ㄴ 안드로이드에서 기본적으로 제공해주는 다이얼로그 팝업
 *         ㄴ 사실 엄밀히 말하면 다이얼로그는 팝업이랑 다르지만.. 매커니즘이 비슷해서 걍 같이 넣음
 *
 *      2) 커스텀 다이얼로그
 *         ㄴ 개발자가 임의의 모양으로 다이얼로그를 만들 수 있다
 *
 *      그 외에 액티비티 자체를 다이얼로그처럼 만드는 방법도 있다
 *
 */

class PopupActivity : AppCompatActivity() {
    lateinit var showDialogBtn:Button
    lateinit var showCustomDialogBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup)

        initView()

        // 기본 다이얼로그
        showDialogBtn.setOnClickListener {
            // 1) AlertDialog.Builder() 로 다이얼로그의 형태와 기능을 만들고
            val msgBuilder = AlertDialog.Builder(this@PopupActivity)
                .setTitle("제목")
                .setMessage("이것은 메시지")
                .setPositiveButton("예", DialogInterface.OnClickListener { dialog, which ->
                    Log.d("DIALOG_RESULT", "예를 눌렀음")
                })
                .setNegativeButton("아니오", DialogInterface.OnClickListener { dialog, which ->
                    Log.d("DIALOG_RESULT", "아니오를 눌렀음")
                })

            // 2) 빌더를 이용하여 다이얼로그를 생성한 뒤
            val msgDlg = msgBuilder.create()

            // 3) 다이얼로그를 보여준다
            msgDlg.show()
        }

        // 커스텀 다이얼로그
        showCustomDialogBtn.setOnClickListener {
            var dialog = Dialog(this@PopupActivity)
            dialog.setContentView(R.layout.dialog_signin)

            dialog.show()

            val dialogOkBtn = dialog.findViewById<Button>(R.id.dialogOkBtn)
            val dialogCancelBtn = dialog.findViewById<Button>(R.id.dialogCancelBtn)

            dialogOkBtn.setOnClickListener {
                Log.d("DIALOG_RESULT", "OK를 눌렀음")
                dialog.dismiss()
            }

            dialogCancelBtn.setOnClickListener {
                Log.d("DIALOG_RESULT", "CANCEL을 눌렀음")
                dialog.dismiss()
            }
        }
    }

    // 뷰 초기화
    fun initView(){
        showDialogBtn = findViewById(R.id.showDialogBtn)
        showCustomDialogBtn = findViewById(R.id.showCustomDialogBtn)
    }
}