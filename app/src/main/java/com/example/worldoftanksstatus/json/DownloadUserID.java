package com.example.worldoftanksstatus.json;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUserID{

    private String userID;
    private String nickName;

    public boolean getDataFromJson(String url){

        if (url != null){
            try {
                JSONObject jsonObject = new JSONObject(url);

                userID = jsonObject.getJSONArray("data")
                        .getJSONObject(0)
                        .getString("account_id");

                nickName = jsonObject.getJSONArray("data")
                        .getJSONObject(0)
                        .getString("nickname");

                return true;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public static class UserIDTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();

                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                }
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

    }

    public String getUserID() {
        return userID;
    }

    public String getNickName() {
        return nickName;
    }
}


