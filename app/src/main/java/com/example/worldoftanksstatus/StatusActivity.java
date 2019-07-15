package com.example.worldoftanksstatus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class StatusActivity extends AppCompatActivity {

    private TextView textViewNickname;
    private TextView textViewBattles;
    private TextView textViewWins;
    private TextView textViewFrags;
    private TextView textViewDamage;
    private TextView textViewXp;

    private String nickName;
    private String server;

    private final String USER_URL = "https://api.worldoftanks.ru/wot/account/list/?application_id=574f9bb7dd1a5433e0ef2fbfe436f342&search=%s";
    private final String USER_STATUS = "https://api.worldoftanks.ru/wot/account/info/?application_id=574f9bb7dd1a5433e0ef2fbfe436f342&account_id=%s&fields=statistics.all.battles%2C+statistics.all.max_damage%2C+statistics.all.max_frags%2C+statistics.all.max_xp%2C+statistics.all.wins";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        textViewNickname = findViewById(R.id.editTextNickname);
        textViewBattles = findViewById(R.id.textViewBattles);
        textViewWins = findViewById(R.id.textViewWins);
        textViewFrags = findViewById(R.id.textViewFrags);
        textViewDamage = findViewById(R.id.textViewDamage);
        textViewXp = findViewById(R.id.textViewXp);

        Intent intent = getIntent();

        if (intent.hasExtra("nickName") && intent.hasExtra("server")){

            nickName = intent.getStringExtra("nickName");
            server = intent.getStringExtra("server");

            DownloadUserIdTask task = new DownloadUserIdTask();
            String userUrl = String.format(USER_URL, nickName);

            task.execute(userUrl);

        } else {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
        }
    }

    private class DownloadUserIdTask extends AsyncTask<String, Void, String>{

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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s != null){
                try {
                    JSONObject jsonObject = new JSONObject(s);

                    String userID = jsonObject.getJSONArray("data").getJSONObject(0).getString("account_id");

                    Log.i("test", userID);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(StatusActivity.this, "s = null", Toast.LENGTH_SHORT).show();
            }

        }

    }
}
