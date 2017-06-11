package com.team.audiomixer.controller;

import android.text.TextUtils;

import com.team.audiomixer.model.User;

import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dykim on 2017-05-28.
 */

public class ServerManager {
    //private Retrofit retrofit;
    private ServerAccessService apiService;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Configuration.SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static ServerManager instance;

    public interface ServerAccessService {
        @POST("join")
        Call<User> createUser(@Body User user);

        @POST("login")
        Call<User> loginUser(@Body User user);
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
        //retrofit = new Retrofit.Builder().baseUrl(Configuration.SERVER_URL).addConverterFactory(GsonConverterFactory.create()).build();
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

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, String username, String password) {
        if (!TextUtils.isEmpty(username)
                && !TextUtils.isEmpty(password)) {
            String authToken = Credentials.basic(username, password);
            return createService(serviceClass, authToken);
        }

        return createService(serviceClass, null, null);
    }

    public static <S> S createService(
            Class<S> serviceClass, final String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor =
                    new AuthenticationInterceptor(authToken);

            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);

                builder.client(httpClient.build());
                retrofit = builder.build();
            }
        }

        return retrofit.create(serviceClass);
    }
}
