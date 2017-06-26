package com.team.audiomixer.controller;

import android.util.Log;

import com.team.audiomixer.model.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static java.lang.Boolean.TRUE;

/**
 * Created by dykim on 2017-05-28.
 */

public class ServerManager {
    private Retrofit retrofit;
    private ServerAccessService apiService;

    private static ServerManager instance;

    public interface ServerAccessService {
        @POST("join")
        Call<User> createUser(@Body User user);

        @POST("login")
        Call<User> loginUser(@Body User user);

        @GET("login/auth/login/kakao")
        Call<String> loginUserKakao();
    }

    public ServerAccessService getAPIService(){
        if(apiService != null){
            return apiService;
        }
        else{
            return null;
        }
    }

    private ServerManager(){
        retrofit = new Retrofit.Builder().baseUrl(Configuration.DBURL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService = retrofit.create(ServerAccessService.class);
    }

    public static ServerManager getInstance(){
        if(instance == null){
            synchronized(ServerManager.class){
                if(instance == null){
                    instance = new ServerManager();
                }
            }
        }
        return instance;
    }
}
