package com.team.audiomixer.audiomixer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MediaListViewAdapter  extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<MediaListViewItem> mMediaListViewItems = new ArrayList<MediaListViewItem>() ;
    private MediaListViewDeleteBtnClickListener deleteBtnListener;
    private MediaListViewSelectBtnClickListener selectBtnListener;

    public interface MediaListViewDeleteBtnClickListener {
        void onClickListenerMediaListViewDeleteBtn(int position);
    }

    public interface MediaListViewSelectBtnClickListener {
        void onClickListenerMediaListViewSelectBtn(int position);
    }

    public void setDeleteBtnListener(MediaListViewDeleteBtnClickListener listener)
    {
        deleteBtnListener = listener;
    }

    public void setSelectBtnListener(MediaListViewSelectBtnClickListener listener)
    {
        selectBtnListener = listener;
    }

    // MediaListViewAdapter 생성자
    public MediaListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return mMediaListViewItems.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.media_listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.mediaListViewTitle) ;
        Button deleteButton = (Button) convertView.findViewById(R.id.mediaListViewDeleteBtn);
        Button selectButton = (Button) convertView.findViewById(R.id.mediaListViewSelectBtn);

        // Data Set(mMediaListViewItems)에서 position에 위치한 데이터 참조 획득
        MediaListViewItem listViewItem = mMediaListViewItems.get(position);

        deleteButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("WritePost", "delete Button listener in adapter");
                mMediaListViewItems.remove(pos);
                notifyDataSetChanged();
                deleteBtnListener.onClickListenerMediaListViewDeleteBtn(pos);
            }
        });

        selectButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Log.d("WritePost", "select Button listener in adapter");
                selectBtnListener.onClickListenerMediaListViewSelectBtn(pos);
            }
        });

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getTitle());
        listViewItem.setSelectBtn(selectButton);

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return mMediaListViewItems.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title) {
        MediaListViewItem item = new MediaListViewItem();

        item.setTitle(title);
        mMediaListViewItems.add(item);
    }

    public void setSelectBtnText(int position, String text) {
        mMediaListViewItems.get(position).setSelectBtnText(text);
    }
}