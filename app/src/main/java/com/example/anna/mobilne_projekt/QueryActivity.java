package com.example.anna.mobilne_projekt;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class QueryActivity extends AppCompatActivity {
    Bundle bundle;
    EditText phoneEditText;
    EditText emailEditText;
    EditText arDateEditText;
    EditText depDateEditText;
    String currentQueryId;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        bundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);


        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        arDateEditText = (EditText) findViewById(R.id.arDateEditText);
        depDateEditText = (EditText) findViewById(R.id.depDateEditText);

        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText surnameEditText = (EditText) findViewById(R.id.surnameEditText);


        EditText daysEditText = (EditText) findViewById(R.id.daysEditText);
        EditText adultsEditText = (EditText) findViewById(R.id.adultsEditText);
        EditText babiesEditText = (EditText) findViewById(R.id.babiesEditText);


        CheckBox dogCheckBox = (CheckBox) findViewById(R.id.dogCheckBox);
        CheckBox trainCheckBox = (CheckBox) findViewById(R.id.trainCheckBox);
        CheckBox cleaningCheckBox = (CheckBox) findViewById(R.id.cleaningCheckBox);
        CheckBox airportCheckBox = (CheckBox) findViewById(R.id.airportCheckBox);
        Intent intent = getIntent();

        currentQueryId = bundle.getString("queryId");
        nameEditText.setText(bundle.getString("name"));
        surnameEditText.setText(bundle.getString("surname"));


        Boolean dogBool = (!bundle.getString("dog").equals("0"));
        Boolean trainBool = (!bundle.getString("train").equals("0"));
        Boolean airportBool = (!bundle.getString("airport").equals("0"));
        Boolean cleaningBool = (!bundle.getString("cleaning").equals("0"));


        dogCheckBox.setChecked(dogBool);
        trainCheckBox.setChecked(trainBool);
        airportCheckBox.setChecked(airportBool);
        cleaningCheckBox.setChecked(cleaningBool);
        arDateEditText.setText(bundle.getString("ar_date"));
        depDateEditText.setText(bundle.getString("dep_date"));
        daysEditText.setText(bundle.getString("days"));
        adultsEditText.setText(bundle.getString("adults"));
        babiesEditText.setText(bundle.getString("babies"));
        emailEditText.setText(bundle.getString("email"));
        phoneEditText.setText(bundle.getString("phone"));

    }

    public void callClient(View view) {

        String phone = phoneEditText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    public void emailClient(View view) {
        String[] email = {emailEditText.getText().toString()};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Rezerwacja mieszkania w Krakowie od: " + arDateEditText.getText().toString() + " do: " + depDateEditText.getText().toString());
        try {
            startActivity(Intent.createChooser(intent, getString(R.string.choosseEmaiClient)));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(QueryActivity.this,
                    R.string.noEmailApp, Toast.LENGTH_SHORT).show();
        }

    }

    public void bookThisQuery(View view){
        intent = new Intent(this, BookingActivity.class);
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.makingBooking)
                .setMessage(R.string.makeBooking)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new makeBookingFromQueryTask().execute();
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();


    }

    private class makeBookingFromQueryTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = ProgressDialog.show(QueryActivity.this, "",
                    getString(R.string.deletingQuery), true);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String reg_url = "http://a-m.netstrefa.pl/database/delete.php";
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
                data = URLEncoder.encode("queryId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(currentQueryId), "UTF-8");
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

                finish();
                intent.putExtras(bundle);
                startActivity(intent);

            } else {
                Toast.makeText(QueryActivity.this,
                        R.string.noInternetConn, Toast.LENGTH_SHORT).show();

            }
        }

    }


}
