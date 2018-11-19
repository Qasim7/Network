package com.example.qasim.network;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar=findViewById(R.id.progress);
        textView=findViewById(R.id.textview);

        NetworkThread networkThread=new NetworkThread();
        networkThread.execute();


    }

    class NetworkThread extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {

            return makeReguestToGoogle();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            textView.setText(s);
        }
    }

    private String makeReguestToGoogle() {
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL("http://www.google.com");

            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader isw = new InputStreamReader(in);

            int data = isw.read();
            StringBuffer stringBuffer = new StringBuffer();
            while (data != -1) {
                char current = (char) data;
                stringBuffer.append(current);
                data = isw.read();
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
