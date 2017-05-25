package com.example.anna.mobilne_projekt;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookingsActivity extends ListActivity {
    DatabaseHandler db;
    private final String KEY_CLIENT = "klient";
    private final String KEY_QUERY = "zapytanie";
    private final String KEY_DESCRIPTION = "opis";

    private final String KEY_ID = "IdZapytania";
    private final String KEY_NAME = "ImieKlienta";
    private final String KEY_SURNAME = "NazwiskoKlienta";
    private final String KEY_EMAIL = "emailKlienta";
    private final String KEY_PHONE = "telefonKlienta";
    Bundle bundle;

    public class Booking {
        private String clientName;
        private String clientSurname;
        private String clientPhone;
        private String clientEmail;
        private int confirmed;
        private String arDate;
        private String depDate;
        private int numberOfAdults;
        private Double adultsCurrentPrice = 0.00;
        private int numberOfBabies;
        private Double babiesCurrentPrice = 0.00;
        private int dog;
        private Double dogCurrentPrice = 0.00;
        private int train;
        private Double trainCurrentPrice = 0.00;
        private int airport;
        private double airportCurrentPrice = 0.00;
        private int cleaning;
        private Double cleaningCurrentPrice = 0.00;


        public Booking(String clientName, String clientSurname, String clientPhone, String clientEmail, int confirmed, String arDate, String depDate, int numberOfAdults, Double adultsCurrentPrice, int numberOfBabies, Double babiesCurrentPrice, boolean dog, Double dogCurrentPrice, boolean train, Double trainCurrentPrice, boolean airport, Double airportCurrentPrice, boolean cleaning, double cleaningCurrentPrice) {


            this.clientName = clientName;
            this.clientSurname = clientSurname;
            this.clientPhone = clientPhone;
            this.clientEmail = clientEmail;
            this.confirmed = confirmed;
            this.arDate = arDate;
            this.depDate = depDate;
            this.numberOfAdults = numberOfAdults;
            this.adultsCurrentPrice = adultsCurrentPrice;
            this.numberOfBabies = numberOfBabies;
            this.babiesCurrentPrice = babiesCurrentPrice;

            if (dog == false) this.dog = 0;
            else this.dog = 1;

            this.dogCurrentPrice = dogCurrentPrice;

            if (train == false) this.train = 0;
            else this.train = 1;

            this.trainCurrentPrice = trainCurrentPrice;

            if (airport == false) this.airport = 0;
            else this.airport = 1;

            this.airportCurrentPrice = airportCurrentPrice;

            if (cleaning == false) this.cleaning = 0;
            else this.cleaning = 1;

            this.cleaningCurrentPrice = cleaningCurrentPrice;
        }

        public Booking() {

        }


        public void setClientName(String newClientName) {
            this.clientName = newClientName;
        }

        public void setClientSurname(String newClientSurname) {
            this.clientSurname = newClientSurname;
        }

        public void setClientPhone(String newClientPhone) {
            this.clientPhone = newClientPhone;
        }

        public void setClientEmail(String newClientEmail) {
            this.clientEmail = newClientEmail;
        }

        public void setConfirmation(int newConfirmed) {
            this.confirmed = newConfirmed;
        }

        public void setArDate(String newArDate) {
            this.arDate = newArDate;
        }

        public void setDepDate(String newDepDate) {
            this.depDate = newDepDate;
        }

        public void setNumberOfAdults(int newNumberOfAdults) {
            this.numberOfAdults = newNumberOfAdults;
        }

        public void setAdultsCurrentPrice(Double newAdultsCurrentPrice) {
            this.adultsCurrentPrice = newAdultsCurrentPrice;
        }

        public void setNumberOfBabies(int newNumberOfBabies) {
            this.numberOfBabies = newNumberOfBabies;
        }

        public void setBabiesCurrentPrice(Double newBabiesCurrentPrice) {
            this.babiesCurrentPrice = newBabiesCurrentPrice;
        }

        public void setDog(int newDog) {
            this.dog = newDog;
        }

        public void setDogCurrentPrice(Double newDogCurrentPrice) {
            this.dogCurrentPrice = newDogCurrentPrice;
        }

        public void setTrain(int newTrain) {
            this.train = newTrain;
        }

        public void setTrainCurrentPrice(Double newTrainCurrentPrice) {
            this.trainCurrentPrice = newTrainCurrentPrice;
        }

        public void setAirport(int newAirport) {
            this.airport = newAirport;
        }

        public void setAirportCurrentPrice(Double newAirportCurrentPrice) {
            this.airportCurrentPrice = newAirportCurrentPrice;
        }

        public void setCleaning(int newCleaning) {
            this.cleaning = newCleaning;
        }

        public void setCleaningCurrentPrice(Double newCleaningCurrentPrice) {
            this.cleaningCurrentPrice = newCleaningCurrentPrice;
        }

        public String getClientName() {
            return clientName;
        }

        public String getClientSurname() {
            return clientSurname;
        }

        public String getClientPhone() {
            return clientPhone;
        }

        public String getClientEmail() {
            return clientEmail;
        }

        public int getNumberOfAdults() {
            return numberOfAdults;
        }

        public Double getAdultsCurrentPrice() {
            return adultsCurrentPrice;
        }

        public int getNumberOfBabies() {
            return numberOfBabies;
        }

        public Double getBabiesCurrentPrice() {
            return babiesCurrentPrice;
        }

        public int getDog() {
            return dog;
        }

        public Double getDogCurrentPrice() {
            return dogCurrentPrice;
        }

        public int getTrain() {
            return train;
        }

        public Double getTrainCurrentPrice() {
            return trainCurrentPrice;
        }

        public int getAirport() {
            return airport;
        }

        public Double getAirportCurrentPrice() {
            return airportCurrentPrice;
        }

        public int getCleaning() {
            return cleaning;
        }

        public Double getCleaningCurrentPrice() {
            return cleaningCurrentPrice;
        }

        public int getConfirmed() {
            return confirmed;
        }

        public String getArDate() {
            return arDate;
        }

        public String getDepDate() {
            return depDate;
        }


    }


    private class DatabaseHandler extends SQLiteOpenHelper {

        // referencja do bazy
        private SQLiteDatabase db;

        //tabela z rezerwacjami
        private static final String TABLE_BOOKINGS = "Bookings";
        private static final String TABLE_ACCOMODATIONS = "Accomodations";
        private static final String TABLE_ADDITIONS = "Additions";
        private static final String TABLE_BOOKING_ACCOMODATIONS = "BookingAccomodations";
        private static final String TABLE_BOOKING_ADDITIONS = "BookingAdditions";

        //nazwy kolumn tabeli bookings
        private static final String KEY_BOOKING_ID = "BookingId";
        private static final String KEY_CLIENT_NAME = "ClientName";
        private static final String KEY_CLIENT_SURNAME = "ClientSurname";
        private static final String KEY_CLIENT_PHONE = "ClientPhone";
        private static final String KEY_CLIENT_EMAIL = "ClientEmail";
        private static final String KEY_CONFIRMED = "Confirmed";
        private static final String KEY_AR_DATE = "ArDate";
        private static final String KEY_DEP_DATE = "DepDate";

        //nazwy kolumn tabeli accommodations
        private static final String KEY_ACC_NAME = "AccName";
        private static final String KEY_ACC_PRICE = "AccPrice";


        //nazwy kolumn tabeli additions
        private static final String KEY_ADD_NAME = "AddName";
        private static final String KEY_ADD_PRICE = "AddPrice";

        //nazwy kolumn z aktualną ceną w  tabelach bookingAccomodations i bookingAdditions
        private static final String KEY_CURR_PRICE = "CurrentPrice";

        //nazwa kolumny z ilością osób w tabeli bookingAccomodations
        private static final String KEY_NUMBER_OF_ACCOMODATIONS = "NumberOfAccomodations";

        //nazwy pól istniejących w tabelach od początku (rodzaje noclegów i opcje dodatkowe)
        private static final String ADULT_ACCOMODATION = "dorosly";
        private static final String BABY_ACCOMODATION = "dziecko";
        private static final String DOG_ACCOMODATION = "pies";
        private static final String TRAIN_ADDITION = "pkp";
        private static final String AIRPORT_ADDITION = "lotnisko";
        private static final String CLEANING_ADDITION = "sprzatanie";

        //wersja bazy
        private static final int DATABASE_VERSION = 1;

        //nazwa bazy
        private static final String DATABASE_NAME = "bookingsDatabase";


        public DatabaseHandler(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            db = this.getWritableDatabase();
        }

        //tworzenie tabeli
        @Override
        public void onCreate(SQLiteDatabase db) {

            String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "(" + KEY_BOOKING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_CLIENT_NAME + " TEXT, " + KEY_CLIENT_SURNAME + " TEXT,  " + KEY_CLIENT_PHONE + " TEXT, " + KEY_CLIENT_EMAIL + " TEXT, " +
                    KEY_CONFIRMED + " INTEGER, " + KEY_AR_DATE + " TEXT," + KEY_DEP_DATE + " TEXT)";

            String CREATE_ACCOMODATIONS_TABLE = "CREATE TABLE " + TABLE_ACCOMODATIONS + "(" + KEY_ACC_NAME + " TEXT PRIMARY KEY)";

            String CREATE_ADDITIONS_TABLE = "CREATE TABLE " + TABLE_ADDITIONS + "(" + KEY_ADD_NAME + " TEXT PRIMARY KEY)";

            String CREATE_BOOKING_ACCOMODATIONS_TABLE = "CREATE TABLE " + TABLE_BOOKING_ACCOMODATIONS + " (" + KEY_BOOKING_ID + " INTEGER, " + KEY_ACC_NAME + " TEXT, " + KEY_CURR_PRICE + " REAL, " + KEY_NUMBER_OF_ACCOMODATIONS + " INT, PRIMARY KEY(" + KEY_BOOKING_ID + ", " + KEY_ACC_NAME + "), FOREIGN KEY( " + KEY_BOOKING_ID + " ) REFERENCES " + TABLE_BOOKINGS + "( " + KEY_BOOKING_ID + "), FOREIGN KEY( " + KEY_ACC_NAME + " ) REFERENCES " + TABLE_ACCOMODATIONS + "( " + KEY_ACC_NAME + "), UNIQUE ( " + KEY_BOOKING_ID + "," + KEY_ACC_NAME + "))";

            String CREATE_BOOKING_ADDITIONS_TABLE = "CREATE TABLE " + TABLE_BOOKING_ADDITIONS + " (" + KEY_BOOKING_ID + " INTEGER, " + KEY_ADD_NAME + " TEXT, " + KEY_CURR_PRICE + " REAL, PRIMARY KEY(" + KEY_BOOKING_ID + ", " + KEY_ADD_NAME + "), FOREIGN KEY( " + KEY_BOOKING_ID + " ) REFERENCES " + TABLE_BOOKINGS + "( " + KEY_BOOKING_ID + "), FOREIGN KEY( " + KEY_ADD_NAME + " ) REFERENCES " + TABLE_ADDITIONS + "( " + KEY_ADD_NAME + "))";

            db.execSQL(CREATE_BOOKINGS_TABLE);
            db.execSQL(CREATE_ACCOMODATIONS_TABLE);
            db.execSQL(CREATE_ADDITIONS_TABLE);
            db.execSQL(CREATE_BOOKING_ACCOMODATIONS_TABLE);
            db.execSQL(CREATE_BOOKING_ADDITIONS_TABLE);


            ContentValues accomodationOption = new ContentValues();
            accomodationOption.put(KEY_ACC_NAME, ADULT_ACCOMODATION);
            db.insert(TABLE_ACCOMODATIONS, null, accomodationOption);
            accomodationOption.clear();
            accomodationOption.put(KEY_ACC_NAME, BABY_ACCOMODATION);
            db.insert(TABLE_ACCOMODATIONS, null, accomodationOption);
            accomodationOption.clear();
            accomodationOption.put(KEY_ACC_NAME, DOG_ACCOMODATION);
            db.insert(TABLE_ACCOMODATIONS, null, accomodationOption);
            accomodationOption.clear();

            ContentValues additionalOption = new ContentValues();
            additionalOption.put(KEY_ADD_NAME, CLEANING_ADDITION);
            db.insert(TABLE_ADDITIONS, null, additionalOption);
            additionalOption.clear();
            additionalOption.put(KEY_ADD_NAME, TRAIN_ADDITION);
            db.insert(TABLE_ADDITIONS, null, additionalOption);
            additionalOption.clear();
            additionalOption.put(KEY_ADD_NAME, AIRPORT_ADDITION);
            db.insert(TABLE_ADDITIONS, null, additionalOption);
            additionalOption.clear();


        }

        //aktualizacja bazy
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //usunięcie tabeli z rezerwacjami

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);

            //ponowne utworzenie tabeli
            onCreate(db);

        }


        public Booking getBooking(int id) {
            Cursor cursor;

            // Pobieranie z bazy danych rezerwacji o podanym Id
            String selectBookingQuery = "select * from " + TABLE_BOOKINGS + " where " + KEY_BOOKING_ID + " = " + id;
            cursor = db.rawQuery(selectBookingQuery, null);

            if (cursor != null) cursor.moveToFirst();

            Booking booking = new Booking();

            booking.setClientName(cursor.getString(1));
            booking.setClientSurname(cursor.getString(2));
            booking.setClientPhone(cursor.getString(3));
            booking.setClientEmail(cursor.getString(4));
            booking.setConfirmation(Integer.parseInt(cursor.getString(5)));
            booking.setArDate(cursor.getString(6));
            booking.setDepDate(cursor.getString(7));

            // Pobieranie z bazy ilości i ceny dorosłych dla rezerwacji o podanym Id
            String selectAdultBookingQuery = "select " + KEY_NUMBER_OF_ACCOMODATIONS + ", " + KEY_CURR_PRICE + " from " + TABLE_BOOKING_ACCOMODATIONS + " where " + KEY_BOOKING_ID + " = " + id + " and " + KEY_ACC_NAME + " = " + "\"" + ADULT_ACCOMODATION + "\"";
            cursor = db.rawQuery(selectAdultBookingQuery, null);
            if (cursor != null) cursor.moveToFirst();
            booking.setNumberOfAdults(Integer.parseInt(cursor.getString(0)));
            booking.setAdultsCurrentPrice(cursor.getDouble(1));

            // Pobieranie z bazy ilości i ceny dzieci dla rezerwacji o podanym Id
            String selectBabiesBookingQuery = "select " + KEY_NUMBER_OF_ACCOMODATIONS + ", " + KEY_CURR_PRICE + " from " + TABLE_BOOKING_ACCOMODATIONS + " where " + KEY_BOOKING_ID + " = " + id + " and " + KEY_ACC_NAME + " = " + "\"" + BABY_ACCOMODATION + "\"";
            cursor = db.rawQuery(selectBabiesBookingQuery, null);
            if (cursor.getCount() > 0) {
                if (cursor != null) cursor.moveToFirst();
                booking.setNumberOfBabies(Integer.parseInt(cursor.getString(0)));
                booking.setBabiesCurrentPrice(cursor.getDouble(1));
            }

            // Pobieranie z bazy psa dla rezerwacji o podanym Id
            String selectDogBookingQuery = "select " + KEY_NUMBER_OF_ACCOMODATIONS + ", " + KEY_CURR_PRICE + " from " + TABLE_BOOKING_ACCOMODATIONS + " where " + KEY_BOOKING_ID + " = " + id + " and " + KEY_ACC_NAME + " = " + "\"" + DOG_ACCOMODATION + "\"";
            cursor = db.rawQuery(selectDogBookingQuery, null);
            if (cursor.getCount() > 0) {
                if (cursor != null) cursor.moveToFirst();
                booking.setDog(cursor.getInt(0));
                booking.setDogCurrentPrice(cursor.getDouble(1));
            }

            // Pobieranie z bazy transportu z dworca dla rezerwacji o podanym Id
            String selectTrainBookingQuery = "select " + KEY_CURR_PRICE + " from " + TABLE_BOOKING_ADDITIONS + " where " + KEY_BOOKING_ID + " = " + id + " and " + KEY_ADD_NAME + " = " + "\"" + TRAIN_ADDITION + "\"";
            cursor = db.rawQuery(selectTrainBookingQuery, null);
            if (cursor.getCount() > 0) {
                if (cursor != null) cursor.moveToFirst();
                booking.setTrain(1);
                booking.setTrainCurrentPrice(cursor.getDouble(0));
            }

            // Pobieranie z bazy transportu z lotniska dla rezerwacji o podanym Id
            String selectAirportBookingQuery = "select " + KEY_CURR_PRICE + " from " + TABLE_BOOKING_ADDITIONS + " where " + KEY_BOOKING_ID + " = " + id + " and " + KEY_ADD_NAME + " = " + "\"" + AIRPORT_ADDITION + "\"";
            cursor = db.rawQuery(selectAirportBookingQuery, null);
            if (cursor.getCount() > 0) {
                if (cursor != null) cursor.moveToFirst();
                booking.setAirport(1);
                booking.setAirportCurrentPrice(cursor.getDouble(0));
            }

            // Pobieranie z bazy sprzątania dla rezerwacji o podanym Id
            String selectCleaningBookingQuery = "select " + KEY_CURR_PRICE + " from " + TABLE_BOOKING_ADDITIONS + " where " + KEY_BOOKING_ID + " = " + id + " and " + KEY_ADD_NAME + " = " + "\"" + CLEANING_ADDITION + "\"";
            cursor = db.rawQuery(selectCleaningBookingQuery, null);
            if (cursor.getCount() > 0) {
                if (cursor != null) cursor.moveToFirst();
                booking.setCleaning(1);
                booking.setCleaningCurrentPrice(cursor.getDouble(0));
            }


            //zwracamy rezerwację
            return booking;
        }



        //aktualizacja danych rezerwacji
        public void updateBooking(Booking booking) {

        //aktualizacja danych w tabeli Bookings
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT_NAME, booking.getClientName());
        values.put(KEY_CLIENT_SURNAME, booking.getClientSurname());
        values.put(KEY_CLIENT_EMAIL, booking.getClientEmail());
        values.put(KEY_CLIENT_PHONE, booking.getClientPhone());
        values.put(KEY_CONFIRMED, booking.getConfirmed());
        values.put(KEY_AR_DATE, booking.getArDate());
        values.put(KEY_DEP_DATE, booking.getDepDate()); ContentValues bookingValues = new ContentValues();
        bookingValues.put(KEY_CLIENT_NAME, booking.getClientName());
        bookingValues.put(KEY_CLIENT_SURNAME, booking.getClientSurname());
        bookingValues.put(KEY_CLIENT_PHONE, booking.getClientPhone());
        bookingValues.put(KEY_CLIENT_EMAIL, booking.getClientEmail());
        bookingValues.put(KEY_AR_DATE, booking.getArDate());
        bookingValues.put(KEY_DEP_DATE, booking.getDepDate());
        bookingValues.put(KEY_CONFIRMED, booking.getConfirmed());

        db.update(TABLE_BOOKINGS, values, KEY_BOOKING_ID + " = " + bundle.getString("BookingId"), null);

            // aktualizacja dorosłych w rezerwacji
            ContentValues adultsAccomodationValues = new ContentValues();
            adultsAccomodationValues.put(KEY_CURR_PRICE, booking.getAdultsCurrentPrice());
            adultsAccomodationValues.put(KEY_NUMBER_OF_ACCOMODATIONS, booking.getNumberOfAdults());
            db.update(TABLE_BOOKING_ACCOMODATIONS, adultsAccomodationValues, KEY_BOOKING_ID + " = " + bundle.getString("BookingId") + " and " + KEY_ACC_NAME + " = " + "\"" + ADULT_ACCOMODATION + "\"", null);

            // aktualizacja dzieci w rezerwacji

            ContentValues babiesAccomodationValues = new ContentValues();
            babiesAccomodationValues.put(KEY_CURR_PRICE, booking.getBabiesCurrentPrice());
            babiesAccomodationValues.put(KEY_NUMBER_OF_ACCOMODATIONS, booking.getNumberOfBabies());
            db.update(TABLE_BOOKING_ACCOMODATIONS, babiesAccomodationValues, KEY_BOOKING_ID + " = " + bundle.getString("BookingId") + " and " + KEY_ACC_NAME + " = " + "\"" + BABY_ACCOMODATION + "\"", null);

            // aktualizacja psa w rezerwacji



            ContentValues dogAccomodationValues = new ContentValues();
            dogAccomodationValues.put(KEY_CURR_PRICE, booking.getDogCurrentPrice());
            dogAccomodationValues.put(KEY_NUMBER_OF_ACCOMODATIONS, booking.getDog());
            db.update(TABLE_BOOKING_ACCOMODATIONS, dogAccomodationValues, KEY_BOOKING_ID + " = " + bundle.getString("BookingId") + " and " + KEY_ACC_NAME + " = " + "\"" + DOG_ACCOMODATION + "\"", null);

            // aktualizacja transportu z dworca

                //sprawdzamy, czy w bazie danych pociąg jest dodany do rezerwacji
                Cursor cursor = null;
                String sql ="SELECT * FROM "+ TABLE_BOOKING_ADDITIONS +" WHERE " +  KEY_BOOKING_ID + "=" + bundle.getString("BookingId") + " and " + KEY_ADD_NAME + "=" + "\"" + TRAIN_ADDITION + "\"";
                cursor= db.rawQuery(sql,null);
            if (booking.getTrain() > 0) {           //  transport z pociągu ma być dodany do rezerwacji
                if (cursor.getCount() == 0) {            // pociągu nie było wcześniej w rezerwacji
                    // dodajemy pociąg do rezerwacji
                    ContentValues trainValues = new ContentValues();
                    trainValues.put(KEY_BOOKING_ID, bundle.getString("BookingId"));
                    trainValues.put(KEY_ADD_NAME, TRAIN_ADDITION);
                    trainValues.put(KEY_CURR_PRICE, booking.getTrainCurrentPrice());
                    db.insert(TABLE_BOOKING_ADDITIONS, null, trainValues);
                }
            }
            else {                  // transportu z pociągu nie ma być w rezerwacji
                    if (cursor.getCount() > 0) {           // pociąg był wcześniej w rezerwacji
                        // usuwamy pociąg z rezerwacji
                        db.delete(TABLE_BOOKING_ADDITIONS,KEY_BOOKING_ID + " = " + bundle.getString("BookingId") + " and " + KEY_ADD_NAME + " = " + "\"" + TRAIN_ADDITION + "\"", null);
                    }
                }

            // aktualizacja transportu z lotniska

            //sprawdzamy, czy w bazie danych lotnisko jest dodane do rezerwacji
            cursor = null;
            sql ="SELECT * FROM "+ TABLE_BOOKING_ADDITIONS +" WHERE " +  KEY_BOOKING_ID + "=" + bundle.getString("BookingId") + " and " + KEY_ADD_NAME + "=" + "\"" + AIRPORT_ADDITION + "\"";
            cursor= db.rawQuery(sql,null);

            if (booking.getAirport() > 0) {           //  transport z lotniska ma być dodany do rezerwacji

                if (cursor.getCount() == 0) {            // lotniska nie było wcześniej w rezerwacji

                    // dodajemy lotnisko do rezerwacji
                    ContentValues airportValues = new ContentValues();
                    airportValues.put(KEY_BOOKING_ID, bundle.getString("BookingId"));
                    airportValues.put(KEY_ADD_NAME, AIRPORT_ADDITION);
                    airportValues.put(KEY_CURR_PRICE, booking.getAirportCurrentPrice());
                    db.insert(TABLE_BOOKING_ADDITIONS, null, airportValues);
                }
            }
            else {                  // transportu z lotniska nie ma być w rezerwacji

                if (cursor.getCount() > 0) {           // lotnisko było wcześniej w rezerwacji
                    // usuwamy lotnisko z rezerwacji
                    db.delete(TABLE_BOOKING_ADDITIONS,KEY_BOOKING_ID + " = " + bundle.getString("BookingId") + " and " + KEY_ADD_NAME + " = " + "\"" + AIRPORT_ADDITION + "\"", null);
                }
            }

            //sprawdzamy, czy w bazie danych sprzątanie jest dodane do rezerwacji
            cursor = null;
            sql ="SELECT * FROM " + TABLE_BOOKING_ADDITIONS +" WHERE " +  KEY_BOOKING_ID + "=" + bundle.getString("BookingId") + " and " + KEY_ADD_NAME + "=" + "\"" + CLEANING_ADDITION + "\"";
            cursor= db.rawQuery(sql,null);

            if (booking.getCleaning() > 0) {           //  sprzątanie ma być dodane do rezerwacji

                if (cursor.getCount() == 0) {            // sprzątania nie było wcześniej w rezerwacji

                    // dodajemy sprzątanie do rezerwacji
                    ContentValues cleaningValues = new ContentValues();
                    cleaningValues.put(KEY_BOOKING_ID, bundle.getString("BookingId"));
                    cleaningValues.put(KEY_ADD_NAME, CLEANING_ADDITION);
                    cleaningValues.put(KEY_CURR_PRICE, booking.getCleaningCurrentPrice());
                    db.insert(TABLE_BOOKING_ADDITIONS, null, cleaningValues);
                }
            }
            else {                  // sprzątania nie ma być w rezerwacji

                if (cursor.getCount() > 0) {           // sprzątanie było wcześniej w rezerwacji
                    // usuwamy sprzątanie z rezerwacji
                    db.delete(TABLE_BOOKING_ADDITIONS,KEY_BOOKING_ID + " = " + bundle.getString("BookingId") + " and " + KEY_ADD_NAME + " = " + "\"" + CLEANING_ADDITION + "\"", null);
                }
            }
    }

    /* public void deleteBooking(Booking booking) {
        db.delete(TABLE_BOOKINGS, KEY_BOOKING_ID + " = ? ", new String[] {String.valueOf(booking.getBookingId())});
    } */

        public void addBooking(Booking booking) {

            // dodawanie danych rezerwacji
            ContentValues bookingValues = new ContentValues();
            bookingValues.put(KEY_CLIENT_NAME, booking.getClientName());
            bookingValues.put(KEY_CLIENT_SURNAME, booking.getClientSurname());
            bookingValues.put(KEY_CLIENT_PHONE, booking.getClientPhone());
            bookingValues.put(KEY_CLIENT_EMAIL, booking.getClientEmail());
            bookingValues.put(KEY_AR_DATE, booking.getArDate());
            bookingValues.put(KEY_DEP_DATE, booking.getDepDate());
            bookingValues.put(KEY_CONFIRMED, booking.getConfirmed());

            long insertedId = db.insert(TABLE_BOOKINGS, null, bookingValues);

            // dodawanie dorosłych do rezerwacji
            ContentValues adultsAccomodationValues = new ContentValues();
            adultsAccomodationValues.put(KEY_BOOKING_ID, insertedId);
            adultsAccomodationValues.put(KEY_ACC_NAME, ADULT_ACCOMODATION);
            adultsAccomodationValues.put(KEY_CURR_PRICE, booking.getAdultsCurrentPrice());
            adultsAccomodationValues.put(KEY_NUMBER_OF_ACCOMODATIONS, booking.getNumberOfAdults());
            db.insert(TABLE_BOOKING_ACCOMODATIONS, null, adultsAccomodationValues);


            // dodawanie dzieci do rezerwacji

                ContentValues babiesAccomodationValues = new ContentValues();
                babiesAccomodationValues.put(KEY_BOOKING_ID, insertedId);
                babiesAccomodationValues.put(KEY_ACC_NAME, BABY_ACCOMODATION);
                babiesAccomodationValues.put(KEY_CURR_PRICE, booking.getBabiesCurrentPrice());
                babiesAccomodationValues.put(KEY_NUMBER_OF_ACCOMODATIONS, booking.getNumberOfBabies());
                db.insert(TABLE_BOOKING_ACCOMODATIONS, null, babiesAccomodationValues);


            // dodawanie psa do rezerwacji

                ContentValues dogAccomodationValues = new ContentValues();
                dogAccomodationValues.put(KEY_BOOKING_ID, insertedId);
                dogAccomodationValues.put(KEY_ACC_NAME, DOG_ACCOMODATION);
                dogAccomodationValues.put(KEY_CURR_PRICE, booking.getDogCurrentPrice());
                dogAccomodationValues.put(KEY_NUMBER_OF_ACCOMODATIONS, booking.getDog());
                db.insert(TABLE_BOOKING_ACCOMODATIONS, null, dogAccomodationValues);



            // dodawanie do rezerwacji transportu z dworca

            if (booking.getTrain() > 0) {
                ContentValues trainValues = new ContentValues();
                trainValues.put(KEY_BOOKING_ID, insertedId);
                trainValues.put(KEY_ADD_NAME, TRAIN_ADDITION);
                trainValues.put(KEY_CURR_PRICE, booking.getTrainCurrentPrice());
                db.insert(TABLE_BOOKING_ADDITIONS, null, trainValues);
            }


            // dodawanie do rezerwacji transportu z lotniska

            if (booking.getAirport() > 0) {
                ContentValues airportValues = new ContentValues();
                airportValues.put(KEY_BOOKING_ID, insertedId);
                airportValues.put(KEY_ADD_NAME, AIRPORT_ADDITION);
                airportValues.put(KEY_CURR_PRICE, booking.getAirportCurrentPrice());
                db.insert(TABLE_BOOKING_ADDITIONS, null, airportValues);
            }


            // dodawanie do rezerwacji sprzątania
            if (booking.getCleaning() > 0) {

                ContentValues cleaningValues = new ContentValues();
                cleaningValues.put(KEY_BOOKING_ID, insertedId);
                cleaningValues.put(KEY_ADD_NAME, CLEANING_ADDITION);
                cleaningValues.put(KEY_CURR_PRICE, booking.getCleaningCurrentPrice());
                db.insert(TABLE_BOOKING_ADDITIONS, null, cleaningValues);
            }



        }

        public ArrayList<HashMap<String, String>> showAllBookings() {
            List<Booking> bookingList = new ArrayList<Booking>();
            String selectQuery = "Select * from " + TABLE_BOOKINGS + " order by " + KEY_AR_DATE + " desc";
            Cursor cursor = db.rawQuery(selectQuery, null);
            ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

            //pętla przez wszystkie elementy
            if (cursor.moveToFirst()) {
                do {
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put(KEY_QUERY, "od: " + cursor.getString(6) + " do: " + cursor.getString(7) + " (" +cursor.getString(2) + ")");
                    map.put(KEY_ID, String.valueOf(cursor.getInt(0)));
                    menuItems.add(map);

                } while (cursor.moveToNext());
            }
            return menuItems;

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);


        db = new DatabaseHandler(this);

        Intent intent = getIntent();
        bundle = getIntent().getExtras();

        if (bundle != null) {

            Booking booking = new Booking(bundle.getString("name"),
                    bundle.getString("surname"),
                    bundle.getString("phone"),
                    bundle.getString("email"),
                    1,
                    bundle.getString("ar_date"),
                    bundle.getString("dep_date"),
                    Integer.valueOf(bundle.getString("adults")),
                    Double.valueOf(bundle.getString("adultsPrice")),
                    Integer.valueOf(bundle.getString("babies")),
                    Double.valueOf(bundle.getString("babiesPrice")),
                    bundle.getBoolean("dog"),
                    Double.valueOf(bundle.getString("dogPrice")),
                    bundle.getBoolean("train"),
                    Double.valueOf(bundle.getString("trainPrice")),
                    bundle.getBoolean("airport"),
                    Double.valueOf(bundle.getString("airportPrice")),
                    bundle.getBoolean("cleaning"),
                    Double.valueOf(bundle.getString("cleaningPrice"))
            );
            if(bundle.containsKey("BookingId")) db.updateBooking(booking);
            else db.addBooking(booking);
        }

        ListAdapter adapter = new SimpleAdapter(BookingsActivity.this, db.showAllBookings(), R.layout.list_item, new String[]{KEY_QUERY, KEY_ID}, new int[]{R.id.queryTextViev, R.id.queryId});
        BookingsActivity.this.setListAdapter(adapter);
    }

    public void onItemClick(View view) {
        ViewGroup row = (ViewGroup) view.getParent();
        TextView tekst = (TextView) row.getChildAt(0);
        int idRezerwacji = Integer.parseInt(tekst.getText().toString());
        Intent intent = new Intent(this, BookingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("BookingId", String.valueOf(idRezerwacji));
        bundle.putString("name", db.getBooking(idRezerwacji).getClientName());
        bundle.putString("surname", db.getBooking(idRezerwacji).getClientSurname());
        bundle.putString("ar_date", db.getBooking(idRezerwacji).getArDate());
        bundle.putString("dep_date", db.getBooking(idRezerwacji).getDepDate());
        bundle.putString("adults", String.valueOf(db.getBooking(idRezerwacji).getNumberOfAdults()));
        bundle.putString("adultsPrice", String.valueOf((db.getBooking(idRezerwacji).getAdultsCurrentPrice())));
        bundle.putString("babies", String.valueOf(db.getBooking(idRezerwacji).getNumberOfBabies()));
        bundle.putString("babiesPrice", String.valueOf((db.getBooking(idRezerwacji).getBabiesCurrentPrice())));
        bundle.putString("dog",  String.valueOf(db.getBooking(idRezerwacji).getDog()));
        bundle.putString("dogPrice", String.valueOf((db.getBooking(idRezerwacji).getDogCurrentPrice())));
        bundle.putString("train",   String.valueOf(db.getBooking(idRezerwacji).getTrain()));
        bundle.putString("trainPrice", String.valueOf((db.getBooking(idRezerwacji).getTrainCurrentPrice())));
        bundle.putString("airport",  String.valueOf(db.getBooking(idRezerwacji).getAirport()));
        bundle.putString("airportPrice", String.valueOf((db.getBooking(idRezerwacji).getAirportCurrentPrice())));
        bundle.putString("cleaning",   String.valueOf(db.getBooking(idRezerwacji).getCleaning()));
        bundle.putString("cleaningPrice", String.valueOf((db.getBooking(idRezerwacji).getCleaningCurrentPrice())));
        bundle.putString("email", db.getBooking(idRezerwacji).getClientEmail());
        bundle.putString("phone", db.getBooking(idRezerwacji).getClientPhone());

        intent.putExtras(bundle);
        startActivity(intent);
    }


}