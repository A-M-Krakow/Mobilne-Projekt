package com.example.anna.mobilne_projekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class QueryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText surnameEditText = (EditText) findViewById(R.id.surnameEditText);
        EditText arDateEditText = (EditText) findViewById(R.id.arDateEditText);
        EditText depDateEditText = (EditText) findViewById(R.id.depDateEditText);
        EditText daysEditText = (EditText) findViewById(R.id.daysEditText);
        EditText adultsEditText = (EditText) findViewById(R.id.adultsEditText);
        EditText babiesEditText = (EditText) findViewById(R.id.babiesEditText);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        EditText phoneEditText = (EditText) findViewById(R.id.phoneEditText);


        CheckBox dogCheckBox = (CheckBox) findViewById(R.id.dogCheckBox);
        CheckBox trainCheckBox = (CheckBox) findViewById(R.id.trainCheckBox);
        CheckBox cleaningCheckBox = (CheckBox) findViewById(R.id.cleaningCheckBox);
        CheckBox airportCheckBox = (CheckBox) findViewById(R.id.airportCheckBox);
        Intent intent = getIntent();
        nameEditText.setText(intent.getStringExtra("name"));
        surnameEditText.setText(intent.getStringExtra("surname"));


       Boolean dogBool = (!intent.getStringExtra("dog").equals("0"))?true:false;
       Boolean trainBool = (!intent.getStringExtra("train").equals("0"))?true:false;
       Boolean airportBool = (!intent.getStringExtra("airport").equals("0"))?true:false;
       Boolean cleaningBool = (!intent.getStringExtra("cleaning").equals("0"))?true:false;


        dogCheckBox.setChecked(dogBool);
        trainCheckBox.setChecked(trainBool);
        airportCheckBox.setChecked(airportBool);
        cleaningCheckBox.setChecked(cleaningBool);

        arDateEditText.setText(intent.getStringExtra("ar_date"));
        depDateEditText.setText(intent.getStringExtra("dep_date"));
        daysEditText.setText(intent.getStringExtra("days"));
        adultsEditText.setText(intent.getStringExtra("adults"));
        babiesEditText.setText(intent.getStringExtra("babies"));
        emailEditText.setText(intent.getStringExtra("email"));
        phoneEditText.setText(intent.getStringExtra("phone"));


      /*  ar_date.setText(intent.getStringExtra("KEY_NAME"));
        dep_date.setText(intent.getStringExtra("KEY_NAME"));
        days.setText(intent.getStringExtra("KEY_NAME"));



        dog.setText();
        train.setText(intent.getStringExtra("KEY_NAME"));
        airport.setText(intent.getStringExtra("KEY_NAME"));
        cleaning.setText(intent.getStringExtra("KEY_NAME")); */









    }
}
