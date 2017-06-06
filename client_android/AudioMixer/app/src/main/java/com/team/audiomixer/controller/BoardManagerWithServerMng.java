package com.team.audiomixer.controller;

import android.util.Log;

import com.team.audiomixer.model.Board;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dykim on 2017-06-07.
 */

public class BoardManagerWithServerMng extends BoardManager {
    private ServerManager serverManager = null;

    public BoardManagerWithServerMng() {
        super.BoardManager();

        serverManager = ServerManager.getInstance();
    }

    @Override
    public void requestBoard(int boardNo) {

        final Call<List<Board>> boardList = serverManager.getAPIService().reqBoardList(boardNo);

        Log.d("BoardManager", "boardNo " + boardNo );

        new Thread()
        {
            public void run()
            {
                boardList.enqueue(new Callback<List<Board>>() {
                    @Override
                    public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                        Log.d("BoardManager", response.body().toString());
                        for (Board mBoard:response.body()) {
                            Log.d("BoardManager", String.valueOf(mBoard.getBoardNo()));
                            Log.d("BoardManager", String.valueOf(mBoard.getDate()));
                            Log.d("BoardManager", String.valueOf(mBoard.getTitle()));
                            Log.d("BoardManager", String.valueOf(mBoard.getFilePath()));
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Board>> call, Throwable t) {

                    }
                });
            }
        }.start();
    }
}
