package com.example.anna.mobilne_projekt;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class QueriesActivity extends ListActivity {

    private String url = "http://a-m.netstrefa.pl/retrieve2.php";
    private Document doc;
    private SharedPreferences sharedPref;
    Context context;

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
    String currentQueryId;
    private XMLParser parser = new XMLParser();
    private ProgressDialog dialog;
    String lastQueryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queries);
        context = this;
        sharedPref = context.getSharedPreferences("lastQueryId", Context.MODE_PRIVATE);

        new ReadXMLTask().execute(url);
    }

    private class ReadXMLTask extends AsyncTask<String, Void, String> {
        @Override


        protected void onPreExecute() {
            dialog = ProgressDialog.show(QueriesActivity.this, "",
                    getString(R.string.loadingData), true);
        }

        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection conn;

            try {
                URL url = new URL(urls[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String s = "";

                while ((s = br.readLine()) != null) {
                    response +=s;
                }
                conn.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return response;

        }

        protected void onPostExecute(String result) {
            dialog.dismiss();

            if (result != "") {
                lastQueryId = "0";

                doc = parser.getDomElement(result);
                ArrayList<HashMap<String, String>> menuItems = new ArrayList<HashMap<String, String>>();

                NodeList nl = doc.getElementsByTagName(KEY_QUERY);

                for (int i = 0; i < nl.getLength(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    Element e = (Element) nl.item(i);
                    map.put(KEY_QUERY, "od: " + parser.getValue(e, KEY_AR_DATE) + " do: " + parser.getValue(e, KEY_DEP_DATE) + " (" + parser.getValue(e, KEY_DAYS) + " dni) ");
                    map.put(KEY_ID, e.getAttribute("id"));
                    menuItems.add(map);
                    if ( Integer.parseInt(e.getAttribute("id")) > Integer.parseInt(lastQueryId) )
                        lastQueryId = e.getAttribute("id");
                }
                ListAdapter adapter = new SimpleAdapter(QueriesActivity.this, menuItems, R.layout.list_item, new String[]{KEY_QUERY, KEY_ID}, new int[]{R.id.queryTextViev, R.id.queryId});
                QueriesActivity.this.setListAdapter(adapter);

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("lastQueryId", lastQueryId);
                editor.commit();
            }
            else {
                finish();
                Toast.makeText(QueriesActivity.this,
                        R.string.noInternetConn, Toast.LENGTH_SHORT).show();
            }
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

    public void onItemClick(View view) {
        ViewGroup row = (ViewGroup) view.getParent();
        TextView currentQueryIdTextViev = (TextView) row.getChildAt(0);
        currentQueryId = currentQueryIdTextViev.getText().toString();

        Element currentQuery = doc.getElementById(currentQueryId);

        Intent intent = new Intent(this, QueryActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("queryId", currentQueryId);
        bundle.putString("name",parser.getValue(currentQuery, KEY_NAME));
        bundle.putString("surname",parser.getValue(currentQuery, KEY_SURNAME));
        bundle.putString("email",parser.getValue(currentQuery, KEY_EMAIL));
        bundle.putString("phone",parser.getValue(currentQuery, KEY_PHONE));
        bundle.putString("adults",parser.getValue(currentQuery, KEY_ADULTS));
        bundle.putString("babies",parser.getValue(currentQuery, KEY_BABIES));
        bundle.putString("dog",parser.getValue(currentQuery, KEY_DOG));
        bundle.putString("train",parser.getValue(currentQuery, KEY_TRAIN));
        bundle.putString("airport",parser.getValue(currentQuery, KEY_AIRPORT));
        bundle.putString("cleaning",parser.getValue(currentQuery, KEY_CLEANING));
        bundle.putString("ar_date",parser.getValue(currentQuery, KEY_AR_DATE));
        bundle.putString("dep_date",parser.getValue(currentQuery, KEY_DEP_DATE));
        bundle.putString("days",parser.getValue(currentQuery, KEY_DAYS));

        finish();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onDelete(final View view) {
        ViewGroup row = (ViewGroup) view.getParent();
        final TextView queryIdTextView = (TextView) row.getChildAt(0);
        currentQueryId = queryIdTextView.getText().toString();

        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.deletingQuery)
                .setMessage(R.string.deleteQuery)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteQuery(Integer.parseInt(currentQueryId));
                    }

                })
                .setNegativeButton(R.string.no, null)
                .show();

    }

    public void refreshList() {
        finish();
        Intent intent = new Intent (this, QueriesActivity.class);
        startActivity(intent);
    }

    public void deleteQuery(int newQueryId) {
        new deleteQueryTask().execute();
    }

    private class deleteQueryTask extends AsyncTask<String, Void, String> {
        private ProgressDialog dialog;

        protected void onPreExecute() {
            dialog = ProgressDialog.show(QueriesActivity.this, "",
                    getString(R.string.deletingQuery), true);
        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            String reg_url = "http://a-m.netstrefa.pl/delete.php";
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

                Toast.makeText(QueriesActivity.this,
                        R.string.queryDeleted, Toast.LENGTH_SHORT).show();
                refreshList();

            } else {
                refreshList();
                Toast.makeText(QueriesActivity.this,
                        R.string.noInternetConn, Toast.LENGTH_SHORT).show();

            }
        }

    }




}
