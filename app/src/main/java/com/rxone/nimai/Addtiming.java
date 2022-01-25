package com.rxone.nimai;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Addtiming extends AppCompatActivity {
    static int dosage;
    private EditText  editText1, editText2, editText3, editText4, editText5, editText6, updater;
    private ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6;
    private Switch aSwitch;
    private NumberPicker numberPicker;
    private Button button;
    private int doseid_ph=0;

    Intent i;

    private int mHour, mMinute;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtiming);
        dosage = Addmed.dosage;

        Dbhandler dbhandler = new Dbhandler(this,"remdb1",null,1);
        Dbhandlerdose dbhandlerdose = new Dbhandlerdose(this,"dsgdb1",null,1);
        doseid_ph=dbhandlerdose.getIdMax();
//        Toast.makeText(this,String.valueOf( Addmed.vr_reminderid), Toast.LENGTH_LONG).show();


        editText1 = findViewById(R.id.editTextTextPersonName7);
        editText2 = findViewById(R.id.editTextTextPersonName2);
        editText3 = findViewById(R.id.editTextTextPersonName3);
        editText4 = findViewById(R.id.editTextTextPersonName4);
        editText5 = findViewById(R.id.editTextTextPersonName5);
        editText6 = findViewById(R.id.editTextTextPersonName6);
        imageButton1 = findViewById(R.id.imageButton8);
        imageButton2 = findViewById(R.id.imageButton3);
        imageButton3 = findViewById(R.id.imageButton7);
        imageButton4 = findViewById(R.id.imageButton6);
        imageButton5 = findViewById(R.id.imageButton5);
        imageButton6 = findViewById(R.id.imageButton4);
        aSwitch = findViewById(R.id.switch5);
        numberPicker = findViewById(R.id.numberPicker3);
        button = findViewById(R.id.button2);


        editText1.setText("00:00 AM");
        if(dosage==1)
        {aSwitch.setEnabled(false);}


        numberPicker.setMaxValue(24 / dosage);
        numberPicker.setEnabled(false);


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alertDialog();
                    editText2.setEnabled(false);
                    editText3.setEnabled(false);
                    editText4.setEnabled(false);
                    editText5.setEnabled(false);
                    editText6.setEnabled(false);
                    imageButton2.setEnabled(false);
                    imageButton3.setEnabled(false);
                    imageButton4.setEnabled(false);
                    imageButton5.setEnabled(false);
                    imageButton6.setEnabled(false);
                    numberPicker.setEnabled(true);


                } else {
                    editText2.setEnabled(true);
                    editText3.setEnabled(true);
                    editText4.setEnabled(true);
                    editText5.setEnabled(true);
                    editText6.setEnabled(true);
                    imageButton2.setEnabled(true);
                    imageButton3.setEnabled(true);
                    imageButton4.setEnabled(true);
                    imageButton5.setEnabled(true);
                    imageButton6.setEnabled(true);
                    numberPicker.setEnabled(false);



                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbhandler.addReminder(new Reminder(1,Addmed.vr_reminderid,Addmed.vr_doctorname,Addmed.vr_medicinename,Addmed.vr_endddate,Addmed.vr_startdate,Addmed.vr_intervals,Thumbnail.imgpath,Addmed.vr_qty));
                for(int i=1;i<=dosage;i++) {

                    switch (i) {

                        case 1:
                            dbhandlerdose.addDose(new Dosage(Addmed.vr_reminderid, ++doseid_ph, editText1.getText().toString()));
                            preparealarm(editText1);


                            break;




                        case 2:
                            dbhandlerdose.addDose(new Dosage(Addmed.vr_reminderid, ++doseid_ph, editText2.getText().toString()));
                            preparealarm(editText2);

                            break;

                        case 3:
                            dbhandlerdose.addDose(new Dosage(Addmed.vr_reminderid, ++doseid_ph, editText3.getText().toString()));
                            preparealarm(editText3);

                            break;

                        case 4:
                            dbhandlerdose.addDose(new Dosage(Addmed.vr_reminderid, ++doseid_ph, editText4.getText().toString()));
                            preparealarm(editText4);

                            break;

                        case 5:
                            dbhandlerdose.addDose(new Dosage(Addmed.vr_reminderid, ++doseid_ph, editText5.getText().toString()));
                            preparealarm(editText5);

                            break;

                        case 6:

                            dbhandlerdose.addDose(new Dosage(Addmed.vr_reminderid, ++doseid_ph, editText6.getText().toString()));
                            preparealarm(editText6);

                            break;






                    }
                }










                Thumbnail.imgpath=null;

                Addmed.vr_doctorname="";
                Addmed.vr_medicinename="";
                Addmed.vr_endddate="";
                Addmed.vr_startdate="";
                Addmed.vr_intervals="";
                Addmed.dosage=1;
                Intent intent = new Intent(getApplicationContext(), Notiscrl.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);

            }
        });

        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                String time = editText1.getText().toString();


                SimpleDateFormat fmt = new SimpleDateFormat("hh:mm aa");
                Date date = null;
                try {
                    date = fmt.parse(time);
                } catch (ParseException e) {

                    e.printStackTrace();
                }

                SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm");

                String formattedTime = fmtOut.format(date);


                int sepPos = formattedTime.lastIndexOf(':');

                Integer timelen = formattedTime.length();
                Integer hr = Integer.parseInt(formattedTime.substring(0, sepPos));
                Integer min = Integer.parseInt(formattedTime.substring(sepPos + 1, timelen));
                hr = hr + numberPicker.getValue();


                if (dosage == 2) {
                    timeintervalmodi(editText2, hr, min, dosage-1);
                }
                if (dosage == 3) {
                    timeintervalmodi(editText2, hr, min, dosage - 2);
                    timeintervalmodi(editText3, hr, min, dosage - 1);
                }
                if (dosage == 4) {
                    timeintervalmodi(editText2, hr, min, dosage - 3);
                    timeintervalmodi(editText3, hr, min, dosage - 2);
                    timeintervalmodi(editText4, hr, min, dosage-1);

                }
                if (dosage == 5) {
                    timeintervalmodi(editText2, hr, min, dosage - 4);
                    timeintervalmodi(editText3, hr, min, dosage - 3);
                    timeintervalmodi(editText4, hr, min, dosage - 2);
                    timeintervalmodi(editText5, hr, min, dosage - 1);

                }
                if (dosage == 6) {
                    timeintervalmodi(editText2, hr, min, dosage - 5);
                    timeintervalmodi(editText3, hr, min, dosage - 4);
                    timeintervalmodi(editText4, hr, min, dosage - 3);
                    timeintervalmodi(editText5, hr, min, dosage - 2);
                    timeintervalmodi(editText6, hr, min, dosage - 1);

                }


            }
        });

