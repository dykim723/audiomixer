package com.team.audiomixer.audiomixer;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.team.audiomixer.controller.BoardManager;
import com.team.audiomixer.model.Board;

import java.util.ArrayList;

import static com.team.audiomixer.audiomixer.MediaBoardListViewItem.BoardListSurfaceViewListener.eBOARD_PLAYER_VIEW_EVENT.eBOARD_PLAYER_VIEW_EVENT_THUMBNAIL_UPDATE;

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
    final int eGUI_HANDLER_CMD_PLAYER_COMPLETE = 105;
    final int eGUI_HANDLER_CMD_PLAYER_VISIBLE_CHANGE = 106;
    final int eGUI_HANDLER_CMD_THUMBNAIL_UPDATE = 107;

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
            int position = position = msg.arg1;
            MediaBoardListViewItem item;

            Log.d("MediaBoard", "set eGUI_HANDLER " + msg.what);
            switch (msg.what)
            {
                case eGUI_HANDLER_CMD_LIST_UPDATE:
                    listViewAdapter.notifyDataSetChanged();
                    break;

                case eGUI_HANDLER_CMD_PLAYER_PREPARE:
                    item = listViewAdapter.getItem(position);
                    item.getPlayBtn().setText("Loading");
                    item.getPlayBtn().setClickable(false);
                    break;

                case eGUI_HANDLER_CMD_PLAYER_PLAY:
                    item = listViewAdapter.getItem(position);
                    item.getPlayBtn().setText("||");
                    item.getPlayBtn().setClickable(true);

                    if(item.getThumbnailImage().getVisibility() == View.VISIBLE) {
                        item.getThumbnailImage().setVisibility(View.INVISIBLE);
                    }

                    if(item.getSurfaceView().getVisibility() == View.INVISIBLE) {
                        item.getSurfaceView().setVisibility(View.VISIBLE);
                    }
                    break;

                case eGUI_HANDLER_CMD_PLAYER_PAUSE:
                    item = listViewAdapter.getItem(position);
                    item.getPlayBtn().setText(">");
                    break;

                case eGUI_HANDLER_CMD_PLAYER_COMPLETE:
                    item = listViewAdapter.getItem(position);
                    item.getPlayBtn().setText(">");
                    break;

                case eGUI_HANDLER_CMD_PLAYER_VISIBLE_CHANGE:
                    item = listViewAdapter.getItem(position);

                    if(item.getPlayBtn().getVisibility() == View.INVISIBLE) {
                        item.getPlayBtn().setVisibility(View.VISIBLE);
                    }
                    else {
                        item.getPlayBtn().setVisibility(View.INVISIBLE);
                    }
                    break;

                case eGUI_HANDLER_CMD_THUMBNAIL_UPDATE:
                    item = listViewAdapter.getItem(position);
                    item.getThumbnailImage().setImageBitmap(item.getBitmap());
                    listViewAdapter.notifyDataSetChanged();
                    item.getThumbnailImage().invalidate();
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onRequestBoard(boolean requestResult) {
        int position = 0;

        if(true == requestResult) {
            ArrayList<Board> boardList = mBoardManager.getBoardList();

            for(int i = 0; i < boardList.size(); i++) {
                Board board = boardList.get(i);

                position = listViewAdapter.getCount();
                listViewAdapter.addItem(board);
                MediaBoardListViewItem item = listViewAdapter.getItem(listViewAdapter.getCount() - 1);
                item.setBoardListItemPlayStateListener(this);
                item.setBoardListSurfaceViewListener(this);
            }

            Message handlerMsg = mGUIHandler.obtainMessage();
            handlerMsg.what = eGUI_HANDLER_CMD_LIST_UPDATE;
            handlerMsg.arg1 = position;
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

            case eBOARD_PLAY_STATE_COMPLETE:
                handlerMsg.what = eGUI_HANDLER_CMD_PLAYER_COMPLETE;
                break;

            default:
                break;
        }

        mGUIHandler.sendMessage(handlerMsg);
    }

    @Override
    public void onClickBoardListItemSurfaceView(eBOARD_PLAYER_VIEW_EVENT event, int position) {
        Message handlerMsg = mGUIHandler.obtainMessage();
        handlerMsg.arg1 = position;

        switch (event) {
            case eBOARD_PLAYER_VIEW_EVENT_PLAYER_VISIVLE_CHANGE:
                handlerMsg.what = eGUI_HANDLER_CMD_PLAYER_VISIBLE_CHANGE;
                break;

            case eBOARD_PLAYER_VIEW_EVENT_THUMBNAIL_UPDATE:
                handlerMsg.what = eGUI_HANDLER_CMD_THUMBNAIL_UPDATE;
                break;

            default:
                break;
        }

        mGUIHandler.sendMessage(handlerMsg);
    }
}
