package com.example.anna.mobilne_projekt;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class BookingActivity extends AppCompatActivity {

    Bundle bundle;
    EditText phoneEditText;
    EditText emailEditText;
    EditText arDateEditText;
    EditText depDateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        bundle = getIntent().getExtras();

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
        nameEditText.setText(bundle.getString("name"));
        surnameEditText.setText(bundle.getString("surname"));


        Boolean dogBool = (!bundle.getString("dog").equals("0")) ? true : false;
        Boolean trainBool = (!bundle.getString("train").equals("0")) ? true : false;
        Boolean airportBool = (!bundle.getString("airport").equals("0")) ? true : false;
        Boolean cleaningBool = (!bundle.getString("cleaning").equals("0")) ? true : false;


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

    private class PriceList{
        Double adultPrice;
        Double babyPrice;
        Double dogPrice;
        Double trainPrice;
        Double airportPrice;
        Double cleaningPrice;

        public PriceList()
        {
            /*// Pobieranie cen z bazy danych
            String selectPricesQuery = "select *"+  KEY_NUMBER_OF_ACCOMODATIONS + ", " + KEY_CURR_PRICE +  " from " + TABLE_BOOKING_ACCOMODATIONS + " where " + KEY_BOOKING_ID + " = " + id + " and " +  KEY_ACC_NAME + " = " + "\"" + ADULT_ACCOMODATION + "\"";
            cursor= db.rawQuery(selectAdultBookingQuery, null);
            if (cursor != null) cursor.moveToFirst();
            booking.setNumberOfAdults(Integer.parseInt(cursor.getString(0)));
            booking.setAdultsCurrentPrice(cursor.getDouble(1)); */
        }

    }
}
