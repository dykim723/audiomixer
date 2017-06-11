package com.team.audiomixer.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.team.audiomixer.audiomixer.LoginActivity;
import com.team.audiomixer.model.User;

import java.util.HashMap;

/**
 * Created by dykim on 2017-06-11.
 */

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AudioMixerPref";

    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(User user){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, user.getNickname());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_TOKEN, user.getToken());
        editor.commit();
    }

    public boolean checkLogin(){
        boolean result = false;

        if(!this.isLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            _context.startActivity(i);
            return false;
        }
        else{
            return true;
        }

    }

    public User getUserDetails(){
        User user = null;
        if(this.isLoggedIn()) {
            user = new User();

            user.setNickname(pref.getString(KEY_NAME, null));
            user.setEmail(pref.getString(KEY_EMAIL, null));
            user.setToken(pref.getString(KEY_TOKEN, null));
        }

        return user;
    }

    public void logoutUser(){
        editor.clear().commit();
        editor.commit();

        Log.d("SessionManager", "LogoutUser");
        Intent i = new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }

    public boolean isLoggedIn(){
        Log.d("SsssionManager", pref.getBoolean(IS_LOGIN, false) + "");
        return pref.getBoolean(IS_LOGIN, false);
    }
}
