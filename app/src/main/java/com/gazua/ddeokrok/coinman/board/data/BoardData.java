package com.gazua.ddeokrok.coinman.board.data;

import android.text.TextUtils;

/**
 * Created by kimju on 2018-02-22.
 */

public class BoardData {
    String title;
    String date;
    String count;
    String linkUrl;
    String userName;
    String userImage;

    public BoardData(String title, String date, String count, String linkUrl, String userName, String userImage) {
        this.title = title;
        this.date = date;
        this.count = TextUtils.isEmpty(count) ? "0" : count;
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

    public String getCount() {
        return count;
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

    public static BoardData asData(String title, String date, String count, String linkUrl, String userName, String userImage) {
        return new BoardData(title, date, count, linkUrl, userName, userImage);
    }

    @Override
    public String toString() {
        return "[ title : " + this.title + ", date : " + this.date + ", count : " + this.count + ", linkUrl : "
                                    + this.linkUrl + ", userName : " + this.userName + ", userimage : " + this.userImage + " ]";
    }
}
