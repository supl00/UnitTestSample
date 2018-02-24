package com.gazua.ddeokrok.coinman.network.page;

public class Page {
    public static final Page EMPTY_PAGE = new Page("");

    String content;

    public Page(String paramString) {
        this.content = paramString;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String paramString) {
        this.content = paramString;
    }
}