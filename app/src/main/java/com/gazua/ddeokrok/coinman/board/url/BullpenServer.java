package com.gazua.ddeokrok.coinman.board.url;

import org.jsoup.nodes.Element;

/**
 * Created by kimju on 2018-03-08.
 */

public class BullpenServer extends BaseServer {
    private static final String URI_BULLPEN = "http://mlbpark.donga.com/mp/b.php?m=search&b=bullpen&query=%EC%BD%94%EC%9D%B8&select=sct";

    @Override
    public String baseUrl() {
        return URI_BULLPEN;
    }

    @Override
    public String totalUrl() {
        return null;
    }

    @Override
    public String listTag() {
        return null;
    }

    @Override
    public String pageTag() {
        return null;
    }

    @Override
    public String categoryTag() {
        return null;
    }

    @Override
    public String parseTitle(Element elements) {
        return null;
    }

    @Override
    public String parseDate(Element elements) {
        return null;
    }

    @Override
    public String parseHitsCount(Element elements) {
        return null;
    }

    @Override
    public String parseLinkUrl(Element elements) {
        return null;
    }

    @Override
    public String parseUserNickname(Element elements) {
        return null;
    }

    @Override
    public String parseUserImage(Element elements) {
        return null;
    }

    public static class Query {
        public static final String PAGE = "p";

        public static String page(String param) {
            return String.format("&%s=%s", PAGE, param);
        }
    }
}
