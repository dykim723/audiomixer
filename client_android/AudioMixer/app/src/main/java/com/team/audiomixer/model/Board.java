package com.team.audiomixer.model;

import java.util.Queue;

/**
 * Created by dykim on 2017-05-28.
 */

public class Board {
    private int BoardNo;
    private Queue<FileInfo> files;
    private Queue<FileInfo> images;
    private String text;
    private Queue<Tag> tags;
    private int likeCount;
    private String Title;
    private String Content;
    private String Date;
    private String UserInfo_Email;
    private String FilePath;


    public Board(int boardNo){
        this.BoardNo = boardNo;
    }

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

    public String getTitle() { return Title; }
    public String getContent() { return Content; }
    public String getDate() { return Date; }
    public String getUserEmail() { return UserInfo_Email; }
    public String getFilePath() { return FilePath; }
    public int getBoardNo() { return BoardNo; }
    public void setTitle(String str) { Title = str; }
    public void setContent(String str) { Content = str; }
    public void setDate(String str) { Date = str; }
    public void setUserEmail(String str) { UserInfo_Email = str; }
    public void setFilePath(String str) { FilePath = str; }
    public void setBoardNo(int number) { BoardNo = number; }
}
