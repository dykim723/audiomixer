package com.team.audiomixer.audiomixer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MediaBoardActivity extends AppCompatActivity {
    private ListView listView;
    private MediaBoardListViewAdapter listViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_board);

        listView = (ListView) findViewById(R.id.mediaBoardListView);
        listViewAdapter = new MediaBoardListViewAdapter();
        listView.setAdapter(listViewAdapter);

        for(int i = 0; i < 10; i++) {
            listViewAdapter.addItem("title " + i, "userID " + i, "content " + i, "contentInfo " + i, "http://192.168.11.108:5000/EXID - 낮보다는 밤.mp4");
        }
    }
}
