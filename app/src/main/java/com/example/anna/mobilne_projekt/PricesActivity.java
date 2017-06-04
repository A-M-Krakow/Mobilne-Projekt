package com.example.anna.mobilne_projekt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


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

        new SendPricesTask().execute();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private class SendPricesTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;
        String dorosly = adultPriceEditText.getText().toString();
        String dziecko = babyPriceEditText.getText().toString();
        String pies = dogPriceEditText.getText().toString();
        String pkp = trainPriceEditText.getText().toString();
        String lotnisko = airportPriceEditText.getText().toString();
        String sprzatanie = cleaningPriceEditText.getText().toString();


        protected void onPreExecute() {
            dialog = ProgressDialog.show(PricesActivity.this, "",
                    getString(R.string.sendingPrices), true);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String reg_url = "http://a-m.netstrefa.pl/update.php";
            URL url = null;
            HttpURLConnection httpURLConnection = null;
            String data = null;
            OutputStream os = null;
            BufferedWriter bufferedWriter = null;
            int statusCode = 0;

            try {
                url = new URL(reg_url);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                os = httpURLConnection.getOutputStream();
                data = URLEncoder.encode("dorosly", "UTF-8") + "=" + URLEncoder.encode(dorosly, "UTF-8") + "&" +
                        URLEncoder.encode("dziecko", "UTF-8") + "=" + URLEncoder.encode(dziecko, "UTF-8") + "&" +
                        URLEncoder.encode("pies", "UTF-8") + "=" + URLEncoder.encode(pies, "UTF-8") + "&" +
                        URLEncoder.encode("lotnisko", "UTF-8") + "=" + URLEncoder.encode(lotnisko, "UTF-8") + "&" +
                        URLEncoder.encode("sprzatanie", "UTF-8") + "=" + URLEncoder.encode(sprzatanie, "UTF-8") + "&" +
                        URLEncoder.encode("pkp", "UTF-8") + "=" + URLEncoder.encode(pkp, "UTF-8");
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                bufferedWriter.write(data);
                bufferedWriter.flush();
                statusCode = httpURLConnection.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (statusCode == 200) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                StringBuilder sb = new StringBuilder();
                String line;

                try {
                    while ((line = reader.readLine()) != null)
                    sb.append(line).append("\n");
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                result = sb.toString();
            }
            return result;

        }

        protected void onPostExecute(String result) {
            dialog.dismiss();
            if (result != "") {
                Toast.makeText(PricesActivity.this,
                        R.string.pricesSent, Toast.LENGTH_SHORT).show();

            } else {
                finish();
                Toast.makeText(PricesActivity.this,
                        R.string.noInternetConn, Toast.LENGTH_SHORT).show();

            }
        }

    }
}








