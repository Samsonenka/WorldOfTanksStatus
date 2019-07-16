package com.example.worldoftanksstatus;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldoftanksstatus.json.DownloadUserID;
import com.example.worldoftanksstatus.json.DownloadUserStatus;

public class StatusActivity extends AppCompatActivity {

    private TextView textViewNickname;
    private TextView textViewBattles;
    private TextView textViewWins;
    private TextView textViewFrags;
    private TextView textViewDamage;
    private TextView textViewXp;

    private String nickName;
    private String server;
    private String userID;

    private final String USER_URL = "https://api.worldoftanks.ru/wot/account/list/?application_id=574f9bb7dd1a5433e0ef2fbfe436f342&search=Belarus1an";
    private final String STATUS_URL = "https://api.worldoftanks.ru/wot/account/info/?application_id=574f9bb7dd1a5433e0ef2fbfe436f342&account_id=66734749&fields=statistics.all.battles%2C+statistics.all.max_damage%2C+statistics.all.max_frags%2C+statistics.all.max_xp%2C+statistics.all.wins";


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
        } else {

            Toast.makeText(this, "Enter player nickname", Toast.LENGTH_SHORT).show();
            Intent backMainActivity = new Intent(this, MainActivity.class);
            startActivity(backMainActivity);
        }

        DownloadUserID downloadUserID = new DownloadUserID();
        DownloadUserStatus downloadUserStatus = new DownloadUserStatus();

        downloadUserID.execute(USER_URL);
        downloadUserStatus.execute(STATUS_URL);

    }
}
