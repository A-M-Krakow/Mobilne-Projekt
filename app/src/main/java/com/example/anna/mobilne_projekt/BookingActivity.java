package com.example.anna.mobilne_projekt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BookingActivity extends AppCompatActivity {
    Context context;
    SharedPreferences sharedPref;

    Bundle bundle;
    EditText phoneEditText;
    EditText emailEditText;
    EditText arDateEditText;
    EditText depDateEditText;
    EditText adultPriceEditText;
    EditText babiesPriceEditText;
    EditText trainPriceEditText;
    EditText airportPriceEditText;
    EditText dogPriceEditText;
    EditText cleaningPriceEditText;
    EditText daysEditText;
    EditText adultsEditText;
    EditText babiesEditText;
    Boolean dogBool;
    Boolean trainBool;
    Boolean airportBool;
    Boolean cleaningBool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        bundle = getIntent().getExtras();
        context = this;
        sharedPref = context.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

         adultPriceEditText = (EditText) findViewById(R.id.adultPriceEditText);
         babiesPriceEditText = (EditText) findViewById(R.id.babiesPriceEditText);
        trainPriceEditText = (EditText) findViewById(R.id.trainPriceEditText);
        airportPriceEditText = (EditText) findViewById(R.id.airportPriceEditText);
        dogPriceEditText = (EditText) findViewById(R.id.dogPriceEditText);
        cleaningPriceEditText = (EditText) findViewById(R.id.cleaningPriceEditText);





        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);

        phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        arDateEditText = (EditText) findViewById(R.id.arDateEditText);
        depDateEditText = (EditText) findViewById(R.id.depDateEditText);


        EditText surnameEditText = (EditText) findViewById(R.id.surnameEditText);

        daysEditText = (EditText) findViewById(R.id.daysEditText);
        adultsEditText = (EditText) findViewById(R.id.adultsEditText);
        babiesEditText = (EditText) findViewById(R.id.babiesEditText);


        CheckBox dogCheckBox = (CheckBox) findViewById(R.id.dogCheckBox);
        CheckBox trainCheckBox = (CheckBox) findViewById(R.id.trainCheckBox);
        CheckBox cleaningCheckBox = (CheckBox) findViewById(R.id.cleaningCheckBox);
        CheckBox airportCheckBox = (CheckBox) findViewById(R.id.airportCheckBox);
        Intent intent = getIntent();


        nameEditText.setText(bundle.getString("name"));
        surnameEditText.setText(bundle.getString("surname"));


        dogBool = (!bundle.getString("dog").equals("0")) ? true : false;
        trainBool = (!bundle.getString("train").equals("0")) ? true : false;
        airportBool = (!bundle.getString("airport").equals("0")) ? true : false;
        cleaningBool = (!bundle.getString("cleaning").equals("0")) ? true : false;


        dogCheckBox.setChecked(dogBool);
        trainCheckBox.setChecked(trainBool);
        airportCheckBox.setChecked(airportBool);
        cleaningCheckBox.setChecked(cleaningBool);

        arDateEditText.setText(bundle.getString("ar_date"));
        depDateEditText.setText(bundle.getString("dep_date"));
        daysEditText.setText(bundle.getString("days"));
        adultsEditText.setText(bundle.getString("adults"));
        adultPriceEditText.setText(sharedPref.getString(getString(R.string.adultPrice), "50"));
        babiesPriceEditText.setText(sharedPref.getString(getString(R.string.babyPrice), "55"));
        trainPriceEditText.setText(sharedPref.getString(getString(R.string.trainPrice), "30"));
        airportPriceEditText.setText(sharedPref.getString(getString(R.string.airportPrice), "40"));
        dogPriceEditText.setText(sharedPref.getString(getString(R.string.dogPrice), "15"));
        cleaningPriceEditText.setText(sharedPref.getString(getString(R.string.cleaningPrice), "15"));
        babiesEditText.setText(bundle.getString("babies"));

        emailEditText.setText(bundle.getString("email"));
        phoneEditText.setText(bundle.getString("phone"));

        countPrices(null);
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
            Toast.makeText(BookingActivity.this,
                    R.string.noEmailApp, Toast.LENGTH_SHORT).show();
        }

    }

    public void countPrices(View view){



        BigDecimal adultPrice = new BigDecimal(adultPriceEditText.getText().toString());
        BigDecimal babyPrice = new BigDecimal(babiesPriceEditText.getText().toString());
        BigDecimal dogPrice = new BigDecimal(dogPriceEditText.getText().toString());
        BigDecimal trainPrice = new BigDecimal(trainPriceEditText.getText().toString());
        BigDecimal airportPrice = new BigDecimal(airportPriceEditText.getText().toString());
        BigDecimal cleaningPrice = new BigDecimal(cleaningPriceEditText.getText().toString());
        BigDecimal numberOfAdults = new BigDecimal(adultsEditText.getText().toString());
        BigDecimal numberOfBabies = new BigDecimal(babiesEditText.getText().toString());
        BigDecimal numberOfDays = new BigDecimal(daysEditText.getText().toString());

        BigDecimal adultAmount = adultPrice.multiply(numberOfDays.multiply(numberOfAdults));
        BigDecimal babiesAmount = babyPrice.multiply(numberOfDays.multiply(numberOfBabies));
        BigDecimal dogAmount = new BigDecimal (0.00);
        if (dogBool)  dogAmount = dogPrice.multiply(numberOfDays);
        BigDecimal trainAmount = new BigDecimal (0.00);
        if (trainBool)  trainAmount = trainPrice;
        BigDecimal airportAmount = new BigDecimal (0.00);
        if (airportBool)  airportAmount = airportPrice;
        BigDecimal cleaningAmount = new BigDecimal (0.00);
        if (cleaningBool)  cleaningAmount = cleaningPrice;
        BigDecimal totalprice = adultAmount.add(babiesAmount.add(dogAmount.add(trainAmount.add(airportAmount.add(cleaningAmount)))));


        TextView totalPriceTextView = (TextView) findViewById(R.id.totalPriceTextView);
        totalPriceTextView.setText(String.valueOf(totalprice));

    }


    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        int id = v.getId();
        if (id == R.id.arDateChangeButton) {
            DatePickerFragment.setFlag(DatePickerFragment.FLAG_START_DATE);
            newFragment.show(getSupportFragmentManager(), "datePicker");

        } else if (id == R.id.depDateChangeButton) {
            DatePickerFragment.setFlag(DatePickerFragment.FLAG_END_DATE);
            newFragment.show(getSupportFragmentManager(), "datePicker");

        }

    }

}
