package com.example.lab01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText editText01;
    private ImageButton bnt01;
    private TextView textView01;
    private float USD;
    String currency_URL = "https://api.exchangerate-api.com/v4/latest/USD";
    String JSON = "";
    Double rates;
    String results = "";
    String line = "";
    String rate = "";
    protected ArrayAdapter <String> menuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText01 = findViewById(R.id.EditText01);
        bnt01 = findViewById(R.id.bnt);
        textView01 = findViewById(R.id.Yen);
        Spinner currencyMenu = findViewById(R.id.currencySpinner);
        menuAdapter  = new ArrayAdapter<String>( MainActivity.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.countryNames1));
        currencyMenu.setAdapter(menuAdapter);
        bnt01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View convertToYen) {
                System.out.println("\n ASYNC started \n");
                BackgroundTask backgroundTask = new BackgroundTask();
                backgroundTask.execute();
                System.out.println("\n ASYNC finished\n");
            }
        });
    }
    private class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        @Override
        protected void onPreExecute() {super.onPreExecute();}
        @Override
        protected String doInBackground(Void... params) {
            results = "";
            try {
                URL web_url = new URL(MainActivity.this.currency_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection)web_url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                InputStream inputStream = httpURLConnection.getInputStream();
                Spinner currencyMenu = findViewById(R.id.currencySpinner);
                System.out.println("\n Testing before connection");
                String countryName;
                BufferedReader bufferedReader = new BufferedReader((new InputStreamReader(inputStream)));
                System.out.println("\n Connection was successful.");
                while(line!=null){
                    line = bufferedReader.readLine();
                    JSON = JSON + line;
                }
                JSONObject obj = new JSONObject(JSON);
                JSONObject objRate = obj.getJSONObject("rates");
                countryName = currencyMenu.getSelectedItem().toString();
                rate = objRate.get(countryName).toString();
                rates = Double.parseDouble(rate);
                System.out.println("\nThe Rate is: " + rate + "\n");
            }
            catch (MalformedURLException e) {
                e.printStackTrace();
            }
            catch (ProtocolException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {super.onProgressUpdate();}
        @Override
        protected void onPostExecute(String rateresult) {
            super.onPostExecute(rateresult);
            double rate1;
            double rate2;
            USD = Float.parseFloat(editText01.getText().toString());
            rate1 = Float.parseFloat(rate);
            rate2 = rate1 * USD;
            textView01.setText(USD + "*" + rate + "=" + rate2);
        }

    }
}
