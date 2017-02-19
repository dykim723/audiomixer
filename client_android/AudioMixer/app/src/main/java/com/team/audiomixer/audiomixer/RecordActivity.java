package com.team.audiomixer.audiomixer;

import android.Manifest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class RecordActivity extends AppCompatActivity {
    private MediaRecorder mMediaRecorder;
    private MediaPlayer mMediaPlayer;
    private Button mButtonRecord;
    private TextView mTextViewDuration;
    private TextView mTextViewPlayTime;
    private TextView mTextViewRecordInfo;

    private TimerTask mTimerTack;
    private Timer mTimer;

    private int mPlayState;
    private int mReayTime;
    SimpleDateFormat mTimeFormat;
    private String mSaveDir;
    private String mSaveFileName;
    private String mPlayFileName;

    final int RECORD_STATE_STOP = 1;
    final int RECORD_STATE_START = 2;
    final int MSG_TIME_TASK = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mButtonRecord = (Button) findViewById(R.id.buttonStartAndStop);
        mTextViewDuration = (TextView) findViewById(R.id.textViewDuration);
        mTextViewPlayTime = (TextView) findViewById(R.id.textViewPlayTime);
        mTextViewRecordInfo = (TextView) findViewById(R.id.textViewRecordInfo);
        mMediaRecorder = null;
        mMediaPlayer = null;
        mTimer = null;

        mPlayState = RECORD_STATE_STOP;
        mReayTime = 3;
        mTimeFormat = new SimpleDateFormat("mm:ss");
        mSaveFileName = "testRecordFile.mp3";
        mPlayFileName = "combined.mp3";
        mSaveDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC) + "/";

        mButtonRecord.setOnClickListener(mBtnRecordOnClickListener);
        String [] permissionStr = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO};
        ActivityCompat.requestPermissions(this, permissionStr, 1);
    }

    public void setTimerTask() {
        mTimerTack = new TimerTask() {
            @Override
            public void run() {
                Message handlerMsg = mHandler.obtainMessage();
                handlerMsg.what = MSG_TIME_TASK;
                mHandler.sendMessage(handlerMsg);
                Log.d("RecordActivity", "Timer task run");
            }
        };
    }

    final Handler mHandler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what)
            {
                case MSG_TIME_TASK:
                    if(mReayTime >= 1) {
                        mTextViewRecordInfo.setText("" + mReayTime);
                        mReayTime--;
                    }
                    else if(mPlayState == RECORD_STATE_STOP){  // Start Music Play, Record
                        mPlayState = RECORD_STATE_START;
                        mTextViewRecordInfo.setText("녹음중");
                        mButtonRecord.setEnabled(true);

                        mTimer.cancel();
                        mTimer = null;

                        startRecord();
                        startMusicStream();

                        mTimer = new Timer();
                        setTimerTask();
                        mTimer.schedule(mTimerTack, 0, 500);
                    }
                    else if (mPlayState == RECORD_STATE_START) {
                        mTextViewPlayTime.setText(mTimeFormat.format(mMediaPlayer.getCurrentPosition()));
                    }
                    break;

                default:
                    break;
            }
        }
    };

    MediaPlayer.OnPreparedListener mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            Log.e("RecordActivity", "START Music");
            mTimer = new Timer();
            setTimerTask();
            mTimer.schedule(mTimerTack, 0, 1000);
            mTextViewDuration.setText(mTimeFormat.format(mMediaPlayer.getDuration()));
        }
    };

    MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.e("RecordActivity", "Music Play Complete");
            mPlayState = RECORD_STATE_STOP;
            mButtonRecord.setText("Record START");
            mTextViewDuration.setText(mTimeFormat.format(0));
            mTextViewPlayTime.setText(mTimeFormat.format(0));
            mTextViewRecordInfo.setText("준비");
            mTimer.cancel();
            mTimer = null;
            mReayTime = 3;
            stopMusicStream();
            stopRecord();
        }
    };

    Button.OnClickListener mBtnRecordOnClickListener = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            Log.d("RecordActivity", "Record button clicked");

            if(mPlayState == RECORD_STATE_STOP)
            {
                mButtonRecord.setText("Record STOP");
                mButtonRecord.setEnabled(false);
                prepareMusicStream();
            }
            else
            {
                mPlayState = RECORD_STATE_STOP;
                mButtonRecord.setText("Record START");
                mTextViewDuration.setText(mTimeFormat.format(0));
                mTextViewPlayTime.setText(mTimeFormat.format(0));
                mTextViewRecordInfo.setText("준비");
                mTimer.cancel();
                mTimer = null;
                mReayTime = 3;
                stopMusicStream();
                stopRecord();
            }
        }
    };

    public void startMusicStream() {
        mMediaPlayer.start();
    }

    public void prepareMusicStream() {
        try {

            if(mMediaPlayer != null){
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource("http://192.168.11.105:5000/static/" + mPlayFileName);
            mMediaPlayer.prepareAsync();
            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        }
        catch (Exception e) {
            Log.e("RecordActivity", e.getMessage());
        }
    }

    public void stopMusicStream() {
        if(mMediaPlayer != null){
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        Log.e("RecordActivity", "STOP Music");
    }

    public void startRecord() {
        try
        {
            if(mMediaRecorder != null){
                mMediaRecorder.stop();
                mMediaRecorder.release();
                mMediaRecorder = null;
            }

            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
            mMediaRecorder.setOutputFile(mSaveDir +  mSaveFileName);
            mMediaRecorder.prepare();
            mMediaRecorder.start();

            Toast.makeText(getApplicationContext(), "Start Record", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {
            Log.d("RecordActivity", "Record err: " + e.getMessage());
        }
    }

    public void stopRecord() {
        if(mMediaRecorder != null) {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }

        Toast.makeText(getApplicationContext(), "Stop Record File save success: " + mSaveDir + mSaveFileName, Toast.LENGTH_SHORT).show();
        Log.d("RecordActivity", "Record result: " + mSaveDir + mSaveFileName);
    }
}
