package com.example.muslum.tabs_example;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Messenger;
import android.support.v4.app.NotificationCompat;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Intent.getIntent;
import static android.content.Intent.getIntentOld;
import static android.content.Intent.normalizeMimeType;

/**
 * Created by Muslum on 5.1.2016.
 */
public class Servisim extends Service {

    public static final long NOTIFY_INTERVAL = 60*1000; // 5 saniyede bir
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    ThreeFragment deneme;
    public static String tarih,saat,mesaj;
    public static String KEY_TEST;
    Bundle extras;
    Database mydb;


    public Servisim() {

    }
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        mydb = new Database(getApplicationContext());
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    public class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    // display toast
                    boolean gelen = mydb.kayitgor();

                    if (gelen == true) {
                        try
                        {
                            doNotify();
                        }
                        catch (Exception e)
                        {

                        }
                    }
                }

            });
        }
    }
    public String getDateTime() {
        // get date time in custom format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
        return sdf.format(new Date());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public void doNotify(){
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), Hakkimizda.class), 0);
        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
        nb.setSmallIcon(R.drawable.notify);
        nb.setSound(sound);
        nb.setContentTitle("AlarMesaj Bildirim");
        nb.setContentText("Mesajınız Başarılı Bir Şekilde Gönderildi");
        nb.setContentIntent(contentIntent);
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.notify);
        nb.setLargeIcon(bm);
        nb.setAutoCancel(true);

        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        nm.notify(100,nb.build());
    }
}
