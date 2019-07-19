package com.example.worldoftanksstatus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.worldoftanksstatus.json.DownloadUserID;
import com.example.worldoftanksstatus.json.DownloadUserStatus;

import java.util.concurrent.ExecutionException;

public class StatusActivity extends AppCompatActivity {

    private TextView textViewNickName;
    private TextView textViewBattles;
    private TextView textViewWins;
    private TextView textViewFrags;
    private TextView textViewDamage;
    private TextView textViewXp;

    private String nickName;
    private String server;

    private final String USER_URL = "https://api.worldoftanks.ru/wot/account/list/?application_id=574f9bb7dd1a5433e0ef2fbfe436f342&search=%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        textViewNickName = findViewById(R.id.textViewNickName);
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

        DownloadUserID.UserIDTask userIDTask = new DownloadUserID.UserIDTask();
        DownloadUserStatus.UserStatusTask userStatusTask = new DownloadUserStatus.UserStatusTask();

        DownloadUserID downloadUserID = new DownloadUserID();
        DownloadUserStatus downloadUserStatus = new DownloadUserStatus();

        try {
            String urlID = userIDTask.execute(String.format(USER_URL, nickName)).get();
            downloadUserID.getDataFromJson(urlID);

            String statusUrl = "https://api.worldoftanks.ru/wot/account/info/?application_id=574f9bb7dd1a5433e0ef2fbfe436f342&account_id=" + downloadUserID.getUserID() +"&fields=statistics.all.battles%2C+statistics.all.max_damage%2C+statistics.all.max_frags%2C+statistics.all.max_xp%2C+statistics.all.wins";
            String ww = userStatusTask.execute(statusUrl).get();
            downloadUserStatus.getDataFromJson(ww);

            Log.i("test", downloadUserID.getUserID());
            Log.i("test", ww);

            textViewNickName.setText(downloadUserID.getNickName());
            textViewBattles.setText(downloadUserStatus.getBattles());
            textViewWins.setText(downloadUserStatus.getWins());
            textViewFrags.setText(downloadUserStatus.getMax_frags());
            textViewDamage.setText(downloadUserStatus.getMax_damage());
            textViewXp.setText(downloadUserStatus.getMax_xp());

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
