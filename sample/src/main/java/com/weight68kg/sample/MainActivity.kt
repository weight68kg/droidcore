package com.weight68kg.sample

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.weight68kg.droidcore.utils.PermissionActivity
import com.weight68kg.droidcore.utils.notify.NotificationUtils
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by weight68kg on 2018/9/10.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_permission.setOnClickListener {
            PermissionActivity.start(this@MainActivity, "开启权限", object : PermissionActivity.PermissionCallback {
                override fun hasPermission() {
                    Toast.makeText(this@MainActivity, "有权限", Toast.LENGTH_SHORT).show()
                }

                override fun noPermission() {
                    Toast.makeText(this@MainActivity, "无", Toast.LENGTH_SHORT).show()
                }
            }, Manifest.permission.CAMERA)
        }

        btn_natification.setOnClickListener {

//            NotificationUtils(this).normalSingleLine(NotificationUtils.Builder().setTitle("这是一个通知标题").setContent("这是一个通知内容").build(), 1)
//            NotificationUtils(this,1).normalMoreLine(NotificationUtils.Builder().setTitle("这是一个多行通知标题").setContent("这是一个多行通知内容，很长很长的哦。这是一个多行通知内容，很长很长的哦。").build())

            val messageList = ArrayList<String>()
            messageList.add("文明,今晚有空吗？")
            messageList.add("晚上跟我一起去玩吧?")
            messageList.add("怎么不回复我？？我生气了！！")
            messageList.add("我真生气了！！！！！你听见了吗!")
            messageList.add("文明，别不理我！！！")
            NotificationUtils(this,1).messageList(NotificationUtils.Builder()
                    .setTitle("这是一个多行通知标题")
                    .setContent("这是一个多行通知内容，很长很长的哦。这是一个多行通知内容，很长很长的哦。")
                    .setMessageList(messageList)
                    .build())


        }

    }
}