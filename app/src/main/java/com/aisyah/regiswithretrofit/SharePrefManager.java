package com.aisyah.regiswithretrofit;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePrefManager {
    public static final String sharenama = "nama";
    public static final String shareusername = "username";

    public static final String ceksudahlogin = "sudahLogin";

    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    //contructor
    public SharePrefManager(Context context){
        sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSharePref(String keysp, String value){
        spEditor.putString(keysp, value);
        spEditor.commit();//dijalankan
    }

    public  void saveLogin(String keysp, Boolean value){
        spEditor.putBoolean(keysp, value);
        spEditor.commit();
    }

    public String getSharenama() {
        return sp.getString(sharenama,"");
    }

    public String getShareusername() {
        return sp.getString(shareusername,"");
    }

    public Boolean getCeksudahLogin(){
        return sp.getBoolean(ceksudahlogin, false);
    }

}
