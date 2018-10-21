package com.weight68kg.samplejava;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.RemoteViews;

import com.weight68kg.droidcore.utils.notify.NotificationUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new NotificationUtils(this, 1)
                .normalSingleLine(new NotificationUtils.Builder()
                        .setTitle("这是一个单行通知title")
                        .setContent("这是一个单行内容")
                        .setSmallIcon(android.R.mipmap.sym_def_app_icon));

        new NotificationUtils(this, 2)
                .normalMoreLine(new NotificationUtils.Builder()
                        .setTitle("这是一个多行通知")
                        .setContent("这是一个多行通知内容，很长很长的哦。这是一个多行通知内容，很长很长的哦。")
                        .setSmallIcon(android.R.mipmap.sym_def_app_icon));

        List<String> messageList = new ArrayList<>();
        messageList.add("文明,今晚有空吗？");
        messageList.add("晚上跟我一起去玩吧?");
        messageList.add("怎么不回复我？？我生气了！！");
        messageList.add("我真生气了！！！！！你听见了吗!");
        messageList.add("文明，别不理我！！！");
        new NotificationUtils(this, 3)
                .messageList(new NotificationUtils.Builder()
                        .setTitle("这是一个消息列表标题")
                        .setContent("这是一个消息列表的内容")
                        .setMessageList(messageList));

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 7, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        int smallIcon = android.R.drawable.btn_default_small;
        int largePic = R.mipmap.shanzhi;
        String ticker = "您有一条新通知";
        String title = "已经抓取屏幕截图";
        String content = "触摸可查看您的屏幕截图";
        new NotificationUtils(this, 7)
                .bigIcon(new NotificationUtils.Builder()
                        .setTitle(title)
                        .setContent(content)
                        .setSmallIcon(smallIcon)
                        .setLargeIcon(largePic)
                        .setTicker(ticker)
                        .setContentIntent(pendingIntent));



//        //设置想要展示的数据内容
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pIntent = PendingIntent.getActivity(this,
//                0x12897, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        String ticker = "您有一条新通知";
//
//        //设置自定义布局中按钮的跳转界面
//        Intent btnIntent =new Intent(this, MainActivity.class);
//        btnIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        //如果是启动activity，那么就用PendingIntent.getActivity，如果是启动服务，那么是getService
//        PendingIntent Pintent = PendingIntent.getActivity(this,
//                (int) SystemClock.uptimeMillis(), btnIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//
//        // 自定义布局
//        RemoteViews remoteViews =new  RemoteViews(this.getPackageName(),
//                R.layout.yyb_notification);
//        remoteViews.setImageViewResource(R.id.image, R.drawable.yybao_bigicon);
//        remoteViews.setTextViewText(R.id.title, "垃圾安装包太多");
//        remoteViews.setTextViewText(R.id.text, "3个无用安装包，清理释放的空间");
//        remoteViews.setOnClickPendingIntent(R.id.button, Pintent);//定义按钮点击后的动作
//        int smallIcon = R.drawable.yybao_smaillicon;
//        //实例化工具类，并且调用接口
//       new  NotificationUtils(this, 8).customView(new  NotificationUtils.Builder()
//                .setRemoteViews(remoteViews)
//                .setContentIntent(pIntent)
//                .setSmallIcon(smallIcon)
//                .setTicker(ticker));
    }
}
