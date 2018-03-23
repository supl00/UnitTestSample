package com.gazua.ddeokrok.coinman.board.data;

import android.text.TextUtils;

/**
 * Created by kimju on 2018-02-22.
 */

public class BoardData {
    int boardNameResId;
    String title;
    String date;
    String viewCount;
    String commentCount;
    String linkUrl;
    String userName;
    String userImage;
    String body;

    public BoardData(int boardNameResId, String title, String date, String viewCount, String commentCount, String linkUrl, String userName, String userImage) {
        this.boardNameResId = boardNameResId;
        this.title = title;
        this.date = date;
        this.viewCount = TextUtils.isEmpty(viewCount) ? "0" : viewCount;
        this.commentCount = TextUtils.isEmpty(commentCount) ? "0" : commentCount;
        this.linkUrl = linkUrl;
        this.userName = userName;
        this.userImage = userImage;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getViewCount() {
        return viewCount;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public int getBoardNameResId() {
        return boardNameResId;
    }

    public static BoardData asData(int boardNameResId, String title, String date, String viewCount, String commentCount, String linkUrl, String userName, String userImage) {
        return new BoardData(boardNameResId, title, date, viewCount, commentCount, linkUrl, userName, userImage);
    }

    @Override
    public String toString() {
        return "[ title : " + this.title + ", date : " + this.date + ", viewCount : " + this.viewCount + ", commentCount : " + this.commentCount + ", linkUrl : "
                                    + this.linkUrl + ", userName : " + this.userName + ", userimage : " + this.userImage + " ]";
    }

    public String getBody() {
        return body;
    }
}
