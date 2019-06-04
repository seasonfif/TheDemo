package com.demo.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;

import com.demo.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 创建时间：2017年05月16日15:43 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class SimpleNotificationActivity extends BaseActivity{


  //Notification.FLAG_FOREGROUND_SERVICE    //表示正在运行的服务
  public static final String NOTIFICATION_TAG = "littlejie";
  public static final int DEFAULT_NOTIFICATION_ID = 1;

  private NotificationManager mNotificationManager;

  @Override public void setView() {
    setContentView(R.layout.activity_simple_notification);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    findViewById(R.id.btn_remove_all_notification).setOnClickListener(this);
    findViewById(R.id.btn_send_notification).setOnClickListener(this);
    findViewById(R.id.btn_remove_notification).setOnClickListener(this);
    findViewById(R.id.btn_send_notification_with_tag).setOnClickListener(this);
    findViewById(R.id.btn_remove_notification_with_tag).setOnClickListener(this);
    findViewById(R.id.btn_send_ten_notification).setOnClickListener(this);
    findViewById(R.id.btn_send_flag_no_clear_notification).setOnClickListener(this);
    findViewById(R.id.btn_send_flag_ongoing_event_notification).setOnClickListener(this);
    findViewById(R.id.btn_send_flag_auto_cancecl_notification).setOnClickListener(this);
    findViewById(R.id.btn_custom1).setOnClickListener(this);
    findViewById(R.id.btn_custom2).setOnClickListener(this);

    mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

    NotificationChannel mChannel = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      String id = "channel_1";
      String description = "123";
      int importance = NotificationManager.IMPORTANCE_HIGH;
      mChannel = new NotificationChannel(id, "123", importance);
      mChannel.setDescription(description);
      mChannel.enableLights(true);
      mChannel.setLightColor(Color.RED);
      mChannel.enableVibration(true);
      mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
      mNotificationManager.createNotificationChannel(mChannel);
    }
  }

  @Override
  public void onClick(View v) {
    super.onClick(v);
    switch (v.getId()) {
      case R.id.btn_remove_all_notification:
        //移除当前 Context 下所有 Notification,包括 FLAG_NO_CLEAR 和 FLAG_ONGOING_EVENT
        mNotificationManager.cancelAll();
        break;
      case R.id.btn_send_notification:
        //发送一个 Notification,此处 ID = 1
        sendNotification();
        break;
      case R.id.btn_remove_notification:
        //移除 ID = 1 的 Notification,注意:该方法只针对当前 Context。
        mNotificationManager.cancel(DEFAULT_NOTIFICATION_ID);
        break;
      case R.id.btn_send_notification_with_tag:
        //发送一个 ID = 1 并且 TAG = littlejie 的 Notification
        //注意:此处发送的通知与 sendNotification() 发送的通知并不冲突
        //因为此处的 Notification 带有 TAG
        sendNotificationWithTag();
        break;
      case R.id.btn_remove_notification_with_tag:
        //移除一个 ID = 1 并且 TAG = littlejie 的 Notification
        //注意:此处移除的通知与 NotificationManager.cancel(int id) 移除通知并不冲突
        //因为此处的 Notification 带有 TAG
        mNotificationManager.cancel(NOTIFICATION_TAG, DEFAULT_NOTIFICATION_ID);
        break;
      case R.id.btn_send_ten_notification:
        //连续发十条 Notification
        sendTenNotifications();
        break;
      case R.id.btn_send_flag_no_clear_notification:
        //发送 ID = 1, flag = FLAG_NO_CLEAR 的 Notification
        //下面两个 Notification 的 ID 都为 1,会发现 ID 相等的 Notification 会被最新的替换掉
        sendFlagNoClearNotification();
        break;
      case R.id.btn_send_flag_auto_cancecl_notification:
        sendFlagAutoCancelNotification();
        break;
      case R.id.btn_send_flag_ongoing_event_notification:
        sendFlagOngoingEventNotification();
        break;
      case R.id.btn_custom1:
        sendCustomNotification(22);
        break;
      case R.id.btn_custom2:
        sendCustomNotification(33);
        sendCustomNotification(34);
        sendCustomNotification(35);
        sendCustomNotification(36);
        break;
    }
  }

  private boolean mCanVibrate = true;
  private Handler mHandler;

  /**
   * 发送最简单的通知,该通知的ID = 1
   */
  private void sendNotification() {


    Notification notification;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      Notification.Builder builder = new Notification.Builder(this, "channel_1")
              .setSmallIcon(Icon.createWithBitmap(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_call)))
              .setContentTitle("Send Notification")
              .setContentText("Hi,My id is 1");
      notification = builder.build();
    }else{
      //这里使用 NotificationCompat 而不是 Notification ,因为 Notification 需要 API 16 才能使用
      //NotificationCompat 存在于 V4 Support Library
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
              .setSmallIcon(R.mipmap.ic_launcher)
              .setContentTitle("Send Notification")
              .setContentText("Hi,My id is 1");
      if (mCanVibrate){
        //某些机型并没有声音
          builder.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notification_alert))
            //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
          .setDefaults(Notification.DEFAULT_VIBRATE);
      }

      //0.5秒内的消息不重复提示
      if (mHandler == null) {
        mHandler = new Handler() {
          @Override public void handleMessage(Message msg) {
            mCanVibrate = true;
          }
        };
      }
      int what = 1; //随便给一个值
      mHandler.removeMessages(what);
      mHandler.sendEmptyMessageDelayed(what, 500);

      notification = builder.build();
    }
    mNotificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);
  }

  /**
   * 使用notify(String tag, int id, Notification notification)方法发送通知
   * 移除对应通知需使用 cancel(String tag, int id)
   */
  private void sendNotificationWithTag() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Send Notification With Tag")
        .setContentText("Hi,My id is 1,tag is " + NOTIFICATION_TAG)
        .setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND);
    mNotificationManager.notify(NOTIFICATION_TAG, DEFAULT_NOTIFICATION_ID, builder.build());
  }

  /**
   * 循环发送十个通知
   */
  private void sendTenNotifications() {
    for (int i = 0; i < 3; i++) {
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
          .setSmallIcon(R.mipmap.ic_launcher)
          .setContentTitle("Send Notification Batch")
          .setContentText("Hi,My id is " + i);
      mNotificationManager.notify(i, builder.build());
    }
  }

  /**
   * 设置FLAG_NO_CLEAR
   * 该 flag 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
   * Notification.flags属性可以通过 |= 运算叠加效果
   */
  private void sendFlagNoClearNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Send Notification Use FLAG_NO_CLEAR")
        .setContentText("Hi,My id is 1,i can't be clear.");
    Notification notification = builder.build();
    //设置 Notification 的 flags = FLAG_NO_CLEAR
    //FLAG_NO_CLEAR 表示该通知不能被状态栏的清除按钮给清除掉,也不能被手动清除,但能通过 cancel() 方法清除
    //flags 可以通过 |= 运算叠加效果
    notification.flags |= Notification.FLAG_NO_CLEAR;
    mNotificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);
  }

  /**
   * 设置FLAG_AUTO_CANCEL
   * 该 flag 表示用户单击通知后自动消失
   */
  private void sendFlagAutoCancelNotification() {
    //设置一个Intent,不然点击通知不会自动消失
    Intent resultIntent = new Intent(this, MainActivity.class);
    PendingIntent resultPendingIntent = PendingIntent.getActivity(
        this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher_browser)
        .setContentTitle("Send Notification Use FLAG_AUTO_CLEAR")
        .setContentText("Hi,My id is 1,i can be clear.")
        .setContentIntent(resultPendingIntent);
    Notification notification = builder.build();
    //设置 Notification 的 flags = FLAG_NO_CLEAR
    //FLAG_AUTO_CANCEL 表示该通知能被状态栏的清除按钮给清除掉
    //等价于 builder.setAutoCancel(true);
    notification.flags |= Notification.FLAG_AUTO_CANCEL;
    mNotificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);
  }

  /**
   * 设置FLAG_ONGOING_EVENT
   * 该 flag 表示发起正在运行事件（活动中）
   */
  private void sendFlagOngoingEventNotification() {
    NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("Send Notification Use FLAG_ONGOING_EVENT")
        .setContentText("Hi,My id is 1,i can't be clear.");
    Notification notification = builder.build();
    //设置 Notification 的 flags = FLAG_NO_CLEAR
    //FLAG_ONGOING_EVENT 表示该通知通知放置在正在运行,不能被手动清除,但能通过 cancel() 方法清除
    //等价于 builder.setOngoing(true);
    notification.flags |= Notification.FLAG_ONGOING_EVENT;
    mNotificationManager.notify(DEFAULT_NOTIFICATION_ID, notification);
  }

  private void sendCustomNotification(int id) {
    Notification.Builder builder = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      builder = new Notification.Builder(this, "channel_1");
    }

    builder.setAutoCancel(true);//设置是否点击通知后会自动消失
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      builder.setSmallIcon(Icon.createWithBitmap(BitmapFactory.decodeResource(getResources(), android.R.drawable.ic_menu_call)));
    }else{
      builder.setSmallIcon(R.mipmap.ic_launcher_browser);//使用RemoteViews时，设置的是状态栏中的小图标，必须要设置
    }
    Notification notification = builder.build();
    //通过xml创建RemoteViews，并且动态改变布局中的内容
    RemoteViews views = new RemoteViews(getPackageName(), R.layout.remote_notification);
    views.setImageViewResource(R.id.iv_icon, R.mipmap.ic_launcher_browser);
    views.setTextViewText(R.id.tv_title, "通知标题");
    views.setTextViewText(R.id.tv_content, "通知的内容");
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    String time = sdf.format(date);
    views.setTextViewText(R.id.tv_time, time);
    //这里需要注意，如果不设置 notification.bigContentView ，则由于通知的高度是固定的，如果remoteview的布局超过了其通知的高度，
    //就会有一部分显示不出来了
    notification.bigContentView = views;
    notification.contentView = views;
    //给整个通知设置一个拉起今日头条首页的PendingIntent
    Intent intentNotification = new Intent();
    intentNotification.setData(Uri.parse("snssdk143://home/news?growth_from=click_schema_aguya7"));
    intentNotification.setPackage("com.ss.android.article.news");
    intentNotification.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    PendingIntent pi = PendingIntent.getActivity(this, 123, intentNotification, PendingIntent.FLAG_CANCEL_CURRENT);
    notification.contentIntent = pi;
    mNotificationManager.notify(id, notification);
  }


  private void sendMyNotification(){

    Notification.Builder builder = new Notification.Builder(this);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      builder.setSmallIcon(Icon.createWithBitmap(null));
    }

    mNotificationManager.notify(999, builder.build());
  }
}
