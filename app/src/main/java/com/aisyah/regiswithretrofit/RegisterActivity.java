package com.aisyah.regiswithretrofit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aisyah.regiswithretrofit.model.ResponseRegis;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edtnama, edtusername, edtpassword;
    Button btnRegister;
    TextView txtlogin;
    ProgressDialog progressDialog;

    BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtnama = findViewById(R.id.edtnama);
        edtusername = findViewById(R.id.edtUsername);
        edtpassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtlogin = findViewById(R.id.txtlogin);

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });

        apiService = ConfigApi.getApiService();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setTitle("Register");
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                requestRegister();
            }
        });

    }

    private void requestRegister() {
        apiService.register(edtnama.getText().toString(),
                edtusername.getText().toString(),
                edtpassword.getText().toString()).enqueue(new Callback<ResponseRegis>() {
            @Override
            public void onResponse(Call<ResponseRegis> call, Response<ResponseRegis> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    try {
                        Log.i("debug", "onResponse : BERHASIL");
                        if (response.body().isSukses()) {
                            Toast.makeText(RegisterActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, response.body().getPesan(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                        //di print errornya
                    }
                } else {
                    Log.i("debug", "onResponse : GAGAL");
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseRegis> call, Throwable t) {

                Log.e("debug", "onFailure : ERROR = " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Server is down, check the internet connection", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }
}
