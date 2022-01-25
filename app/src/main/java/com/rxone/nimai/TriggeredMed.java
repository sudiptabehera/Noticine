package com.rxone.nimai;


import androidx.appcompat.app.AppCompatActivity;

import android.app.KeyguardManager;
import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TriggeredMed extends AppCompatActivity {
private ImageButton mute,taken,skip;
private TextView medname,qtyval;
private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);






        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {

            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(this.KEYGUARD_SERVICE);
            keyguardManager.requestDismissKeyguard(this, null);
        }
        else {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        setContentView(R.layout.activity_triggered_med);






        medname=findViewById(R.id.textView13);
        imageView=findViewById(R.id.imageView3);
        qtyval= findViewById(R.id.textView14);

        Intent intent = getIntent();

        String medName =intent.getStringExtra("medicinename");
        String vr_qtyval =intent.getStringExtra("qty");
        byte[] imgpath = getIntent().getByteArrayExtra("imgpath");


        medname.setText(medName);
        qtyval.setText(vr_qtyval);
        if(imgpath!=null)
        {
            Bitmap bmp = BitmapFactory.decodeByteArray(imgpath, 0, imgpath.length);
            imageView.setImageBitmap(bmp);
        }







        mute=findViewById(R.id.imageButton10);


        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        skip=findViewById(R.id.imageButton9);


        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        taken=findViewById(R.id.imageButton);


        taken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MyAlarm.mp.stop();
                finish();

            }
        });
    }

}