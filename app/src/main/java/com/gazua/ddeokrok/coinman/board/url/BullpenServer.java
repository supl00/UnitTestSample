package com.gazua.ddeokrok.coinman.board.url;

import com.gazua.ddeokrok.coinman.common.Logger;

import org.jsoup.nodes.Element;

import io.reactivex.Observable;

/**
 * Created by kimju on 2018-03-08.
 */

public class BullpenServer extends BaseServer {
    private static final String TAG = "BullpenServer";
    private static final String URI_BULLPEN = "http://mlbpark.donga.com/mp/b.php?m=search&b=bullpen&query=%EC%BD%94%EC%9D%B8&select=sct";

    @Override
    public String baseUrl() {
        return URI_BULLPEN;
    }

    @Override
    public String totalUrl() {
        return baseUrl() + TAG_AND + pageTag();
    }

    @Override
    public String listTag() {
        return "div.contents > ul.lists > li.items";
    }

    @Override
    public String pageTag() {
        return "p=" + (this.currentPage * 30 + 1);
    }

    @Override
    public String categoryTag() {
        return null;
    }

    @Override
    public String parseTitle(Element elements) {
        return elements.select(".title ").text();
    }

    @Override
    public String parseDate(Element elements) {
        return elements.select(".date").html();
    }

    @Override
    public String parseHitsCount(Element elements) {
//        return elements.select(".viewV").html();
        return "0";
    }

    @Override
    public String parseReplyCount(Element elements) {
        return elements.select(".replycnt").text();
    }

    @Override
    public String parseLinkUrl(Element elements) {
        return elements.select(".title > a").attr("href");
    }

    @Override
    public String parseUserNickname(Element elements) {
        return elements.select(".nick").text();
    }

    @Override
    public String parseUserImage(Element elements) {
        return elements.select("img").attr("src");
    }
}
