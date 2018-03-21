package com.gazua.ddeokrok.coinman.board.url;

import com.gazua.ddeokrok.coinman.common.Logger;

import org.jsoup.nodes.Element;

import io.reactivex.Observable;

/**
 * Created by kimju on 2018-03-08.
 */

public class ClienServer extends BaseServer {
    private static final String TAG = "ClienServer";
    private static final String URI_CLIEN = "https://clien.net/service/board/cm_vcoin?&od=T31";

    @Override
    public String baseUrl() {
        return URI_CLIEN;
    }

    @Override
    public String totalUrl() {
        return baseUrl() + TAG_AND + pageTag();
    }

    @Override
    public String listTag() {
        return "div.list_item.symph_row";
    }

    @Override
    public String pageTag() {
        Logger.d(TAG, "pageTag : " + currentPage);
        return "po=" + this.currentPage;
    }

    @Override
    public String categoryTag() {
        return null;
    }

    @Override
    public String parseTitle(Element elements) {
        return Observable.fromIterable(elements.select(".list_title .list_subject > span "))
                .filter(element -> element.hasAttr("data-role"))
                .singleElement()
                .doOnError(throwable -> Logger.e(TAG, "message : " + throwable.getMessage()))
                .map(Element::text)
                .blockingGet("");
    }

    @Override
    public String parseDate(Element elements) {
        return elements.select(".timestamp").text();
    }

    @Override
    public String parseHitsCount(Element elements) {
        return elements.select(".list_hit").text();
    }

    @Override
    public String parseReplyCount(Element elements) {
        return elements.select(".list_reply.reply_symph").text();
    }

    @Override
    public String parseLinkUrl(Element elements) {
        return "https://m.clien.net/" + elements.select("a.list_subject").attr("href");
    }

    @Override
    public String parseUserNickname(Element elements) {
        return elements.select(".nickname").text();
    }

    @Override
    public String parseUserImage(Element elements) {
        return elements.select(".nickname > img").attr("src");
    }
}
