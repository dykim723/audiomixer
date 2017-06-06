package com.team.audiomixer.controller;

import android.util.Log;

import com.team.audiomixer.model.Board;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.team.audiomixer.controller.DBManager.ServerAccessKey.JOIN;

/**
 * Created by dykim on 2017-05-28.
 */

public class BoardManager implements DBManager.DBManagerExcutePostListener {
    private ArrayList<Board> mBoardList = new ArrayList<Board>() ;
    private BoardManagerRequestBoardListener mRequestBoardListener;

    public interface BoardManagerRequestBoardListener {
        void onRequestBoard(boolean requestResult);
    }

    public void setBoardManagerRequestBoardListener(BoardManagerRequestBoardListener listener)
    {
        mRequestBoardListener = listener;
    }

    public void setDBManagerListener(BoardManager listener) {
        DBManager.setDBManagerExcutePostListener(listener);
    }

    public void BoardManager() {
    }

    public ArrayList<Board> getBoardList() { return mBoardList; }

    public void requestBoard(int boardNo) {
        final JSONObject json = new JSONObject();

        Log.d("BoardManager", "boardNo " + boardNo );

        try {
            json.put("BoardNo", boardNo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread()
        {
            public void run()
            {
                DBManager.excutePost(DBManager.ServerAccessKey.BOARD, json);
            }
        }.start();
    }

    @Override
    public void onExcutePost(boolean excuteResult) {
        if(true == excuteResult) {
            JSONArray json = DBManager.getResponseJSON();
            Log.d("BoardManager", "json " + json);
            Log.d("BoardManager", "excuteResult " + excuteResult);

            for (int i = 0; i < json.length(); i++) {
                Board board = new Board("");

                try {
                    board.setBoardNo(Integer.parseInt(json.getJSONObject(i).get("BoardNo").toString()));
                    board.setTitle(json.getJSONObject(i).get("Title").toString());
                    board.setContent(json.getJSONObject(i).get("Content").toString());
                    board.setDate(json.getJSONObject(i).get("Date").toString());
                    board.setUserEmail(json.getJSONObject(i).get("UserInfo_Email").toString());
                    board.setFilePath((Configuration.DBURL + json.getJSONObject(i).get("FilePath").toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mBoardList.add(board);
            }

            Log.d("BoardManager", "mBoardList.size() " + mBoardList.size());
        }
        else {
            Log.d("BoardManager", "excuteResult Fail !!!!!!!" + excuteResult);
        }

        mRequestBoardListener.onRequestBoard(excuteResult);
    }
}
