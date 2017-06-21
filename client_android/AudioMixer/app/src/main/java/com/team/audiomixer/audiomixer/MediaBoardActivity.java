package com.team.audiomixer.audiomixer;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.team.audiomixer.controller.BoardManager;
import com.team.audiomixer.model.Board;

import java.util.ArrayList;

public class MediaBoardActivity extends AppCompatActivity
    implements BoardManager.BoardManagerRequestBoardListener
    , MediaBoardListViewItem.BoardListItemPlayStateListener
    , MediaBoardListViewItem.BoardListSurfaceViewListener
{
    private ListView listView;
    private MediaBoardListViewAdapter listViewAdapter;
    private BoardManager mBoardManager;

    final int eGUI_HANDLER_CMD_LIST_UPDATE = 101;
    final int eGUI_HANDLER_CMD_PLAYER_PREPARE = 102;
    final int eGUI_HANDLER_CMD_PLAYER_PLAY = 103;
    final int eGUI_HANDLER_CMD_PLAYER_PAUSE = 104;
    final int eGUI_HANDLER_CMD_PLAYER_VISIBLE = 105;
    final int eGUI_HANDLER_CMD_PLAYER_INVISIBLE = 106;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_board);

        listView = (ListView) findViewById(R.id.mediaBoardListView);
        listViewAdapter = new MediaBoardListViewAdapter();
        listView.setAdapter(listViewAdapter);
        mBoardManager = new BoardManager();

        mBoardManager.setDBManagerListener(mBoardManager);
        mBoardManager.setBoardManagerRequestBoardListener(this);
        mBoardManager.requestBoard(-1);
    }

    final Handler mGUIHandler = new Handler(){
        public void handleMessage(Message msg){
            int position = 0;
            MediaBoardListViewItem item;

            switch (msg.what)
            {
                case eGUI_HANDLER_CMD_LIST_UPDATE:
                    listViewAdapter.notifyDataSetChanged();
                    break;

                case eGUI_HANDLER_CMD_PLAYER_PREPARE:
                    position = msg.arg1;
                    item = listViewAdapter.getItem(position);
                    item.getPlayBtn().setText("Loading");
                    break;

                case eGUI_HANDLER_CMD_PLAYER_PLAY:
                    position = msg.arg1;
                    item = listViewAdapter.getItem(position);
                    item.getPlayBtn().setText("||");
                    break;

                case eGUI_HANDLER_CMD_PLAYER_PAUSE:
                    position = msg.arg1;
                    item = listViewAdapter.getItem(position);
                    item.getPlayBtn().setText(">");
                    break;

                case eGUI_HANDLER_CMD_PLAYER_VISIBLE:
                    position = msg.arg1;
                    item = listViewAdapter.getItem(position);
                    item.getPlayBtn().setVisibility(View.VISIBLE);
                    break;

                case eGUI_HANDLER_CMD_PLAYER_INVISIBLE:
                    position = msg.arg1;
                    item = listViewAdapter.getItem(position);
                    item.getPlayBtn().setVisibility(View.INVISIBLE);
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onRequestBoard(boolean requestResult) {
        if(true == requestResult) {
            ArrayList<Board> boardList = mBoardManager.getBoardList();

            for(int i = 0; i < boardList.size(); i++) {
                Board board = boardList.get(i);

                listViewAdapter.addItem(board);
                MediaBoardListViewItem item = listViewAdapter.getItem(listViewAdapter.getCount() - 1);
                item.setBoardListItemPlayStateListener(this);
                item.setBoardListSurfaceViewListener(this);
            }

            Message handlerMsg = mGUIHandler.obtainMessage();
            handlerMsg.what = eGUI_HANDLER_CMD_LIST_UPDATE;
            mGUIHandler.sendMessage(handlerMsg);
        }
    }

    @Override
    public void onBoardListItemPlayStateChanged(eBOARD_PLAY_STATE playState, int position) {
        Message handlerMsg = mGUIHandler.obtainMessage();
        handlerMsg.arg1 = position;

        switch (playState) {
            case eBOARD_PLAY_STATE_PREPARE:
                handlerMsg.what = eGUI_HANDLER_CMD_PLAYER_PREPARE;
                break;

            case eBOARD_PLAY_STATE_PLAY:
                handlerMsg.what = eGUI_HANDLER_CMD_PLAYER_PLAY;
                break;

            case eBOARD_PLAY_STATE_PAUSE:
                handlerMsg.what = eGUI_HANDLER_CMD_PLAYER_PAUSE;
                break;

            default:
                break;
        }

        mGUIHandler.sendMessage(handlerMsg);
    }

    @Override
    public void onClickBoardListItemSurfaceView(boolean isVisible, int position) {
        Message handlerMsg = mGUIHandler.obtainMessage();
        handlerMsg.arg1 = position;

        if(true == isVisible) {
            handlerMsg.what = eGUI_HANDLER_CMD_PLAYER_INVISIBLE;
        }
        else {
            handlerMsg.what = eGUI_HANDLER_CMD_PLAYER_VISIBLE;
        }

        mGUIHandler.sendMessage(handlerMsg);
    }
}
