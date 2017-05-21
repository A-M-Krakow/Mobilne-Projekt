package com.example.anna.mobilne_projekt;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class PricesActivity extends AppCompatActivity {
    Context context;
    SharedPreferences sharedPref;
    EditText adultPriceEditText;
    EditText babyPriceEditText;
    EditText dogPriceEditText;
    EditText trainPriceEditText;
    EditText airportPriceEditText;
    EditText cleaningPriceEditText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);
        context = this;
        sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        adultPriceEditText = (EditText) findViewById(R.id.adultPriceEditText);
        babyPriceEditText = (EditText) findViewById(R.id.babyPriceEditText);
        dogPriceEditText = (EditText) findViewById(R.id.dogPriceEditText);
        trainPriceEditText = (EditText) findViewById(R.id.trainPriceEditText);
        airportPriceEditText = (EditText) findViewById(R.id.airportPriceEditText);
        cleaningPriceEditText = (EditText) findViewById(R.id.cleaningPriceEditText);



        adultPriceEditText.setText(sharedPref.getString(getString(R.string.adultPrice), "50"));
        babyPriceEditText.setText(sharedPref.getString(getString(R.string.babyPrice), "35"));
        dogPriceEditText.setText(sharedPref.getString(getString(R.string.dogPrice), "10"));
        trainPriceEditText.setText(sharedPref.getString(getString(R.string.trainPrice), "25"));
        airportPriceEditText.setText(sharedPref.getString(getString(R.string.airportPrice), "35"));
        cleaningPriceEditText.setText(sharedPref.getString(getString(R.string.cleaningPrice), "30"));

    }

    public void setPrices(View view)
    {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.adultPrice), adultPriceEditText.getText().toString());
        editor.putString(getString(R.string.babyPrice),  babyPriceEditText.getText().toString());
        editor.putString(getString(R.string.dogPrice),  dogPriceEditText.getText().toString());
        editor.putString(getString(R.string.trainPrice), trainPriceEditText.getText().toString());
        editor.putString(getString(R.string.airportPrice), airportPriceEditText.getText().toString());
        editor.putString(getString(R.string.cleaningPrice), cleaningPriceEditText.getText().toString());
        editor.commit();
    }






}
