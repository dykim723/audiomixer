package com.team.audiomixer.audiomixer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.team.audiomixer.controller.BoardManager;
import com.team.audiomixer.controller.BoardManagerWithServerMng;
import com.team.audiomixer.model.Board;

import java.util.ArrayList;

public class MediaBoardActivity extends AppCompatActivity implements BoardManager.BoardManagerRequestBoardListener {
    private ListView listView;
    private MediaBoardListViewAdapter listViewAdapter;
    private BoardManager mBoardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_board);

        listView = (ListView) findViewById(R.id.mediaBoardListView);
        listViewAdapter = new MediaBoardListViewAdapter();
        listView.setAdapter(listViewAdapter);
        //mBoardManager = new BoardManager();
        mBoardManager = new BoardManagerWithServerMng();
        mBoardManager.setDBManagerListener(mBoardManager);
        mBoardManager.setBoardManagerRequestBoardListener(this);

        mBoardManager.requestBoard(-1);
    }

    @Override
    public void onRequestBoard(boolean requestResult) {
        if(true == requestResult) {
            ArrayList<Board> boardList = mBoardManager.getBoardList();

            for(int i = 0; i < boardList.size(); i++) {
                Board board = boardList.get(i);
                listViewAdapter.addItem(board.getTitle(), board.getUserEmail(), board.getContent(), board.getDate(), board.getFilePath());
            }

            listViewAdapter.notifyDataSetChanged();
        }
    }
}
