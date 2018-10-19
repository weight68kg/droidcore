package com.weight68kg.sample

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.weight68kg.droidcore.utils.PermissionActivity
import com.weight68kg.droidcore.utils.notify.NotificationUtils
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.app.PendingIntent
import androidx.core.app.NotificationCompat


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
            }, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        btn_natification.setOnClickListener {

            //            NotificationUtils(this, 1)
//                    .normalSingleLine(NotificationUtils.Builder()
//                    .setTitle("这是一个通知标题")
//                    .setContent("这是一个通知内容")
//                            .setTicker("悬浮")
//                    .build())
//            NotificationUtils(this,1).normalMoreLine(NotificationUtils.Builder().setTitle("这是一个多行通知标题").setContent("这是一个多行通知内容，很长很长的哦。这是一个多行通知内容，很长很长的哦。").build())

//            val messageList = ArrayList<String>()
//            messageList.add("文明,今晚有空吗？")
//            messageList.add("晚上跟我一起去玩吧?")
//            messageList.add("怎么不回复我？？我生气了！！")
//            messageList.add("我真生气了！！！！！你听见了吗!")
//            messageList.add("文明，别不理我！！！")
//            NotificationUtils(this,1).messageList(NotificationUtils.Builder()
//                    .setTitle("这是一个消息列表标题")
//                    .setContent("这是一个消息列表的内容")
//                    .setMessageList(messageList)
//                    .build())

            suspension()
//            button()
        }

    }

    fun suspension() {
        //设置想要展示的数据内容
        val smallIcon = R.mipmap.shanzhi
        val largeIcon = R.mipmap.shanzhi
        val ticker = "您有一条新通知"
        val title = "范冰冰"
        val content = "文明，今晚在希尔顿酒店2016号房哈"

        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(this,
                0x123, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val lefticon = R.mipmap.shanzhi
        val lefttext = "回复"
        val leftIntent = Intent()
        leftIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val leftPendingIntent = PendingIntent.getActivity(this,
                0x332, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val righticon = R.mipmap.shanzhi
        val righttext = "拨打"
        val rightIntent = Intent(this, MainActivity::class.java)
        rightIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val rightPendingIntent = PendingIntent.getActivity(this,
                0x1212, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        NotificationUtils(this, 1)
                .headsUp(NotificationUtils.Builder()
                        .setTitle(title)
                        .setSmallIcon(smallIcon)
                        .setLargeIcon(largeIcon)
                        .setContent(content)
                        .setTicker(ticker)
                        .setContentIntent(pendingIntent)
                        .setAction(NotificationCompat.Action(lefticon, lefttext, leftPendingIntent), NotificationCompat.Action(righticon, righttext, rightPendingIntent)))
    }

    fun button() {
        //设置想要展示的数据内容
        val ticker = "您有一条新通知"
        val smallIcon = R.mipmap.shanzhi
        val lefticon = R.mipmap.shanzhi
        val lefttext = "以后再说"
        val leftIntent = Intent()
        leftIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        val leftPendIntent = PendingIntent.getActivity(this,
                0x54, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val righticon = R.mipmap.shanzhi
        val righttext = "安装"
        val rightIntent = Intent(this, PermissionActivity::class.java)
        rightIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val rightPendIntent = PendingIntent.getActivity(this,
                0x554, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        NotificationUtils(this, 2)
                .cordButton(NotificationUtils.Builder()
                        .setTitle("系统更新已下载完毕")
                        .setContent("Android 6.0.1")
                        .setTicker(ticker)
                        .setSmallIcon(smallIcon)
                        .setSound(true)
                        .setVibrate(true)
                        .setLight(false)
                        .setAction(NotificationCompat.Action(lefticon, lefttext, leftPendIntent)
                                , NotificationCompat.Action(righticon, righttext, rightPendIntent))
                )
    }
}