package com.team.audiomixer.controller;

import android.util.Log;

import com.team.audiomixer.model.User;

import java.lang.reflect.Member;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.Boolean.FALSE;

/**
 * Created by dykim on 2017-05-28.
 */

public class MembershipManager {

    private String tag = "MembershipManager";
    private ServerManager serverManager = null;

    public MembershipManager(){
        serverManager = ServerManager.getInstance();
    }

    public boolean createUser(String email, String password, String nickname) {

        User tUser = new User(email, password, nickname);
        Call<User> user = serverManager.getAPIService().createUser(tUser);

        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("JoinActivity", "from Server::" + response.body().getEmail() + " " + response.body().getPassword() + " " + response.body().getNickname());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        return true;
    }

    public boolean loginUser(String email, String password) {

        User tUser = new User(email, password);

        Call<User> user = serverManager.getAPIService().loginUser(tUser);

        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    Log.d("JoinActivity", "from Server::" + response.body().getEmail() + " " + response.body().getPassword() + " " + response.body().getNickname());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

        return true;
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
