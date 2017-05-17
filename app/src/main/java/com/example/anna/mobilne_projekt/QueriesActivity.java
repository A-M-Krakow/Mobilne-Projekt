package com.example.anna.mobilne_projekt;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class QueriesActivity extends ListActivity {

    private String url = "http://a-m.netstrefa.pl/retrieve2.php";

    private final String KEY_CLIENT = "klient";
    private final String KEY_QUERY = "zapytanie";
    private final String KEY_DESCRIPTION = "opis";

    private final String KEY_ID = "IdZapytania";
    private final String KEY_NAME = "ImieKlienta";
    private final String KEY_SURNAME = "NazwiskoKlienta";
    private final String KEY_EMAIL = "emailKlienta";
    private final String KEY_PHONE = "telefonKlienta";

    private final String KEY_AR_DATE = "dataPrzyj";
    private final String KEY_DEP_DATE = "dataWyj";

    private final String KEY_DAYS = "iloscDni";
    private final String KEY_ADULTS = "iloscDoroslych";
    private final String KEY_BABIES = "iloscDzieci";
    private final String KEY_TRAIN = "pkp";
    private final String KEY_AIRPORT= "lotnisko";
    private final String KEY_DOG= "pies";
    private final String KEY_CLEANING= "sprzatanie";







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);

        new ReadXMLTask().execute(url);
    }

    private class ReadXMLTask extends AsyncTask<String, Void, String> {
        @Override

        protected void onPreExecute() {}

        protected String doInBackground(String... urls) {
            String response = "";


            try {
                URL url = new URL(urls[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String s = "";

                while ((s = br.readLine()) != null) {
                    response +=s;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(String result) {
            XMLParser parser = new XMLParser();
            Document doc = parser.getDomElement(result);
            ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

            NodeList nl = doc.getElementsByTagName(KEY_QUERY);

            for (int i = 0; i<nl.getLength(); i++) {
                HashMap<String, String> map = new HashMap<String, String>();
                Element e = (Element) nl.item(i);

                map.put(KEY_QUERY, "od: " + parser.getValue(e, KEY_AR_DATE) + " do: " +  parser.getValue(e, KEY_DEP_DATE) + " (" + parser.getValue(e, KEY_DAYS) + " dni)");
                map.put(KEY_NAME, parser.getValue(e, KEY_NAME));
                map.put(KEY_SURNAME, parser.getValue(e, KEY_SURNAME));
                map.put(KEY_EMAIL, parser.getValue(e, KEY_EMAIL));
                map.put(KEY_PHONE, parser.getValue(e, KEY_PHONE));
                map.put(KEY_ADULTS, parser.getValue(e, KEY_ADULTS));
                map.put(KEY_BABIES, parser.getValue(e, KEY_BABIES));
                map.put(KEY_DOG, parser.getValue(e, KEY_DOG));
                map.put(KEY_TRAIN, parser.getValue(e, KEY_TRAIN));
                map.put(KEY_AIRPORT, parser.getValue(e, KEY_AIRPORT));
                map.put(KEY_CLEANING, parser.getValue(e, KEY_CLEANING));
                menuItems.add(map);
            }
            Log.i("ABC", menuItems.toString());
            ListAdapter adapter = new SimpleAdapter(QueriesActivity.this, menuItems,R.layout.list_item, new String[] {KEY_QUERY}, new int[] {R.id.queryTextViev});

            QueriesActivity.this.setListAdapter(adapter);

        }



    }

    public class XMLParser{

        public Document getDomElement(String xml){
            Document doc = null;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is);
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }

        public String getElementValue(Node elem) {
            Node child;
            if(elem != null) {
                if (elem.hasChildNodes()) {
                    for ( child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                        if (child.getNodeType()==Node.TEXT_NODE) {
                            return child.getNodeValue();
                        }
                    }
                }
            }
            return "";
        }

        public String getValue(Element item, String str) {
            NodeList n = item.getElementsByTagName(str);
            return this.getElementValue(n.item(0));
        }
    }


}
