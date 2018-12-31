package com.govege.trollo.govege;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Trollo on 2018-12-29.
 */

public class HTTPRequester extends AsyncTask<URL, Void, String> {
    private String method;

    public HTTPRequester(String method){
        this.method = method;
    }

    @Override
    protected String doInBackground(URL... urls) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
            urlConnection.setRequestMethod(this.method);
            try {
                BufferedReader response = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String line;
                StringBuilder content = new StringBuilder();
                while((line = response.readLine()) != null){
                    content.append(line);
                }
                response.close();
                return content.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
