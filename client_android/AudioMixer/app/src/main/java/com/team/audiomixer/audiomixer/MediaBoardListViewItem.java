package com.team.audiomixer.audiomixer;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

/**
 * Created by Administrator on 2017-06-03.
 */

public class MediaBoardListViewItem {
    private int mPosition;
    private ImageView profileImage;
    private Button playBtn;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private TextView userID;
    private TextView title;
    private TextView content;
    private TextView contentInfo;
    private MediaPlayer mediaPlayer;
    private String strUserID;
    private String strTitle;
    private String strContent;
    private String strContentInfo;
    private String mMediaPlayerSource;
    private boolean mIsPrepared;
    private BoardListItemPlayStateListener mPlayStateListener;
    private BoardListSurfaceViewListener mSurfaceViewListener;

    public interface BoardListItemPlayStateListener {
        enum eBOARD_PLAY_STATE {
            eBOARD_PLAY_STATE_PREPARE,
            eBOARD_PLAY_STATE_PLAY,
            eBOARD_PLAY_STATE_PAUSE
        }

        void onBoardListItemPlayStateChanged(eBOARD_PLAY_STATE playState, int position);
    }

    public void setBoardListItemPlayStateListener(BoardListItemPlayStateListener listener)
    {
        mPlayStateListener = listener;
    }

    public interface BoardListSurfaceViewListener {
        void onClickBoardListItemSurfaceView(boolean isVisible, int position);
    }

    public void setBoardListSurfaceViewListener(BoardListSurfaceViewListener listener)
    {
        mSurfaceViewListener = listener;
    }

    public void setPosition(int pos) { mPosition = pos; }
    public int getPosition() { return mPosition; }
    public void setProfileImage(ImageView view) { profileImage = view; }
    public void setSurfaceHolder(SurfaceHolder holder) { surfaceHolder = holder; }
    public SurfaceHolder getSurfaceHolder() { return surfaceHolder; }
    public MediaPlayer getMediaPlayer() { return mediaPlayer; }
    public Button getPlayBtn() { return playBtn; }
    public void setPlayBtn(Button btn) {
        playBtn = btn;
        playBtn.setOnClickListener(mPlayBtnOnClickListener);
    }

    Button.OnClickListener mPlayBtnOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mIsPrepared == false) {
                try {
                    mediaPlayer.setDataSource(mMediaPlayerSource);
                    mPlayStateListener.onBoardListItemPlayStateChanged(BoardListItemPlayStateListener.eBOARD_PLAY_STATE.eBOARD_PLAY_STATE_PREPARE, mPosition);
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                            mPlayStateListener.onBoardListItemPlayStateChanged(BoardListItemPlayStateListener.eBOARD_PLAY_STATE.eBOARD_PLAY_STATE_PLAY, mPosition);
                        }
                    });
                    mediaPlayer.prepareAsync();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                mIsPrepared = true;
            }
            else {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    mPlayStateListener.onBoardListItemPlayStateChanged(BoardListItemPlayStateListener.eBOARD_PLAY_STATE.eBOARD_PLAY_STATE_PAUSE, mPosition);
                }
                else
                {
                    mediaPlayer.start();
                    mPlayStateListener.onBoardListItemPlayStateChanged(BoardListItemPlayStateListener.eBOARD_PLAY_STATE.eBOARD_PLAY_STATE_PLAY, mPosition);
                }
            }
        }
    };

    SurfaceView.OnClickListener mSurfaceViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(playBtn.getVisibility() == View.VISIBLE) {
                mSurfaceViewListener.onClickBoardListItemSurfaceView(true, mPosition);
            }
            else {
                mSurfaceViewListener.onClickBoardListItemSurfaceView(false, mPosition);
            }
        }
    };

    public void setMediaPlayer(MediaPlayer mp) {
        mediaPlayer = mp;
        mIsPrepared = false;

        /*try {
            mediaPlayer.setDataSource(mMediaPlayerSource);
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public void setSurfaceView(SurfaceView view) {
        surfaceView = view;
        surfaceView.setOnClickListener(mSurfaceViewOnClickListener);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(surfaceHolderListener);
    }

    public void setUserID(TextView userID) {
        this.title = userID;
    }
    public TextView getUserID() {
        return this.userID ;
    }
    public void setTitle(TextView title) {
        this.title = title;
    }
    public TextView getTitle() {
        return this.title ;
    }
    public void setContent(TextView content) {
        this.title = content;
    }
    public TextView getContent() {
        return this.content ;
    }
    public void setContentInfo(TextView info) { this.contentInfo = info; }
    public TextView getContentInfo() {
        return this.contentInfo ;
    }
    public void setmMediaPlayerSource(String source) { mMediaPlayerSource = source; }
    public String getmMediaPlayerSource() { return mMediaPlayerSource; }
    public void setUserIDText(String str) { strUserID = str; }
    public void setTitleText(String str) { strTitle = str; }
    public void setContentText(String str) { strContent = str; }
    public void setContentInfoText(String str) { strContentInfo = str; }
    public String getUserIDText() { return strUserID; }
    public String getTitleText() { return  strTitle; }
    public String getContentText() { return strContent; }
    public String getContentInfoText() { return strContentInfo; }

    private SurfaceHolder.Callback surfaceHolderListener = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            mediaPlayer.setDisplay(holder);
            Log.d("MediaBoard", "surfaceCreated !!!!");
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.d("MediaBoard", "surfaceDestroyed !!!!");
        }
    };
}
