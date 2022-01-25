package com.rxone.nimai;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EditDetails extends AppCompatActivity {
    static boolean removed=false;
    List<Dosage> dosagelist = new ArrayList<>();
    Dbhandler dbhandler = new Dbhandler(this,"remdb1",null,1);
    Dbhandlerdose dbhandlerdose = new Dbhandlerdose(this,"dsgdb1",null,1);
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_details);

        TextView doc=findViewById(R.id.textView19);
        TextView med=findViewById(R.id.textView21);
        TextView intrvl=findViewById(R.id.textView23);
        TextView dos=findViewById(R.id.textView24);
        TextView endd=findViewById(R.id.textView30);
        Button buttondel=findViewById(R.id.button4);


        Reminder r=dbhandler.getRemd(CustomAdapter.id);
        dosagelist=dbhandlerdose.listDosage(CustomAdapter.id);



        String dosetiming="";

        Iterator<Dosage> iter = dosagelist.iterator();

        while(iter.hasNext())
        {
            Dosage yp = iter.next();
           if((i++)%2==0)
               dosetiming=dosetiming+" ["+yp.getDosetime()+"]"+"\n";
           else

            dosetiming=dosetiming+" ["+yp.getDosetime()+"]";



        }






            String daysofweek="";
        if(r.getIntrvl().charAt(0)=='1') {
            daysofweek=daysofweek+"SUN "+"   ";
        }
        if(r.getIntrvl().charAt(1)=='1') {
            daysofweek=daysofweek+"MON "+"  ";
        }
        if(r.getIntrvl().charAt(2)=='1') {
            daysofweek=daysofweek+"TUE "+"   ";
        }
        if(r.getIntrvl().charAt(3)=='1') {
            daysofweek=daysofweek+"WED "+"   ";
        }
        if(r.getIntrvl().charAt(4)=='1') {
            daysofweek=daysofweek+"THU "+"   ";
        }
        if(r.getIntrvl().charAt(5)=='1') {
            daysofweek=daysofweek+"FRI "+"   ";
        }
        if(r.getIntrvl().charAt(6)=='1') {
            daysofweek=daysofweek+"SAT "+"   ";
        }



        doc.setText(r.getDoc());
        med.setText(r.getMed());
        intrvl.setText(daysofweek);
        dos.setText(dosetiming+"\n"+" "+r.getQty());
        if(r.getEnd_date().equals("20-12-2030"))
            endd.setText("No end date");
        else
        endd.setText(r.getEnd_date());


        buttondel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(ALARM_SERVICE);
                Intent myIntent = new Intent(getApplicationContext(), MyAlarm.class);
                myIntent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
                Iterator<Dosage> iter2 = dosagelist.iterator();
                while(iter2.hasNext())
                {
                    Dosage yp = iter2.next();
                    int delid = yp.getDos_id();

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            getApplicationContext(), delid, myIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                            pendingIntent.cancel();



                    alarmManager.cancel(pendingIntent);
                }
                dbhandler.deleteRem(CustomAdapter.id);

                Intent intent = new Intent(getApplicationContext(), Notiscrl.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("remmed",r.getMed());
                dbhandlerdose.deleteRem(CustomAdapter.id);
                removed=true;

                startActivity(intent);

            }
        });
    }


}