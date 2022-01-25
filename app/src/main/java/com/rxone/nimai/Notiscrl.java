package com.rxone.nimai;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Notiscrl extends AppCompatActivity {
    Dbhandler dbhandler = new Dbhandler(this,"remdb1",null,1);


    private RecyclerView recyclerView;


        List<Reminder> reminderlist = new ArrayList<>();








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notiscrl);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("NIMAI");
        toolbar.setTitleTextColor(Color.WHITE);


        reminderlist = dbhandler.listReminders();
        setSupportActionBar(toolbar);




        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerview);

        if(EditDetails.removed==true)
        {   String medname=getIntent().getExtras().getString("remmed");
            Snackbar.make(recyclerView,"Reminder for "+medname+" is deleted!",Snackbar.LENGTH_SHORT).show();
            EditDetails.removed=false;
        }
        if (reminderlist.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            CustomAdapter r = new CustomAdapter(this,reminderlist,recyclerView);
            recyclerView.setAdapter(r);
            }
        else {
            recyclerView.setVisibility(View.GONE);
            Snackbar.make(recyclerView,"No reminders \nCreate one now.",Snackbar.LENGTH_SHORT).show();
        }
    }
    public void openAddmed(View v)
    {
        Intent intent= new Intent(this,Thumbnail.class);
        startActivity(intent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.survey)
        {
            Toast.makeText(this, "Survey selected", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this,survey.class);
            startActivity(intent);


        }
        else if(id == R.id.about)
        {
            Toast.makeText(this, "About Section", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this,about.class);
            startActivity(intent);
        }
        else if(id == R.id.exit)
        {
            this.finish();
        }
        else if(id==R.id.bug) {
            Toast.makeText(this, "Reporting bug", Toast.LENGTH_SHORT).show();
            Intent intent= new Intent(this,Bug_report.class);
            startActivity(intent);
        }
        return true;
        }
}