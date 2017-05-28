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

    public void setText(String text) {
        this.text = text;
    }

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
}
