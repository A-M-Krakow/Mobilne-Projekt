package com.example.anna.mobilne_projekt;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class QueryActivity extends AppCompatActivity {
    EditText phoneEditText;
    EditText emailEditText;
    EditText arDateEditText;
    EditText depDateEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        nameEditText.setText(intent.getStringExtra("name"));
        surnameEditText.setText(intent.getStringExtra("surname"));


        Boolean dogBool = (!intent.getStringExtra("dog").equals("0")) ? true : false;
        Boolean trainBool = (!intent.getStringExtra("train").equals("0")) ? true : false;
        Boolean airportBool = (!intent.getStringExtra("airport").equals("0")) ? true : false;
        Boolean cleaningBool = (!intent.getStringExtra("cleaning").equals("0")) ? true : false;


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
    }

    public void callClient(View view) {

        String phone = phoneEditText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    public void emailClient(View view) {
        String[] email = {emailEditText.getText().toString()};
        Intent intent = new Intent(Intent.ACTION_SEND);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");

        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Rezerwacja mieszkania w Krakowie od: " + arDateEditText.getText().toString() + " do: " + depDateEditText.getText().toString());
        String messageText =  "W związku z przesłanym zapytaniem o dostępność mieszkania w Krakowie w terminie  od: "
                + arDateEditText.getText().toString() + " do: " + depDateEditText.getText().toString() + " informuję, że ";


        switch(view.getId())
        {
            case R.id.emailButtonYes:
                messageText+=" w tym przedziale czasowym mieszkanie jest dostępne.  W celu dokonania rezerwacji, proszę o kontakt telefoniczny na numer: +48884680129.";
                break;
            case R.id.emailButtonNo:
                messageText+=" w tym przedziale czasowym nie ma możliwoście rezerwacji.";
                break;
            default:
                throw new RuntimeException("Unknow button ID");
        }

        messageText+=" Z poważaniem, Anna Madej.";

        intent.putExtra(Intent.EXTRA_TEXT,
                messageText);


        try {
            startActivity(Intent.createChooser(intent, getString(R.string.choosseEmaiClient)));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(QueryActivity.this,
                    "Brak aplikacji do wysyłania wiadomości!", Toast.LENGTH_SHORT).show();
        }

    }


}
