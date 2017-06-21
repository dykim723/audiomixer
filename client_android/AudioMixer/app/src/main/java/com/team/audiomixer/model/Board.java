package com.team.audiomixer.model;

import java.util.Queue;

/**
 * Created by dykim on 2017-05-28.
 */

public class Board {
    private Queue<FileInfo> files;
    private Queue<FileInfo> images;
    private String text;
    private Queue<Tag> tags;
    private int likeCount;
    private String mTitle;
    private String mContent;
    private String mDate;
    private String mUserEmail;
    private String mFilePath;
    private String mFileType;
    private String mThumbnailPath;
    private int mWidth;
    private int mHeight;
    private int mBoardNo;

    public Board(String text) {
        this.text = text;
    }

    public Queue<FileInfo> getFiles() {
        return files;
    }
    public boolean setFile(FileInfo file)
    {
        return files.add(file);
    }
    public boolean setImage(FileInfo image)
    {
        return images.add(image);
    }
    public Queue<FileInfo> getImages() {
        return images;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) { this.text = text; }
    public Queue<Tag> getTags() {
        return tags;
    }
    public boolean setTags(Tag tag) {
        return tags.add(tag);
    }
    public int getLikeCount() {
        return likeCount;
    }
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
    public int like()
    {
        return ++this.likeCount;
    }

    public String getTitle() { return mTitle; }
    public String getContent() { return  mContent; }
    public String getDate() { return mDate; }
    public String getUserEmail() { return mUserEmail; }
    public String getFilePath() { return mFilePath; }
    public String getFileType() { return mFileType; }
    public String getThumbnailPath() { return mThumbnailPath; }
    public int getWidth() { return mWidth; }
    public int getHeight() { return mHeight; }
    public int getBoardNo() { return mBoardNo; }
    public void setTitle(String str) { mTitle = str; }
    public void setContent(String str) { mContent = str; }
    public void setDate(String str) { mDate = str; }
    public void setUserEmail(String str) { mUserEmail = str; }
    public void setFilePath(String str) { mFilePath = str; }
    public void setFileType(String str) { mFileType = str; }
    public void setThumbnailPath(String str) { mThumbnailPath = str; }
    public void setWidth(int number) { mWidth = number; }
    public void setHeight(int number) { mHeight = number; }
    public void setBoardNo(int number) { mBoardNo = number; }
}
