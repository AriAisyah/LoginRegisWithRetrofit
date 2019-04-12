package com.aisyah.regiswithretrofit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txtnama;
    Button btnLogout;

    SharePrefManager sharePrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharePrefManager = new SharePrefManager(this);

        txtnama = findViewById(R.id.txtnama);
        btnLogout = findViewById(R.id.btnlogout);

        txtnama.setText(sharePrefManager.getSharenama());


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharePrefManager.saveLogin(SharePrefManager.ceksudahlogin, false);
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
