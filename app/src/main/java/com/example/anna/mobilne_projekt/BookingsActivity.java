package com.example.anna.mobilne_projekt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class BookingsActivity extends AppCompatActivity {





    public class Booking {
        private int bookingId;
        private String clientName;
        private String clientSurname;
        private String clientPhone;
        private String clientEmail;
        private int confirmed;
        private String arDate;
        private int numberOfDays;
        private int numberOfAdults;
        private Double adultsCurrentPrice;
        private int numberOfBabies;
        private int dog;
        private int train;
        private int airport;
        private int cleaning;


        public Booking(int bookingId, String clientName, String clientSurname, String clientPhone, String clientEmail, int confirmed, String arDate, int numberOfDays, int numberOfAdults, Double adultsCurrentPrice, int numberOfBabies, boolean dog, boolean train, boolean airport, boolean cleaning) {

            this.bookingId = bookingId;
            this.clientName = clientName;
            this.clientSurname = clientSurname;
            this.clientPhone = clientPhone;
            this.clientEmail = clientEmail;
            this.confirmed = confirmed;
            this.arDate = arDate;
            this.numberOfDays = numberOfDays;
            this.numberOfAdults = numberOfAdults;
            this.adultsCurrentPrice = adultsCurrentPrice;
            this.numberOfBabies = numberOfBabies;

            if(dog==false) this.dog=0;
            else this.dog=1;

            if(train==false) this.train=0;
            else this.train=1;

            if(airport==false) this.airport=0;
            else this.airport=1;

            if(cleaning==false) this.cleaning=0;
            else this.cleaning=1;
        }

        public Booking(){

        }

        public void setBookingId(int  newBookingId) {
            this.bookingId = newBookingId;
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

        public void setNumberOfDays(int newNumberOfDays) {
            this.numberOfDays = newNumberOfDays;
        }

        public void setNumberOfAdults (int newNumberOfAdults) {this.numberOfAdults = newNumberOfAdults;}

        public void setAdultsCurrentPrice(Double  newAdultsCurrentPrice) {this.adultsCurrentPrice = newAdultsCurrentPrice;}

        public int getBookingId() {
            return bookingId;
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

        public int getDog() {
            return dog;
        }
        public int getTrain() {
            return train;
        }

        public int getAirport() {
            return airport;
        }

        public int getCleaning() {
            return cleaning;
        }

        public int getConfirmed() {
            return confirmed;
        }

        public String getArDate() {
            return arDate;
        }

        public int getNumberOfDays() {
            return numberOfDays;
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
        private static final String KEY_NUMBER_DAYS = "NumberOfDays";

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
            String CREATE_BOOKINGS_TABLE = "CREATE TABLE " + TABLE_BOOKINGS + "(" + KEY_BOOKING_ID + " INTEGER PRIMARY KEY, " +
                    KEY_CLIENT_NAME + " TEXT, " + KEY_CLIENT_SURNAME + " TEXT,  " + KEY_CLIENT_PHONE + " TEXT, " + KEY_CLIENT_EMAIL + " TEXT, " +
                    KEY_CONFIRMED + " INTEGER, " + KEY_AR_DATE + " DATETIME," + KEY_NUMBER_DAYS + " INTEGER)";

           String CREATE_ACCOMODATIONS_TABLE = "CREATE TABLE " + TABLE_ACCOMODATIONS + "(" + KEY_ACC_NAME + " TEXT PRIMARY KEY," + KEY_ACC_PRICE + " REAL)";

           String CREATE_ADDITIONS_TABLE = "CREATE TABLE " + TABLE_ADDITIONS + "(" + KEY_ADD_NAME + " TEXT PRIMARY KEY," + KEY_ADD_PRICE + " REAL)";

           String CREATE_BOOKING_ACCOMODATIONS_TABLE = "CREATE TABLE " + TABLE_BOOKING_ACCOMODATIONS + " (" + KEY_BOOKING_ID + " INTEGER, " + KEY_ACC_NAME + " TEXT, " + KEY_CURR_PRICE + " REAL, " + KEY_NUMBER_OF_ACCOMODATIONS + " INT, PRIMARY KEY(" + KEY_BOOKING_ID + ", " + KEY_ACC_NAME + "), FOREIGN KEY( " + KEY_BOOKING_ID  + " ) REFERENCES " + TABLE_BOOKINGS + "( " + KEY_BOOKING_ID + "), FOREIGN KEY( " + KEY_ACC_NAME  + " ) REFERENCES " + TABLE_ACCOMODATIONS+ "( " + KEY_ACC_NAME +"), UNIQUE ( " + KEY_BOOKING_ID + ","  + KEY_ACC_NAME + "))";

           String CREATE_BOOKING_ADDITIONS_TABLE = "CREATE TABLE " + TABLE_BOOKING_ADDITIONS + " (" + KEY_BOOKING_ID + " INTEGER, " + KEY_ADD_NAME + " TEXT, " + KEY_CURR_PRICE + " REAL, PRIMARY KEY(" + KEY_BOOKING_ID + ", " + KEY_ADD_NAME + "), FOREIGN KEY( " + KEY_BOOKING_ID  + " ) REFERENCES " + TABLE_BOOKINGS + "( " + KEY_BOOKING_ID + "), FOREIGN KEY( " + KEY_ADD_NAME  + " ) REFERENCES " + TABLE_ADDITIONS+ "( " + KEY_ADD_NAME +"))";

            db.execSQL(CREATE_BOOKINGS_TABLE);
            db.execSQL(CREATE_ACCOMODATIONS_TABLE);
            db.execSQL(CREATE_ADDITIONS_TABLE);
            db.execSQL(CREATE_BOOKING_ACCOMODATIONS_TABLE);
            db.execSQL(CREATE_BOOKING_ADDITIONS_TABLE);



            ContentValues accomodationOption = new ContentValues();
            accomodationOption.put(KEY_ACC_NAME, ADULT_ACCOMODATION);
            accomodationOption.put(KEY_ACC_PRICE, "0");
            db.insert(TABLE_ACCOMODATIONS, null, accomodationOption);
            accomodationOption.clear();
            accomodationOption.put(KEY_ACC_NAME, BABY_ACCOMODATION);
            accomodationOption.put(KEY_ACC_PRICE, "0");
            db.insert(TABLE_ACCOMODATIONS, null, accomodationOption);
            accomodationOption.clear();
            accomodationOption.put(KEY_ACC_NAME, DOG_ACCOMODATION);
            accomodationOption.put(KEY_ACC_PRICE, "0");
            db.insert(TABLE_ACCOMODATIONS, null, accomodationOption);
            accomodationOption.clear();

            ContentValues additionalOption = new ContentValues();
            additionalOption.put(KEY_ADD_NAME, CLEANING_ADDITION);
            additionalOption.put(KEY_ADD_PRICE, "0");
            db.insert(TABLE_ADDITIONS, null, additionalOption);
            additionalOption.clear();
            additionalOption.put(KEY_ADD_NAME, TRAIN_ADDITION);
            additionalOption.put(KEY_ADD_PRICE, "0");
            db.insert(TABLE_ADDITIONS, null, additionalOption);
            additionalOption.clear();
            additionalOption.put(KEY_ADD_NAME, AIRPORT_ADDITION);
            additionalOption.put(KEY_ADD_PRICE, "0");
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

       booking.setBookingId(Integer.parseInt(cursor.getString(0)));
       booking.setClientName(cursor.getString(1));
       booking.setClientSurname(cursor.getString(2));
       booking.setClientPhone(cursor.getString(3));
       booking.setClientEmail(cursor.getString(4));
       booking.setConfirmation(Integer.parseInt(cursor.getString(5)));
       booking.setArDate(cursor.getString(6));
       booking.setNumberOfDays(Integer.parseInt(cursor.getString(7)));

       // Pobieranie z bazy ilości i ceny dorosłych dla rezerwacji o podanym Id
       String selectAdultBookingQuery = "select "+  KEY_NUMBER_OF_ACCOMODATIONS + ", " + KEY_CURR_PRICE +  " from " + TABLE_BOOKING_ACCOMODATIONS + " where " + KEY_BOOKING_ID + " = " + id + " and " +  KEY_ACC_NAME + " = " + "\"" + ADULT_ACCOMODATION + "\"";
       cursor= db.rawQuery(selectAdultBookingQuery, null);
       if (cursor != null) cursor.moveToFirst();
       booking.setNumberOfAdults(Integer.parseInt(cursor.getString(0)));
       booking.setAdultsCurrentPrice(cursor.getDouble(1));


        //zwracamy rezerwację
        return booking;
    }

    public List<Booking> getAllBookings(){
        List<Booking> bookingList = new ArrayList<Booking>();

        //zapytanie SQL
        String selectQuery = "SELECT * FROM " + TABLE_BOOKINGS;

        Cursor cursor = db.rawQuery(selectQuery, null);

        //pętla przez wszystkie elementy z dodaniem ich do listy
        if (cursor.moveToFirst()) {
            do
            {
                Booking booking = new Booking();
                booking.setBookingId(Integer.parseInt(cursor.getString(0)));
                booking.setClientName(cursor.getString(1));
                booking.setClientSurname(cursor.getString(2));
                booking.setClientEmail(cursor.getString(3));
                booking.setClientPhone(cursor.getString(4));
                booking.setConfirmation(Integer.parseInt(cursor.getString(5)));
                booking.setArDate(cursor.getString(6));
                booking.setNumberOfDays(Integer.parseInt(cursor.getString(7)));

                bookingList.add(booking);

            } while (cursor.moveToNext());
        }
        return bookingList;
    }

    //aktualizacja rezerwacji
    public int updateBooking(Booking booking) {
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT_NAME, booking.getClientName());
        values.put(KEY_CLIENT_SURNAME, booking.getClientSurname());
        values.put(KEY_CLIENT_EMAIL, booking.getClientEmail());
        values.put(KEY_CLIENT_PHONE, booking.getClientPhone());
        values.put(KEY_CONFIRMED, booking.getConfirmed());
        values.put(KEY_AR_DATE, booking.getArDate());
        values.put(KEY_NUMBER_DAYS, booking.getNumberOfDays());

        //Aktualizacja wiersza
        return db.update(TABLE_BOOKINGS, values, KEY_BOOKING_ID + " =? ", new String[] {String.valueOf(booking.getBookingId())});
    }

    public void deleteBooking(Booking booking) {
        db.delete(TABLE_BOOKINGS, KEY_BOOKING_ID + " = ? ", new String[] {String.valueOf(booking.getBookingId())});
    }

    public void addBooking(Booking booking) {

        // dodawanie danych rezerwacji
        ContentValues bookingValues = new ContentValues();
        bookingValues.put(KEY_BOOKING_ID, booking.getBookingId());
        bookingValues.put(KEY_CLIENT_NAME, booking.getClientName());
        bookingValues.put(KEY_CLIENT_SURNAME, booking.getClientSurname());
        bookingValues.put(KEY_CLIENT_PHONE, booking.getClientPhone());
        bookingValues.put(KEY_CLIENT_EMAIL, booking.getClientEmail());
        bookingValues.put(KEY_AR_DATE, booking.getArDate());
        bookingValues.put(KEY_NUMBER_DAYS, booking.getNumberOfDays());
        bookingValues.put(KEY_CONFIRMED, booking.getConfirmed());

        db.insert(TABLE_BOOKINGS, null, bookingValues);

        // dodawanie dorosłych do rezerwacji
            ContentValues adultsAccomodationValues = new ContentValues();
            adultsAccomodationValues.put(KEY_BOOKING_ID, booking.getBookingId());
            adultsAccomodationValues.put(KEY_ACC_NAME, ADULT_ACCOMODATION);
            adultsAccomodationValues.put(KEY_CURR_PRICE, booking.getAdultsCurrentPrice());
            adultsAccomodationValues.put(KEY_NUMBER_OF_ACCOMODATIONS, booking.getNumberOfAdults());
            db.insert(TABLE_BOOKING_ACCOMODATIONS, null, adultsAccomodationValues);


        // dodawanie dzieci do rezerwacji
        if (booking.getNumberOfBabies()>0) {
            ContentValues babiesAccomodationValues = new ContentValues();
            babiesAccomodationValues.put(KEY_BOOKING_ID, booking.getBookingId());
            babiesAccomodationValues.put(KEY_ACC_NAME, BABY_ACCOMODATION);
            babiesAccomodationValues.put(KEY_CURR_PRICE, 14);
            babiesAccomodationValues.put(KEY_NUMBER_OF_ACCOMODATIONS, booking.getNumberOfBabies());
            db.insert(TABLE_BOOKING_ACCOMODATIONS, null, babiesAccomodationValues);
        }

        // dodawanie psa do rezerwacji
        if (booking.getDog()>0) {
            ContentValues dogAccomodationValues = new ContentValues();
            dogAccomodationValues.put(KEY_BOOKING_ID, booking.getBookingId());
            dogAccomodationValues.put(KEY_ACC_NAME, DOG_ACCOMODATION);
            dogAccomodationValues.put(KEY_CURR_PRICE, 12);
            dogAccomodationValues.put(KEY_NUMBER_OF_ACCOMODATIONS, booking.getDog());
            db.insert(TABLE_BOOKING_ACCOMODATIONS, null, dogAccomodationValues);
        }

        // dodawanie do rezerwacji transportu z dworca
        if (booking.getTrain()>0) {
            ContentValues trainValues = new ContentValues();
            trainValues.put(KEY_BOOKING_ID, booking.getBookingId());
            trainValues.put(KEY_ADD_NAME, TRAIN_ADDITION);
            trainValues.put(KEY_CURR_PRICE, 2);
            db.insert(TABLE_BOOKING_ADDITIONS, null, trainValues);
        }

        // dodawanie do rezerwacji transportu z lotniska
        if (booking.getAirport()>0) {
            ContentValues airportValues = new ContentValues();
            airportValues.put(KEY_BOOKING_ID, booking.getBookingId());
            airportValues.put(KEY_ADD_NAME, AIRPORT_ADDITION);
            airportValues.put(KEY_CURR_PRICE, 3);
            db.insert(TABLE_BOOKING_ADDITIONS, null, airportValues);
        }

        // dodawanie do rezerwacji sprzątania
        if (booking.getCleaning()>0) {
            ContentValues airportValues = new ContentValues();
            airportValues.put(KEY_BOOKING_ID, booking.getBookingId());
            airportValues.put(KEY_ADD_NAME, CLEANING_ADDITION);
            airportValues.put(KEY_CURR_PRICE, 7);
            db.insert(TABLE_BOOKING_ADDITIONS, null, airportValues);
        }

            }

        }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        DatabaseHandler db = new DatabaseHandler(this);


        // Booking(int bookingId, String clientName, String clientSurname, String clientPhone, String clientEmail, int confirmed, String arDate, int numberOfDays, int numberOfAdults, Double adultsCurrentPrice, int numberOfBabies, boolean dog, boolean train, boolean airport, boolean cleaning)
      int bookindId = 1;
      db.addBooking( new Booking(bookindId, "Anna", "Madej", "122222222", "sd@sd.pl", 1, "2017-01-30", 15, 2, 50.00, 1, true, false, true, false));
      bookindId++;
      db.addBooking( new Booking(bookindId, "Anna", "Madej", "122222222", "sd@sd.pl", 1, "2017-01-30", 15, 3, 40.00, 1, false, true, false, true));


      Booking pobranaBooking = db.getBooking(1);

      Log.i("LOL",
              pobranaBooking.getBookingId() + " | " +
              pobranaBooking.getClientName() + " | " +
              pobranaBooking.getClientSurname() + " | " +
              pobranaBooking.getClientPhone() + " | " +
              pobranaBooking.getClientEmail() + " | " +
              pobranaBooking.getConfirmed() + " | " +
              pobranaBooking.getNumberOfAdults() + " | " +
              pobranaBooking.getAdultsCurrentPrice() + " | " +
              pobranaBooking.getArDate() + " | " +
              pobranaBooking.getNumberOfDays() + " | ");

        /*List<Booking> bookings = db.getAllBookings();

        for (Booking booking : bookings) {
            String log = booking.getBookingId() + " " + booking.getClientName() + " " + booking.getClientSurname() + " " + booking.getClientPhone() + " " + booking.getClientEmail() + " " + booking.getConfirmed() + " " + booking.getArDate() + " " + booking.getNumberOfDays();
            Log.i("dbinfo", log);
        } */


    }
}
