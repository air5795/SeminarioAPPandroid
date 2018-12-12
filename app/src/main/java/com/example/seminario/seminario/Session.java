package com.example.seminario.seminario;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    //public void setusename(String usename) {
    //    prefs.edit().putString("usename", usename).commit();
    ///}

    public void settoken(String token) {
        prefs.edit().putString("token", token).commit();
    }

    public void setauth(Boolean auth) {
        prefs.edit().putBoolean("auth", auth).commit();
    }
    //public String getusename() {
        //String usename = prefs.getString("usename","");
        //return usename;
    ///}

    public String gettoken() {
        String token = prefs.getString("token","");
        return token;
    }

    public Boolean getauth() {
        Boolean auth = prefs.getBoolean("auth",false);
        return auth;
    }
}
