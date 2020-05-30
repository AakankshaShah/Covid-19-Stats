package com.example.covid_19statisticscountrywise;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Stats extends AppCompatActivity {

    EditText country,tc,tnc,tr,tnr,td,tnd;
    ArrayList<String> countryName=new ArrayList<String>();
    ArrayList<String> tcases=new ArrayList<String>();
    ArrayList<String> tnewcases=new ArrayList<String>();
    ArrayList<String> trec=new ArrayList<String>();
    ArrayList<String> tnewrec=new ArrayList<String>();
    ArrayList<String> tdeath=new ArrayList<String>();
    ArrayList<String> tnewdeath=new ArrayList<String>();
    String easyPuzzle;



    public class DownloadTaskk extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String res = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream ior = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(ior);
                int data = reader.read();
                while (data != -1) {
                    char current = (char) data;
                    res += current;
                    data = reader.read();
                }
                return res;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }

        @Override
        protected void onPostExecute(String res) {
            super.onPostExecute(res);

            try {
                JSONObject jsonObjs = new JSONObject(res);
                JSONArray ja_data = jsonObjs.getJSONArray("Countries");
                int length = ja_data.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jObjs = ja_data.getJSONObject(i);
                    countryName.add(jObjs.getString("Country"));
                    tcases.add(jObjs.getString("TotalConfirmed"));
                    tnewcases.add(jObjs.getString("NewConfirmed"));
                    tnewdeath.add(jObjs.getString("NewDeaths"));
                    tdeath.add(jObjs.getString("TotalDeaths"));
                    tnewrec.add(jObjs.getString("NewRecovered"));
                    trec.add(jObjs.getString("TotalRecovered"));

                }
//                for(int ij = 0; ij < tnewcases.size(); ij++) {
//                    Log.i("Arraylist",tnewcases.get(ij));
//                    }
                int position=countryName.indexOf(easyPuzzle);
                country.setText(easyPuzzle);
                tc.setText(tcases.get(position));
                tnc.setText(tnewcases.get(position));
                tr.setText(trec.get(position));
                tnr.setText(tnewrec.get(position));
                td.setText(tdeath.get(position));
                tnd.setText(tnewdeath.get(position));



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        country=findViewById(R.id.conans);
        tc=findViewById(R.id.conans2);
        tnc=findViewById(R.id.conans3);
        tr=findViewById(R.id.conans4);
        tnr=findViewById(R.id.conans5);
        td=findViewById(R.id.conans6);
        tnd=findViewById(R.id.conans7);
        Intent intent = getIntent();
        easyPuzzle = intent.getExtras().getString("epuzzle");

        DownloadTaskk tk=new DownloadTaskk();
        tk.execute("https://api.covid19api.com/summary");


    }

}
