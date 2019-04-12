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

import com.aisyah.regiswithretrofit.model.ResponseUser;
import com.aisyah.regiswithretrofit.model.UserItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText edtusername, edtpassword;
    Button btnlogin;
    TextView txtregis;

    BaseApiService apiService;

    ProgressDialog progressDialog;

    SharePrefManager sharePrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtusername = findViewById(R.id.edtUsername);
        edtpassword = findViewById(R.id.edtPassword);
        btnlogin = findViewById(R.id.btnLogin);
        txtregis = findViewById(R.id.txtregis);

        txtregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        apiService = ConfigApi.getApiService();

        sharePrefManager = new SharePrefManager(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.setTitle("Login");
                progressDialog.show();

                requestLogin();

            }
        });
        //jika sudah login maka kita langsung ke main activity
        if (sharePrefManager.getCeksudahLogin()){
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }

    }

    private void requestLogin() {
        apiService.login(edtusername.getText().toString(), edtpassword.getText().toString()).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    try {
                        // JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (response.body().isSukses()) {
                            Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();

//                            JSONArray array = jsonObject.getJSONArray("user");
//                            String nama = array.getJSONObject(0).getString("nama");

                            sharePrefManager.saveSharePref(SharePrefManager.sharenama, edtusername.getText(). toString());
                            sharePrefManager.saveLogin(SharePrefManager.ceksudahlogin, true );
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            //i.putExtra("nama", nama);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Gagal", Toast.LENGTH_SHORT).show();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();

                    }
                    //addcatchclause's

                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Server is down", Toast.LENGTH_SHORT).show();
                Log.e("debug", "OnFailure : Error = " + t.toString());
                progressDialog.dismiss();
            }
        });
    }
}
