package com.team.audiomixer.controller;

import android.util.Log;

import com.team.audiomixer.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import static com.team.audiomixer.controller.DBManager.ServerAccessKey.JOIN;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by dykim on 2017-05-28.
 */

public class MembershipManager {

    private String tag = "MembershipManager";

    public boolean createUser(String email, String password, String nickname) {
        JSONObject json = new JSONObject();
        User user = new User(email, password, nickname);

        Log.d(tag, email + " " + password + " " + nickname);
        // TODO: Insert Data into DB

        try {
            json.put("email", email);
            json.put("password", password);
            json.put("nickname", nickname);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return DBManager.excutePost(JOIN, json);
    }

    public boolean modifyUser(){
        return FALSE;
    }

    public boolean removeUser(String email){
        return FALSE;
    }

    public User findUser(String email){
        return null;
    }


}
