package com.team.audiomixer.controller.login;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.kakao.auth.Session;
import com.team.audiomixer.audiomixer.R;
import com.team.audiomixer.controller.ServerManager;
import com.team.audiomixer.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessActivity extends AppCompatActivity {
    ServerManager serverManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        serverManager = ServerManager.getInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String accessToken = Session.getCurrentSession().getAccessToken();
        String refreshToken = Session.getCurrentSession().getRefreshToken();

        Log.d("TOKEN!!!!", accessToken + " :: " + refreshToken);

        User user = new User("dy", "dy", "dy");
        user.setAccessToken(accessToken);
        user.setRefreshToken(refreshToken);
        Call<User> loginInfo = serverManager.getAPIService().loginUserKakao(user);

        loginInfo.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("SuccessActivity", response.body().toString());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

}
