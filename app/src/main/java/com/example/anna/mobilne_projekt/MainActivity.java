package com.example.anna.mobilne_projekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.anna.mobilne_projekt.R;

public class MainActivity extends AppCompatActivity {

    Button queriesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queriesButton = (Button) findViewById(R.id.queriesButton);
    }

    public void showQueries(View view)
    {
        Context context;
        SharedPreferences sharedPref;
        context = this;
        sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if(sharedPref.contains(getString(R.string.adultPrice))) {
            Intent intent = new Intent(this, QueriesActivity.class);
            startActivity(intent);
        }
        else  {
            Toast.makeText(MainActivity.this,
                    R.string.noPricelist, Toast.LENGTH_SHORT).show();
            setPrices(null);
        }
    }

    public void makeBooking(View view)
    {
        Intent intent = new Intent(this, BookingActivity.class);
        startActivity(intent);
    }

    public void setPrices(View view)
    {
        Intent intent = new Intent(this, PricesActivity.class);
        startActivity(intent);
    }

    public void showBookings(View view)
    {
        Intent intent = new Intent(this, BookingsActivity.class);
        startActivity(intent);
    }

}
