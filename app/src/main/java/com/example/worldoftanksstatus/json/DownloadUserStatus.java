package com.example.worldoftanksstatus.json;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class DownloadUserStatus{

    private String wins;
    private String max_damage;
    private String max_frags;
    private String battles;
    private String max_xp;

    public void getDataFromJson(String url){

        try {
            JSONObject jsonObject = new JSONObject(url);

            wins = jsonObject.getJSONObject("data")
                    .getJSONObject("66734749")
                    .getJSONObject("statistics")
                    .getJSONObject("all")
                    .getString("wins");

            max_damage = jsonObject.getJSONObject("data")
                    .getJSONObject("66734749")
                    .getJSONObject("statistics")
                    .getJSONObject("all")
                    .getString("max_damage");

            max_frags = jsonObject.getJSONObject("data")
                    .getJSONObject("66734749")
                    .getJSONObject("statistics")
                    .getJSONObject("all")
                    .getString("max_frags");

            battles = jsonObject.getJSONObject("data")
                    .getJSONObject("66734749")
                    .getJSONObject("statistics")
                    .getJSONObject("all")
                    .getString("battles");

            max_xp = jsonObject.getJSONObject("data")
                    .getJSONObject("66734749")
                    .getJSONObject("statistics")
                    .getJSONObject("all")
                    .getString("max_xp");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getWins() {
        return wins;
    }

    public String getMax_damage() {
        return max_damage;
    }

    public String getMax_frags() {
        return max_frags;
    }

    public String getBattles() {
        return battles;
    }

    public String getMax_xp() {
        return max_xp;
    }

    public static class UserStatusTask extends AsyncTask<String, Void, String> {

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

}


