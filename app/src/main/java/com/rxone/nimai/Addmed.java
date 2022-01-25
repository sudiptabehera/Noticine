package com.rxone.nimai;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Addmed extends AppCompatActivity {
    private Switch edswitch;
    private Switch dateswitch;
    private AutoCompleteTextView autoCompleteTextViewDoc;
    private AutoCompleteTextView autoCompleteTextViewMed;
    private DatePicker datePicker;
    private String intervals="";
    static int vr_reminderid;
    static String vr_doctorname="";
    static String vr_medicinename="";
    static String vr_endddate="";
    static String vr_startdate="";
    static String vr_intervals="";
    static String vr_qty="";




    private CheckBox cboxsun;
    private CheckBox cboxmon;
    private CheckBox cboxtue;
    private CheckBox cboxwed;
    private CheckBox cboxthu;
    private CheckBox cboxfri;
    private CheckBox cboxsat;
    private NumberPicker dosPicker;

    private Button button;
    private TextView textViewqty;

    static int dosage=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmed);
        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Tablets","ml","Drop","Applications"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        Dbhandler dbhandler = new Dbhandler(this,"remdb1",null,1);



        String[] docs = getResources().getStringArray(R.array.docs);
        AutoCompleteTextView autoCompleteTextView1 = findViewById(R.id.autoCompleteTextView3);
        ArrayAdapter<String> adapter1 =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, docs);
        autoCompleteTextView1.setAdapter(adapter1);
        String[] countriesList = getResources().getStringArray(R.array.meds);
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countriesList);
        autoCompleteTextView.setAdapter(adapter3);

        edswitch = findViewById(R.id.switch1);
        cboxsun = findViewById(R.id.checkBox);
        cboxmon = findViewById(R.id.checkBox2);
        cboxtue = findViewById(R.id.checkBox3);
        cboxwed = findViewById(R.id.checkBox4);
        cboxthu = findViewById(R.id.checkBox5);
        cboxfri = findViewById(R.id.checkBox6);
        cboxsat = findViewById(R.id.checkBox7);
        dosPicker = findViewById(R.id.numberPicker1);
        textViewqty = findViewById(R.id.editTextNumberDecimal);
        button = findViewById(R.id.button2);
        datePicker = findViewById(R.id.simpleDatePicker);
        dateswitch = findViewById(R.id.switch2);
        autoCompleteTextViewDoc = findViewById(R.id.autoCompleteTextView3);
        autoCompleteTextViewMed = findViewById(R.id.autoCompleteTextView2);





        dateswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    datePicker.setEnabled(false);

                } else {
                    datePicker.setEnabled(true);

                }

            }
        });



        edswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cboxsun.setEnabled(false);
                    cboxmon.setEnabled(false);
                    cboxtue.setEnabled(false);
                    cboxwed.setEnabled(false);
                    cboxthu.setEnabled(false);
                    cboxfri.setEnabled(false);
                    cboxsat.setEnabled(false);

                } else {
                    cboxsun.setEnabled(true);
                    cboxmon.setEnabled(true);
                    cboxtue.setEnabled(true);
                    cboxwed.setEnabled(true);
                    cboxthu.setEnabled(true);
                    cboxfri.setEnabled(true);
                    cboxsat.setEnabled(true);

                }


            }
        });
        dosPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dosage = newVal;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intervals="";
                if(cboxsun.isChecked()&&!edswitch.isChecked())
                {
                    intervals=intervals+"1";
                }
                else{
                    intervals=intervals+"0";
                }
                if(cboxmon.isChecked()&&!edswitch.isChecked())
                {
                    intervals=intervals+"1";
                }
                else{
                    intervals=intervals+"0";
                }

                if(cboxtue.isChecked()&&!edswitch.isChecked())
                {
                    intervals=intervals+"1";
                }
                else{
                    intervals=intervals+"0";
                }
                if(cboxwed.isChecked()&&!edswitch.isChecked())
                {
                    intervals=intervals+"1";
                }
                else{
                    intervals=intervals+"0";
                }
                if(cboxthu.isChecked()&&!edswitch.isChecked())
                {
                    intervals=intervals+"1";
                }
                else{
                    intervals=intervals+"0";
                }
                if(cboxfri.isChecked()&&!edswitch.isChecked())
                {
                    intervals=intervals+"1";
                }
                else{
                    intervals=intervals+"0";
                }
                if(cboxsat.isChecked()&&!edswitch.isChecked())
                {
                    intervals=intervals+"1";
                }
                else{
                    intervals=intervals+"0";
                }
                if(edswitch.isChecked())
                {
                    intervals="1111111";
                }




                String enddate= datePicker.getDayOfMonth()+"-"+(datePicker.getMonth()+1)+"-"+datePicker.getYear();


                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date date = null;
                try {
                    date = (Date)formatter.parse(enddate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat newFormat = new SimpleDateFormat("dd-MM-yyyy");
                String edate;
                if(!dateswitch.isChecked())
                edate = newFormat.format(date);
                else
                    edate = "20-12-2030";

                String sdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

                vr_reminderid=dbhandler.getIdMax();

                vr_doctorname=autoCompleteTextViewDoc.getText().toString();
                vr_medicinename=autoCompleteTextViewMed.getText().toString();
                vr_endddate=edate;
                vr_startdate=sdate;
                vr_intervals=intervals;
                vr_qty=textViewqty.getText()+" "+dropdown.getSelectedItem().toString();

                if((vr_doctorname.equals(""))||(vr_endddate.equals(""))||(vr_intervals.equals(""))||(vr_medicinename.equals("")))
                {
                    Snackbar.make(v,"All fields are required!",Snackbar.LENGTH_SHORT).show();
                }
                else {


                    Intent intent = new Intent(getApplicationContext(), Addtiming.class);
                    startActivity(intent);
                }



            }
        });
    }

}