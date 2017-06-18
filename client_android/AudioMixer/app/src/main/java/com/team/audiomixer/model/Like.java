package com.team.audiomixer.model;

/**
 * Created by dykim on 2017-05-28.
 */

public class Like {
    private User user = null;
    private Board board = null;
    private String date = "";

    public Like(User user, Board board, String date) {
        this.user = user;
        this.board = board;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
