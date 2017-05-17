package com.example.anna.mobilne_projekt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        Intent intent = new Intent(this, QueriesActivity.class);
        startActivity(intent);
    }
}