//


        switch (dosage) {
            case 6:
                editText6.setVisibility(editText6.VISIBLE);
                imageButton6.setVisibility(imageButton6.VISIBLE);


            case 5:
                editText5.setVisibility(editText5.VISIBLE);
                imageButton5.setVisibility(imageButton5.VISIBLE);


            case 4:
                editText4.setVisibility(editText4.VISIBLE);
                imageButton4.setVisibility(imageButton4.VISIBLE);


            case 3:
                editText3.setVisibility(editText3.VISIBLE);
                imageButton3.setVisibility(imageButton3.VISIBLE);


            case 2:
                editText2.setVisibility(editText2.VISIBLE);
                imageButton2.setVisibility(imageButton2.VISIBLE);


            case 1:
                editText1.setVisibility(editText1.VISIBLE);
                imageButton1.setVisibility(imageButton1.VISIBLE);


        }


    }



    public void onClick(View v) {


        if (v == imageButton1)
            updater = editText1;
        if (v == imageButton2)
            updater = editText2;
        if (v == imageButton3)
            updater = editText3;
        if (v == imageButton4)
            updater = editText4;
        if (v == imageButton5)
            updater = editText5;
        if (v == imageButton6)
            updater = editText6;


        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        String time = hourOfDay + ":" + minute;

                        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                        Date date = null;
                        try {
                            date = fmt.parse(time);
                        } catch (ParseException e) {

                            e.printStackTrace();
                        }

                        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

                        String formattedTime = fmtOut.format(date);


                        updater.setText(formattedTime);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }

    public void timeintervalmodi(EditText a, int hr, int min, int stagestep) {
        hr = hr + (numberPicker.getValue() * (stagestep - 1));


        String time = hr + ":" + min;

        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = fmt.parse(time);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");

        String formattedTime = fmtOut.format(date);


        a.setText(formattedTime);
    }

    private void alertDialog() {
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

        if (24 / dosage >= 12) {
            dlgAlert.setMessage("First dose time" + " < " + 24 / dosage + " PM");
        } else {
            dlgAlert.setMessage("First dose time" + " < " + 24 / dosage + " AM");
        }


        dlgAlert.setTitle("Important");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(false);
        dlgAlert.create().show();
    }

    private void preparealarm(EditText a)
    {
        String time = a.getText().toString();


        SimpleDateFormat fmt = new SimpleDateFormat("hh:mm aa");
        Date date = null;
        try {
            date = fmt.parse(time);
        } catch (ParseException e) {

            e.printStackTrace();
        }

        SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm");

        String formattedTime = fmtOut.format(date);


        int sepPos = formattedTime.lastIndexOf(':');

        Integer timelen = formattedTime.length();
        Integer hr = Integer.parseInt(formattedTime.substring(0, sepPos));
        Integer min = Integer.parseInt(formattedTime.substring(sepPos + 1, timelen));



        Calendar calendar = Calendar.getInstance();

            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                    hr, min, 0);








        setAlarm(calendar.getTimeInMillis(),hr,min);
    }
    public void setAlarm(long time,int hr,int min) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


         i = new Intent(this, MyAlarm.class);
        Bundle extras = new Bundle();

        i.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        extras.putString("idmedicine",Addmed.vr_medicinename);
        extras.putString("intervals",Addmed.vr_intervals);
        extras.putString("enddate",Addmed.vr_endddate);
        extras.putString("dose_id",String.valueOf(doseid_ph));

        extras.putString("hrtime", String.valueOf(hr));
        extras.putString("mintime", String.valueOf(min));
        extras.putString("qty",Addmed.vr_qty);


        i.putExtras(extras);
        i.putExtra("imgpath", Thumbnail.imgpath);










        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),doseid_ph, i, PendingIntent.FLAG_UPDATE_CURRENT);
        Calendar calendarcheck= Calendar.getInstance();

        long diff = calendarcheck.getTimeInMillis()-time;
        if(diff>0) {
            time=time+86400000;
            Log.d("extended","extended");
        }
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                time , 24*60*60*1000, pi);
//        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pi);

        Log.d("comparing", String.valueOf(hr));

        Log.d("comparing", String.valueOf(min));



        Toast.makeText(this, "Alarm is set", Toast.LENGTH_SHORT).show();

        }


    }


