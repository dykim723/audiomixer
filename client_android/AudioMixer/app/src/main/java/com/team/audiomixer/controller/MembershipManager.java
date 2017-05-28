package com.team.audiomixer.controller;

import android.util.Log;

import com.team.audiomixer.model.User;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by dykim on 2017-05-28.
 */

public class MembershipManager {

    private String tag = "MembershipManager";

    public boolean createUser(String email, String password, String nickname) {

        User user = new User(email, password, nickname);

        Log.d(tag, email + " " + password + " " + nickname);
        // TODO: Insert Data into DB


        return TRUE;
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
