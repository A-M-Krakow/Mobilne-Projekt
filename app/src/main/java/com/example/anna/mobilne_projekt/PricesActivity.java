package com.example.anna.mobilne_projekt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;


public class PricesActivity extends AppCompatActivity {
    private String url = "http://a-m.netstrefa.pl/update.php";
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
        String url = new String("http://a-m.netstrefa.pl/update.php");
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
        adultPriceEditText.setText(sharedPref.getString(getString(R.string.adultPrice), ""));
        babyPriceEditText.setText(sharedPref.getString(getString(R.string.babyPrice), ""));
        dogPriceEditText.setText(sharedPref.getString(getString(R.string.dogPrice), ""));
        trainPriceEditText.setText(sharedPref.getString(getString(R.string.trainPrice), ""));
        airportPriceEditText.setText(sharedPref.getString(getString(R.string.airportPrice), ""));
        cleaningPriceEditText.setText(sharedPref.getString(getString(R.string.cleaningPrice), ""));


    }

    public void setPrices(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.adultPrice), adultPriceEditText.getText().toString());
        editor.putString(getString(R.string.babyPrice), babyPriceEditText.getText().toString());
        editor.putString(getString(R.string.dogPrice), dogPriceEditText.getText().toString());
        editor.putString(getString(R.string.trainPrice), trainPriceEditText.getText().toString());
        editor.putString(getString(R.string.airportPrice), airportPriceEditText.getText().toString());
        editor.putString(getString(R.string.cleaningPrice), cleaningPriceEditText.getText().toString());
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}








