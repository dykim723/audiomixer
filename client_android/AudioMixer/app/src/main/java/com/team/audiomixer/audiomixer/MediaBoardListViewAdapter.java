package com.team.audiomixer.audiomixer;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017-06-03.
 */

public class MediaBoardListViewAdapter extends BaseAdapter {
    private ArrayList<MediaBoardListViewItem> mMediaListViewItems = new ArrayList<MediaBoardListViewItem>() ;

    public void addItem(int boardNo, String title, String userID, String content, String date, String mediaPlayerSource) {
        MediaBoardListViewItem item = new MediaBoardListViewItem();

        item.setTitleText(title);
        item.setUserIDText(userID);
        item.setContentText(content);
        item.setDateText(date);
        item.setmMediaPlayerSource(mediaPlayerSource);
        item.setBoardNo(boardNo);

        mMediaListViewItems.add(item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.media_board_listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        SurfaceView surfaceView = (SurfaceView) convertView.findViewById(R.id.playerView);
        ImageView profileImage = (ImageView) convertView.findViewById(R.id.profileImage);
        Button imageButton = (Button) convertView.findViewById(R.id.playBtn);
        Button likeButton = (Button) convertView.findViewById(R.id.btnLike);
        Button replyButton = (Button) convertView.findViewById(R.id.btnReply);
        TextView userID = (TextView) convertView.findViewById(R.id.userID) ;
        TextView title = (TextView) convertView.findViewById(R.id.title) ;
        TextView content = (TextView) convertView.findViewById(R.id.content);
        TextView contentInfo = (TextView) convertView.findViewById(R.id.contentInfo);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        MediaPlayer mediaPlayer = new MediaPlayer();

        // Data Set(mMediaListViewItems)에서 position에 위치한 데이터 참조 획득
        MediaBoardListViewItem listViewItem = mMediaListViewItems.get(pos);

        // 아이템 내 각 위젯에 데이터 반영
        content.setText(listViewItem.getContentText());
        title.setText(listViewItem.getTitleText());
        userID.setText(listViewItem.getUserIDText());
        date.setText(listViewItem.getDateText());

        listViewItem.setPosition(pos);
        listViewItem.setContent(content);
        listViewItem.setTitle(title);
        listViewItem.setUserID(userID);
        listViewItem.setContentInfo(contentInfo);
        listViewItem.setProfileImage(profileImage);
        listViewItem.setSurfaceView(surfaceView);
        listViewItem.setMediaPlayer(mediaPlayer);
        listViewItem.setPlayBtn(imageButton);
        listViewItem.setLikeBtn(likeButton);
        listViewItem.setReplyBtn(replyButton);
        listViewItem.setDate(date);

        return convertView;
    }

    @Override
    public int getCount() {
        return mMediaListViewItems.size();
    }

    @Override
    public MediaBoardListViewItem getItem(int position) {
        return mMediaListViewItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
