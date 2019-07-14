package com.example.worldoftanksstatus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNickname;
    private Spinner spinnerServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNickname = findViewById(R.id.editTextNickname);
        spinnerServer = findViewById(R.id.spinnerServer);
    }

    public void onClickShowStatus(View view) {

        String nickName = editTextNickname.getText().toString();
        String server = spinnerServer.getSelectedItem().toString();

        if (!nickName.isEmpty()) {
            Intent intent = new Intent(this, StatusActivity.class);
            intent.putExtra("nickName", nickName);
            intent.putExtra("server", server);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Enter nickname", Toast.LENGTH_SHORT).show();
        }
    }
}
