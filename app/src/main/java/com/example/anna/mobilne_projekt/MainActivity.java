package com.example.anna.mobilne_projekt;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.RingtoneManager;
import android.net.Uri;
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

public class MainActivity extends AppCompatActivity {

    Button queriesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queriesButton = (Button) findViewById(R.id.queriesButton);
        showNotification();
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

