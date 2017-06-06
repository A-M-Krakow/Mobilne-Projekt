package com.example.anna.mobilne_projekt;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.NotificationManager;
import android.view.View;
import android.content.Context;

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

public class MainActivity extends AppCompatActivity {

    Button queriesButton;
    private String lastQueryId;
    Context context;
    SharedPreferences sharedPref;
    SharedPreferences sharedPrefLastQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        sharedPrefLastQuery = context.getSharedPreferences("lastQueryId", Context.MODE_PRIVATE);
        queriesButton = (Button) findViewById(R.id.queriesButton);
        lastQueryId = sharedPrefLastQuery.getString("lastQueryId", "0");
        checkNewQueries();
    }

    public void showNotification() {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, QueriesActivity.class), 0);
        Resources r = getResources();
        Uri uri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notification = new NotificationCompat.Builder(this)
                .setTicker(r.getString(R.string.notification_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(r.getString(R.string.notification_title))
                .setContentText(r.getString(R.string.notification_text))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setSound(uri)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    public void showQueries(View view)
    {
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

    public void checkNewQueries() {
        
        new checkNewQueriesTask().execute();
    }

    private class checkNewQueriesTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String reg_url = "http://a-m.netstrefa.pl/check.php";
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
                char[] currentQueryId;
                data = URLEncoder.encode("queryId", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(lastQueryId), "UTF-8");
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
            if (result != "") {
                showNotification();
            }
        }

    }


}

