package com.gazua.ddeokrok.coinman.board.url;

import com.gazua.ddeokrok.coinman.common.Logger;

import org.jsoup.nodes.Element;

/**
 * Created by kimju on 2018-03-08.
 */

public class BullpenServer extends BaseServer {
    private static final String TAG = "BullpenServer";
    private static final String URI_BULLPEN = "http://mlbpark.donga.com/mp/b.php?m=search&b=bullpen&query=%EC%BD%94%EC%9D%B8&select=sct";

    public BullpenServer() {
        super(UrlBuilder.TARGET_SERVER_BULLPEN);
    }

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
        return "div.contents > div.left_cont > div.tbl_box > table.tbl_type01 > tbody > tr";
    }

    @Override
    public String bodyContentsTag() {
        return "div.contents > div.left_cont";
    }

    @Override
    public String bodyContentsTextTag() {
        return "div.view_context > div.ar_txt";
    }

    @Override
    public String pageTag() {
        Logger.d(TAG, "pageTag : " + currentPage);
        return "p=" + (this.currentPage * 30 + 1);
    }

    @Override
    public String categoryTag() {
        return null;
    }

    @Override
    public String parseTitle(Element element) {
        return element.select(".t_left > a").attr("title");
    }

    @Override
    public String parseDate(Element element) {
        return element.select(".date").text();
    }

    @Override
    public String parseHitsCount(Element element) {
        return element.select(".viewV").text();
    }

    @Override
    public String parseReplyCount(Element element) {
        return element.select(".replycnt").text();
    }

    @Override
    public String parseLinkUrl(Element element) {
        return element.select(".t_left > a").attr("href");
    }

    @Override
    public String parseUserNickname(Element element) {
        return element.select(".nick").text();
    }

    @Override
    public String parseUserImage(Element element) {
        return element.select("img").attr("src");
    }
}
