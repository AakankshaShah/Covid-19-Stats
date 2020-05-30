package com.example.covid_19statisticscountrywise;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.Collections;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {


ArrayAdapter<String> ad;
ArrayList<String> ar = new ArrayList<String>();
AutoCompleteTextView actv;
  public class DownloadTask extends AsyncTask<String,Void,String> {

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
             JSONObject jsonObj = new JSONObject(res);
              JSONArray ja_data = jsonObj.getJSONArray("Countries");
              int length = ja_data.length();
              for (int i = 0; i < length; i++) {
                  JSONObject jObj = ja_data.getJSONObject(i);
                 //Log.i("conyty list:", jObj.getString("Country"));
                ar.add(jObj.getString("Country"));
                //ad = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,ar);
                //actv.setAdapter(ad);
                  for(int ij = 0; ij < ar.size(); ij++) {
                   Log.i("Arraylist",ar.get(ij));
                  }
              }  ad = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,ar);
                 actv.setAdapter(ad);
                        //for(int ij = 0; ij < ar.size(); ij++) {
                      //Log.i("Arraylist",ar.get(ij));
                  //}
         } catch (JSONException e) {
              e.printStackTrace();
        }
//

      }

  }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       actv=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        DownloadTask task=new DownloadTask();
        task.execute("https://api.covid19api.com/summary");
//        for(int ij = 0; ij < ar.size(); ij++)
//        {
//                     Log.i("Arraylist",ar.get(ij));
//        }
        //ad = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ar);
        //actv.setAdapter(ad);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
   public void nextClicked(View view)
   {String pass=actv.getText().toString();
       Intent intentz=new Intent(this,Stats.class);
        intentz.putExtra("epuzzle",pass);
        startActivity(intentz);

    }
}





