package com.rxone.nimai;


import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;


public class MyAlarm extends BroadcastReceiver {
    static MediaPlayer mp;
    NotificationManager nm;
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;
    //the method will be fired when the alarm is triggerred


    public void onReceive(Context context, Intent intent) {
        
        Bundle extras = intent.getExtras();
        String medname =extras.getString("idmedicine");
        String intervals =extras.getString("intervals");
        String enddate =extras.getString("enddate");
        String hrtime = extras.getString("hrtime");
        String mintime = extras.getString("mintime");
        String qty = extras.getString("qty");


        byte[] imgpath = intent.getByteArrayExtra("imgpath");

        Log.d("compareme", medname);

        int hr=Integer.parseInt(hrtime);
        int min=Integer.parseInt(mintime);












        Calendar now = Calendar.getInstance();

        Log.d("checkhrc",String.valueOf(now.get( Calendar.HOUR_OF_DAY)));
        Log.d("checkhra",String.valueOf( hr));
        Log.d("checkminc",String.valueOf( now.get(Calendar.MINUTE)));
        Log.d("checkmina",String.valueOf( min));

        int totaltc=(now.get(Calendar.HOUR_OF_DAY)*60)+now.get(Calendar.MINUTE);
        int totalta=(hr*60)+min;
        Log.d("checkc",String.valueOf( totaltc));
        Log.d("checka",String.valueOf( totalta));
//        if(totalta+20>=totaltc)
        if(5<6)
        {
            Log.d("checktrig","triggered");
            final SimpleDateFormat[] sdformat = {new SimpleDateFormat("dd-MM-yyyy")};
            Date d1= null;
            String curdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            try {
                d1 = sdformat[0].parse(curdate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date d2 = null;
            try {
                d2 = sdformat[0].parse(enddate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(d1.compareTo(d2) > 0) {


                Intent alarmIntent = new Intent(context, MyAlarm.class);

                int tag = Integer.parseInt(extras.getString("dose_id"));
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, tag, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                pendingIntent.cancel();
                manager.cancel(pendingIntent);
                Toast.makeText(context, "Alarm Canceled ", Toast.LENGTH_SHORT).show();

                Intent intents = new Intent(context, MainActivity.class);
                showNotification(context, "Medication Complete!", "Your Medication for "+medname+" has ended today", intents, tag);




            } else {


                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_WEEK);

                if(((intervals.charAt(0)=='1')&&(day==Calendar.SUNDAY))||((intervals.charAt(1)=='1')&&(day==Calendar.MONDAY))||((intervals.charAt(2)=='1')&&(day==Calendar.TUESDAY))||((intervals.charAt(3)=='1')&&(day==Calendar.WEDNESDAY))||((intervals.charAt(4)=='1')&&(day==Calendar.THURSDAY))||((intervals.charAt(5)=='1')&&(day==Calendar.FRIDAY))||((intervals.charAt(6)=='1')&&(day==Calendar.SATURDAY))) {


                    Toast.makeText(context, medname, Toast.LENGTH_SHORT).show();
                    mp = MediaPlayer.create(context, R.raw.alanoticine);

                    int tag = Integer.parseInt(extras.getString("dose_id"));

                    Intent intention = new Intent(context, TriggeredMed.class);
                    intention.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intention.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
                    intention.addFlags(FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                    intention.putExtra("medicinename", medname);
                    intention.putExtra("imgpath",imgpath);
                    intention.putExtra("qty",qty);
                    showNotification(context, "Take your medication", "Please take "+medname+"\n and complete your schedule.", intention, tag);
                    context.startActivity(intention);

                    // initialise outside listener to prevent looping
                    int[] count = {0};
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                        int maxCount = 3;

                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {

                            if(count[0] < maxCount) {
                                count[0]++;
                                mp.seekTo(0);
                                mp.start();
                            }
                        }});


                    mp.start();
                    Toast.makeText(context, "Alarm....", Toast.LENGTH_LONG).show();








                }
            }
        }











    }


    public void showNotification(Context context, String title, String message, Intent intent, int reqCode) {


        PendingIntent pendingIntent = PendingIntent.getActivity(context, reqCode, intent, PendingIntent.FLAG_ONE_SHOT);
        String CHANNEL_ID = "channel_name";// The id of the channel.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.pills)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel Name";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        notificationManager.notify(reqCode, notificationBuilder.build()); // 0 is the request code, it should be unique id

        Log.d("showNotification", "showNotification: " + reqCode);
    }

}