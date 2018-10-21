package com.weight68kg.sample

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.weight68kg.droidcore.utils.PermissionActivity
import com.weight68kg.droidcore.utils.notify.NotificationUtils
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import android.R.attr.button
import android.widget.RemoteViews
import android.os.SystemClock


fun String.lastChar(): Char = get(length - 1)
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

                        NotificationUtils(this, 1)
                    .normalSingleLine(NotificationUtils.Builder()
                    .setTitle("这是一个通知标题")
                    .setContent("这是一个通知内容")
                            )
            NotificationUtils(this,2)
                    .normalMoreLine(NotificationUtils.Builder()
                            .setTitle("这是一个多行通知标题")
                            .setContent("这是一个多行通知内容，很长很长的哦。这是一个多行通知内容，很长很长的哦。"))
//
            val messageList = ArrayList<String>()
            messageList.add("文明,今晚有空吗？")
            messageList.add("晚上跟我一起去玩吧?")
            messageList.add("怎么不回复我？？我生气了！！")
            messageList.add("我真生气了！！！！！你听见了吗!")
            messageList.add("文明，别不理我！！！")
            NotificationUtils(this,3).messageList(NotificationUtils.Builder()
                    .setTitle("这是一个消息列表标题")
                    .setContent("这是一个消息列表的内容")
                    .setMessageList(messageList)
                  )

            suspension()
            notify_progress()
            button()
            notify_bigPic()
            notify_customview()

        }

    }

    fun suspension() {
        //设置想要展示的数据内容
        val smallIcon = android.R.drawable.stat_notify_more
        val largeIcon = android.R.drawable.stat_notify_more
        val ticker = "您有一条新通知"
        val title = "范冰冰"
        val content = "文明，今晚在希尔顿酒店2016号房哈"

        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(this,
                0x123, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val lefticon = android.R.drawable.stat_notify_more
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

        NotificationUtils(this, 4)
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

        NotificationUtils(this, 6)
                .cordButton(NotificationUtils.Builder()
                        .setTitle("系统更新已下载完毕")
                        .setContent("Android 6.0.1")
                        .setTicker(ticker)
                        .setSmallIcon(smallIcon)
                        .setSound(true)
                        .setVibrate(true)
                        .setLight(false)
                        .setAction(NotificationCompat.Action(lefticon, lefttext, leftPendIntent)
                                , NotificationCompat.Action(righticon, righttext, rightPendIntent)))
    }


    /**
     * 高仿Android系统下载样式
     */
    private fun notify_progress() {
        //设置想要展示的数据内容
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val rightPendIntent = PendingIntent.getActivity(this,
                0x998, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val smallIcon = R.mipmap.shanzhi
        val ticker = "您有一条新通知"
        //实例化工具类，并且调用接口

        NotificationUtils(this, 5).progress(NotificationUtils.Builder()
                .setTitle("Android 6.0.1 下载")
                .setContent("正在下载中")
                .setTicker(ticker)
                .setSmallIcon(smallIcon)
                .setContentIntent(rightPendIntent)
                .setSound(false)
                .setLight(false)
                .setVibrate(true))
    }

    /**
     * 高仿系统截图通知
     */
    private fun notify_bigPic() {
        //设置想要展示的数据内容
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pIntent = PendingIntent.getActivity(this,
                0x45654, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val smallIcon = android.R.drawable.btn_default_small
        val largePic = R.mipmap.shanzhi
        val ticker = "您有一条新通知"
        val title = "已经抓取屏幕截图"
        val content = "触摸可查看您的屏幕截图"
        //实例化工具类，并且调用接口
        NotificationUtils(this, 7).bigIcon(NotificationUtils.Builder()
                .setTitle(title)
                .setContent(content)
                .setSmallIcon(smallIcon)
                .setLargeIcon(largePic)
                .setTicker(ticker)
                .setContentIntent(pIntent))
    }

    /**
     * 高仿应用宝
     */
    private fun notify_customview() {
        //设置想要展示的数据内容
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pIntent = PendingIntent.getActivity(this,
                0x12897, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val ticker = "您有一条新通知"

        //设置自定义布局中按钮的跳转界面
        val btnIntent = Intent(this, MainActivity::class.java)
        btnIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        //如果是启动activity，那么就用PendingIntent.getActivity，如果是启动服务，那么是getService
        val Pintent = PendingIntent.getActivity(this,
                SystemClock.uptimeMillis().toInt(), btnIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        // 自定义布局
        val remoteViews = RemoteViews(this.getPackageName(),
                R.layout.yyb_notification)
        remoteViews.setImageViewResource(R.id.image, R.drawable.yybao_bigicon)
        remoteViews.setTextViewText(R.id.title, "垃圾安装包太多")
        remoteViews.setTextViewText(R.id.text, "3个无用安装包，清理释放的空间")
        remoteViews.setOnClickPendingIntent(R.id.button, Pintent)//定义按钮点击后的动作
        val smallIcon = R.drawable.yybao_smaillicon
        //实例化工具类，并且调用接口
        NotificationUtils(this, 8).customView(NotificationUtils.Builder()
                .setRemoteViews(remoteViews)
                .setContentIntent(pIntent)
                .setSmallIcon(smallIcon)
                .setTicker(ticker))
    }
}